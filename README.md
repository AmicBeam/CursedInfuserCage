# CursedInfuserByCage

本模组是 **Goety** 的扩展 mod，允许诅咒注入器（Cursed Infuser）和阴影注入器（Grim Infuser）在放置在诅咒之笼（Cursed Cage）上方时也能正常工作，并从诅咒之笼中消耗灵魂能量。

## 功能

- ✅ **诅咒注入器支持**：诅咒注入器（`goety:cursed_infuser`）可以放置在诅咒之笼上方工作
- ✅ **阴影注入器支持**：阴影注入器（`goety:grim_infuser`）也可以放置在诅咒之笼上方工作
- ✅ **灵魂能量消耗**：注入器工作时会消耗诅咒之笼中的灵魂能量
- ✅ **可配置消耗间隔**：通过配置文件设置能量消耗间隔（默认每20tick消耗1点，最小为1）
- ✅ **仅在配方工作时消耗**：只有在配方实际进行时才会消耗能量，不会每tick都消耗
- ✅ **Ponder 场景教程**：提供 Ponder 场景，演示如何使用诅咒之笼激活注入器（需要安装 Ponder 模组）

## 依赖

### 必需依赖

- **Minecraft**: 1.20.1
- **Forge**: 47.0.0 或更高版本
- **Goety Mod**: 2.5 或更高版本

### 可选依赖

- **Ponder**: 用于查看交互式教程场景（不安装也可以正常使用模组）

### 安装

1. 确保已安装上述必需依赖
2. 将 `cursedinfuserbycage-1.0.0.jar` 放入 `mods` 文件夹
3. 启动游戏

## 配置

配置文件位于 `.minecraft/config/cursedinfuserbycage-common.toml`

### 配置项说明

- `soulEnergyConsumeInterval`（默认值：20）
  - 描述：注入器工作时，每隔多少tick消耗1点灵魂能量
  - 范围：1 到 Integer.MAX_VALUE
  - 说明：默认值为20（1秒），最小值为1。设置为1时每tick消耗1点，设置为20时每20tick（1秒）消耗1点

### 配置示例

```toml
[Cursed Infuser By Cage]
    # How many ticks between each soul energy consumption when the infuser is working. Default: 20 (1 second), Minimum: 1
    soulEnergyConsumeInterval = 20
```

## 使用方法

1. 放置一个诅咒之笼（Cursed Cage）并确保其中有灵魂能量
2. 将诅咒注入器或阴影注入器放置在诅咒之笼的正上方
3. 放入需要处理的物品
4. 注入器会正常工作，并从诅咒之笼中消耗灵魂能量

## 构建

### 前提条件

- JDK 17 或更高版本
- Gradle（已包含在项目中）

### 构建命令

**Windows PowerShell**：
```powershell
.\gradlew.bat build
```

**Linux/Mac**：
```bash
./gradlew build
```

构建完成后，JAR 文件位于 `build/reobfJar/output.jar`。

## 技术实现

本 mod 使用 **Mixin** 技术修改 Goety 模组的注入器逻辑：

1. **状态检测**：使用 `@ModifyVariable` 修改 `tick()` 方法中的 `flag` 变量，使其能够检测诅咒之笼
2. **能量消耗**：使用 `@Inject` 在 `work()` 方法末尾注入能量消耗逻辑
3. **间隔控制**：使用计数器实现可配置的消耗间隔，仅在配方实际工作时消耗能量
4. **兼容性**：支持 `CursedInfuserBlockEntity` 和 `GrimInfuserBlockEntity` 两种注入器

## 兼容性

- ✅ 完全兼容 Goety 2.5 及以上版本
- ✅ 兼容原版 Minecraft 1.20.1
- ✅ 与其他 mod 无已知冲突

## 许可证

MIT License

## 作者

CursedInfuserByCage

## AI 声明

本项目的开发过程中使用了 AI 辅助工具（Cursor AI）进行代码编写和问题解决。

