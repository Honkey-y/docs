-- PostgreSQL 只读取证；不会创建索引、清缓存或重置统计。
-- 账户库、系统库分离时分别连接执行。目录查询不依赖 account/system schema 名。
-- 统计行数为估计；部分会话信息需相应监控权限。

SELECT current_database() AS database_name, current_schema() AS current_schema,
       current_setting('search_path') AS search_path, version();

-- 1. 实际索引及有效状态：看目标表与列，不能只看索引名是否存在。
SELECT ns.nspname AS schema_name, t.relname AS table_name,
       ix.relname AS index_name, i.indisprimary, i.indisunique,
       i.indisvalid, i.indisready, pg_get_indexdef(i.indexrelid) AS definition
FROM pg_index i
JOIN pg_class t ON t.oid = i.indrelid
JOIN pg_class ix ON ix.oid = i.indexrelid
JOIN pg_namespace ns ON ns.oid = t.relnamespace
WHERE t.relname IN (
  'user_info','sp_user_info','user_enterprise_pk','sys_role','sys_user_role_pk',
  'user_group','user_group_user_pk','user_group_parent_pk',
  'user_group_space','user_group_role','role_info','role_permission',
  'permission_info','sys_space_info','create_element_info',
  'create_element_info_permission')
ORDER BY ns.nspname, t.relname, ix.relname;

-- 2. 把无索引的目标表也展示出来。
SELECT s.schemaname, s.relname, s.n_live_tup, s.n_dead_tup,
       s.seq_scan, s.seq_tup_read, s.idx_scan, s.idx_tup_fetch,
       s.last_analyze, s.last_autoanalyze, s.last_autovacuum,
       pg_size_pretty(pg_total_relation_size(s.relid)) AS total_size
FROM pg_stat_all_tables s
WHERE s.schemaname NOT IN ('pg_catalog','information_schema')
  AND s.relname IN (
  'user_info','sp_user_info','user_enterprise_pk','sys_role','sys_user_role_pk',
  'user_group','user_group_user_pk','user_group_parent_pk',
  'user_group_space','user_group_role','role_info','role_permission',
  'permission_info','sys_space_info','create_element_info',
  'create_element_info_permission')
ORDER BY s.schemaname, s.relname;
-- seq_scan 为累计值，不是“本次压测慢查询次数”。比较同一统计周期的前后差值。

-- 3. 旧通用索引名到底属于哪个表？
SELECT ns.nspname AS schema_name, t.relname AS table_name,
       ix.relname AS index_name, pg_get_indexdef(i.indexrelid) AS definition
FROM pg_index i
JOIN pg_class t ON t.oid = i.indrelid
JOIN pg_class ix ON ix.oid = i.indexrelid
JOIN pg_namespace ns ON ns.oid = t.relnamespace
WHERE ix.relname IN ('index_pr_user_id','index_pr_enterprise_id',
                     'index_pr_enterprise_code','index_pr_role_id',
                     'index_pr_user_id_urp','index_pr_user_id_etp',
                     'index_pr_user_id_gup','index_pr_status_unf')
ORDER BY ns.nspname, ix.relname;

-- 4. Flyway 表位置；找到后在对应 schema 执行下方的可选查询。
SELECT table_schema, table_name FROM information_schema.tables
WHERE table_name LIKE '%flyway%history%'
ORDER BY table_schema, table_name;
-- 替换 <schema> 后单独执行：
-- SELECT installed_rank, version, description, script, installed_on, success
-- FROM <schema>.flyway_schema_history
-- WHERE script LIKE '%same_index_to_different%'
-- ORDER BY installed_rank;

-- 5. 压测当下的会话与阻塞。这里只反映当前数据库的数据库连接，
-- JDBC 池内还没拿到连接的请求不会出现在这里。
SELECT pid, application_name, client_addr, state,
       wait_event_type, wait_event,
       clock_timestamp() - query_start AS query_age,
       clock_timestamp() - xact_start AS transaction_age,
       pg_blocking_pids(pid) AS blocking_pids,
       left(query, 1200) AS query_sample
FROM pg_stat_activity
WHERE datname = current_database() AND pid <> pg_backend_pid()
  AND state IS DISTINCT FROM 'idle'
ORDER BY query_start NULLS LAST;

SELECT application_name, state, wait_event_type, wait_event, count(*) AS sessions
FROM pg_stat_activity
WHERE datname = current_database()
GROUP BY application_name, state, wait_event_type, wait_event
ORDER BY sessions DESC;

-- 6. 统计周期与数据库级累计量，压测前后各采样一次。
SELECT datname, numbackends, xact_commit, xact_rollback, blks_read, blks_hit,
       temp_files, temp_bytes, deadlocks, stats_reset
FROM pg_stat_database WHERE datname = current_database();

-- 7. 以下仅为单条查询的执行计划模板，替换实际 schema/参数后单独执行。
-- 默认不运行 ANALYZE；EXPLAIN ANALYZE 会真实执行 SELECT。
-- 可先 EXPLAIN (COSTS, VERBOSE)，再在可控窗口用 (ANALYZE, BUFFERS)。
-- 用客户的实际 ORM SQL 与真实组列表补齐计划，尤其注意数据倾斜。
--
-- EXPLAIN (ANALYZE, BUFFERS)
-- SELECT DISTINCT uep.enterprise_id, uep.enterprise_code, ug.space_id
-- FROM <account_schema>.user_enterprise_pk uep
-- LEFT JOIN <account_schema>.user_group ug ON uep.enterprise_id=ug.enterprise_id
-- WHERE uep.user_id='<真实用户>' AND uep.enterprise_code='<真实企业code>';
--
-- EXPLAIN (ANALYZE, BUFFERS)
-- SELECT * FROM <account_schema>.user_group_parent_pk
-- WHERE enterprise_id='<真实企业ID>';
--
-- EXPLAIN (ANALYZE, BUFFERS)
-- SELECT * FROM <account_schema>.user_group
-- WHERE enterprise_id='<真实企业ID>' AND status='<实际ENABLE键值>';
--
-- EXPLAIN (ANALYZE, BUFFERS)
-- SELECT ugr.type,ugr.id,ugr.role_info_id,ri.code,ugr.app,ugr.space
-- FROM <system_schema>.user_group_role ugr
-- JOIN <system_schema>.role_info ri ON ugr.role_info_id=ri.id
-- WHERE ugr.space='<真实空间>'
-- AND ((ugr.type='USER' AND ugr.id='<真实用户>')
--   OR (ugr.type='GROUP' AND ugr.id IN ('<真实组1>','<真实组2>')));

-- 8. 可选：若安装了 pg_stat_statements，先查扩展 schema，再按该扩展版本
-- 的字段查看 calls/total_exec_time/mean_exec_time/rows/shared_blks_read 等。
SELECT e.extname, ns.nspname AS extension_schema, e.extversion
FROM pg_extension e JOIN pg_namespace ns ON ns.oid=e.extnamespace
WHERE e.extname='pg_stat_statements';
-- 不重置统计。查询文本可能包含业务参数，交付日志时按现场要求脱敏。
