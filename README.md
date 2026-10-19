# Kazer Client (1.8.9 Forge Mod)

Kazer Client 是一个基于 **CheatBreaker (CB) 客户端** 反混淆、重构而来的 Minecraft 1.8.9 Forge 客户端模组。原版 CB 以混淆类名（`a.java`、`b.java`、`c/a.java` 等）发布，本仓库已将全部顶层类与包名还原为语义化命名（如 `CheatBreakerClient`、`ModuleManager`、`HudEditorScreen` 等）


> **免责声明**：本项目仅供学习与研究，原 CheatBreaker 客户端版权归其原作者所有。请勿用于任何违反 Minecraft EULA 或服务器规则的用途。



- 高仿Lunar Client



## 构建环境要求

| 项目 | 要求 |
|---|---|
| 构建环境 | Windows + **JDK 8**（推荐 Eclipse Temurin 8），`JAVA_HOME` 指向 JDK 8 |

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
- 还有一些”lIIIIlIIllIIlIIlIIIlIIllI“等没有完全解开混淆

## 许可

- 本仓库代码基于反编译的 CheatBreaker 客户端整理，**仅供学习交流**，不提供任何担保。
- Minecraft 版权归 Mojang ,此客户端居然还写着不归Mojang所有!

> By deepseek v4.1 flash and wedlla
