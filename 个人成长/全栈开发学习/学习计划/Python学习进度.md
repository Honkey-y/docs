# Python 学习进度

> 状态：已确认
> 最后更新：2026-09-02
> 适用范围：`/Users/yang/PycharmProjects/WelcomeScreen/test`
> 关联文档：[Python 学习路线](../规划/Python学习路线.md)、[Python 学习执行计划](Python学习执行计划.md)、[AI 带练会话约定](AI带练会话约定.md)

## 当前快照

| 项目 | 当前值 |
| --- | --- |
| 当前单元 | `PY-12` 综合项目：工具目录服务 |
| 单元状态 | 已完成（含用户豁免） |
| 已通过单元 | 8 / 13 |
| 已完成单元 | 13 / 13（含 5 个用户豁免单元） |
| 用户豁免单元 | `PY-07`、`PY-08`、`PY-09`、`PY-10`、`PY-12` |
| 执行模式 | 六周速成 |
| 目标投入 | 约 48–60 小时 |
| 默认学习强度 | 每周 8–10 小时 |
| 练习项目 | `/Users/yang/PycharmProjects/WelcomeScreen/test` |
| 学习基线 | Python 3.12 |
| 当前解释器 | `/Users/yang/PycharmProjects/WelcomeScreen/.venv/bin/python`（Python 3.12.0，PyCharm 当前解释器） |
| 下一次会话 | 按需回补用户豁免项、清理两条非法学习数据，或明确启动下一阶段学习路线 |

## 起点证据

### 已接触但尚未通过门禁

- 函数定义、参数、返回值和 `__main__` 入口。
- `str`、`list`、`dict` 和元组解包。
- 基础类型标注，如 `str`、`int`、`tuple[str, int]`。
- `dataclass`、实例属性和实例方法。

### 初始问题及处理结果

- `03.py` 曾因把 `list[dict]` 当成单个 `dict` 访问而抛出 `TypeError`；现已改为遍历字典元素并按名称查找，存在与不存在案例均有断言。
- `04.py` 曾使用 `is` 比较字符串；现已使用 `==` 比较值，并验证启用、禁用和 `None` 三条路径。
- `tool`、`_findTool` 和 `newTools` 已分别整理为 `Tool`、`_find_tool` 和 `new_tools`。
- PyCharm 当前使用父项目 `.venv` 的 Python 3.12.0，系统 `python3` 也由 `pyenv` 选择为 3.12.0；`test/.venv` 中仍保留未使用的 Python 3.9.6 环境，避免误激活即可。

## 单元看板

状态使用“未开始 / 进行中 / 已通过 / 已完成（含用户豁免） / 待复习”。“已通过”必须有门禁评分和验证证据；用户明确豁免的未验证项只能记为“已完成（含用户豁免）”。

| 单元 | 主题 | 状态 | 完成日期 | 证据 |
| --- | --- | --- | --- | --- |
| `PY-00` | 基线诊断与环境统一 | 已通过 | 2026-08-27 | Python 3.12；`01.py`～`04.py` 全部退出码 0；门禁 6/8 |
| `PY-01` | 对象模型、基础类型与可变性 | 已通过 | 2026-08-28 | 别名/浅复制、定向复制和字符串清洗练习均可运行；门禁 6/8 |
| `PY-02` | 容器、控制流与数据变换 | 已通过 | 2026-08-28 | 筛选、缺字段、顺序去重和稳定排序练习可运行；门禁 6/8 |
| `PY-03` | 函数、参数、作用域与类型标注 | 已通过 | 2026-08-31 | 默认参数、函数契约、作用域、参数收集和类型标注练习可运行；门禁 6/8 |
| `PY-04` | Pythonic 迭代与生成器 | 已通过 | 2026-08-31 | 生成器消费、`yield`、`enumerate`、`zip` 和推导式练习可运行；门禁 6/8 |
| `PY-05` | 异常、资源与常用标准库 | 已通过 | 2026-08-31 | 文件/JSON 读写、异常分类、字段校验和资源管理可运行；门禁 6/8 |
| `PY-06` | 类、数据模型、模块与包 | 已通过 | 2026-08-31 | `Tool`/`ToolRegistry` 组合、包拆分、公开 API 与无副作用导入均可运行；门禁 6/8 |
| `PY-07` | 工程化、测试与可维护性 | 已完成（含用户豁免） | 2026-09-01 | pytest/TDD/`src` editable 已验证；参数化、fixture、Ruff、mypy 由用户豁免，未通过门禁 |
| `PY-08` | 命令行、配置与 HTTP 客户端 | 已完成（含用户豁免） | 2026-09-01 | httpx、Client、503/超时测试和幂等性已验证；CLI、非法 JSON 与独立响应变式由用户豁免 |
| `PY-09` | SQL、事务与持久化 | 已完成（含用户豁免） | 2026-09-01 | MySQL/PyMySQL/SQLAlchemy 查询、写入、主键回填、唯一约束和 rollback 已验证；现代映射/模型校验/精确异常由用户豁免 |
| `PY-10` | FastAPI Web API | 已完成（含用户豁免） | 2026-09-01 | GET/POST、Session dependency、ORM 响应和 404/409 已实现；Pydantic Field/422、API 隔离测试、列表/启停与 lifespan 由用户豁免 |
| `PY-11` | `asyncio` 与并发选择 | 已通过 | 2026-09-02 | gather、单任务超时、部分结果、Semaphore、Task 取消清理、阻塞与 to_thread 对比均已实际运行；门禁 6/8 |
| `PY-12` | 综合项目：工具目录服务 | 已完成（含用户豁免） | 2026-09-02 | README、可重复脚本、FastAPI/MySQL/异步综合链路和最终验收已完成；独立综合实现与完整测试门禁由用户豁免 |

## 下一次会话任务卡

