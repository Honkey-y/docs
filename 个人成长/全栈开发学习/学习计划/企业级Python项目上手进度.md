# 企业级 Python 项目上手进度

> 状态：进行中
> 最后更新：2026-09-03
> 适用范围：`/Users/yang/deepfinance_project/pyserver`
> 关联文档：[企业级 Python 项目上手路线](../规划/企业级Python项目上手路线.md)、[AI 带练会话约定](AI带练会话约定.md)、[基础 Python 学习进度](Python学习进度.md)

## 当前快照

| 项目 | 当前值 |
| --- | --- |
| 当前单元 | `ENT-PY-01` 启动、配置与生命周期 |
| 单元状态 | 进行中 |
| 已通过单元 | 0 / 9 |
| 已完成单元 | 0 / 9 |
| 执行模式 | 六周企业项目上手 |
| 目标投入 | 约 48–60 小时 |
| 参考项目 | `/Users/yang/deepfinance_project/pyserver` |
| 扫描基线 | `master` 分支，提交 `25def03` |
| 仓库边界 | 默认只读；开始实改前确认目标与工作分支 |
| 下一次会话 | 用整数 Option 变式确认环境变量字符串经 `load_env()` 转换为声明的 Python 类型 |

## 前置状态

- 基础路线 `PY-00`～`PY-12` 已按用户口径完成 13 / 13。
- 其中 8 个单元通过门禁，5 个单元为“已完成（含用户豁免）”。
- 企业路线不重做 Java、SQL、事务和 HTTP 通用基础；发现 Python 特有缺口时定点回补。
- 企业路线的目标是读懂、隔离、测试、排障和交付真实项目，不以看完目录或启动服务代替验收。

## 单元看板

状态使用“未开始 / 进行中 / 已通过 / 已完成（含用户豁免） / 待复习”。“已通过”必须有实际调用链、运行、测试或交付证据。

| 单元 | 主题 | 状态 | 完成日期 | 证据 |
| --- | --- | --- | --- | --- |
| `ENT-PY-00` | 环境与可复现基线 | 待复习 | - | 环境与绿色测试基线已建立；失败阶段分类尚未独立通过 |
| `ENT-PY-01` | 启动、配置与生命周期 | 进行中 | - | 已验证字符串 `on` 经 reload 转为 `True bool`；待独立完成整数类型转换变式 |
| `ENT-PY-02` | HTTP 请求、依赖和异常契约 | 未开始 | - | - |
| `ENT-PY-03` | 外部 API、服务发现与重试 | 未开始 | - | - |
| `ENT-PY-04` | Celery 与分布式任务生命周期 | 未开始 | - | - |
| `ENT-PY-05` | 纵向业务切片 | 未开始 | - | - |
| `ENT-PY-06` | pytest、fixture 与测试替身 | 未开始 | - | - |
| `ENT-PY-07` | 部署、可观测性与故障定位 | 未开始 | - | - |
| `ENT-PY-08` | 首个企业级小改动 | 未开始 | - | - |

## 下一次会话任务卡

### 目标

在不启动任何进程的前提下，读懂 Web 服务的真实启动命令及其入口表达式。

### 步骤

1. 展开 `serverctl.sh` 中的 `EXEC`、`CONFIG`、`APP` 变量。
2. 解释 Gunicorn 的 `-c gunicorn.conf.py`。
3. 解释 `main:app` 中模块名与对象名的对应关系。
4. 区分 `bash -n` 语法检查、模块导入和真正启动服务三种动作。

### 验收

- [x] 能写出变量展开后的完整 Gunicorn 命令，并独立完成 Celery 同构变式。
- [ ] 能指出 `main` 是模块、`app` 是该模块中的 ASGI 应用对象。
- [ ] 能说明为何当前只检查而不执行启动脚本。

## 已知边界

- 项目包含公司内部依赖，公共软件源未必能完成全新安装。
- 项目兼容 Python 3.8+，不能只按本机 Python 3.12 语法判断可修改范围。
- 项目使用 Pydantic v1 和 SQLAlchemy 1.3；学习现有代码时不能混入 v2/2.x API。
- `.codegraph/` 是代码理解索引，不视为学习产生的业务改动。
- 测试和配置中若发现历史凭据，不展示、不复制，新样例统一使用假值。

## 当前薄弱点

