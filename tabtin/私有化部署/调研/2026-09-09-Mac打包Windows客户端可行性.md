# Mac 打包 Windows 客户端可行性

> 状态：已确认（x64 已真实出包；真机验收待完成）
> 最后更新：2026-09-10
> 适用范围：192.168.31.230 / private/260907 / Windows x64 与 ia32
> 关联文档：[新机部署与使用记录](../代码执行计划/2026-09-09-新机部署与使用记录.md)

日期：2026-09-09。只读调研，未安装 Wine、未触发构建。核对项目版本为 electron-builder 25.1.8、Electron 41.0.0；不把最新 v27 配置套到现有版本。

## 2026-09-10 实施结论

原调研路线已落地：同一台新 Mac `mini@192.168.31.230` 使用 Wine 11.0、Windows Python 3.12.13/PyInstaller 和 Windows x64 预编译组件，成功生成快速未签名 NSIS 安装包并上传 `Share/private/windows`。没有新增 Windows 物理打包机。

已检查最终 EXE 提取载荷、PE 架构、ASAR 完整性、版本、客户五项地址。NSIS 自带 x86 提权助手为明确例外，客户端原生组件要求 x64。成功任务 `bed7f67d-d5b0-4dcf-aa41-e16132f582fd`，版本 `1.0.0-win64.5`，证据见[说明](../说明.md)和[部署记录](../代码执行计划/2026-09-09-新机部署与使用记录.md)。

Windows 快速模式跳过源码映射，完整模式在本地保存源码映射；均未签名，不涉及 Apple 公证。完整模式尚未另行实际出包，ia32 未实现。Windows 真机安装和业务验收仍由测试人员负责，Wine 工具探针不替代真机验收。控制台身份已由统一 token 改为 SaaS 实时验证。

## 原始可行性调查（2026-09-09 历史快照）

以下“未装 Wine”“没有成功安装包”“尚未接线”等是调查当天状态，已被上方实际构建结果更新；保留一手来源和推导供追溯。

## 结论

同事的说法在通用 Electron/NSIS 层面成立：Mac 可以交叉生成 Windows 安装包，并不必然需要另一台物理打包机。但当前 TabTin 还不能据此认定能生成可交付 Windows 客户端；真正的限制是项目携带的 Windows 原生组件与打包脚本，而不仅是安装 Wine。

优先验证 Windows x64。Windows ia32 暂不能承诺：Electron 41.0.0 官方确实发布了 win32-ia32 二进制，但项目 node-pty 1.1.0 缺少 ia32 预编译物（主任务已核对），Electron 能打并不等于整个客户端能运行。

## 一手证据与条件

1. **跨平台与原生依赖**：25.1.8 官方文档明确 macOS/Linux 可以本地构建 Windows（Appx 除外），提供 `--win --x64` 与 `--ia32`。同时明确原生依赖需要目标平台编译或预编译包。`npmRebuild: false` 只是跳过重编译，不会把 darwin `.node` 转成 Windows `.node`。
   - https://github.com/electron-userland/electron-builder/blob/electron-builder%4025.1.8/pages/multi-platform-build.md
2. **Wine 与 Apple Silicon**：25.1.8 的 `wine.ts` 将调用委托给 `app-builder-bin`，依赖钉在 `5.0.0-alpha.10`。上游 app-builder 的 Wine 实现按 Catalina 及以上选择 `wine-4.0.1-mac/bin/wine64`，可通过 `USE_SYSTEM_WINE=true` 使用系统 Wine；没有 x64 helper 时会拒绝在 Catalina+ 执行 ia32 helper。此限制是“执行构建辅助程序”，不等同于“禁止生成 ia32 目标”。该默认工具链年代较老，Apple Silicon 上的 x86_64 辅助程序需要 Rosetta/兼容 Wine 路径的实际验证，文档不能代替运行探针。
   - https://github.com/electron-userland/electron-builder/blob/electron-builder%4025.1.8/packages/app-builder-lib/src/wine.ts
   - https://github.com/electron-userland/electron-builder/blob/electron-builder%4025.1.8/packages/builder-util/package.json
   - https://github.com/develar/app-builder/blob/7004925f95d8f034fc88d7e782c9aa7583debb8e/pkg/wine/wine.go
   - 边界：最后一项是上游源码快照，尚未证明与已发布 alpha.10 二进制的构建 SHA 一致；应以真实工具执行结果为准。
3. **NSIS 不全依赖运行 Windows 安装器**：25.1.8 在 Mac 使用 mac/makensis；Catalina+ 用 UninstallerReader 提取卸载器，失败才退到 VM。不能把 Windows 安装测试当成已完成。
   - https://github.com/electron-userland/electron-builder/blob/electron-builder%4025.1.8/packages/app-builder-lib/src/targets/nsis/NsisTarget.ts
4. **32 位 Electron 的事实**：Electron 41.0.0 release 存在 `electron-v41.0.0-win32-ia32.zip`，因此项目当前 Electron 版本本身不是 ia32 阻碍。每个 native addon、Python 与附带工具还须逐一核对。
   - https://github.com/electron/electron/releases/tag/v41.0.0
5. **Windows 签名与 Apple 公证不同**：25.1.8 支持 macOS 上通过 osslsigncode 与 Windows `.pfx/.p12` 签 Windows exe。依赖 Windows 证书存储的 certificateSubjectName / certificateSha1 仅 Windows 可用。SaaS 的 Apple Developer ID 与 Apple 公证材料不能作为 Windows Authenticode 证书。测试包可以不签，但必须如实标记；正式签名另查现有 Windows 签名材料与调用方式。
   - https://github.com/electron-userland/electron-builder/blob/electron-builder%4025.1.8/packages/app-builder-lib/src/codeSign/windowsSignToolManager.ts