### 目标

Python 主路线已按当前完成口径结束。后续优先处理真实使用中暴露的豁免风险，或另行确认下一阶段目标。

### 步骤

1. 若继续维护当前项目，优先补请求范围校验、API/数据库隔离测试和 Engine lifespan。
2. 若恢复工程门禁，清理 Ruff/mypy 诊断并补 fixture、参数化与异步测试。
3. 若清理数据库，仅在用户明确授权后删除 `id=27`、`id=29` 两条 `risk=0` 学习数据。
4. 若开启新路线，根据新目标单独规划，不把当前豁免项改写成已通过。

### 本次验收

- [x] 现有包、数据库、API 和异步功能可以从文档化命令重复运行。
- [x] 综合功能覆盖已有正常、失败和边界路径，真实凭据未写入 README 或仓库文件。
- [x] 异步健康检查保留部分成功、限制并发并正确处理超时。
- [x] 最终报告区分“已通过”与“已完成（含用户豁免）”，明确仍需回补的能力。

## 当前薄弱点

- 测试设计需要继续加强：一个断言应尽量只验证一个行为，避免因其他条件失败而产生假阳性。
- 对嵌套可变对象仍需先画出层级，再决定在哪一层复制；`PY-01` 的共享列表修复使用过完整实现提示，后续继续用新变式巩固。
- 组合多个筛选条件时曾把风险上下界方向写反；继续先用自然语言写出“保留/跳过”规则，再翻译为布尔表达式。
- 顺序去重和排序曾依赖完整实现或局部提示；已能使用 `set`、`sorted(key=...)` 和简单 `lambda`，后续需要在新任务中独立迁移。
- `PY-03` 中曾把 `enabled_only=False` 误写为“只匹配禁用工具”；继续把布尔参数理解为是否启用额外限制，而不是与字段直接比较。
- 类型标注不会在运行时自动校验或转换；部分练习签名仍较宽泛，后续工程化阶段用静态检查继续收紧。
- 新出现的 Python 专属函数、语法或标准库应先说明用途、工作方式和 Java 对照，再安排练习；避免在学习者尚不认识 API 时直接出题。
- `PY-04` 中曾混淆生成器的输入来源和单次 `next()` 的返回形状；已能解释生成器保存执行位置、耗尽后不可复用，而列表每次遍历会创建新迭代器。
- `PY-05` 中曾把加载错误转换为空列表，导致合法空配置与失败不可区分；已建立“底层保留异常、应用边界分类展示”的契约。
- 外部 JSON 校验必须先判断类型再比较范围；风险校验使用过完整局部实现，后续继续通过不同字段迁移。Python 日志 API 的专门练习因已有 Java 日志经验而跳过，留到 `PY-07` 工程化配置时验证。
- `save_tools()` 当前未传 `ensure_ascii=False`，纯英文数据不受影响；需要写入中文时补充该参数。
- `PY-06` 对组合的直觉正确，但仍把 Registry 表述成抽象工具或工厂；后续需要继续区分“持有并管理既有对象”与“负责创建对象”。继承的主要问题是错误的 is-a 关系和暴露不受控接口，并非对象数量。
- `PY-07` 已完成 pytest、失败测试驱动修复和 `src/` editable 安装；参数化、fixture、Ruff、mypy 由用户豁免，状态为“已完成（含用户豁免）”而非“已通过”。
- `PY-08` 已掌握环境变量、显式超时、Client 生命周期、HTTP 失败与幂等边界；argparse、非法 JSON 和独立响应变式由用户豁免，状态为“已完成（含用户豁免）”而非“已通过”。
- `PY-09` 已完成 MySQL 学习表、PyMySQL、参数绑定、唯一约束、主键回填、SQLAlchemy Engine/Session 和 rollback；dataclass `__post_init__`、`Mapped`/`mapped_column`、精确列 metadata 与精确 `IntegrityError` 捕获由用户豁免。
- `PY-10` 已完成 FastAPI GET/POST、请求级 Session、ORM 到响应模型转换和 404/409；Pydantic `Field` 约束、422 证据、完整 API 测试、列表/启停接口和 Engine lifespan 由用户豁免。数据库仍保留两条由未校验 POST 写入的 `risk=0` 学习记录，未经删除授权未清理。
- `PY-11` 已实际验证 await/gather、单任务 timeout、Semaphore、Task cancel/finally、`time.sleep` 阻塞事件循环和 `asyncio.to_thread`；演示代码仍混合多段实验且缺少自动化异步测试，但核心执行模型已通过运行与解释验证。
- `PY-12` 初版健康编排在循环内逐个 await，实际仍为串行；异步 endpoint 还直接调用同步 Session 并回传原始异常。AI 按用户请求改为 gather 并发和固定模拟目标，移除同步数据库依赖及内部异常泄露。`src.tool_registry` 错误导入已统一为正式 `tool_registry` 包名。
- 全路线已完成 13 / 13，但只有 8 个单元达到门禁“已通过”；`PY-07`～`PY-10`、`PY-12` 含用户豁免。最终项目可运行不等于生产就绪，README 已列出限制。
- 当前名称清洗会把连续内部空格转换为连续下划线；这不阻塞当前约定，若需求改为压缩空白再使用 `split`/`join`。
- 需要继续巩固 `pyenv`、虚拟环境与依赖管理工具的职责边界；当前未使用的 `test/.venv` 为 Python 3.9.6，注意不要误激活。

## 路线完成总结