- 已建立可完成全量安装的 Python 3.11.15 arm64 项目虚拟环境；仍需确认它是否与线上实际版本一致。
- 仓库没有声明准确的线上 Python 版本，仍需从团队、CI 或部署环境确认；本机已另用 `uv` 安装 CPython 3.11.15 arm64 作为学习环境候选。
- 需要继续区分“环境标记选中的约束分支”和“解析器最终安装的具体版本”。
- 已给出 pytest 收集失败、测试断言失败以及解释器语义变化的标准对照；该项保留为 `ENT-PY-00` 待复习缺口。
- 需要理解 `pip check` 只验证包元数据约束，不能发现 pandas/NumPy 这类本地 C 扩展 ABI 不兼容。
- Python 3.11 改变了 `str` 混入 `Enum` 的默认格式化结果，现有 validator 测试辅助函数未显式使用 `.value`；线上真实 Python 版本仍需确认。
- `ENT-PY-01` 已通过 Shell 变量展开、直接运行、startup 回调和顶层调用判断；下一缺口是从回调定位线程、协程及外部系统副作用。
- 学习者明确反馈 Celery、broker 等 Python 生态/企业框架概念尚不熟悉；已掌握 broker 的排队作用，后续仍须先给“定义、Java 类比、项目位置、副作用”再要求读代码。
- 已通过故障隔离场景确认 `CeleryMonitor` 只是旁路观察者；下一项陌生框架概念是服务发现。
- 服务发现已建立初步类比；需补充逻辑服务名还用于动态上下线、故障切换、扩缩容和环境解耦，并区分两层策略。
- 服务发现两层策略已通过；下一项是理解 `OPTION` 描述符、配置源优先级与“导入前必须先设置环境”的原因。
- 已掌握 Apollo 优先、缺项回退 `os.environ` 的规则；需要用一个实际配置项串起描述符加载与使用时机。
- `LOG_LEVEL` 场景中暂时混淆了 `OPTION.general.log_level` 的存储值与开发模式下 `setup('DEBUG')` 的实际日志配置。
- 已通过实际输出看到环境变量变化不会主动推送给 Option；仍需迁移到非字符串类型并理解 `coerce()`。
- 布尔变式首次把 reload 理解成恢复默认；已验证实际规则是“先查配置，找到后转换，否则保留/使用默认”。
- 尚未独立画出启动、请求、任务和进程调用链。

## 学习会话日志

### 2026-09-03 第 23 次：ENT-PY-01 布尔 Option 转换

- 本次目标：用布尔配置验证 reload 时环境值优先于默认值，并观察字符串到 Python 类型的转换。
- 实际完成：学习者正确判断 reload 前值为 `False bool`，但认为 reload 后仍会恢复默认 False。纠正并用隔离假环境验证：首次加载为 `False bool`；设置环境字符串 `on` 但未 reload 时仍为 `False bool`；显式 `load_env()` 后通过 `Option.coerce()` 更新为 `True bool`。未影响真实进程环境或启动服务。
- 验证证据：隔离命令输出 `initial: False bool`、`before reload: False bool`、`after reload: True bool`。
- 门禁评分：理解 1/2，独立实现 1/2，调试与测试 2/2，工程质量 2/2。
- 仍需加强：默认值只在没有配置值时生效；配置值存在时先按 `val_type` 转换再保存。
- 使用的最高提示等级：4。
- 下一次会话：给定整数字符串环境值，判断 reload 后的值及 Python 类型。

### 2026-09-03 第 22 次：ENT-PY-01 Option 快照与显式 reload

- 本次目标：确定环境变量在 import/load 之后发生变化时，Option 是否自动同步。
- 实际完成：学习者表示不确定应取第一次还是最后一次环境值；给出结论并在隔离子进程中使用纯假配置验证。Option 首次 `load_env()` 保存 WARNING；随后仅把 `os.environ` 改为 ERROR，读取仍为 WARNING；再次显式 `load_env()` 后才更新为 ERROR。实验清空并临时替换子进程环境，不影响真实配置或服务。
- 验证证据：隔离命令输出 `after initial load: WARNING`、`after env change only: WARNING`、`after explicit reload: ERROR`。
- 门禁评分：理解 1/2，独立实现 0/2，调试与测试 2/2，工程质量 2/2。
- 仍需加强：把“外部容器中的值发生变化”和“应用重新读取/收到热更新事件”视为两个独立动作；迁移到 bool/int 类型转换。
- 使用的最高提示等级：4。
- 下一次会话：用 `block_shell_exec_api` 的布尔值完成变式：设置字符串 `on` 前后分别判断读取值与类型。

### 2026-09-03 第 21 次：ENT-PY-01 `LOG_LEVEL` 配置值与应用值

