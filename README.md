# TheGame - AI 文字冒险游戏

一款基于 AI 大语言模型的 Android 文字冒险游戏，玩家可以自定义游戏世界、角色和 NPC，与 AI 主持人互动推进剧情。

## 功能特性

- 🎮 **自定义游戏设定** — 自由定义游戏背景、世界设定
- 🧙 **自定义主角** — 设定主角属性类目和初始值
- 👥 **自定义 NPC** — 创建初始 NPC 角色
- 🤖 **AI 驱动剧情** — 基于 DeepSeek 大模型生成剧情
- 💾 **三层数据架构** — 设定层 / 状态层 / 总结层，永久保存
- 📱 **Material 3 设计** — Jetpack Compose 现代化 UI
- 🔄 **剧情总结** — 自动总结近期剧情，保持上下文连贯

## 技术栈

| 层级 | 技术 |
|------|------|
| 语言 | Kotlin |
| UI | Jetpack Compose (Material 3) |
| 架构 | MVVM + Clean Architecture |
| 依赖注入 | 手动 DI (AppModule) |
| 本地数据库 | Room |
| 数据存储 | DataStore Preferences |
| 网络 | Retrofit + OkHttp |
| 异步 | Kotlin Coroutines + Flow |
| 导航 | Navigation Compose |

## 项目结构

```
textgame/app/src/main/java/com/textgame/
├── data/
│   ├── local/                  # 本地数据层
│   │   ├── db/                 # Room 数据库
│   │   │   ├── dao/            # 数据访问对象
│   │   │   └── entity/         # 数据库实体
│   │   ├── SettingsManager.kt  # 设置管理
│   │   └── SettingsPreferences.kt
│   ├── remote/                 # 远程数据层
│   │   └── ai/                 # AI 服务接口
│   └── repository/             # 仓库实现
├── di/
│   └── AppModule.kt            # 依赖注入模块
├── domain/
│   ├── model/                  # 领域模型
│   ├── repository/             # 仓库接口
│   └── usecase/                # 用例层
├── presentation/
│   ├── ui/                     # UI 层
│   │   ├── main/               # 主菜单
│   │   ├── creator/            # 游戏创建
│   │   ├── game/               # 游戏界面
│   │   └── settings/           # 设置界面
│   └── viewmodel/              # ViewModel
└── MainActivity.kt             # 主 Activity
```

## 三层数据架构

```
┌─────────────────────────────────────────┐
│           设定层 (Setting Layer)          │
│  游戏背景、世界设定、NPC初始设定          │
│  长期保存，游戏创建时设定                │
├─────────────────────────────────────────┤
│           状态层 (State Layer)            │
│  主角状态、NPC 状态、游戏状态            │
│  每轮对话后实时更新                      │
├─────────────────────────────────────────┤
│           总结层 (Summary Layer)          │
│  短期剧情总结（自动生成）                │
│  每 5-10 轮对话更新一次                  │
│  作为 AI 上下文减少 token 消耗           │
└─────────────────────────────────────────┘
```

## 快速开始

### 环境要求

- Android Studio Hedgehog (2023.1.1) 或更高
- JDK 17
- Android SDK 34
- Gradle 7.6+

### 本地编译

```bash
cd textgame

# Debug 版本
./gradlew assembleDebug

# Release 版本
./gradlew assembleRelease
```

编译产物位于 `app/build/outputs/apk/`

### API Key 配置

在应用内「设置」页面填入你的 DeepSeek API Key 即可开始游戏。

支持的服务商：
- **DeepSeek** (默认)

## CI/CD

本项目使用 GitHub Actions 自动编译 APK 并发布 Release。

### 工作流

- **push / PR 到 main** — 自动编译并上传 APK 到 Artifacts
- **发布 Release** — 自动编译并将 APK 附加到 Release 页面
- **手动触发** — 在 Actions 页面手动触发，可选填版本号发布

### 触发 Release 发布

```bash
# 打 tag 并推送，自动触发编译和发布
git tag v1.0.0
git push origin v1.0.0
```

或在 GitHub 上创建 Release，工作流会自动构建并附加 APK。

## 许可证

详见 [LICENSE](LICENSE) 文件。
