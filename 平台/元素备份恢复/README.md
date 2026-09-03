# 元素备份与恢复

> 状态：草稿
> 最后更新：2026-08-07
> 适用范围：DeepFOS 平台元素备份与恢复（`system-server` / `view-main-v2`）
> 关联文档：
> - [代码执行计划/2026-08-06-前端元素备份恢复页面.md](./代码执行计划/2026-08-06-前端元素备份恢复页面.md)
> - [代码执行计划/2026-08-07-恢复预览按哈希差异加载优化.md](./代码执行计划/2026-08-07-恢复预览按哈希差异加载优化.md)
> - [代码执行计划/2026-08-07-全量恢复功能扩展.md](./代码执行计划/2026-08-07-全量恢复功能扩展.md)
> - [代码执行计划/2026-08-07-恢复重建与删除组件通知.md](./代码执行计划/2026-08-07-恢复重建与删除组件通知.md)
> - 后端研发计划：`deepfos-platform/ai-docs/plan/element-backup-mvp-validation-plan.md`
> - 后端恢复规则：`deepfos-platform/ai-docs/plan/element-partial-restore-rules.md`

## 范围

提供当前空间下按 App 的手动全量/部分备份、备份列表、备份树勾选恢复，以及从 FULL 快照对整个 App 配置做精确全量恢复的规划与实现依据。

## 当前状态

| 层 | 状态 |
| --- | --- |
| 后端部分恢复（`system-server` `elementbackup`） | 已实现，当前开发分支 `feature_app_snapshot` |
| 后端全量恢复 | 已实现（见全量恢复功能扩展代码执行计划） |
| 恢复重建/删除组件通知 | 已实现（见恢复重建与删除组件通知代码执行计划） |
| 前端页面与 API 封装 | 待实现（见代码执行计划） |
| 产品权限码 / 当前元素树接口归属 | 待确认 |

## 恢复执行响应与运维日志（组件通知）

`POST .../restore` 成功提交数据后，组件通知失败**不**把整次恢复标为失败：

| 响应字段 | 含义 |
| --- | --- |
| `status` | 数据恢复结果；通知失败时仍为 `SUCCESS` |
| `notificationFailedCount` | 缓存失效发布或 REAR 通知失败次数 |
| `notificationWarnings` | 可定位的告警文案（含 elementId / 通知类型） |

固定顺序：`REMOVE/FRONT`（事务前，失败则不执行恢复）→ App 事务 commit → 缓存失效 → `REMOVE/REAR` → `IMPORT/REAR(CREATE)`。  
仅 `ELEMENT` 的 `RECREATE` / `DELETE` 发通知；`UPDATE` / `NOOP` / `BLOCKED` 与目录动作不发。

汇总日志关键字示例：`restore done`、`restore-notice FRONT`、`restore-notice REAR`（含 mode、snapshotId、planDigest 前缀与各类通知计数）。

## 文档索引

- `规划/`：预留功能/接口规范（可从后端 ai-docs 同步摘录）
- `代码执行计划/`：前端落地、恢复预览性能优化、整个 App 全量恢复和恢复动作组件通知方案