- 本次目标：用 `LOG_LEVEL` 理解 Option 描述符的默认值、环境绑定、import 时加载与日志系统应用。
- 实际完成：学习者在在“导入完成后才修改 `os.environ` 且无 reload”场景中回答“dev 为 DEBUG，否则默认 INFO”，混淆了两个层次。纠正为：`OPTION.general.log_level` 在 import 时已加载/落为默认 INFO，之后普通修改 `os.environ` 不会自动改变它；而 `main.py` 的 dev 分支会单独调用 `setup('DEBUG', ...)`，改变实际日志系统但不反写 Option。CodeGraph 还确认日志模块 import 时会先按环境做一次 setup，main 随后再次配置，热更新则通过 Option callback 重新应用。未修改环境或启动服务。
- 验证证据：CodeGraph 对 `General.log_level`、`Option.__get__/__set__`、`AppOptions.load_env`、`src/log.py::setup` 与 `src/main.py` 日志分支的调用路径。
- 门禁评分：理解 1/2，独立实现 0/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：始终先指出问题问的是“配置对象保存的值”还是“下游组件已经应用的运行状态”。
- 使用的最高提示等级：2。
- 下一次会话：给定 import 前设置和 import 后设置两个场景，分别判断 Option 值与实际日志值。

### 2026-09-03 第 20 次：ENT-PY-01 配置源优先级

- 本次目标：判断远程配置源与操作系统环境变量同时存在时的取值与回退规则。
- 实际完成：学习者正确回答 Apollo 启用时优先采用 Apollo，同名项在 Apollo 缺失时回退到 `os.environ`。补充明确 AppOptions 使用 `if/elif` 一次选择一种远程配置源，而不是同时合并 Apollo、Spring、Nacos；Nacos 的配置中心能力与前一阶段的服务发现能力是两个不同边界。CodeGraph 进一步定位 `OPTION.general.log_level` 的声明、`create_env_getter()`、`load_env()`、描述符赋值及 reload 元数据，为下一步单项追踪做准备。
- 验证证据：学习者回答；CodeGraph 对 `AppOptions.create_env_getter/load_env`、`Category.set_default` 和 `General.log_level` 的调用链。
- 门禁评分：理解 2/2，独立实现 2/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：理解 `Option.__get__/__set__` 为何能在普通属性访问背后完成默认值、转换、校验和来源记录。
- 使用的最高提示等级：0。
- 下一次会话：根据 `log_level = Option(default, val_type=str, bind_env='LOG_LEVEL', reload_opt=...)` 推导无配置、有环境配置及导入后修改环境三种结果。

### 2026-09-03 第 19 次：ENT-PY-01 服务发现策略通过与配置加载入口

- 本次目标：在具体配置中区分服务发现实现与实例选择策略，并开始定位配置加载时机。
- 实际完成：学习者正确指出 Nacos 负责取得注册实例列表，RoundRobin 负责从列表选择实例，两层策略门禁通过。随后 CodeGraph 与目标源码核查确认：模块顶层先构造 `OPTION = AppOptions()`，识别进程/开发模式并可加载 `.env`，在非测试模式下立即执行 `OPTION.load_env()` 与 `OPTION.validate()`；因此配置必须在导入 `src.main`/`src.options` 前准备，而不是等 FastAPI startup。未展示配置值或启动服务。
- 验证证据：学习者回答；CodeGraph 对 `ServiceDiscovery.instantiate/get_url` 与 `CACHE_STRATEGY` 的调用链；`src/options.py:554`～`572` 的只读源码。
- 门禁评分：理解 2/2，独立实现 2/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：理解 AppOptions 如何在 Apollo/Spring/Nacos 与操作系统环境变量之间选择值，以及默认值/类型转换发生的位置。
- 使用的最高提示等级：0。
- 下一次会话：给定外部配置源与 `os.environ` 同时存在同名值，判断当前项目采用哪个值并说明回退规则。

### 2026-09-03 第 18 次：ENT-PY-01 服务发现的两层策略

- 本次目标：理解服务发现为何使用逻辑服务名，并识别项目中的可替换实现模式。
- 实际完成：学习者识别出 Nacos 继承 `ServiceDiscovery`，并将其类比为策略模式；指出逻辑服务名可用于负载均衡。补充确认项目实际组合了两层策略：Eureka/Nacos/K8s 决定从哪种注册中心获取和更新实例列表，Ranked/RoundRobin/Random 决定从实例缓存中如何 `pick_best()`；前一层还结合 `__init_subclass__` 自动注册与配置驱动工厂。逻辑名称除负载均衡外，还支持动态上下线、故障切换、扩缩容和不同环境地址解耦。未启动注册中心或执行网络 I/O。
- 验证证据：学习者回答；CodeGraph 对 `ServiceDiscovery.__init_subclass__/instantiate/get_url`、`CACHE_STRATEGY` 和三个缓存实现的调用链。
- 门禁评分：理解 2/2，独立实现 1/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：在一个具体配置场景中明确“发现实现”和“实例选择策略”各自负责什么。
- 使用的最高提示等级：1。
- 下一次会话：给定 Nacos + RoundRobin，分别指出谁获取实例列表、谁从列表中选择本次请求的地址。