- 完成日期：2026-09-02。
- 已完成：13 / 13。
- 已通过门禁：8 / 13。
- 已完成（含用户豁免）：5 / 13，分别为 `PY-07`、`PY-08`、`PY-09`、`PY-10`、`PY-12`。
- 可运行产物：editable `tool_registry` 包、MySQL 学习表、FastAPI GET/POST/健康接口、PyMySQL/SQLAlchemy/httpx/asyncio 示例和 README。
- 最终自动化状态：pytest 5 passed，依赖检查通过，源码编译通过。
- 未清理外部状态：`afakmv_meta.py_learning_tools` 中 `id=27`、`id=29` 为 `risk=0` 的学习数据；删除需用户明确授权。
- 最终结论：当前 Python 路线按用户定义的含豁免完成口径已结束；不能把 5 个豁免单元描述为完整能力验证通过。

## 学习会话日志

### 2026-09-02 第 14 次：`PY-12` README 与最终验收（含用户豁免）

- 本次目标：生成不含秘密的项目运行说明，确保 README 中列出的入口可重复执行，并完成全路线最终验收。
- 实际完成：学习者明确现有 `config.local.toml` 足够且不需要额外模板，并要求 AI 直接生成 README。AI 创建根级 `README.md`，记录项目结构、Python/依赖安装、本地 TOML 配置格式、MySQL 表要求、FastAPI 启动与接口、测试命令、其他模块入口、安全边界和全部用户豁免。最终审计发现 `tool_registry.db` 每次运行会重复插入 `search_order` 并退出 1，`tool_registry.orm` 每次运行会故意重复插入并输出数据库错误；保留写入函数的同时将两个 `main()` 收敛为只读、可重复入口。
- 验证证据：`python -m compileall -q src` 成功；`python -m pip check` 输出 `No broken requirements found`；`python -m pytest -q` 输出 `5 passed`；错误 `src.tool_registry` 导入为 0，Tool 类型身份唯一；`python -m tool_registry.db` 输出 4 且退出 0，`python -m tool_registry.orm` 输出连接/查询结果且退出 0，HTTP 与 asyncio 模块均退出 0；TestClient 对 `/tools/1`、不存在工具和 `/health/tools` 分别返回 200、404、200，健康结果保留两条成功与一条 timeout；MCP 只读核对 `py_learning_tools` 仍为 4 行，最终验收未新增或删除数据库数据。
- 门禁评分：理解 2/2，独立实现 1/2，调试与测试 1/2，工程质量 1/2，总分 5/8；按用户规则记为“已完成（含用户豁免）”，不记为“已通过”。
- 仍需加强：综合健康功能初版的并发和 async/sync 边界由 AI 修复；README 由 AI 生成；自动化测试只有 5 条且未覆盖 API/async/数据库隔离；TestClient 有上游弃用警告；数据库保留两条 `risk=0` 数据；全部豁免项见 README 和当前薄弱点。
- 使用的最高提示等级：4。
- 下一次会话：按需回补任一豁免项、经明确授权清理非法学习数据，或启动新的学习路线。

### 2026-09-02 第 13 次：`PY-12` 异步健康接口集成（进行中）

- 本次目标：盘点工程并在低提示下把 timeout、部分失败和 Semaphore 健康检查接入 FastAPI，同时消除重复包身份。
- 实际完成：全量检查正式包与测试，发现 demo 和两份测试重新使用 `src.tool_registry`，运行时确认它与 editable 安装的 `tool_registry` 是两个不同包、两个不同 `Tool` 类。学习者完成 `check_tools_health` 和 `/health/tools` 初版，但编排在循环中逐个 await，仍为串行；async endpoint 直接调用同步 SQLAlchemy Session、使用随机延迟并将 `str(exc)` 暴露给客户端。学习者要求 AI 直接修复错误目录后，AI 同时做了与本次验收直接相关的最小语义修正：统一导入为 `tool_registry`，改用 `gather` 并发，健康 endpoint 移除同步数据库和随机数，使用固定模拟目标并保留部分超时结果。
- 验证证据：`rg` 未再发现 `src.tool_registry` 导入；`python -m compileall -q src` 成功；`python -m pytest -q` 输出 `5 passed`；运行时 `tool_registry.Tool is tool_registry.models.Tool` 为 `True`；TestClient 请求 `/health/tools` 返回 200，内容依次为 `search_order` 成功、`refund_order` timeout、`query_weather` 成功，总耗时约 0.212 秒。
- 门禁评分：本单元尚未最终评分，当前核心综合功能可运行，README 和最终验收仍待完成。
- 仍需加强：健康接口当前使用固定模拟目标而非数据库配置；没有独立的异步/API 自动化测试；TestClient 出现上游 `httpx` 迁移弃用警告；历史模块仍有已豁免的格式、类型和输入校验缺口。
- 使用的最高提示等级：4；学习者完成初版，AI 按明确要求直接修复并发和异步边界。
- 下一次会话：编写不含秘密的 README，记录安装、`config.local.toml`、MySQL 学习表、API/脚本/测试命令和已豁免风险，然后执行最终验收。

### 2026-09-02 第 12 次：`PY-11` `asyncio` 与并发选择