## 结合当前项目的判断

以下为主任务代码扫描结果，非上游官方通用限制：

- 构建脚本只接受 arm64/x64；ia32 需要完整接线，不能只加下拉选项。
- node-pty 的 Windows x64 预编译物存在，ia32 缺失；其他原生依赖仍需逐个匹配 Electron ABI、操作系统、CPU 架构。
- 内置 Python Windows runtime 默认要求 Windows 宿主（允许预置归档）；PyInstaller filegen 构建要求同宿主/同架构。需得到与源码、依赖版本可追溯匹配的 Windows 产物，或选择同一 Mac 内 Windows 虚拟机等执行方案。不能用宿主产物冒充。
- 新机未装 Wine/Docker。Wine 只解决部分 Windows 工具执行问题，不能自动解决全部 Python/native 组件。

## 建议的验证顺序与未覆盖边界

Mac Intel 当前任务完成后，先做 Windows x64 依赖清单与工具探针，再用隔离构建目录出一个不签名测试包；验证包内所有 `.node`/exe/DLL 是 Windows x64，而非 Mach-O/darwin 文件，验证配置注入与完整资源。之后在 Windows 环境安装、启动，测试终端（node-pty）、文件生成/Python、卸载及升级。无需新增物理打包机，但最终 Windows 安装验收仍需要 Windows 执行环境；在 Wine 中启动不等于 Windows 真机验收。

目前没有成功 Windows 安装包、没有完整原生组件审计、没有 Windows 运行/安装结果，因此只能确认技术路线有条件可行，不能宣称本平台已支持 Windows x64/ia32。


## 本仓库与机器证据

代码固定于 `private/260907` 提交 `3f102680a81741ca49dcb12f6bf4df8dfbc708c8`。CodeGraph 查询未覆盖相关 Shell 构建脚本，已用固定 Git 提交的定点读取补齐，没有重建索引。

| 位置 | 核对结果与影响 |
| --- | --- |
| `apps/tabtin-electron/scripts/build-packaged-app.sh:146` | ARCH 只接受 arm64/x64；ia32 在入口报错 |
| 同文件 `:412` | 代码本身允许 host/target 不同，但明确提醒 node-pty 等可能残留宿主二进制，不能将成功封装视为可交付 |
| 同文件 `:1449` | Go CLI 已按目标 windows/amd64 构建；这一块已有跨平台基础 |
| 同文件 `:1617` | PyInstaller 文件生成程序只允许同宿主、同架构现场构建；可选预置目标产物。缺失可被警告放过，不等于功能完整 |
| 同文件 `:2013` | 可选 native 包补齐只针对 darwin 的跨架构，未补 Mac → Windows 对应机制 |
| `scripts/electron/package/build-python-runtime-for-target.sh:100` | Windows Python runtime 无匹配归档时仅允许 Windows 宿主构建；需准备对应归档和依赖指纹，不能复制 Mac runtime |
| `apps/tabtin-electron/package.json:375` | 全局 files 排除了 sharp-win32 等目录；需连同目标平台资源规则核对，不能只安装依赖 |
| `apps/tabtin-electron/scripts/audit-packaged-artifact.mjs:903` | Windows 原生资产规范目前重点检查 node-pty conpty.node，不能替代整个 Windows 功能集验收 |
| 新机 `node-pty/prebuilds` | win32-x64 实际有 conpty.node、pty.node、winpty 等文件；无 win32-ia32 |
| 新机缓存 | 没有 Windows Python runtime、Windows filegen；Wine/Docker 未安装。Rosetta 已安装且 x64 执行探针通过 |
| 新私有控制台 `src/machine.mjs`、`src/plan.mjs` | Mac targets 只开放两种 Mac 架构，Community Windows 制品审计尚未接线；这是当前实现限制，不是 macOS 的能力上限 |

以上仓库源码可从[固定源码提交](https://github.com/larchiveai/TabTin/tree/3f102680a81741ca49dcb12f6bf4df8dfbc708c8)按路径定位；本任务实现位于 `codex/private-pack-machine`。

## 建议路线：保留一台物理打包机

优先评估 Mac + Wine + 版本固定的 Windows 预编译依赖，先做 Windows x64 测试包，保持原串行队列、客户配置、统一 token 和 Share/private/windows 交付位置。不接入此前建议的 Windows Runner。缺少的私有二进制若能从已交付制品或已知可信归档获取，必须核对源码版本、依赖指纹和校验和，不直接复制旧安装目录混装。

如果 Windows Python/PyInstaller 等组件无法通过预编译资产或 Wine 完整准备，再评估同一台 Mac 内的 Windows 环境；这是备选方案，不是本轮已获批准的新增部署，不能预先承诺 16 GB 机器的运行表现。

```mermaid
flowchart LR
  A[当前 Mac 控制台] --> B[固定源码与客户配置]
  B --> C[Windows x64 目标依赖与运行时]
  C --> D[Wine 与 NSIS 封装]
  D --> E[包内地址 / PE架构 / 原生组件审计]
  E --> F[Share/private/windows]
  F --> G[测试人员 Windows 安装与功能验收]
```

验收顺序：Wine/NSIS 最小探针 → Windows x64 依赖清单与 PE 架构检查 → 完整测试包 → 配置与关键资源审计 → 现有 Windows 测试电脑安装、启动、终端、文件生成、卸载/升级。测试电脑不是新增打包机。ia32 待 native/Python/CLI 全链路具备相应产物后再独立评估，不作为 x64 首轮的承诺。

本轮只读研究，没有安装 Wine、没有执行 Windows 构建、没有修改控制台目标列表；不能把这份调研当作 Windows 已支持的交付证明。