### 2026-09-03 第 17 次：ENT-PY-01 monitor 旁路故障隔离

- 本次目标：用故障场景确认 `CeleryMonitor` 不在任务执行主链路中。
- 实际完成：学习者正确判断 broker 与 worker 正常、仅 monitor 停止时任务仍可执行，系统只失去监控能力。worker/monitor 边界通过。随后 CodeGraph 只读定位下一项启动依赖：`ServiceDiscovery.start()` 在 FastAPI startup 创建异步任务，按配置选择 Eureka、Nacos 或 K8s 实现，初始化并周期更新服务实例缓存；`get_url()` 再按逻辑服务名选择实例。未启动服务或外部 I/O，未展示连接配置。
- 验证证据：学习者回答；CodeGraph 对 `src/api/discovery.py::ServiceDiscovery.start/instantiate/get_url` 及实现类的调用链。
- 门禁评分：理解 2/2，独立实现 2/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：建立服务发现的“服务名 → 注册中心 → 实例地址 → 本地缓存”心智模型。
- 使用的最高提示等级：0。
- 下一次会话：判断调用方只知道逻辑服务名时，服务发现解决了什么问题，以及注册中心不可用的影响。

### 2026-09-03 第 16 次：ENT-PY-01 worker 与 monitor 边界

- 本次目标：区分真正消费任务的 Celery worker 与 Web 进程中的 `CeleryMonitor`。
- 实际完成：学习者正确判断 Redis 与 Web 正常、worker 未启动时，任务会留在 Redis broker 且不会执行；同时提出 monitor 是否监听事件后再把任务交给 worker。纠正为两条独立路径：producer 经 broker 向 worker 传递任务，worker 直接从 broker 消费；monitor 通过事件通道旁路观察 worker/任务状态，不参与任务分发或执行。未启动任何服务或修改源码。
- 验证证据：学习者对“无 worker”场景的回答；上一会话 CodeGraph 已确认 `CeleryMonitor.run()` 使用 `EventReceiver` 接收事件，而 worker 启动命令直接指定 Celery task app。
- 门禁评分：理解 1/2，独立实现 1/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：用故障隔离场景确认 monitor 不在任务主链路中。
- 使用的最高提示等级：1。
- 下一次会话：判断仅 `CeleryMonitor` 停止时，任务执行与可观测性分别受到什么影响。

### 2026-09-03 第 15 次：ENT-PY-01 Celery broker 排队语义

- 本次目标：确认 `apply_async()` 返回 task id 时，任务发送与任务执行是两个不同结果。
- 实际完成：在先解释 producer、Redis broker、Celery worker 与 result backend 后，学习者独立判断“拿到 task id 但 worker 尚未消费”不算执行完成，并说明当前仅把任务保存到 Redis、仍待执行。broker 核心语义通过；下一步只补 worker 与 Web 内 `CeleryMonitor` 的区别。
- 验证证据：学习者回答“只算存到 Redis，还待执行”。
- 门禁评分：理解 2/2，独立实现 2/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：区分真正执行任务的独立 worker 进程与只监听事件的 Web 后台监控线程。
- 使用的最高提示等级：2。
- 下一次会话：回答仅启动 Web 时，`celerymon.start` 为什么仍可能连接 Redis broker。

### 2026-09-03 第 14 次：ENT-PY-01 Celery broker 前置概念补充

- 本次目标：在分析启动副作用前，建立 Celery producer、broker、worker、result backend 与 monitor 的最小心智模型。
- 实际完成：学习者明确反馈 Celery broker 等概念尚未建立，教学方式调整为先解释框架机制再出题。CodeGraph 只读确认本项目在 Web/业务侧通过 Celery app 发布任务，由独立 worker 消费；Redis 同时承担 broker 与 result backend，但使用分开的逻辑存储；`CeleryMonitor` 是 Web 进程启动的守护线程，会通过 Celery 连接接收 worker/task 事件。未读取或展示任何连接值，未启动 Redis、Celery 或 Web 服务，未修改企业仓库源码。
- 验证证据：CodeGraph 对 `src/celeryd/celeryapp.py`、`src/celeryd/celeryconfig.py`、`src/init/celerymonitor.py` 的结构与调用链。
- 门禁评分：理解 0/2，独立实现 0/2，调试与测试 0/2，工程质量 2/2。
- 仍需加强：先能区分“任务消息等待执行”和“任务结果/状态已保存”，再继续判断启动外部依赖。
- 使用的最高提示等级：2。
- 下一次会话：回答 `apply_async()` 返回 task id 时，broker、worker 和 result backend 分别完成了什么。