- 本次目标：建立协程、Task、事件循环、超时、取消、并发限制和同步阻塞桥接的真实执行模型。
- 实际完成：实现模拟工具健康检查并使用 `asyncio.gather` 并发执行，能够解释 `async def` 调用只产生 coroutine、`await` 既等待当前操作又向事件循环交权，gather 会调度并汇总多个 awaitable。为单个检查增加 `asyncio.timeout`，将预期超时转换为稳定失败结果并保留其他成功项。使用 `Semaphore` 限制并发，初次因任务数量/时长组合导致总耗时仍约 0.3 秒，经时间线分析后确认限流本身正确并按用户要求计为完成。实现 `create_task`、`cancel`、`CancelledError` 和 `finally` 清理；进一步理解 `create_task` 只把任务放入 ready 队列，当前协程必须在 await 点交权，`Event` 可替代猜测式 sleep 进行启动握手。最后对比 `time.sleep` 与 `asyncio.to_thread`：三次阻塞调用约 0.6 秒，线程桥接约 0.2 秒，组合计时约 0.8 秒。
- 验证证据：`python -m tool_registry.async_health` 多轮实际输出证明并发耗时取最大值、单任务超时不丢失其他结果、取消时先执行“清理资源”再打印“任务已取消”；最终输出约 0.817 秒符合阻塞组 0.6 + to_thread 组 0.2。`PYTHONASYNCIODEBUG=1` 明确报告三个 blocking Task 各阻塞事件循环约 0.2 秒，且无 pending Task 或未等待 coroutine 警告。
- 门禁评分：理解 2/2，独立实现 2/2，调试与测试 1/2，工程质量 1/2，总分 6/8，通过。
- 仍需加强：Semaphore 演示未按统一时长的 5 个任务重新跑满三批；取消启动握手只理解 Event，未替换现有 sleep；异步代码缺少 pytest 自动化测试；演示文件中保留较多注释掉的历史片段和混合计时。
- 使用的最高提示等级：2。
- 下一次会话：进入 `PY-12`，先盘点现有工程，再把异步健康检查接入 API 或选择另一个低提示综合功能完成最终验收。

### 2026-09-01 第 11 次：`PY-10` FastAPI Web API（含用户豁免）

- 本次目标：把 SQLAlchemy 持久化链路接入 FastAPI，建立工具详情和新增接口，理解 Pydantic 响应序列化与请求级 Session dependency。
- 实际完成：安装 FastAPI 0.141.1、Pydantic 2.13.5 和 Uvicorn 0.52.4；创建 FastAPI `app`，修正过一次未实例化 app 和误把 SQLAlchemy `engine` 模块当 Engine 实例的问题。使用 `yield` dependency 为每个请求提供并关闭 Session；GET `/tools/{id}` 通过 `session.get` 返回现有 ORM record，Pydantic `ConfigDict(from_attributes=True)` 自动序列化，并验证 200/404。POST `/tools` 使用现有 dataclass `Tool` 和 SQLAlchemy 写入，映射重复名称为 409；初版在事务 commit 后按字典访问 ORM record 导致响应失败，并因缺少范围校验写入两条 `risk=0` 数据，随后改为直接返回完整 record。学习者明确认为 Java/Python 中请求 DTO/注解校验并非强制，选择只保留概念并将 Pydantic `Field` 请求校验与后续完整接口视为完成豁免。
- 验证证据：FastAPI TestClient 实际请求 `/tools/1` 返回 200 和完整 JSON，不存在 ID 返回 404；现有 pytest 输出 `5 passed`；数据库记录证明 POST 已执行并提交，重复名称异常映射代码存在。当前表包含合法 `search_order`/`refund_order`，以及未清理的 `id=27 ccc risk=0`、`id=29 ccc2 risk=0`。
- 门禁评分：理解 2/2，独立实现 1/2，调试与测试 1/2，工程质量 1/2，总分 5/8；按用户规则记为“已完成（含用户豁免）”，不记为“已通过”。
- 仍需加强：请求输入没有明确范围校验；POST 成功/422/409 没有自动化 API 证据；Engine 未通过 lifespan 释放；异常映射、响应语言和测试数据库隔离未统一；列表筛选、启停和完整 OpenAPI 契约未实现。
- 使用的最高提示等级：2；请求/响应模型和 POST 事务结构由 AI 提供局部骨架，学习者完成实现后选择豁免剩余验证。
- 下一次会话：进入 `PY-11`，先解释协程、Task、事件循环、超时和取消在 Python 中的真实执行模型，再实现受限并发健康检查。

### 2026-09-01 第 10 次：`PY-09` MySQL、PyMySQL 与 SQLAlchemy 2.0（含用户豁免）

- 本次目标：将持久化主线切换到现有 MySQL，完成原生驱动和 SQLAlchemy 2.0 的查询、写入、主键回填和事务失败闭环。
- 实际完成：只读识别 `fh_dev_mysql` 为目标 MCP，确认 MySQL 5.7.44、InnoDB 和空 schema `afakmv_meta`；经精确确认后创建唯一学习表 `py_learning_tools`，配置主键、名称唯一约束、启用/风险索引与 utf8mb4。使用未提交的 `config.local.toml` 和 Python `tomllib` 管理本地连接配置，PyMySQL `DictCursor` 完成 COUNT、参数化 INSERT、`lastrowid` 与显式 commit/rollback。初次示例因 dataclass 不做运行时校验写入 `risk=10`，后精确修正该行；学习者明确豁免 `__post_init__` 校验。重复名称通过 PyMySQL 触发 1062 并 rollback。随后引入 SQLAlchemy 2.0，使用 `URL.create`、Engine、`text()`、Connection 和 Session；修复过 `create_engine` 缺 URL、原始字符串不可执行和连接池释放顺序问题。学习者选择经典 `Column` 风格并豁免 `Mapped`/`mapped_column` 与精确 metadata 对齐；完成 Session 查询、ORM `add`/`flush` 主键回填和重复名称失败后的 `session.begin()` rollback，并能区分 `scalar`、`scalars`、`scalar_one` 与 `session.get`。
- 验证证据：MCP `SHOW CREATE TABLE`/`SHOW INDEX` 确认表结构；PyMySQL COUNT 初始为 0，参数化 INSERT 返回自增 ID，重复名称抛 `pymysql.err.IntegrityError 1062` 且行数不变；SQLAlchemy `SELECT 1` 输出 1，查询 `search_order` 成功，ORM 插入 `refund_order` 后主键回填；重复 ORM INSERT 抛 `sqlalchemy.exc.IntegrityError`，自动 rollback 后同一 Session COUNT 为 2；最终 MCP 确认表中只有 `search_order` 和 `refund_order` 两行。
- 门禁评分：理解 2/2，独立实现 1/2，调试与测试 1/2，工程质量 1/2，总分 5/8；按用户规则记为“已完成（含用户豁免）”，不记为“已通过”。
- 仍需加强：领域模型仍允许非法 risk；ORM 映射未精确声明 BIGINT/TINYINT、长度和 nullable；异常捕获仍偏宽；共享真实数据库尚未形成自动化测试隔离；数据库凭据已迁出源码，但曾在聊天/工作文件出现，仍建议轮换。
- 使用的最高提示等级：4；本地配置与 DictCursor 代码由 AI 完整修改，后续 SQLAlchemy 查询/写入由学习者完成；未补的模型验证和现代类型映射登记为用户豁免。
- 下一次会话：进入 `PY-10`，先说明 FastAPI/Pydantic/依赖注入与 Spring MVC/Jackson/容器的映射，再建立只读工具详情 API。

