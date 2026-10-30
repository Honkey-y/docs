# TabTin Community 注册验证码发送失败排查说明

日期：2026-09-09  
对象：私有化服务端打包、部署负责人

## 结论

本次注册验证码请求已经到达服务端，服务端返回的具体错误是：

> 邮件服务器地址(EMAIL_HOST)未配置

当前已确认的阻断点是服务端邮件发送配置缺失。请优先修复服务端运行环境中的 SMTP 配置；仅针对这一错误，不需要重新打客户端包。

## 现象和证据

用户在 macOS 的 `tabtin-community` 客户端注册时，点击发送验证码，界面提示“发送验证码失败”；Charles 未捕获到对应请求。

检查本机社区版日志：

```text
~/Library/Logs/TabTin Community/main.log
```

2026-09-09 当地时间 18:19 至 18:25，多次出现同一错误，其中一条为：

```text
[2026-09-09 18:25:53.629] [error] (Main)
[Renderer:ERROR] [API] Request failed:
{"url":"/auth/send-verification-code","method":"POST","error":"邮件服务器地址(EMAIL_HOST)未配置"}
```

仓库中对应的服务端报错位置：

```text
apps/tabtin_django/apps/services/email/services/factory.py
_validate_tencent_config()
```

该函数在邮件服务配置的 `host` 为空时抛出上述错误。因此，这次失败已有服务端业务错误返回，不是仅凭客户端通用提示推测。

## 为什么 Charles 没抓到

当前仓库实现中，这条 API 请求通过 Electron 主进程代理，最终使用 Node 的 `http` / `https` 模块发送，而非页面直接发出的浏览器请求。

这类请求不会自动像浏览器请求一样使用 Charles 系统代理。因此，Charles 没记录不能作为“请求未到服务端”的判断依据。

相关实现：`apps/tabtin-electron/src/main/api-proxy.ts` 中的 `makeRequest()`。

## 请服务端负责人检查

1. 检查实际运行的 Django 服务是否获得完整、非空的邮件配置。不要只检查打包机器或配置模板，需确认配置已注入运行容器或进程。
2. 检查是否有空值覆盖。例如 `EMAIL_HOST=` 会覆盖代码中的默认地址，仍会触发本次错误。
3. 按实际邮件服务商填写以下配置，确认 SMTP 服务已开通、账号或授权码有效，发件人地址符合服务商要求。

```dotenv
# 示例：465 端口隐式 SSL。地址和账号需替换为实际服务商配置。
EMAIL_HOST=smtp.example.com
EMAIL_PORT=465
EMAIL_USE_SSL=True
EMAIL_USE_TLS=False
EMAIL_HOST_USER=sender@example.com
EMAIL_HOST_PASSWORD=<SMTP密码或授权码>
DEFAULT_FROM_EMAIL=sender@example.com
```

如使用 587 端口的 STARTTLS，应按服务商要求调整端口和 SSL/TLS 设置，不要同时开启两者。密码、授权码不要提交到仓库或写入排查回执。

仓库部署模板 `deployment/compose.env.example` 已列出部分邮件配置，但注释只提到 TabMail；本次证据表明注册验证码发邮件也依赖这套配置，建议同步完善部署说明。

若实际使用仓库的 `docker-compose.deploy.yml`，检查其引用的 `deployment/compose.env`。如果采用其他私有化打包方式，以该部署实际使用的环境变量、配置文件或 Secret 注入方式为准。

修改后使配置在实际运行服务中生效。Docker Compose 的环境变量变更通常需要重新创建相关容器，单纯 `restart` 不会更新容器创建时的环境变量；如邮件任务由 worker 执行，也需同步更新对应 worker。

## 验收与回执

- 在运行服务中确认邮件地址、端口、加密方式、发件人配置正确；用户名和密码只确认是否已配置，不输出密码。
- 确认服务端可连接 SMTP 地址及端口，并能完成认证和发信。
- 使用测试邮箱在社区版注册页面重新发送验证码，确认界面成功、邮箱收到邮件、验证码能完成注册。
- 检查对应时刻的服务端日志，确认不再出现 `EMAIL_HOST` 缺失错误；若出现新的认证、连接或发件人错误，继续按新错误处理。
- 回执建议包含服务端版本、配置生效方式、验收时间及注册结果，不包含密码或验证码。

## 排查边界和后续改进

本次已读取客户端实际日志并核对仓库实现；未登录私有化服务器、未修改配置、未执行修复后的注册验收。目前只能确认邮件地址缺失是已暴露的阻断点，其他 SMTP 配置是否齐全仍需服务端验证。

启动早期另有访问 `192.168.31.230:6060` 的 `EHOSTUNREACH` 记录，涉及其他接口；后续验证码请求已有上述业务错误返回，不应把早期网络错误当成本次发码失败的直接原因。如仍间歇性连接失败，再单独检查局域网连通性。

客户端仅展示“发送验证码失败”，没有呈现这次日志中的具体原因，建议后续改善错误提示；服务端部署流程也可增加邮件配置完整性检查，避免问题到注册时才暴露。