### 2026-09-03 第 13 次：ENT-PY-01 import 调用与 startup 延迟执行

- 本次目标：区分 `apply_patches()` 的导入时调用与 `on_startup` 列表中方法对象的延迟调用。
- 实际完成：学习者正确判断 `apply_patches()` 会立即执行，`on_startup` 只保存方法对象；进一步纠正判断依据：关键不是“最外层”，因为赋值和列表构造本身也在模块顶层执行，而是 `apply_patches()` 是带括号的调用表达式，列表元素 `ServiceDiscovery.start` 没有括号。CodeGraph 确认该调用会在 import 时替换 Starlette 异常中间件方法；FastAPI startup 才调用 Celery 监控、服务发现和配置监控。未导入真实应用或启动服务，仓库仍为 `master`，仅有原有 `.codegraph/` 未跟踪目录。
- 验证证据：学习者预测；CodeGraph 对 `src/main.py`、`src/lib/patches.py::apply_patches` 和生命周期回调的只读调用链；`.venv/bin/python` 仍为 3.11.15。
- 门禁评分：理解 2/2，独立实现 2/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：从每个 startup/shutdown 回调指出线程、协程和外部依赖边界，并形成完整启动时序。
- 使用的最高提示等级：1。
- 下一次会话：说明为什么此时执行 `serverctl.sh start` 可能连接 Celery/Redis、服务发现或配置源，并列出至少两个外部边界。

### 2026-09-02 第 12 次：ENT-PY-01 startup 回调对象

- 本次目标：判断 `on_startup` 列表中的函数是否会在模块导入时立即执行。
- 实际完成：学习者独立判断列表中的三个方法/函数不会立即执行，并指出它们需要后续触发。补充确认没有调用括号时表达式只取得函数或绑定方法对象；FastAPI 保存这些对象，在应用 lifespan startup 阶段统一调用。类比 Java 的方法引用 `obj::start` 与直接调用 `obj.start()`。未启动任何服务，未修改企业仓库源码。
- 验证证据：学习者对 `on_startup = [celerymon.start, ServiceDiscovery.start, start_option_monitor]` 的正确预测与依据。
- 门禁评分：理解 2/2，独立实现 2/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：把同一规则应用到 `main.py` 的其他顶层表达式，形成完整 import/startup 时序。
- 使用的最高提示等级：0。
- 下一次会话：对比 `apply_patches()` 与 `on_startup` 列表，判断哪个会在 import 时执行。

### 2026-09-02 第 11 次：ENT-PY-01 直接运行变式与 dunder 名称

- 本次目标：完成 `__name__` 直接运行变式，并理解双下划线特殊名称的用途。
- 实际完成：学习者正确判断直接执行 `python src/main.py` 时 `__name__` 为 `__main__`，且入口块中的 `uvicorn.run(...)` 会执行；补充说明模块顶层的 `app = PythonServer(...)` 会在此前先执行。解释了 `__name__` 是解释器设置的特殊模块属性，`"__main__"` 是入口模块使用的特殊字符串值，双下划线两侧名称通常属于 Python 数据模型或运行时协议。未运行真实入口或启动服务。
- 验证证据：学习者对直接运行变式的回答；前一轮内存演示已经验证相同行为。
- 门禁评分：理解 1/2，独立实现 1/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：明确直接运行和导入都会执行顶层初始化；区分 dunder 协议名与普通业务变量。
- 使用的最高提示等级：2。
- 下一次会话：分析 `on_startup = [celerymon.start, ServiceDiscovery.start, ...]` 在模块导入时是否调用这些函数。

### 2026-09-02 第 10 次：ENT-PY-01 `main:app` 与 `__name__`

- 本次目标：理解 Gunicorn 的 `main:app` 如何导入模块，以及顶层代码与直接运行入口的执行差异。
- 实际完成：学习者首次预测 `uvicorn.run(...)` 会在 Gunicorn 导入时执行，同时认为 `app = PythonServer(...)` 会初始化。随后用内存最小示例验证：以 `__name__='main'` 模拟导入时执行顶层代码但不进入入口块；以 `__name__='__main__'` 模拟直接运行时，顶层代码和入口块都执行。未导入真实 `src/main.py`，未启动任何服务，未修改企业仓库源码。
- 验证证据：内存示例的导入模式只输出顶层标记，直接运行模式额外输出 `direct-entry block`。
- 门禁评分：理解 1/2，独立实现 0/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：不要把“导入模块”和“执行模块为主程序”混为一谈；需完成直接运行变式。
- 使用的最高提示等级：4。
- 下一次会话：判断执行 `python src/main.py` 时顶层 `app = PythonServer(...)` 与入口块中的 `uvicorn.run(...)` 是否都会运行，并说明 `__name__` 的值。