### 2026-09-01 路线调整：`PY-09` 改用现有 MySQL（不计学习会话）

- 调整原因：学习者已有可用 MySQL 实例，明确要求数据库主线不再使用 SQLite。
- 只读核查：预配置 MCP 中 `fh_dev_mysql` 与目标账户/端口匹配；服务器为 MySQL 5.7.44，默认 InnoDB；`afakmv_meta` 已存在且当前无表。schema 默认字符集为 `utf8`、排序规则为 `utf8_general_ci`。
- 新主线：原生 SQL/PyMySQL → SQLAlchemy 2.0；学习对象仅使用 `afakmv_meta.py_learning_*` 命名空间，表显式使用 `utf8mb4`，工具名称归一化后建立唯一约束。
- 安全边界：明文密码未写入文档或仓库；应用凭据只通过环境变量/本机安全配置提供。AI 在本次调整中只执行了 `SHOW`/`SELECT`，没有创建、修改或删除任何数据库对象。
- 下一次会话：解释 MySQL 参数绑定、主键、唯一约束和 InnoDB 事务，再经学习者确认后创建第一张 `py_learning_` 表。

### 2026-09-01 路线状态调整：用户豁免计入完成（不计学习会话）

- 调整原因：学习者明确要求将主动跳过且只保留概念了解的内容计为完成，以便继续高优先级主线。
- 完成口径：有规定门禁证据的单元记为“已通过”；没有规定证据但被学习者明确豁免的单元记为“已完成（含用户豁免）”。两者都计入路线推进，但分别统计。
- 本次处理：`PY-07` 的参数化、fixture、Ruff 和 mypy 实操登记为用户豁免；`PY-08` 的 argparse 实操、非法 JSON 测试和完整实现后的独立响应变式登记为用户豁免。
- 当前统计：已通过 7 / 13；已完成 9 / 13（其中用户豁免 2 个单元）。
- 下一次会话：进入 `PY-09`，从 MySQL、参数化 SQL、唯一约束和事务边界开始；用户后续仍可恢复任何豁免项。

### 2026-09-01 第 9 次：`PY-08` 配置、HTTP 客户端与幂等性（进行中）

- 本次目标：建立 Python 后端常见的 HTTP 客户端链路，理解环境变量、连接复用、超时、状态码、响应建模和重试幂等边界。
- 实际完成：先说明 argparse 与环境变量/配置文件的职责差异，学习者选择只保留 argparse 认知而跳过实操。将 httpx 0.28.1 声明为运行依赖并完成真实 GET 请求；初版代码对超时做了无价值的同类异常包装，经评审后简化为 `get → raise_for_status → json`。随后将 `httpx.Client` 生命周期交给调用方并通过参数注入，实现连接池复用。使用 `MockTransport` 独立验证 503 转 `HTTPStatusError` 与读取超时传播。学习者实现 `ToolApiClient` 后请求路径和 JSON 建模存在问题，并明确要求 AI 完整修改；AI 增加顶层 JSON/字段校验、`/tools/{name}` 路径和完整 Tool 相等测试。在此过程中发现包被同时以 `src.tool_registry` 与 `tool_registry` 导入，导致两个 dataclass 类型字段相同却不相等，最终统一为相对导入。非法 JSON 测试和完整实现后的缺省 tags 变式按学习者要求跳过。最后完成重试与幂等性诊断，能够说明 POST 读取超时后服务端可能已执行，幂等必须由服务端契约、稳定幂等键和事务/唯一约束共同保证。
- 验证证据：`/Users/yang/PycharmProjects/WelcomeScreen/.venv/bin/python -m tool_registry.http_client` 对公共测试接口返回合法 JSON；`python -m pytest -q` 输出 `5 passed`；HTTP 测试覆盖 503 与 ReadTimeout；成功 MockTransport handler 校验 `/tools/search_order`，Tool 完整字段映射通过；运行时验证 `tool_registry.Tool is tool_registry.models.Tool is http_client.Tool` 为 `True`。
- 门禁评分：理解 2/2，独立实现 1/2，调试与测试 1/2，工程质量 1/2，总分 5/8，未通过，状态保持进行中。
- 仍需加强：外部 JSON 建模依赖完整实现提示；缺少非法 JSON、非对象响应和独立字段变式证据；CLI 未实作；原始 httpx 异常是否转换为领域错误尚未形成最终契约。服务端幂等不能只靠客户端重试逻辑。
- 使用的最高提示等级：4；要求的不同响应形状迁移题被学习者明确跳过，因此不能把完整实现计为独立通过证据。
- 下一次会话：若继续当前单元，完成一个不照抄的响应变式；若继续向后学习，则保留 PY-07/PY-08 未通过状态并明确这些工程门禁仍需回补。

### 2026-09-01 第 8 次：`PY-07` 工程化、测试与可维护性（进行中）

