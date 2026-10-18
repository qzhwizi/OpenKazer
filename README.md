# Kazer Client (1.8.9 Forge Mod)

Kazer Client 是一个基于 **CheatBreaker (CB) 客户端** 反混淆、重构而来的 Minecraft 1.8.9 Forge 客户端模组。原版 CB 以混淆类名（`a.java`、`b.java`、`c/a.java` 等）发布，本仓库已将全部顶层类与包名还原为语义化命名（如 `CheatBreakerClient`、`ModuleManager`、`HudEditorScreen` 等），并附带一键启动脚本，可直接作为 **Forge 1.8.9 模组** 或 **独立启动包** 运行。

> \*\*免责声明\*\*：本项目仅供学习与研究，原 CheatBreaker 客户端版权归其原作者所有。请勿用于任何违反 Minecraft EULA 或服务器规则的用途。

## 功能概览

* 全新主菜单 / 全景背景 / 账号系统界面
* 可视化 HUD 编辑器（拖拽、锚点、缩放、Profile 保存）
* 内置模块：按键显示、BossBar、药水效果、物品数量、击杀提示、聊天增强、旧版动画、平滑/FPS 优化等
* 饰品（Cosmetics）系统与跑酷/击杀特效
* 运行时转换器（`RuntimeTransformer`）负责对原版类做字节码注入# Kazer Client (1.8.9 Forge Mod)

Kazer Client 是一个基于 **CheatBreaker (CB) 客户端** 反混淆、重构而来的 Minecraft 1.8.9 Forge 客户端模组。原版 CB 以混淆类名（`a.java`、`b.java`、`c/a.java` 等）发布，本仓库已将全部顶层类与包名还原为语义化命名（如 `CheatBreakerClient`、`ModuleManager`、`HudEditorScreen` 等）

> **免责声明**：本项目仅供学习与研究，原 CheatBreaker 客户端版权归其原作者所有。请勿用于任何违反 Minecraft EULA 或服务器规则的用途。



- 高仿Lunar Client


## 构建环境要求

| 项目 | 要求 |
|---|---|
| 构建环境 | Windows + **JDK 8**（推荐 Eclipse Temurin 8），`JAVA_HOME` 指向 JDK 8 |
| 运行环境 | Minecraft **1.8.9** + Forge **11.15.1.1902**（1.8.9），可选 OptiFine **HD U M5** |
| 显存/内存 | 建议 `-Xmx4096M` 以上 |

```bat
安装 JDK 8 并设置环境变量

:: 2. 在仓库根目录执行
gradlew.bat build

:: 3. 构建产物
build\libs\KazerClient-0.1.39.jar
```

- 首次构建会自动下载 ForgeGradle 依赖，需要联网。
- 构建成功标志：`BUILD SUCCESSFUL`。

## 使用方式 ：作为 Forge 模组

1. 用普通启动器安装 **Forge 1.8.9 (11.15.1.1902)**。
2. 将 `KazerClient-0.1.39.jar` 放入 `.minecraft\mods\`。
3. （可选）将 OptiFine `OptiFine_1.8.9_HD_U_M5.jar` 放入 `.minecraft\mods\1.8.9\`。
4. 选择 Forge 1.8.9 的版本启动即可，无需额外配置。


## 仓库结构

```
Kazer_Src\
├── build.gradle            # ForgeGradle 构建脚本（含 coremod MANIFEST 修复）
├── gradlew / gradlew.bat   # Gradle Wrapper
├── launcher\start-kazer.bat# 一键启动脚本模板
└── src\main\
    ├── java\               # 全部源码（反混淆后）
    │   ├── com\cheatbreaker\client\...   # CB 客户端主体（GUI/模块/网络等）
    │   └── cn\kazer\client189\...        # 1.8.9 适配层（运行时转换器、IME、旧动画等）
    └── resources\
        ├── mcmod.info
        └── META-INF\MANIFEST.MF
```

## 反混淆说明

- 原始 CB jar 中所有单字母混淆类（`a`~`z`、`A` 及单字母包）已全部重命名为语义化名称，字段/方法名保留混淆原名（仅重命名类与包，保证最小改动）。
- 部分原版类引用通过 `RuntimeTransformer` 在运行时注入，生产环境使用 SRG 名（`field_*`/`func_*`）匹配，源码中以 `is(name, "mcp名", "srg名")` 双名形式出现。
- 构建脚本中的 `FIX 3`/`FIX 4` 注释为已修复的打包问题（retromapping 任务禁用、coremod MANIFEST 写入），请勿删除。

## 许可

- 本仓库代码基于反编译的 CheatBreaker 客户端整理，**仅供学习交流**，不提供任何担保。
- Minecraft 版权归 Mojang / Microsoft 所有；Forge 属于 MinecraftForge 团队；OptiFine 属于 sp614x。
（按键状态、标题动画、聊天行标签等）

## 环境要求

|项目|要求|
|-|-|
|构建环境|Windows + **JDK 8**（推荐 Eclipse Temurin 8），`JAVA\_HOME` 指向 JDK 8|
|运行环境|Minecraft **1.8.9** + Forge **11.15.1.1902**（1.8.9），可选 OptiFine **HD U M5**|
|显存/内存|建议 `-Xmx4096M` 以上|

## 构建教程

```bat
:: 1. 安装 JDK 8 并设置环境变量
set JAVA\_HOME=C:\\Program Files\\Eclipse Adoptium\\jdk-8.0.432.6-hotspot
set PATH=%JAVA\_HOME%\\bin;%PATH%