### 2026-09-02 第 9 次：ENT-PY-01 变量展开变式与模块定位

- 本次目标：通过 Celery 同构变式确认 Shell 变量展开，并定位 `main:app` 中的 `main` 模块。
- 实际完成：学习者将 `$EXEC -A "$PACKAGE".celeryd.task worker` 独立展开为等效命令 `celery -A "src".celeryd.task worker`；确认引号与后续字面量会拼成同一参数 `src.celeryd.task`，Shell 变量展开门禁通过。随后使用 `importlib.util.find_spec` 只定位而不导入，确认从 `src` 工作目录解析的 `main` 是 `src/main.py`。未导入应用、未启动进程或外部服务，未修改企业仓库源码。
- 验证证据：学习者变式答案；`find_spec("main").origin` → `/Users/yang/deepfinance_project/pyserver/src/main.py`。
- 门禁评分：理解 1/2，独立实现 2/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：解释 `main:app` 的模块/对象关系，以及 import 时模块顶层代码与 `__main__` 守卫的执行边界。
- 使用的最高提示等级：2。
- 下一次会话：判断 Gunicorn 导入 `main:app` 时 `src/main.py` 的顶层代码和 `if __name__ == '__main__'` 分别是否执行。

### 2026-09-02 第 8 次：ENT-PY-01 展开 Gunicorn 启动命令

- 本次目标：从 `serverctl.sh` 的变量定义还原真正交给 Shell 执行的 Gunicorn 命令。
- 实际完成：学习者首次尝试将脚本名、`CONFIG=...` 赋值形式和 `APP:...` 混入最终命令；随后使用只打印、不执行进程的 Bash 演示，确认 `$EXEC -c $CONFIG $APP` 展开为 `gunicorn -c gunicorn.conf.py main:app`。解释了变量定义只负责保存值，变量使用位置只替换为值本身。未启动 Gunicorn 或任何外部服务，未修改企业仓库源码。
- 验证证据：Bash `printf` 展开结果为 `command: gunicorn -c gunicorn.conf.py main:app`。
- 门禁评分：理解 1/2，独立实现 0/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：独立展开 Shell 变量；区分脚本管理入口与脚本内部真正启动的可执行程序。
- 使用的最高提示等级：4。
- 下一次会话：完成一个 Celery 启动命令的同构变量展开变式。

### 2026-09-02 第 7 次：ENT-PY-00 缺口顺延与 ENT-PY-01 启动前检查

- 本次目标：完成失败分类变式，并在不启动服务的前提下确认启动工具和脚本可用性。
- 实际完成：学习者仍未能独立区分测试收集失败、断言失败与解释器语义变化，因此 `ENT-PY-00` 不标记通过，缺口登记为待复习；为保持实操主线，顺延进入相邻的 `ENT-PY-01`。确认项目环境中的 Gunicorn 与 Celery 命令可用，并用 Bash 只解析模式检查 `serverctl.sh`、`workerctl.sh`、`startup.sh`，未执行任何启动逻辑。
- 验证证据：`.venv/bin/gunicorn --version` → 22.0.0；`.venv/bin/celery --version` → 1.1.31；`bash -n src/serverctl.sh src/workerctl.sh src/startup.sh` → 退出码 0、无语法错误。
- 门禁评分：理解 1/2，独立实现 0/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：`ENT-PY-00` 的失败阶段分类；`ENT-PY-01` 从真实 shell 变量还原进程启动命令。
- 使用的最高提示等级：4。
- 下一次会话：根据 `EXEC=gunicorn`、`CONFIG=gunicorn.conf.py`、`APP=main:app` 展开完整命令，并解释 `main:app`。

### 2026-09-02 第 6 次：ENT-PY-00 修复本地 ABI 并归并测试失败