- 本次目标：建立 pytest 红绿闭环、开发依赖声明和 `src/` editable 包结构，并了解 fixture、参数化、Ruff 与类型检查的职责。
- 实际完成：在 `pyproject.toml` 使用 `[dependency-groups].dev` 声明并安装 pytest 9.1.1、Ruff 0.16.5 和 mypy 2.3.1；确认它们安装在父项目 Python 3.12 虚拟环境而非系统 Python。创建 `tests/test_models.py` 并由 pytest 正确发现；新增重复工具名称测试，先稳定复现 `ToolRegistry` 缺少 `add()` 的 `AttributeError`，再独立实现 `add()` 使测试转绿，并使用 `pytest.raises(..., match=...)` 验证异常消息。参数化和 fixture 只说明用途后按学习者要求跳过。Ruff 只读检查发现正式包 5 个 lint 问题、5 个格式问题；默认 mypy 通过、严格 mypy 发现 5 个公开 API/签名问题，学习者选择暂不修复。随后将正式包迁移到 `src/tool_registry`，把编号练习移到根级 `exercises`，补充 setuptools 构建配置，清理旧项目名 `test` 的 editable 登记并以 `tool-registry` 重新安装。
- 验证证据：`/Users/yang/PycharmProjects/WelcomeScreen/.venv/bin/python -m pytest -q` 输出 `2 passed`；`pip show test` 找不到旧项目，`pip show tool-registry` 显示当前 editable 项目；`tool_registry.__file__` 指向 `/Users/yang/PycharmProjects/WelcomeScreen/test/src/tool_registry/__init__.py`；`python -m tool_registry.demo` 正常运行。Ruff scoped check仍有 5 项，严格 mypy 曾有 5 项，尚无清零证据。
- 门禁评分：理解 1/2，独立实现 2/2，调试与测试 2/2，工程质量 1/2，暂计 6/8；因 Ruff、类型检查、fixture/参数化证据缺失，单元门禁未通过，状态保持进行中。
- 仍需加强：测试成功分支未补；重复异常测试仍有冗余；需区分 IDE 本地检查与仓库/CI 可重复门禁；`src/` 只放可发布源码，练习应留在 `exercises/`；Ruff 和严格 mypy 的现有诊断仍待处理。
- 使用的最高提示等级：3；在测试骨架后已完成不同规则的 `add()` 实现、异常消息匹配和独立的 `src/` 结构迁移。
- 下一次会话：完成最小 Ruff/mypy 清零并决定 fixture/参数化是否以代码留证；若继续跳过，则明确保留 `PY-07` 未通过状态，不进入后续门禁结论。

### 2026-08-31 第 7 次：`PY-06` 类、数据模型、模块与包

- 本次目标：使用 `dataclass` 建模单个工具，用组合实现 `ToolRegistry` 集合行为，并形成可稳定导入、无副作用的 Python 包。
- 实际完成：基于旧练习 `00/04.py` 识别 dataclass 自动生成的构造、表示和字段相等语义；`06/06-01.py` 新建带 `risk` 与独立 `tags` 列表的 `Tool`，修正过一次 `append(list)` 造成嵌套列表的问题，并实现 `can_execute`。随后实现组合持有 `list[Tool]` 的 `ToolRegistry`，完成按名称查找和委托 `Tool.can_execute` 的可执行筛选，修正过一次绕过 Registry 直接操作内部列表的调用方式。最终拆分根级 `tool_registry` 包的 models、registry、demo 和 `__init__.py`，修复 demo 中把包内模块误当顶层模块导入的问题，并通过包级公开 API、`main` 守卫和模块运行验证。
- 验证证据：使用 `/Users/yang/PycharmProjects/WelcomeScreen/.venv/bin/python 06/06-01.py` 验证 dataclass、独立标签、查找和可执行筛选；`/Users/yang/PycharmProjects/WelcomeScreen/.venv/bin/python -m tool_registry.demo` 正常运行；纯 `import tool_registry.demo` 只输出 `import finished`，没有演示副作用；`from tool_registry import Tool, ToolRegistry` 可直接导入两个公开类型。
- 门禁评分：理解 1/2，独立实现 2/2，调试与测试 1/2，工程质量 2/2，总分 6/8，通过。
- 仍需加强：Registry 是聚合/集合管理对象，不是抽象 Tool 或对象工厂；组合表达 has-a，继承表达 is-a；包内绝对/相对导入的解析路径需继续通过工程结构巩固；代码格式和签名细节留到 Ruff/类型检查统一处理。
- 使用的最高提示等级：3；在修复 demo 导入骨架后，已分别通过模块执行、纯导入无副作用和包级公开 API 三种入口验证。
- 下一次会话：先说明 `pytest`、fixture、参数化、Ruff 和类型检查的职责，再为 Tool/ToolRegistry 写第一组自动化测试。

### 2026-08-31 第 6 次：`PY-05` 异常、资源与常用标准库

