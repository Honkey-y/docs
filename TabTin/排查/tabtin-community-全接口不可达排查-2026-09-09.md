# TabTin Community 全接口不可达排查

时间：2026-09-09 19:21–19:24（Asia/Shanghai）

## 当前结论

故障已收敛到社区版客户端进程访问局域网的链路。服务端当前可达，社区版重启后仍稳定出现 `EHOSTUNREACH`。最强线索是 macOS 本地网络识别时，将社区版的可执行文件 UUID 关联到了正式版。

**已确认存在身份映射冲突；尚未通过修正后的对照测试证明它是唯一根因。** 不宜将本次问题归为 SMTP 配置或直接要求服务端重打包。

## 实测证据

### 1. 同机直连服务端正常

绕过系统代理执行：

```sh
curl --noproxy '*' --connect-timeout 4 --max-time 8 http://192.168.31.230:6060/health
curl --noproxy '*' --connect-timeout 4 --max-time 8 -o /dev/null -w '%{http_code}\n' http://192.168.31.230:6060/api/marketplace/discovery-patterns
```

结果：健康接口 HTTP 200、`status=healthy`；发现接口 HTTP 200。Ping 3 次全部成功，路由走 en0。

### 2. 社区版重启仍失败

退出并重新启动 `/Applications/tabtin-community.app` 后，新进程于 19:23:14 起出现：

```text
网络请求错误(GET http://192.168.31.230:6060/api/marketplace/discovery-patterns):
Error: connect EHOSTUNREACH 192.168.31.230:6060 - Local (192.168.31.104:61754)
```

同样影响 OSS 配置；此前项目、工作区、通知、退出登录和发送验证码等请求也报相同错误。错误发生在连接阶段，没有收到 HTTP 业务响应。

原始日志：`~/Library/Logs/TabTin Community/main.log`。

### 3. macOS 对同一网络访问给出不同应用身份

系统 `UserEventAgent` 的 `com.apple.networkextension` 日志反复成对出现：

```text
2026-09-09 19:23:14.362 LocalNetwork: found bundle id com.tabtin.community by PID
2026-09-09 19:23:14.364 LocalNetwork: found bundle id com.tabtin.app by UUID 4C4C4450-5555-3144-A175-A5A5EB513DF3
```

读取命令：

```sh
/usr/bin/log show --last 10m --style compact --predicate 'process == "UserEventAgent" AND subsystem == "com.apple.networkextension"'
```

### 4. 两个安装包的主程序 UUID 完全相同

```sh
dwarfdump --uuid /Applications/tabtin-community.app/Contents/MacOS/tabtin-community /Applications/TabTin.app/Contents/MacOS/TabTin
```

结果：两者都是 `4C4C4450-5555-3144-A175-A5A5EB513DF3`（arm64），但 Bundle ID 分别为 `com.tabtin.community` 和 `com.tabtin.app`。

系统版本：macOS 15.7.2。社区包签名为 adhoc，无 TeamIdentifier；签名完整性校验通过，且 Info.plist 已有 `NSLocalNetworkUsageDescription`，不能简单归因于缺少权限说明或签名损坏。

系统设置 → 隐私与安全性 → 本地网络：列表只显示已开启的 TabTin，没有单独显示 Community。单凭该 UI 不能证明 Community 已获得独立授权。

## 下一步建议

客户端打包负责人优先检查不同发行版共用 Electron 主程序 UUID 的情况，验证应用身份、主程序及相关 Helper 身份、签名与本地网络权限的关联。需要以修正后的测试包或其他受控对照证明原因，不要仅改 Bundle ID 后假定已解决。

验收必须覆盖正式版与 Community 同机安装：两者能分别获得正确的本地网络权限；社区版重启后发现接口成功；系统日志不再把 Community 的网络访问映射成正式版；再走登录和页面加载流程。

若测试需要调整系统本地网络权限，先明确受影响应用。当前没有修改系统权限、清理授权数据库、卸载正式版、重签名或修改任何安装包。

## 与先前验证码错误的关系

18:25 的验证码请求曾收到服务端“邮件服务器地址(EMAIL_HOST)未配置”，说明当时该次请求已到服务端。19:19 之后的本次现象是连接阶段不可达，两者不能混为同一个问题。恢复网络后，邮件配置仍需独立验收。