- 本次目标：验证 NumPy 1.x 本地兼容修复，取得绿色离线基线，并把大量测试失败归并为可解释的根因。
- 实际完成：学习者将 NumPy 固定为 1.26.4，pandas 1.5.3 成功导入；`pip check` 通过。validator/cache 组合测试完整执行为 66 通过、57 失败、10 个 fixture 错误。最小双版本复现确认 Python 3.9 中 `str + Enum` 的 f-string 输出枚举值，而 Python 3.11 输出成员名，导致 validator 测试辅助函数生成 `$key_RSType.string...$` 而 fixture 使用 `$key_string...$`，大量结果来自同一版本语义根因。缓存唯一失败来自 2025 年实现把同分排序从“最后加入优先”改为稳定排序的“最先加入优先”，但 2024 年测试未同步。随后运行三个不依赖已知漂移断言的缓存测试，全部通过。未修改企业仓库源码，未启动外部服务。
- 验证证据：`python -c 'import numpy, pandas; ...'` → NumPy 1.26.4 / pandas 1.5.3；`.venv/bin/python -m pip check` → `No broken requirements found`；validator/cache 组合测试 → `57 failed, 66 passed, 10 errors`；Python 3.9/3.11 最小 Enum 对照分别输出枚举值/成员名；三个 RankedCache 基础测试 → `3 passed in 0.01s`；Git diff/blame 证明缓存实现与旧测试预期漂移。
- 门禁评分：理解 1/2，独立实现 2/2，调试与测试 2/2，工程质量 2/2。
- 仍需加强：学习者暂时将三类问题都归因于“引用包的位置不同”；已提供标准答案，需通过不同场景的简化变式确认迁移。
- 使用的最高提示等级：4。
- 下一次会话：完成一个不涉及当前项目名称的失败分类变式；通过后将 `ENT-PY-00` 标为已通过，并进入 `ENT-PY-01` 的安装后启动链阅读。

### 2026-09-02 第 5 次：ENT-PY-00 定位 pandas/NumPy ABI 冲突

- 本次目标：扩展最小离线测试基线，并区分依赖元数据一致与运行时二进制兼容。
- 实际完成：纠正失败阶段类比：先前缺少 `rsa` 属于类似 Java classpath 的收集/导入失败，`test_multi_bind_env` 属于测试方法执行后的断言失败；运行 validator/cache 测试时再次在收集阶段失败。实际版本为 pandas 1.5.3、NumPy 2.4.6；pandas 元数据对 Python 3.11 仅声明 `numpy>=1.23.2` 而没有 `<2` 上界，因此 `pip check` 通过，但 pandas C 扩展在导入时报告 ABI 尺寸不匹配。未修改企业仓库源码，未启动外部服务。
- 验证证据：`.venv/bin/python -m pytest tests/test_validator.py tests/lib/test_cache.py -q` → 收集 `test_validator.py` 时导入 pandas 抛出 `ValueError: numpy.dtype size changed`；包元数据显示 pandas 1.5.3 / NumPy 2.4.6 及无 NumPy 2 上界的依赖声明。
- 门禁评分：理解 1/2，独立实现 1/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：区分缺包、Python 包版本约束和本地扩展 ABI 三层问题；完成 NumPy 1.x 本地兼容固定并复测。
- 使用的最高提示等级：2。
- 下一次会话：执行 `python -m pip install 'numpy==1.26.4'`，验证 pandas/NumPy 导入，再重跑两组离线测试。

### 2026-09-02 第 4 次：ENT-PY-00 建立可运行测试基线

- 本次目标：验证 Python 3.11 项目环境的解释器、依赖完整性与最小离线测试结果。
- 实际完成：学习者重建 `.venv` 并完成依赖安装；实际核对为 CPython 3.11.15 arm64，`pip check` 无损坏依赖，关键版本符合项目约束；运行 `tests/test_option.py`，19 个测试完成执行，其中 18 个通过、1 个断言失败。CodeGraph 与 Git blame 显示 `server.base` 当前具有普通默认值，缺少两个绑定环境变量时不会抛 `KeyError`；对应测试断言写于 2023 年，而默认值实现于 2025 年修改，属于实现/测试漂移，不是解释器、依赖或外部环境失败。未启动任何服务，未修改企业仓库源码。
- 验证证据：`.venv/bin/python` → Python 3.11.15 / arm64；`.venv/bin/python -m pip check` → `No broken requirements found`；`.venv/bin/python -m pytest tests/test_option.py -q` → `18 passed, 1 failed`；`git blame` 显示测试断言与当前默认值来自不同年份的提交。
- 门禁评分：理解 1/2，独立实现 2/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：用自己的话说明为何当前失败是旧测试预期与新实现不一致，并区分收集阶段失败与测试执行阶段失败。
- 使用的最高提示等级：2。
- 下一次会话：完成失败分类讲解，再运行 `tests/test_validator.py` 与 `tests/lib/test_cache.py`，决定 `ENT-PY-00` 门禁是否通过。

### 2026-09-02 第 3 次：ENT-PY-00 安装兼容解释器