- 本次目标：使用 `pathlib`、`with` 和 `json` 建立工具配置读写闭环，区分文件、JSON、字段和业务校验错误，并明确异常边界。
- 实际完成：创建正常、非法 JSON、缺少根字段和非法风险配置；`05/05-01.py` 使用 `Path` 与 `with` 读取 JSON，并让 `load_tools` 保持“成功返回 `list[dict]`、失败保留异常”的稳定契约。展示边界分别处理 `FileNotFoundError`、`JSONDecodeError`、`KeyError` 和 `ValueError`；曾将所有失败返回为空列表，经合法空配置反例后修正。字段校验先后暴露缺失风险的 `KeyError`、字符串比较的 `TypeError` 和布尔值被当成整数等问题，最终统一转成带上下文的 `ValueError`。随后实现 JSON 写入和重新读取，修正过一次因可选 key 导致返回类型漂移的设计，最终完成工具列表往返一致性验证。日志概念因学习者已有 Java 日志经验只做 Python API 说明，未追加独立代码练习。
- 验证证据：使用 `/Users/yang/PycharmProjects/WelcomeScreen/.venv/bin/python 05/05-01.py` 验证正常、非法 JSON、缺字段、不存在文件和非法风险五条路径；额外直接调用 `validate_tools` 验证缺失、字符串、布尔和越界风险均抛 `ValueError`，边界 `1`、`5` 被接受；`output_tools.json` 重新读取后两端类型均为 `list` 且内容比较为 `True`。
- 门禁评分：理解 2/2，独立实现 1/2，调试与测试 1/2，工程质量 2/2，总分 6/8，通过。
- 仍需加强：异常分类和返回契约经过多轮提示才稳定；外部数据校验顺序需要继续迁移；`save_tools` 仍缺 `ensure_ascii=False`；日志的 Python 配置与不重复记录原则留到 `PY-07` 通过实际工程命令验证。
- 使用的最高提示等级：4；完整风险校验后已通过独立 JSON 写入/读取往返任务验证不同规则迁移。
- 下一次会话：先解释 `dataclass` 的作用及与 Java record/POJO 的差异，再把工具字典建模为 `Tool`。

### 2026-08-31 第 5 次：`PY-04` Pythonic 迭代与生成器

- 本次目标：理解 iterable、iterator 和 generator 的消费模型，掌握 `yield`、`enumerate`、`zip`、列表推导式与生成器表达式的常见用途。
- 实际完成：先预测并运行 `iter`/`next` 的逐次消费与耗尽；区分列表推导式立即构造结果和生成器表达式按需计算。`04/04-01.py` 独立实现使用 `yield` 流式产生启用工具名称，验证 `next`、剩余消费、重复消费、新生成器实例和空输入，并用 `enumerate(start=1)` 编号。`04/04-02.py` 使用 `zip(..., strict=True)` 组合并行字段，修正覆盖内置 `dict` 名称的问题，再改为列表推导式，并使用生成器表达式配合 `sum` 和 `any` 完成统计。学习者明确提出后续遇到 Python 专属 API 时需先解释用途和工作方式再练习。
- 验证证据：使用 `/Users/yang/PycharmProjects/WelcomeScreen/.venv/bin/python` 运行 `04/04-01.py` 和 `04/04-02.py`，均退出码 0；生成器依次输出 `<class 'generator'>`、首个名称、剩余列表、耗尽空列表，新实例重新产生完整结果；`zip` 组合产生三条正确工具记录；启用名称、数量和高风险判断分别输出 `['search_order', 'refund_order']`、`2`、`True`；额外验证长度不一致的 `zip(..., strict=True)` 抛出 `ValueError`。
- 门禁评分：理解 2/2，独立实现 2/2，调试与测试 1/2，工程质量 1/2，总分 6/8，通过。
- 仍需加强：生成器表达式的源数据与输出曾混淆；`next()` 返回单个元素而非列表；推导式前需先明确结果变量代表完整数据还是已筛选数据；当前仍以打印验证为主。
- 使用的最高提示等级：2。
- 下一次会话：先解释 `pathlib`、`with`、`json` 和相关异常的用途，再实现工具配置读取与失败分类。

### 2026-08-31 第 4 次：`PY-03` 函数、参数、作用域与类型标注

- 本次目标：掌握可变默认参数、位置/关键字参数、返回契约、作用域、`*args`/`**kwargs` 和常用类型标注，形成可运行的工具查询函数。
- 实际完成：`03/03-01.py` 使用 `None` 修复默认列表跨调用共享，并验证显式传入列表时复用调用者对象；`03/03-02.py` 实现带关键字专用参数的工具查找，初版把 `enabled_only=False` 错写为只匹配禁用工具，随后按业务守卫条件修正；`03/03-03.py` 通过参数和返回值传递计数状态，未在函数中修改全局变量；`03/03-04.py` 正确使用 `*tags` 和 `**metadata` 组装工具字典。另通过实际运行确认类型标注不会自动检查或转换运行时参数，并建立 `Callable[[dict], bool]` 的基础阅读能力；按实际开发频率不追加专门的 `Callable` 练习。
- 验证证据：使用 `/Users/yang/PycharmProjects/WelcomeScreen/.venv/bin/python` 运行 `03/03-01.py`、`03/03-02.py`、`03/03-03.py`、`03/03-04.py`，均退出码 0；对 `find_tool` 额外验证启用工具默认查询、禁用工具默认查询、`enabled_only=True`、不存在名称和位置传入关键字专用参数；直接运行 `echo_risk('high')` 输出 `high` 和 `<class 'str'>`，证明标注只作为类型信息保存。
- 门禁评分：理解 1/2，独立实现 2/2，调试与测试 1/2，工程质量 2/2，总分 6/8，通过。
- 仍需加强：默认参数求值时机、局部/全局作用域和类型标注运行时边界均在提示后掌握，需在后续新代码中再次迁移；复杂布尔契约先用自然语言拆成守卫条件；部分签名仍可进一步精确，但不阻塞当前阶段。
- 使用的最高提示等级：2。
- 下一次会话：预测迭代器逐次消费及耗尽后的输出，区分 iterable、iterator 和 generator。

### 2026-08-28 第 3 次：`PY-02` 容器、控制流与数据变换