:: 2. 在仓库根目录执行
gradlew.bat build

:: 3. 构建产物
build\\libs\\KazerClient-0.1.39.jar
```

* 首次构建会自动下载 ForgeGradle 依赖，需要联网。
* 构建成功标志：`BUILD SUCCESSFUL`。

## 使用方式 A：作为 Forge 模组（推荐）

1. 用普通启动器安装 **Forge 1.8.9 (11.15.1.1902)**。
2. 将 `KazerClient-0.1.39.jar` 放入 `.minecraft\\mods\\`。
3. （可选）将 OptiFine `OptiFine\_1.8.9\_HD\_U\_M5.jar` 放入 `.minecraft\\mods\\1.8.9\\`。
4. 选择 Forge 1.8.9 的版本启动即可，无需额外配置。

## 使用方式 B：一键启动包

`launcher/start-kazer.bat` 是独立启动脚本模板，把它与以下内容放进同一个文件夹即可一键启动（游戏数据保存在该文件夹内，与主 `.minecraft` 隔离）：

```
KazerLauncher\\
├── start-kazer.bat      ← 启动脚本（顶部为配置区）
├── jre\\                 ← （可选）捆绑的 Java 8 运行时
└── mods\\
    ├── KazerClient-0.1.39.jar
    └── 1.8.9\\
        └── OptiFine\_1.8.9\_HD\_U\_M5.jar   ← （可选）
```

### 启动脚本配置项（编辑 bat 顶部）

|变量|说明|
|-|-|
|`USERNAME`|游戏内用户名|
|`UUID`|玩家 UUID；填 `AUTO` 则按离线模式从用户名自动生成（MD5 v3）|
|`ACCESS\_TOKEN`|正版令牌；离线游玩填 `0` 即可|
|`MEMORY`|最大内存，如 `4096M`|
|`ACCOUNTS\_URL` / `ACCOUNTS\_SECRET`|Kazer 内置账号服务地址与密钥（通过 `-Dkazer.accounts.\*` 传入客户端）|

### 启动包依赖

* 脚本默认从 `%APPDATA%\\.minecraft` 复用官方资源：`libraries\\` 依赖库、`versions\\1.8.9\\1.8.9.jar`、Forge natives、`assets\\`。
* 因此要求**官方/普通启动器至少成功启动过一次 Forge 1.8.9**。
* 修改 `MC\_DIR` 变量可指向其它 Minecraft 安装目录。

## 仓库结构

```
Kazer\_Src\\
├── build.gradle            # ForgeGradle 构建脚本（含 coremod MANIFEST 修复）
├── gradlew / gradlew.bat   # Gradle Wrapper
├── launcher\\start-kazer.bat# 一键启动脚本模板
└── src\\main\\
    ├── java\\               # 全部源码（反混淆后）
    │   ├── com\\cheatbreaker\\client\\...   # CB 客户端主体（GUI/模块/网络等）
    │   └── cn\\kazer\\client189\\...        # 1.8.9 适配层（运行时转换器、IME、旧动画等）
    └── resources\\
        ├── mcmod.info
        └── META-INF\\MANIFEST.MF
```

## 反混淆说明

* 原始 CB jar 中所有单字母混淆类（`a`\~`z`、`A` 及单字母包）已全部重命名为语义化名称，字段/方法名保留混淆原名（仅重命名类与包，保证最小改动）。
* 部分原版类引用通过 `RuntimeTransformer` 在运行时注入，生产环境使用 SRG 名（`field\_\*`/`func\_\*`）匹配，源码中以 `is(name, "mcp名", "srg名")` 双名形式出现。
* 构建脚本中的 `FIX 3`/`FIX 4` 注释为已修复的打包问题（retromapping 任务禁用、coremod MANIFEST 写入），请勿删除。

## 许可

* 本仓库代码基于反编译的 CheatBreaker 客户端整理，**仅供学习交流**，不提供任何担保。
* Minecraft 版权归 Mojang / Microsoft 所有；Forge 属于 MinecraftForge 团队；OptiFine 属于 sp614x。