- 本次目标：理解“系统解释器”和“项目虚拟环境”的区别，并安装不覆盖现有 Python 的 3.11 解释器。
- 实际完成：学习者使用 `uv` 安装 CPython 3.11.15 macOS arm64；保留了 pyenv Python 3.12.0 与系统 Python 3.9.6，尚未重建项目 `.venv`、安装依赖或启动服务。
- 验证证据：`uv python list --only-installed` 显示 `cpython-3.11.15-macos-aarch64-none`，可执行文件链接位于 `/Users/yang/.local/bin/python3.11`，实际解释器位于 `uv` 管理目录。
- 门禁评分：理解 1/2，独立实现 2/2，调试与测试 1/2，工程质量 2/2。
- 仍需加强：确认安装一个 Python 解释器不会自动改变已有 `.venv`；下一步需要显式选择 3.11 创建虚拟环境。
- 使用的最高提示等级：2。
- 下一次会话：清理仅含失败安装残留的 3.9 `.venv`，用 `uv venv --python 3.11 --seed` 重建并验证解释器。

### 2026-09-02 第 2 次：ENT-PY-00 首次完整依赖安装

- 本次目标：按真实项目流程创建隔离环境并尝试安装开发与生产依赖，定位第一处安装阻塞。
- 实际完成：学习者使用系统 Python 3.9.6 创建项目 `.venv` 并执行全量安装；`deepfos-celery` wheel 构建成功，安装最终停在 pandas；进一步确认机器为 Apple Silicon arm64、项目在 Python 3.9 下精确选择 `pandas==1.2.0`，而公共发行文件没有 CPython 3.9 的 macOS arm64 wheel，pip 因而尝试源码构建。安装事务未留下 pandas、pytest、rsa 或 deepfos-celery 等目标包；未启动任何服务，未修改企业仓库源码。
- 验证证据：`python -m pip install -r requirements-dev.txt` → `Failed building wheel for pandas`；`.venv/bin/python` → Python 3.9.6 / arm64；公开 PyPI 发行元数据仅提供 CPython 3.9 macOS x86_64 wheel；`deepfos-celery 1.1.31` 在公共 PyPI 可查询。
- 门禁评分：理解 1/2，独立实现 1/2，调试与测试 1/2，工程质量 1/2。
- 仍需加强：区分私有包问题、缺少二进制 wheel、源码构建失败三类安装问题；确认真实运行版本后选择兼容解释器。
- 使用的最高提示等级：2。
- 下一次会话：决定是否安装 Python 3.11 并重建 `.venv`；不修改 `requirements.txt`，重新安装后运行 `pip check` 与最小 pytest。

### 2026-09-02 第 1 次：ENT-PY-00 环境与依赖诊断

- 本次目标：核对项目解释器与已有环境，理解依赖文件和 Python 版本标记，并运行最小离线测试。
- 实际完成：确认终端 `python` 为 pyenv Python 3.12.0，`python3` 为系统 Python 3.9.6，pyenv 仅登记 3.12.0；确认 `requirements-dev.txt` 通过 `-r src/requirements.txt` 加载生产依赖，并按当前解释器计算环境标记；检查 IDE 指向 `/Users/yang/PycharmProjects/WelcomeScreen/.venv`，但该环境使用与项目不兼容的新一代框架栈；未安装、升级依赖，未启动任何外部服务，未修改企业仓库源码。
- 验证证据：`python -m pytest tests/test_option.py -q` → Python 3.12 缺少 pytest；`python3 -m pytest tests/test_option.py -q` → Python 3.9.6 缺少 pytest；`/Users/yang/PycharmProjects/WelcomeScreen/.venv/bin/python -m pytest tests/test_option.py -q` → pytest 加载 `tests/conftest.py` 时因缺少 `rsa` 停在收集阶段，0 个测试执行。
- 门禁评分：理解 1/2，独立实现 0/2，调试与测试 1/2，工程质量 1/2。
- 仍需加强：准确表述兼容版本范围；解释 pytest 的 `conftest.py` 自动发现，以及“收集失败”和“测试断言失败”的区别。
- 使用的最高提示等级：1。
- 下一次会话：学习者用自己的话解释本次收集链和失败分类，并给出一个 Python 与 Java 测试环境的差异；通过后收口 `ENT-PY-00`。

### 2026-09-02 路线初始化（不计学习会话）

- 调整原因：基础 Demo 路线已经收口，学习者希望通过现有企业级 Python 项目建立真实开发能力。
- 实际完成：使用 CodeGraph 只读扫描项目入口、配置、请求依赖、外部 API、Celery、测试和部署结构；创建 `ENT-PY-00`～`ENT-PY-08` 路线，并将企业阶段与基础阶段分开记录。
- 未计完成：本次只做路线和 Skill 初始化，没有执行企业项目课程、测试或代码改动。
- 下一次会话：从 `ENT-PY-00` 开始核对环境并建立最小测试基线。