- 本次目标：根据 `list[dict]` 数据形状完成启用筛选、缺字段处理、顺序去重和按风险稳定排序。
- 实际完成：正确识别列表、字典和字符串层级，并区分严格键访问的 `KeyError` 与 `dict.get` 默认值；`02/02-01.py` 独立完成启用名称筛选。`02/02-02.py` 先使用结果列表线性去重，后改用集合但丢失顺序；学习者要求直接修改后，AI 实现“结果列表 + 已见集合”。随后学习者完成更综合的 `02/02-03.py`，修正风险条件方向，在保留完整字典的前提下使用 `sorted(key=lambda ...)` 排序，再提取名称；能够预测二级元组排序结果 `c、a、b`。
- 验证证据：使用 `/Users/yang/PycharmProjects/WelcomeScreen/.venv/bin/python` 运行 `02/02-01.py`、`02/02-02.py`、`02/02-03.py`，均退出码 0；`02/02-02.py` 在 `PYTHONHASHSEED=1、2、3、4` 下均稳定输出 `['search_order', 'search_order_3']`；对综合查询额外验证空列表、缺少 `enabled`/`risk`、禁用工具、重复名称、同风险稳定顺序、`max_risk=1` 和输入未修改，结果均符合约定。
- 门禁评分：理解 2/2，独立实现 1/2，调试与测试 1/2，工程质量 2/2，总分 6/8，通过。
- 仍需加强：集合不承诺插入顺序，需要用列表承载有序结果；复杂布尔条件先写反过一次；筛选后应保留排序所需的数据，不能过早只留下名称；测试仍主要依赖打印和 AI 补充边界运行。
- 使用的最高提示等级：4；完整去重实现后已通过不同规则的综合筛选与排序任务验证迁移。
- 下一次会话：预测可变默认列表参数的多次调用结果，解释默认参数求值时机并完成修复。

### 2026-08-28 第 2 次：`PY-01` 对象模型、基础类型与可变性

- 本次目标：建立名称绑定模型，区分别名、外层浅复制与嵌套对象共享；完成无副作用更新和工具名称清洗。
- 实际完成：先预测并验证 `tools`、`alias`、`copied` 的对象关系；`01/01-01.py` 验证外层列表和元素身份。`01/01-02.py` 的 `add_tag` 先后暴露直接修改原列表、只复制外层列表、只复制字典但继续共享内部列表等失败，学习者明确要求完整答案后，AI 改为只复制修改路径。随后学习者在 `01/01-03.py` 完成改名变式，经局部提示把新字典重新放回新列表；`01/01-04.py` 完成 `None`、空字符串、纯空白、大小写和单个内部空格的名称清洗。
- 验证证据：使用 `/Users/yang/PycharmProjects/WelcomeScreen/.venv/bin/python` 分别运行 `01/01-01.py`、`01/01-02.py`、`01/01-03.py`、`01/01-04.py`，均退出码 0；`add_tag` 输出证明原数据不变，修改字典与标签列表身份均为 `False`，未修改字典身份为 `True`；改名变式输出为字典身份 `False`、标签身份 `True`、未修改字典身份 `True`；直接调用名称清洗函数验证五个约定输入全部符合预期。
- 门禁评分：理解 2/2，独立实现 1/2，调试与测试 1/2，工程质量 2/2，总分 6/8，通过。
- 仍需加强：嵌套复制需要继续先判断被修改的具体层级；遇到失败时仍较依赖提示定位；测试以打印为主。连续内部空格是否压缩为单个下划线需要由后续明确契约决定。
- 使用的最高提示等级：4；完整实现后已完成不同规则的改名变式。
- 下一次会话：描述 `list[dict]` 工具数据的形状，独立实现启用工具筛选，并处理空列表和缺少 `enabled` 字段。

### 2026-08-27 第 1 次：`PY-00` 基线诊断与环境统一

- 本次目标：统一 Python 3.12 运行环境，修复 `03.py` 的容器访问和 `04.py` 的比较语义，并用断言验证。
- 实际完成：学习者独立实现按名称遍历查找并解释循环元素为 `dict`；修正字符串值比较、`None` 身份判断和启用状态逻辑；增加存在、不存在、启用、禁用和 `None` 案例。AI 按学习者要求完成 PEP 8 命名和类型标注的机械整理。
- 验证证据：`../.venv/bin/python --version` 输出 Python 3.12.0；使用 `../.venv/bin/python` 依次运行 `01.py`、`02.py`、`03.py`、`04.py`，全部退出码为 0；`pyproject.toml` 声明 `requires-python = ">=3.12"`。
- 门禁评分：理解 2/2，独立实现 2/2，调试与测试 1/2，工程质量 1/2，总分 6/8，通过。
- 仍需加强：曾出现禁用断言因名称同时不匹配而假阳性；继续训练单一变量测试。区分 `pyenv` 的版本选择与虚拟环境的依赖隔离。
- 使用的最高提示等级：2。
- 下一次会话：预测并验证列表别名与浅复制的修改结果，画出名称到对象的引用关系。

### 2026-08-27 第 0 次：路线建立与代码盘点

- 本次目标：建立可跨会话执行的 Python 学习路线。
- 实际完成：核实现有四个练习文件、解释器版本和运行结果；建立路线、执行计划、带练协议和进度页。
- 验证证据：`01.py`、`02.py`、`04.py` 正常结束；`03.py` 在查找函数中稳定复现 `TypeError`。
- 门禁评分：未进行学习门禁评分。
- 仍需加强：待 `PY-00` 正式诊断。
- 使用的最高提示等级：不适用。
- 下一次会话：按上方任务卡完成 `PY-00` 第一轮诊断。

## 更新规则

- 每次有效学习会话结束后更新“当前快照”“单元看板”“当前薄弱点”“下一次会话任务卡”和日志。
- 日志最新记录放最上方；第 0 次路线建立记录保留在底部。
- 单元通过后必须写入日期、四维评分和文件/测试证据。
- 用户明确要求跳过并计为完成时，状态写为“已完成（含用户豁免）”，列出未验证项，并与“已通过”分开统计。
- 实际代码与进度记录冲突时，先复核代码和运行结果，再修正文档。
- 不删除失败记录；后续在新日志中说明如何解决，以保留真实学习轨迹。
