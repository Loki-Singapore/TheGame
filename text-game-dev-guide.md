# AI 文字游戏开发实施文档

> 技术栈：Node.js + Express + 流式大模型 API + 移动端适配前端 + 本地文件持久化

---

## 目录

- [第一阶段：项目初始化与基础框架](#第一阶段项目初始化与基础框架)
- [第二阶段：配置系统与大模型接入](#第二阶段配置系统与大模型接入)
- [第三阶段：数据层设计与持久化](#第三阶段数据层设计与持久化)
- [第四阶段：游戏核心逻辑实现](#第四阶段游戏核心逻辑实现)
- [第五阶段：前端界面开发（移动端适配）](#第五阶段前端界面开发移动端适配)
- [第六阶段：Linux 一键启动与部署](#第六阶段linux-一键启动与部署)
- [附录：完整目录结构与配置模板](#附录完整目录结构与配置模板)

---

## 第一阶段：项目初始化与基础框架

### 1.1 技术选型说明

| 层级 | 技术方案 | 说明 |
|------|----------|------|
| 后端框架 | Express.js | 轻量级、社区成熟、中间件丰富 |
| 前端方案 | 原生 HTML + CSS + JavaScript | 无构建步骤，移动端适配简单，本地易运行 |
| 数据持久化 | JSON 文件 (lowdb 或原生 fs) | 零配置，本地开箱即用，便于备份迁移 |
| 大模型通信 | axios + SSE (Server-Sent Events) | 支持流式响应，前端逐字展示 |
| 大模型 SDK | 各家官方 SDK 或通用 OpenAI 兼容 SDK | DeepSeek 等多数厂商兼容 OpenAI 接口规范 |

### 1.2 项目目录结构

```
text-game/
├── server/                 # 后端代码
│   ├── index.js           # 入口文件
│   ├── config/            # 配置模块
│   │   └── index.js       # 配置加载与校验
│   ├── models/            # 数据模型层
│   │   ├── gameData.js    # 游戏数据持久化
│   │   └── schema.js      # 数据结构定义
│   ├── services/          # 业务逻辑层
│   │   ├── llmService.js  # 大模型调用服务
│   │   ├── gameEngine.js  # 游戏引擎核心
│   │   └── summaryService.js # 总结层更新服务
│   ├── routes/            # 路由层
│   │   ├── game.js        # 游戏相关接口
│   │   └── config.js      # 配置相关接口
│   └── middleware/        # 中间件
│       └── sse.js         # SSE 流式响应中间件
├── public/                # 前端静态文件
│   ├── index.html         # 主页面
│   ├── css/
│   │   └── style.css      # 样式（移动端优先）
│   └── js/
│       └── app.js         # 前端逻辑
├── data/                  # 数据存储目录
│   ├── game-state.json    # 游戏状态数据
│   └── config.json        # 用户配置
├── scripts/               # 脚本目录
│   └── start.sh           # Linux 一键启动脚本
├── package.json
├── .env.example           # 环境变量模板
└── README.md
```

### 1.3 初始化步骤

```bash
# 1. 创建项目目录
mkdir text-game && cd text-game

# 2. 初始化 npm
npm init -y

# 3. 安装核心依赖
npm install express cors dotenv
npm install -D nodemon

# 4. 创建目录结构
mkdir -p server/config server/models server/services server/routes server/middleware
mkdir -p public/css public/js data scripts
```

### 1.4 最小可运行后端 (server/index.js)

```javascript
const express = require('express');
const cors = require('cors');
const path = require('path');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());
app.use(express.static(path.join(__dirname, '../public')));

// 健康检查
app.get('/api/health', (req, res) => {
  res.json({ status: 'ok', timestamp: Date.now() });
});

app.listen(PORT, () => {
  console.log(`游戏服务器运行在 http://localhost:${PORT}`);
});
```

---

## 第二阶段：配置系统与大模型接入

### 2.1 配置系统设计

用户可配置项分为两类：
- **环境变量配置** (`.env`)：API Key、服务商选择等敏感信息
- **游戏设定配置** (JSON)：游戏背景、状态类目、初始 NPC 等游戏内容

### 2.2 支持的大模型服务商

| 服务商 | 接口兼容 | SDK/库 | 流式支持 |
|--------|----------|--------|----------|
| OpenAI | 原生 | openai npm 包 | 是 |
| DeepSeek | OpenAI 兼容 | openai npm 包 (baseURL 配置) | 是 |
| 通义千问 (DashScope) | OpenAI 兼容 | openai npm 包 (baseURL 配置) | 是 |
| 智谱 AI (GLM) | OpenAI 兼容 | openai npm 包 (baseURL 配置) | 是 |
| 月之暗面 (Moonshot) | OpenAI 兼容 | openai npm 包 (baseURL 配置) | 是 |

### 2.3 环境变量模板 (.env.example)

```env
# 服务端口
PORT=3000

# 大模型服务商选择: openai | deepseek | qwen | glm | moonshot | custom
LLM_PROVIDER=deepseek

# API Key (用户必填)
LLM_API_KEY=

# 自定义 baseURL (当 PROVIDER 为 custom 时使用)
LLM_BASE_URL=

# 模型名称
LLM_MODEL=deepseek-chat

# 数据存储路径
DATA_DIR=./data
```

### 2.4 配置加载模块 (server/config/index.js)

```javascript
const fs = require('fs');
const path = require('path');

const PROVIDER_CONFIGS = {
  openai: {
    baseURL: 'https://api.openai.com/v1',
    defaultModel: 'gpt-3.5-turbo',
  },
  deepseek: {
    baseURL: 'https://api.deepseek.com/v1',
    defaultModel: 'deepseek-chat',
  },
  qwen: {
    baseURL: 'https://dashscope.aliyuncs.com/compatible-mode/v1',
    defaultModel: 'qwen-turbo',
  },
  glm: {
    baseURL: 'https://open.bigmodel.cn/api/paas/v4',
    defaultModel: 'glm-4-flash',
  },
  moonshot: {
    baseURL: 'https://api.moonshot.cn/v1',
    defaultModel: 'moonshot-v1-8k',
  },
};

function loadConfig() {
  const provider = process.env.LLM_PROVIDER || 'deepseek';
  const apiKey = process.env.LLM_API_KEY;
  const baseURL = process.env.LLM_BASE_URL || 
    (PROVIDER_CONFIGS[provider]?.baseURL);
  const model = process.env.LLM_MODEL || 
    (PROVIDER_CONFIGS[provider]?.defaultModel);

  if (!apiKey) {
    console.warn('⚠️  警告: LLM_API_KEY 未设置，请在 .env 文件中配置');
  }

  return {
    provider,
    apiKey,
    baseURL,
    model,
    port: process.env.PORT || 3000,
    dataDir: path.resolve(process.env.DATA_DIR || './data'),
  };
}

module.exports = { loadConfig, PROVIDER_CONFIGS };
```

### 2.5 大模型服务 (server/services/llmService.js)

使用 OpenAI 官方 SDK（兼容模式）实现流式调用：

```bash
npm install openai
```

```javascript
const OpenAI = require('openai');
const { loadConfig } = require('../config');

class LLMService {
  constructor() {
    const config = loadConfig();
    this.config = config;
    this.client = null;
    if (config.apiKey && config.baseURL) {
      this.client = new OpenAI({
        apiKey: config.apiKey,
        baseURL: config.baseURL,
      });
    }
  }

  isReady() {
    return this.client !== null;
  }

  /**
   * 流式对话
   * @param {Array} messages - 消息数组 [{role, content}]
   * @param {Function} onChunk - 每收到一个 chunk 的回调
   * @param {Function} onComplete - 完成时回调
   * @param {Function} onError - 错误回调
   */
  async streamChat(messages, onChunk, onComplete, onError) {
    if (!this.client) {
      onError(new Error('LLM 未配置，请先设置 API Key'));
      return;
    }

    try {
      const stream = await this.client.chat.completions.create({
        model: this.config.model,
        messages: messages,
        stream: true,
        temperature: 0.8,
        max_tokens: 2000,
      });

      let fullContent = '';

      for await (const chunk of stream) {
        const content = chunk.choices[0]?.delta?.content || '';
        if (content) {
          fullContent += content;
          onChunk(content);
        }
      }

      onComplete(fullContent);
    } catch (error) {
      onError(error);
    }
  }
}

module.exports = new LLMService();
```

### 2.6 SSE 中间件 (server/middleware/sse.js)

```javascript
function sseMiddleware(req, res, next) {
  res.setHeader('Content-Type', 'text/event-stream');
  res.setHeader('Cache-Control', 'no-cache');
  res.setHeader('Connection', 'keep-alive');
  res.setHeader('X-Accel-Buffering', 'no');

  res.sseSend = function(event, data) {
    res.write(`event: ${event}\n`);
    res.write(`data: ${JSON.stringify(data)}\n\n`);
  };

  res.sseEnd = function() {
    res.write('event: end\ndata: [DONE]\n\n');
    res.end();
  };

  next();
}

module.exports = sseMiddleware;
```

---

## 第三阶段：数据层设计与持久化

### 3.1 数据三层架构

```
┌─────────────────────────────────────────┐
│              设定层 (Setting Layer)        │
│  游戏背景、世界设定、角色设定、长期记忆    │
│  每次状态更新时同步更新，永久保留          │
├─────────────────────────────────────────┤
│              状态层 (State Layer)          │
│  主角状态、NPC 状态、游戏状态              │
│  每轮对话后更新，实时反映当前游戏进度      │
├─────────────────────────────────────────┤
│              总结层 (Summary Layer)        │
│  短期剧情总结、最近 5-10 轮对话摘要        │
│  每 5-10 轮更新一次，作为 LLM 上下文       │
└─────────────────────────────────────────┘
```

### 3.2 数据结构定义 (server/models/schema.js)

```javascript
// 用户可自定义的状态类目模板
const DEFAULT_CHARACTER_CATEGORIES = [
  { key: 'health', name: '生命值', type: 'number', default: 100 },
  { key: 'sanity', name: '理智值', type: 'number', default: 100 },
  { key: 'strength', name: '力量', type: 'number', default: 10 },
  { key: 'intelligence', name: '智力', type: 'number', default: 10 },
  { key: 'charisma', name: '魅力', type: 'number', default: 10 },
  { key: 'luck', name: '幸运', type: 'number', default: 10 },
  { key: 'inventory', name: '物品栏', type: 'array', default: [] },
  { key: 'skills', name: '技能', type: 'array', default: [] },
  { key: 'description', name: '背景描述', type: 'text', default: '' },
];

const DEFAULT_GAME_CATEGORIES = [
  { key: 'day', name: '游戏天数', type: 'number', default: 1 },
  { key: 'location', name: '当前地点', type: 'text', default: '' },
  { key: 'timeOfDay', name: '时间段', type: 'text', default: '早晨' },
  { key: 'quests', name: '任务列表', type: 'array', default: [] },
  { key: 'reputation', name: '声望', type: 'object', default: {} },
  { key: 'flags', name: '剧情标记', type: 'object', default: {} },
];

// 完整游戏数据结构
function createGameDataStructure(userConfig) {
  const {
    gameBackground = '',
    characterCategories = DEFAULT_CHARACTER_CATEGORIES,
    gameCategories = DEFAULT_GAME_CATEGORIES,
    initialNPCs = [],
    playerName = '主角',
  } = userConfig;

  // 构建初始主角状态
  const playerState = {};
  characterCategories.forEach(cat => {
    playerState[cat.key] = cat.default;
  });
  playerState.name = playerName;

  // 构建初始游戏状态
  const gameState = {};
  gameCategories.forEach(cat => {
    gameState[cat.key] = cat.default;
  });

  // 构建初始 NPC 状态
  const npcStates = {};
  initialNPCs.forEach(npc => {
    npcStates[npc.id] = {
      id: npc.id,
      name: npc.name,
      description: npc.description || '',
      relationship: npc.relationship || 0,
      location: npc.location || '',
      isAlive: true,
      customState: npc.customState || {},
    };
  });

  return {
    // ===== 设定层 =====
    setting: {
      gameBackground,
      characterCategories,
      gameCategories,
      initialNPCs,
      worldSetting: gameBackground,
      importantEvents: [],
      establishedFacts: [],
    },

    // ===== 状态层 =====
    state: {
      player: playerState,
      npcs: npcStates,
      game: gameState,
      currentRound: 0,
      lastUpdateTime: Date.now(),
    },

    // ===== 总结层 =====
    summary: {
      currentSummary: gameBackground,
      lastSummaryRound: 0,
      recentDialogues: [],
      summaryInterval: 5, // 每5轮更新一次总结
    },

    // ===== 对话历史 =====
    dialogueHistory: [],
  };
}

module.exports = {
  DEFAULT_CHARACTER_CATEGORIES,
  DEFAULT_GAME_CATEGORIES,
  createGameDataStructure,
};
```

### 3.3 数据持久化 (server/models/gameData.js)

```javascript
const fs = require('fs');
const path = require('path');
const { loadConfig } = require('../config');
const { createGameDataStructure } = require('./schema');

class GameData {
  constructor() {
    const config = loadConfig();
    this.dataDir = config.dataDir;
    this.stateFile = path.join(this.dataDir, 'game-state.json');
    this.configFile = path.join(this.dataDir, 'config.json');
    this._ensureDataDir();
    this._cache = null;
  }

  _ensureDataDir() {
    if (!fs.existsSync(this.dataDir)) {
      fs.mkdirSync(this.dataDir, { recursive: true });
    }
  }

  load() {
    if (this._cache) return this._cache;

    if (fs.existsSync(this.stateFile)) {
      try {
        const raw = fs.readFileSync(this.stateFile, 'utf-8');
        this._cache = JSON.parse(raw);
        return this._cache;
      } catch (e) {
        console.error('读取游戏数据失败:', e.message);
      }
    }
    return null;
  }

  save(data) {
    this._cache = data;
    const tmpFile = this.stateFile + '.tmp';
    fs.writeFileSync(tmpFile, JSON.stringify(data, null, 2));
    fs.renameSync(tmpFile, this.stateFile);
  }

  newGame(userConfig) {
    const gameData = createGameDataStructure(userConfig);
    this.save(gameData);
    this.saveUserConfig(userConfig);
    return gameData;
  }

  saveUserConfig(config) {
    fs.writeFileSync(this.configFile, JSON.stringify(config, null, 2));
  }

  loadUserConfig() {
    if (fs.existsSync(this.configFile)) {
      try {
        return JSON.parse(fs.readFileSync(this.configFile, 'utf-8'));
      } catch (e) {
        return null;
      }
    }
    return null;
  }

  // ===== 状态层操作 =====
  updatePlayerState(changes) {
    const data = this.load();
    if (!data) return null;
    data.state.player = { ...data.state.player, ...changes };
    data.state.lastUpdateTime = Date.now();
    this._syncSettingFromState(data);
    this.save(data);
    return data.state.player;
  }

  updateNPCState(npcId, changes) {
    const data = this.load();
    if (!data || !data.state.npcs[npcId]) return null;
    data.state.npcs[npcId] = { ...data.state.npcs[npcId], ...changes };
    data.state.lastUpdateTime = Date.now();
    this._syncSettingFromState(data);
    this.save(data);
    return data.state.npcs[npcId];
  }

  updateGameState(changes) {
    const data = this.load();
    if (!data) return null;
    data.state.game = { ...data.state.game, ...changes };
    data.state.lastUpdateTime = Date.now();
    this._syncSettingFromState(data);
    this.save(data);
    return data.state.game;
  }

  // ===== 设定层同步 =====
  _syncSettingFromState(data) {
    const setting = data.setting;
    const state = data.state;

    if (state.game.day !== undefined) {
      setting.establishedFacts = setting.establishedFacts.filter(
        f => !f.startsWith('当前天数:')
      );
      setting.establishedFacts.push(`当前天数: 第${state.game.day}天`);
    }

    if (state.game.location) {
      setting.establishedFacts = setting.establishedFacts.filter(
        f => !f.startsWith('当前地点:')
      );
      setting.establishedFacts.push(`当前地点: ${state.game.location}`);
    }
  }

  addImportantEvent(event) {
    const data = this.load();
    if (!data) return;
    data.setting.importantEvents.push({
      id: Date.now(),
      round: data.state.currentRound,
      event,
      timestamp: Date.now(),
    });
    this.save(data);
  }

  // ===== 总结层操作 =====
  updateSummary(summaryText, round) {
    const data = this.load();
    if (!data) return;
    data.summary.currentSummary = summaryText;
    data.summary.lastSummaryRound = round;
    data.summary.recentDialogues = [];
    this.save(data);
  }

  addRecentDialogue(dialogue) {
    const data = this.load();
    if (!data) return;
    data.summary.recentDialogues.push(dialogue);
    if (data.summary.recentDialogues.length > 20) {
      data.summary.recentDialogues = data.summary.recentDialogues.slice(-20);
    }
    this.save(data);
  }

  // ===== 对话历史 =====
  addDialogue(entry) {
    const data = this.load();
    if (!data) return;
    data.dialogueHistory.push(entry);
    data.state.currentRound = data.dialogueHistory.length;
    this.save(data);
  }

  shouldUpdateSummary() {
    const data = this.load();
    if (!data) return false;
    const roundsSinceLastSummary = 
      data.state.currentRound - data.summary.lastSummaryRound;
    return roundsSinceLastSummary >= data.summary.summaryInterval;
  }

  reset() {
    this._cache = null;
    if (fs.existsSync(this.stateFile)) {
      fs.unlinkSync(this.stateFile);
    }
  }
}

module.exports = new GameData();
```

---

## 第四阶段：游戏核心逻辑实现

### 4.1 游戏引擎 (server/services/gameEngine.js)

```javascript
const gameData = require('../models/gameData');
const llmService = require('./llmService');
const summaryService = require('./summaryService');

class GameEngine {
  /**
   * 开始新游戏
   */
  startNewGame(userConfig) {
    const gameDataObj = gameData.newGame(userConfig);
    return {
      success: true,
      message: '新游戏已创建',
      data: {
        setting: gameDataObj.setting,
        state: gameDataObj.state,
        summary: gameDataObj.summary,
      },
    };
  }

  /**
   * 获取当前游戏状态
   */
  getGameState() {
    const data = gameData.load();
    if (!data) {
      return { success: false, message: '暂无游戏存档' };
    }
    return {
      success: true,
      data: {
        setting: data.setting,
        state: data.state,
        summary: data.summary,
        dialogueCount: data.dialogueHistory.length,
      },
    };
  }

  /**
   * 构建 LLM 消息上下文
   */
  buildMessages(userInput) {
    const data = gameData.load();
    if (!data) return [];

    const { setting, state, summary } = data;

    const systemPrompt = `你是一个文字冒险游戏的主持人（DM）。请根据以下设定推动剧情发展。

【游戏背景/设定】
${setting.gameBackground || setting.worldSetting}

【重要历史事件】
${setting.importantEvents.map((e, i) => `${i + 1}. ${e.event}`).join('\n') || '暂无'}

【已知事实】
${setting.establishedFacts.map((f, i) => `- ${f}`).join('\n') || '暂无'}

【当前剧情总结】
${summary.currentSummary}

【主角状态】
${JSON.stringify(state.player, null, 2)}

【NPC 状态】
${JSON.stringify(state.npcs, null, 2)}

【游戏状态】
${JSON.stringify(state.game, null, 2)}

请遵守以下规则：
1. 用生动的第二人称叙述推动剧情
2. 根据玩家行动合理更新状态
3. 输出格式要求：
   - 首先是剧情叙述（200-500字）
   - 然后用 <STATE_UPDATE> 标签包裹状态变更 JSON
   - 状态变更只包含需要变化的字段
   - 如果有新 NPC 出现，在 <NEW_NPC> 标签中描述

示例输出：
你推开了那扇沉重的木门，一股陈旧的气息扑面而来...

<STATE_UPDATE>
{
  "player": { "health": 95 },
  "game": { "location": "古老的书房" }
}
</STATE_UPDATE>
`;

    const recentDialogues = summary.recentDialogues.slice(-10);
    const messages = [
      { role: 'system', content: systemPrompt },
      ...recentDialogues,
      { role: 'user', content: userInput },
    ];

    return messages;
  }

  /**
   * 解析 LLM 输出中的状态更新
   */
  parseStateUpdate(content) {
    const stateMatch = content.match(/<STATE_UPDATE>([\s\S]*?)<\/STATE_UPDATE>/);
    const npcMatch = content.match(/<NEW_NPC>([\s\S]*?)<\/NEW_NPC>/);

    let stateUpdate = null;
    let newNPC = null;

    if (stateMatch) {
      try {
        stateUpdate = JSON.parse(stateMatch[1].trim());
      } catch (e) {
        console.error('解析状态更新失败:', e.message);
      }
    }

    if (npcMatch) {
      try {
        newNPC = JSON.parse(npcMatch[1].trim());
      } catch (e) {
        console.error('解析新 NPC 失败:', e.message);
      }
    }

    const narrative = content
      .replace(/<STATE_UPDATE>[\s\S]*?<\/STATE_UPDATE>/g, '')
      .replace(/<NEW_NPC>[\s\S]*?<\/NEW_NPC>/g, '')
      .trim();

    return { narrative, stateUpdate, newNPC };
  }

  /**
   * 应用状态更新
   */
  applyStateUpdate(stateUpdate, newNPC) {
    if (!stateUpdate && !newNPC) return;

    if (stateUpdate?.player) {
      gameData.updatePlayerState(stateUpdate.player);
    }
    if (stateUpdate?.game) {
      gameData.updateGameState(stateUpdate.game);
    }
    if (stateUpdate?.npcs) {
      for (const [npcId, changes] of Object.entries(stateUpdate.npcs)) {
        gameData.updateNPCState(npcId, changes);
      }
    }
    if (newNPC) {
      const data = gameData.load();
      const npcId = `npc_${Date.now()}`;
      data.state.npcs[npcId] = {
        id: npcId,
        name: newNPC.name || '未知 NPC',
        description: newNPC.description || '',
        relationship: 0,
        location: newNPC.location || data.state.game.location || '',
        isAlive: true,
        customState: {},
      };
      gameData.save(data);
    }
  }

  /**
   * 处理玩家输入（流式响应）
   */
  async processPlayerInput(userInput, res) {
    const data = gameData.load();
    if (!data) {
      res.sseSend('error', { message: '请先开始新游戏' });
      res.sseEnd();
      return;
    }

    const messages = this.buildMessages(userInput);
    let fullContent = '';

    await llmService.streamChat(
      messages,
      (chunk) => {
        fullContent += chunk;
        res.sseSend('chunk', { content: chunk });
      },
      async (finalContent) => {
        const { narrative, stateUpdate, newNPC } = this.parseStateUpdate(finalContent);

        this.applyStateUpdate(stateUpdate, newNPC);

        gameData.addDialogue({
          role: 'user',
          content: userInput,
          round: data.state.currentRound + 1,
          timestamp: Date.now(),
        });
        gameData.addDialogue({
          role: 'assistant',
          content: narrative,
          rawContent: finalContent,
          round: data.state.currentRound + 1,
          timestamp: Date.now(),
        });

        gameData.addRecentDialogue({ role: 'user', content: userInput });
        gameData.addRecentDialogue({ role: 'assistant', content: narrative });

        res.sseSend('narrative', { content: narrative });
        res.sseSend('state_update', { stateUpdate, newNPC });

        const currentData = gameData.load();
        if (currentData && gameData.shouldUpdateSummary()) {
          res.sseSend('info', { message: '正在更新剧情总结...' });
          try {
            await summaryService.generateSummary();
            const updated = gameData.load();
            res.sseSend('summary_updated', {
              summary: updated.summary.currentSummary,
            });
          } catch (e) {
            console.error('更新总结失败:', e.message);
          }
        }

        const latest = gameData.load();
        res.sseSend('state', {
          player: latest.state.player,
          npcs: latest.state.npcs,
          game: latest.state.game,
          round: latest.state.currentRound,
        });

        res.sseEnd();
      },
      (error) => {
        console.error('LLM 调用失败:', error);
        res.sseSend('error', { message: error.message || '调用失败' });
        res.sseEnd();
      }
    );
  }
}

module.exports = new GameEngine();
```

### 4.2 总结层服务 (server/services/summaryService.js)

```javascript
const gameData = require('../models/gameData');
const llmService = require('./llmService');

class SummaryService {
  /**
   * 生成剧情总结
   */
  async generateSummary() {
    const data = gameData.load();
    if (!data) return;

    const { setting, summary, state } = data;

    const prompt = `请对以下文字冒险游戏的最近剧情进行总结，提炼出重要的剧情发展、人物关系变化、状态变化等关键点。

【原有的剧情总结】
${summary.currentSummary}

【最近的对话记录】
${summary.recentDialogues.map(d => 
  `${d.role === 'user' ? '玩家' : 'DM'}: ${d.content}`
).join('\n')}

【当前主角状态】
${JSON.stringify(state.player, null, 2)}

【当前游戏状态】
${JSON.stringify(state.game, null, 2)}

请输出一份连贯的剧情总结，要求：
1. 保留原有总结中的重要信息，融入新的剧情发展
2. 突出关键事件、人物关系变化、地点变化
3. 字数控制在 300-500 字
4. 直接输出总结内容，不要其他格式`;

    let newSummary = '';

    await new Promise((resolve, reject) => {
      llmService.streamChat(
        [{ role: 'user', content: prompt }],
        (chunk) => { newSummary += chunk; },
        () => resolve(),
        (err) => reject(err)
      );
    });

    gameData.updateSummary(newSummary, state.currentRound);
    return newSummary;
  }
}

module.exports = new SummaryService();
```

### 4.3 游戏路由 (server/routes/game.js)

```javascript
const express = require('express');
const router = express.Router();
const gameEngine = require('../services/gameEngine');
const sseMiddleware = require('../middleware/sse');

// 获取游戏状态
router.get('/state', (req, res) => {
  const result = gameEngine.getGameState();
  res.json(result);
});

// 开始新游戏
router.post('/new', (req, res) => {
  const userConfig = req.body;
  const result = gameEngine.startNewGame(userConfig);
  res.json(result);
});

// 玩家输入（流式响应）
router.post('/chat', sseMiddleware, (req, res) => {
  const { input } = req.body;
  if (!input) {
    return res.status(400).json({ error: '输入内容不能为空' });
  }
  gameEngine.processPlayerInput(input, res);
});

// 重置游戏
router.post('/reset', (req, res) => {
  const gameData = require('../models/gameData');
  gameData.reset();
  res.json({ success: true, message: '游戏已重置' });
});

module.exports = router;
```

### 4.4 配置路由 (server/routes/config.js)

```javascript
const express = require('express');
const router = express.Router();
const gameData = require('../models/gameData');
const { PROVIDER_CONFIGS } = require('../config');

// 获取可用的服务商列表
router.get('/providers', (req, res) => {
  const providers = Object.entries(PROVIDER_CONFIGS).map(([key, val]) => ({
    id: key,
    name: key === 'openai' ? 'OpenAI' :
          key === 'deepseek' ? 'DeepSeek' :
          key === 'qwen' ? '通义千问 (DashScope)' :
          key === 'glm' ? '智谱 AI (GLM)' :
          key === 'moonshot' ? '月之暗面 (Moonshot)' : key,
    defaultModel: val.defaultModel,
  }));
  res.json({ providers });
});

// 保存游戏配置
router.post('/game-config', (req, res) => {
  const config = req.body;
  gameData.saveUserConfig(config);
  res.json({ success: true });
});

// 获取游戏配置
router.get('/game-config', (req, res) => {
  const config = gameData.loadUserConfig();
  res.json({ config });
});

// 测试 API 连接
router.post('/test-connection', async (req, res) => {
  const llmService = require('../services/llmService');
  if (!llmService.isReady()) {
    return res.json({ success: false, message: 'API Key 未配置' });
  }
  try {
    let success = false;
    await new Promise((resolve) => {
      llmService.streamChat(
        [{ role: 'user', content: '你好，请回复"连接成功"。' }],
        () => { success = true; },
        () => resolve(),
        () => resolve()
      );
    });
    res.json({ success, message: success ? '连接成功' : '连接失败' });
  } catch (error) {
    res.json({ success: false, message: error.message });
  }
});

module.exports = router;
```

---

## 第五阶段：前端界面开发（移动端适配）

### 5.1 设计原则

- **移动端优先**：使用 Flexbox / Grid，viewport meta 标签
- **响应式布局**：适配 320px - 1200px 屏幕
- **大按钮、大字体**：便于触屏操作
- **暗色主题**：长时间游戏护眼

### 5.2 主页面 (public/index.html)

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="theme-color" content="#1a1a2e">
  <title>AI 文字冒险</title>
  <link rel="stylesheet" href="/css/style.css">
</head>
<body>
  <div id="app">
    <!-- 配置页面 -->
    <div id="setup-view" class="view">
      <div class="setup-container">
        <h1>🎮 AI 文字冒险</h1>
        
        <div class="setup-section">
          <h3>API 配置</h3>
          <div class="form-group">
            <label>选择服务商</label>
            <select id="provider-select">
              <option value="deepseek">DeepSeek</option>
              <option value="openai">OpenAI</option>
              <option value="qwen">通义千问</option>
              <option value="glm">智谱 AI</option>
              <option value="moonshot">月之暗面</option>
              <option value="custom">自定义</option>
            </select>
          </div>
          <div class="form-group">
            <label>API Key</label>
            <input type="password" id="api-key" placeholder="请输入 API Key">
          </div>
          <div class="form-group" id="base-url-group" style="display:none">
            <label>Base URL</label>
            <input type="text" id="base-url" placeholder="https://api.example.com/v1">
          </div>
          <div class="form-group">
            <label>模型名称</label>
            <input type="text" id="model-name" placeholder="deepseek-chat">
          </div>
          <button id="test-conn-btn" class="btn btn-secondary">测试连接</button>
          <span id="conn-status" class="status-text"></span>
        </div>

        <div class="setup-section">
          <h3>游戏设定</h3>
          <div class="form-group">
            <label>游戏背景</label>
            <textarea id="game-bg" rows="4" 
              placeholder="例如：你是一名在末世中求生的幸存者..."></textarea>
          </div>
          <div class="form-group">
            <label>主角名字</label>
            <input type="text" id="player-name" placeholder="主角" value="主角">
          </div>
          <div class="form-group">
            <label>初始 NPC（每行一个，格式：名字|描述）</label>
            <textarea id="initial-npcs" rows="3" 
              placeholder="老张|村口杂货店老板&#10;李医生|镇上的医生"></textarea>
          </div>
          <details class="advanced-settings">
            <summary>高级设置（自定义状态类目）</summary>
            <div class="form-group">
              <label>主角状态类目（JSON 格式）</label>
              <textarea id="char-cats" rows="4" class="mono"></textarea>
            </div>
            <div class="form-group">
              <label>游戏状态类目（JSON 格式）</label>
              <textarea id="game-cats" rows="4" class="mono"></textarea>
            </div>
          </details>
        </div>

        <button id="start-btn" class="btn btn-primary btn-large">开始游戏</button>
      </div>
    </div>

    <!-- 游戏页面 -->
    <div id="game-view" class="view" style="display:none">
      <div class="game-header">
        <button id="menu-btn" class="icon-btn">☰</button>
        <span class="header-title">AI 文字冒险</span>
        <button id="state-btn" class="icon-btn">📊</button>
      </div>

      <div id="dialogue-container" class="dialogue-container">
        <div class="dialogue-placeholder">
          <p>游戏开始，输入你的行动吧...</p>
        </div>
      </div>

      <div class="input-area">
        <textarea id="user-input" placeholder="输入你的行动或对话..." rows="2"></textarea>
        <button id="send-btn" class="btn btn-primary send-btn">发送</button>
      </div>

      <!-- 状态面板（侧滑） -->
      <div id="state-panel" class="state-panel">
        <div class="panel-header">
          <h3>状态面板</h3>
          <button id="close-state-btn" class="icon-btn">✕</button>
        </div>
        <div class="panel-content">
          <div class="state-section">
            <h4>主角状态</h4>
            <div id="player-state" class="state-list"></div>
          </div>
          <div class="state-section">
            <h4>游戏状态</h4>
            <div id="game-state" class="state-list"></div>
          </div>
          <div class="state-section">
            <h4>NPC 列表</h4>
            <div id="npc-list" class="npc-list"></div>
          </div>
          <div class="state-section">
            <h4>剧情总结</h4>
            <div id="summary-text" class="summary-text"></div>
          </div>
        </div>
      </div>

      <!-- 菜单面板 -->
      <div id="menu-panel" class="state-panel left-panel">
        <div class="panel-header">
          <h3>菜单</h3>
          <button id="close-menu-btn" class="icon-btn">✕</button>
        </div>
        <div class="panel-content">
          <button id="new-game-btn" class="btn btn-secondary full-width">新游戏</button>
          <button id="back-setup-btn" class="btn btn-secondary full-width">返回设置</button>
          <button id="save-game-btn" class="btn btn-secondary full-width">查看存档</button>
        </div>
      </div>

      <div id="overlay" class="overlay" style="display:none"></div>
    </div>
  </div>

  <script src="/js/app.js"></script>
</body>
</html>
```

### 5.3 样式 (public/css/style.css)

```css
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  -webkit-tap-highlight-color: transparent;
}

:root {
  --bg-primary: #1a1a2e;
  --bg-secondary: #16213e;
  --bg-tertiary: #0f3460;
  --text-primary: #eaeaea;
  --text-secondary: #a0a0a0;
  --accent: #e94560;
  --accent-hover: #ff6b81;
  --success: #4ade80;
  --warning: #fbbf24;
  --error: #f87171;
  --border: #2d3748;
  --input-bg: #1e293b;
}

html, body {
  height: 100%;
  width: 100%;
  overflow: hidden;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  background: var(--bg-primary);
  color: var(--text-primary);
  font-size: 16px;
  line-height: 1.6;
}

#app {
  height: 100%;
  width: 100%;
}

.view {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
}

/* ===== 设置页面 ===== */
.setup-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  overflow-y: auto;
  height: 100%;
  width: 100%;
}

.setup-container h1 {
  text-align: center;
  margin-bottom: 24px;
  font-size: 28px;
}

.setup-section {
  background: var(--bg-secondary);
  border-radius: 12px;
  padding: 16px;
  margin-bottom: 16px;
}

.setup-section h3 {
  margin-bottom: 12px;
  color: var(--accent);
  font-size: 18px;
}

.form-group {
  margin-bottom: 12px;
}

.form-group label {
  display: block;
  margin-bottom: 6px;
  font-size: 14px;
  color: var(--text-secondary);
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 12px;
  background: var(--input-bg);
  border: 1px solid var(--border);
  border-radius: 8px;
  color: var(--text-primary);
  font-size: 16px;
  outline: none;
  transition: border-color 0.2s;
}

.form-group input:focus,
.form-group select:focus,
.form-group textarea:focus {
  border-color: var(--accent);
}

.form-group textarea {
  resize: vertical;
  font-family: inherit;
}

.mono {
  font-family: 'Courier New', monospace;
  font-size: 13px;
}

.advanced-settings {
  margin-top: 12px;
}

.advanced-settings summary {
  cursor: pointer;
  color: var(--text-secondary);
  font-size: 14px;
  padding: 8px 0;
}

/* ===== 按钮 ===== */
.btn {
  padding: 12px 20px;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  touch-action: manipulation;
}

.btn:active {
  transform: scale(0.98);
}

.btn-primary {
  background: var(--accent);
  color: white;
}

.btn-primary:hover {
  background: var(--accent-hover);
}

.btn-secondary {
  background: var(--bg-tertiary);
  color: var(--text-primary);
}

.btn-large {
  width: 100%;
  padding: 16px;
  font-size: 18px;
  margin-top: 8px;
}

.full-width {
  width: 100%;
  margin-bottom: 8px;
}

.icon-btn {
  background: transparent;
  border: none;
  color: var(--text-primary);
  font-size: 20px;
  padding: 8px 12px;
  cursor: pointer;
  border-radius: 8px;
}

.icon-btn:active {
  background: var(--bg-tertiary);
}

.status-text {
  margin-left: 12px;
  font-size: 14px;
}

.status-text.success { color: var(--success); }
.status-text.error { color: var(--error); }

/* ===== 游戏页面 ===== */
.game-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  background: var(--bg-secondary);
  border-bottom: 1px solid var(--border);
  flex-shrink: 0;
}

.header-title {
  font-weight: 600;
  font-size: 16px;
}

.dialogue-container {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  -webkit-overflow-scrolling: touch;
}

.dialogue-placeholder {
  text-align: center;
  color: var(--text-secondary);
  padding: 40px 20px;
}

.dialogue-entry {
  margin-bottom: 16px;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.dialogue-entry.user {
  display: flex;
  justify-content: flex-end;
}

.dialogue-bubble {
  max-width: 85%;
  padding: 12px 16px;
  border-radius: 16px;
  font-size: 15px;
  line-height: 1.6;
  white-space: pre-wrap;
  word-wrap: break-word;
}

.dialogue-entry.user .dialogue-bubble {
  background: var(--accent);
  color: white;
  border-bottom-right-radius: 4px;
}

.dialogue-entry.assistant .dialogue-bubble {
  background: var(--bg-secondary);
  color: var(--text-primary);
  border-bottom-left-radius: 4px;
}

.typing-indicator {
  display: inline-block;
  width: 20px;
  height: 20px;
}

.typing-indicator::after {
  content: '▋';
  animation: blink 1s infinite;
}

@keyframes blink {
  0%, 50% { opacity: 1; }
  51%, 100% { opacity: 0; }
}

/* ===== 输入区域 ===== */
.input-area {
  display: flex;
  padding: 12px;
  background: var(--bg-secondary);
  border-top: 1px solid var(--border);
  gap: 8px;
  flex-shrink: 0;
}

#user-input {
  flex: 1;
  padding: 12px;
  background: var(--input-bg);
  border: 1px solid var(--border);
  border-radius: 20px;
  color: var(--text-primary);
  font-size: 16px;
  outline: none;
  resize: none;
  font-family: inherit;
  max-height: 120px;
}

#user-input:focus {
  border-color: var(--accent);
}

.send-btn {
  padding: 0 20px;
  border-radius: 20px;
  min-width: 60px;
}

.send-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* ===== 状态面板 ===== */
.state-panel {
  position: fixed;
  top: 0;
  right: -100%;
  width: 85%;
  max-width: 360px;
  height: 100%;
  background: var(--bg-secondary);
  z-index: 1001;
  transition: right 0.3s ease;
  display: flex;
  flex-direction: column;
}

.state-panel.left-panel {
  right: auto;
  left: -100%;
  transition: left 0.3s ease;
}

.state-panel.open {
  right: 0;
}

.state-panel.left-panel.open {
  left: 0;
}

.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px;
  border-bottom: 1px solid var(--border);
}

.panel-header h3 {
  font-size: 18px;
}

.panel-content {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  -webkit-overflow-scrolling: touch;
}

.state-section {
  margin-bottom: 20px;
}

.state-section h4 {
  color: var(--accent);
  margin-bottom: 10px;
  font-size: 15px;
}

.state-list {
  background: var(--bg-primary);
  border-radius: 8px;
  padding: 8px;
}

.state-item {
  display: flex;
  justify-content: space-between;
  padding: 8px 12px;
  font-size: 14px;
  border-bottom: 1px solid var(--border);
}

.state-item:last-child {
  border-bottom: none;
}

.state-item .key {
  color: var(--text-secondary);
}

.state-item .value {
  color: var(--text-primary);
  font-weight: 500;
}

.npc-item {
  background: var(--bg-primary);
  border-radius: 8px;
  padding: 12px;
  margin-bottom: 8px;
}

.npc-item h5 {
  color: var(--accent);
  margin-bottom: 4px;
}

.npc-item p {
  font-size: 13px;
  color: var(--text-secondary);
}

.summary-text {
  background: var(--bg-primary);
  border-radius: 8px;
  padding: 12px;
  font-size: 13px;
  line-height: 1.6;
  color: var(--text-secondary);
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 1000;
}

/* ===== 响应式适配 ===== */
@media (min-width: 768px) {
  .state-panel {
    width: 360px;
  }
  
  .dialogue-bubble {
    max-width: 70%;
  }
}

@media (min-width: 1024px) {
  .setup-container {
    padding-top: 40px;
  }
  
  .dialogue-container {
    max-width: 800px;
    margin: 0 auto;
    width: 100%;
  }
  
  .input-area {
    justify-content: center;
  }
  
  .input-area > * {
    max-width: 800px;
  }
}
```

### 5.4 前端逻辑 (public/js/app.js)

```javascript
const App = {
  state: {
    currentView: 'setup',
    isStreaming: false,
    gameState: null,
  },

  init() {
    this.loadSavedConfig();
    this.bindEvents();
    this.checkGameState();
  },

  loadSavedConfig() {
    const apiKey = localStorage.getItem('llm_api_key');
    const provider = localStorage.getItem('llm_provider');
    const model = localStorage.getItem('llm_model');
    const baseURL = localStorage.getItem('llm_base_url');

    if (provider) document.getElementById('provider-select').value = provider;
    if (apiKey) document.getElementById('api-key').value = apiKey;
    if (model) document.getElementById('model-name').value = model;
    if (baseURL) document.getElementById('base-url').value = baseURL;

    const gameBg = localStorage.getItem('game_bg');
    const playerName = localStorage.getItem('player_name');
    const npcs = localStorage.getItem('initial_npcs');

    if (gameBg) document.getElementById('game-bg').value = gameBg;
    if (playerName) document.getElementById('player-name').value = playerName;
    if (npcs) document.getElementById('initial-npcs').value = npcs;

    this.toggleBaseURL();
  },

  saveConfig() {
    const provider = document.getElementById('provider-select').value;
    const apiKey = document.getElementById('api-key').value;
    const model = document.getElementById('model-name').value;
    const baseURL = document.getElementById('base-url').value;

    localStorage.setItem('llm_provider', provider);
    if (apiKey) localStorage.setItem('llm_api_key', apiKey);
    if (model) localStorage.setItem('llm_model', model);
    if (baseURL) localStorage.setItem('llm_base_url', baseURL);

    const gameBg = document.getElementById('game-bg').value;
    const playerName = document.getElementById('player-name').value;
    const npcs = document.getElementById('initial-npcs').value;

    if (gameBg) localStorage.setItem('game_bg', gameBg);
    if (playerName) localStorage.setItem('player_name', playerName);
    if (npcs) localStorage.setItem('initial_npcs', npcs);
  },

  bindEvents() {
    document.getElementById('provider-select').addEventListener('change', () => {
      this.toggleBaseURL();
      this.updateDefaultModel();
    });

    document.getElementById('test-conn-btn').addEventListener('click', () => {
      this.testConnection();
    });

    document.getElementById('start-btn').addEventListener('click', () => {
      this.startGame();
    });

    document.getElementById('send-btn').addEventListener('click', () => {
      this.sendMessage();
    });

    document.getElementById('user-input').addEventListener('keydown', (e) => {
      if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault();
        this.sendMessage();
      }
    });

    document.getElementById('menu-btn').addEventListener('click', () => {
      this.togglePanel('menu');
    });

    document.getElementById('state-btn').addEventListener('click', () => {
      this.togglePanel('state');
    });

    document.getElementById('close-state-btn').addEventListener('click', () => {
      this.closePanels();
    });

    document.getElementById('close-menu-btn').addEventListener('click', () => {
      this.closePanels();
    });

    document.getElementById('overlay').addEventListener('click', () => {
      this.closePanels();
    });

    document.getElementById('back-setup-btn').addEventListener('click', () => {
      this.showView('setup');
      this.closePanels();
    });

    document.getElementById('new-game-btn').addEventListener('click', () => {
      if (confirm('确定要开始新游戏吗？当前进度将丢失。')) {
        this.resetGame();
      }
    });
  },

  toggleBaseURL() {
    const provider = document.getElementById('provider-select').value;
    const group = document.getElementById('base-url-group');
    group.style.display = provider === 'custom' ? 'block' : 'none';
  },

  updateDefaultModel() {
    const provider = document.getElementById('provider-select').value;
    const models = {
      deepseek: 'deepseek-chat',
      openai: 'gpt-3.5-turbo',
      qwen: 'qwen-turbo',
      glm: 'glm-4-flash',
      moonshot: 'moonshot-v1-8k',
    };
    if (models[provider]) {
      document.getElementById('model-name').value = models[provider];
    }
  },

  async testConnection() {
    this.saveConfig();
    const statusEl = document.getElementById('conn-status');
    statusEl.textContent = '测试中...';
    statusEl.className = 'status-text';

    try {
      const res = await fetch('/api/config/test-connection', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({}),
      });
      const data = await res.json();
      if (data.success) {
        statusEl.textContent = '✓ 连接成功';
        statusEl.className = 'status-text success';
      } else {
        statusEl.textContent = '✗ ' + (data.message || '连接失败');
        statusEl.className = 'status-text error';
      }
    } catch (e) {
      statusEl.textContent = '✗ 请求失败';
      statusEl.className = 'status-text error';
    }
  },

  async checkGameState() {
    try {
      const res = await fetch('/api/game/state');
      const data = await res.json();
      if (data.success) {
        this.state.gameState = data.data;
      }
    } catch (e) {
      console.error(e);
    }
  },

  async startGame() {
    this.saveConfig();

    const gameBg = document.getElementById('game-bg').value.trim();
    const playerName = document.getElementById('player-name').value.trim() || '主角';
    const npcsText = document.getElementById('initial-npcs').value.trim();

    if (!gameBg) {
      alert('请填写游戏背景');
      return;
    }

    const initialNPCs = [];
    if (npcsText) {
      npcsText.split('\n').forEach((line, idx) => {
        const parts = line.split('|');
        if (parts[0]?.trim()) {
          initialNPCs.push({
            id: `npc_${idx}`,
            name: parts[0].trim(),
            description: parts[1]?.trim() || '',
          });
        }
      });
    }

    const config = {
      gameBackground: gameBg,
      playerName,
      initialNPCs,
    };

    try {
      const res = await fetch('/api/game/new', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(config),
      });
      const data = await res.json();
      if (data.success) {
        this.state.gameState = data.data;
        this.showView('game');
        this.updateStatePanel();
        this.clearDialogue();
      }
    } catch (e) {
      alert('创建游戏失败: ' + e.message);
    }
  },

  async resetGame() {
    try {
      await fetch('/api/game/reset', { method: 'POST' });
      this.state.gameState = null;
      this.clearDialogue();
      this.showView('setup');
      this.closePanels();
    } catch (e) {
      alert('重置失败');
    }
  },

  showView(view) {
    this.state.currentView = view;
    document.getElementById('setup-view').style.display = view === 'setup' ? 'block' : 'none';
    document.getElementById('game-view').style.display = view === 'game' ? 'flex' : 'none';
  },

  togglePanel(panel) {
    const overlay = document.getElementById('overlay');
    const statePanel = document.getElementById('state-panel');
    const menuPanel = document.getElementById('menu-panel');

    if (panel === 'state') {
      statePanel.classList.toggle('open');
      menuPanel.classList.remove('open');
    } else {
      menuPanel.classList.toggle('open');
      statePanel.classList.remove('open');
    }

    const isOpen = statePanel.classList.contains('open') || menuPanel.classList.contains('open');
    overlay.style.display = isOpen ? 'block' : 'none';

    if (panel === 'state' && statePanel.classList.contains('open')) {
      this.updateStatePanel();
    }
  },

  closePanels() {
    document.getElementById('state-panel').classList.remove('open');
    document.getElementById('menu-panel').classList.remove('open');
    document.getElementById('overlay').style.display = 'none';
  },

  clearDialogue() {
    document.getElementById('dialogue-container').innerHTML = 
      '<div class="dialogue-placeholder"><p>游戏开始，输入你的行动吧...</p></div>';
  },

  addDialogue(role, content, isStreaming = false) {
    const container = document.getElementById('dialogue-container');
    
    const placeholder = container.querySelector('.dialogue-placeholder');
    if (placeholder) placeholder.remove();

    if (!isStreaming) {
      const entry = document.createElement('div');
      entry.className = `dialogue-entry ${role}`;
      entry.innerHTML = `<div class="dialogue-bubble"></div>`;
      container.appendChild(entry);
      const bubble = entry.querySelector('.dialogue-bubble');
      bubble.textContent = content;
      container.scrollTop = container.scrollHeight;
      return bubble;
    } else {
      const entries = container.querySelectorAll('.dialogue-entry');
      const lastEntry = entries[entries.length - 1];
      if (lastEntry && lastEntry.classList.contains('assistant')) {
        const bubble = lastEntry.querySelector('.dialogue-bubble');
        return bubble;
      }
      return null;
    }
  },

  async sendMessage() {
    if (this.state.isStreaming) return;

    const input = document.getElementById('user-input');
    const text = input.value.trim();
    if (!text) return;

    this.addDialogue('user', text);
    input.value = '';
    this.state.isStreaming = true;
    document.getElementById('send-btn').disabled = true;

    const bubble = this.addDialogue('assistant', '', false);
    let fullContent = '';
    let typingEl = document.createElement('span');
    typingEl.className = 'typing-indicator';
    bubble.appendChild(typingEl);

    try {
      const eventSource = this.createStreamRequest(text, bubble, typingEl);
    } catch (e) {
      this.state.isStreaming = false;
      document.getElementById('send-btn').disabled = false;
    }
  },

  createStreamRequest(text, bubble, typingEl) {
    let fullContent = '';
    
    fetch('/api/game/chat', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ input: text }),
    }).then(response => {
      const reader = response.body.getReader();
      const decoder = new TextDecoder();
      let buffer = '';

      return reader.read().then(function processResult(result) {
        if (result.done) return;

        buffer += decoder.decode(result.value, { stream: true });
        const lines = buffer.split('\n\n');
        buffer = lines.pop() || '';

        for (const line of lines) {
          if (!line.trim()) continue;
          
          const eventMatch = line.match(/^event:\s*(.+)/m);
          const dataMatch = line.match(/^data:\s*(.+)/m);
          
          if (!eventMatch || !dataMatch) continue;
          
          const event = eventMatch[1].trim();
          const dataStr = dataMatch[1].trim();
          
          if (dataStr === '[DONE]') {
            typingEl.remove();
            this.state.isStreaming = false;
            document.getElementById('send-btn').disabled = false;
            return;
          }

          try {
            const data = JSON.parse(dataStr);
            this.handleStreamEvent(event, data, bubble, typingEl, (c) => fullContent = c);
          } catch (e) {
            console.error('解析事件失败:', e);
          }
        }

        return reader.read().then(processResult.bind(this));
      }.bind(this));
    }).catch(err => {
      console.error('流请求失败:', err);
      typingEl.remove();
      bubble.textContent = '请求失败: ' + err.message;
      this.state.isStreaming = false;
      document.getElementById('send-btn').disabled = false;
    });
  },

  handleStreamEvent(event, data, bubble, typingEl, setContent) {
    switch (event) {
      case 'chunk':
        if (typingEl) typingEl.remove();
        bubble.textContent += data.content;
        setContent(bubble.textContent);
        document.getElementById('dialogue-container').scrollTop = 
          document.getElementById('dialogue-container').scrollHeight;
        break;

      case 'narrative':
        bubble.textContent = data.content;
        setContent(data.content);
        break;

      case 'state_update':
        if (data.stateUpdate) {
          this.applyStateUpdate(data.stateUpdate);
        }
        break;

      case 'state':
        this.state.gameState = {
          ...this.state.gameState,
          state: {
            player: data.player,
            npcs: data.npcs,
            game: data.game,
            currentRound: data.round,
          },
        };
        break;

      case 'summary_updated':
        if (this.state.gameState) {
          this.state.gameState.summary = {
            ...this.state.gameState.summary,
            currentSummary: data.summary,
          };
        }
        break;

      case 'error':
        if (typingEl) typingEl.remove();
        bubble.textContent = '错误: ' + data.message;
        this.state.isStreaming = false;
        document.getElementById('send-btn').disabled = false;
        break;

      case 'end':
        if (typingEl) typingEl.remove();
        this.state.isStreaming = false;
        document.getElementById('send-btn').disabled = false;
        break;
    }
  },

  applyStateUpdate(update) {
    if (!this.state.gameState) return;
    if (update.player) {
      this.state.gameState.state.player = {
        ...this.state.gameState.state.player,
        ...update.player,
      };
    }
    if (update.game) {
      this.state.gameState.state.game = {
        ...this.state.gameState.state.game,
        ...update.game,
      };
    }
  },

  updateStatePanel() {
    if (!this.state.gameState) return;
    const { player, game, npcs } = this.state.gameState.state;

    const playerEl = document.getElementById('player-state');
    playerEl.innerHTML = '';
    for (const [key, val] of Object.entries(player)) {
      if (key === 'name') continue;
      const div = document.createElement('div');
      div.className = 'state-item';
      const displayKey = this.getCategoryDisplayName(key);
      let displayVal = val;
      if (typeof val === 'object') displayVal = JSON.stringify(val);
      div.innerHTML = `<span class="key">${displayKey}</span><span class="value">${displayVal}</span>`;
      playerEl.appendChild(div);
    }

    const gameEl = document.getElementById('game-state');
    gameEl.innerHTML = '';
    for (const [key, val] of Object.entries(game)) {
      const div = document.createElement('div');
      div.className = 'state-item';
      const displayKey = this.getCategoryDisplayName(key);
      let displayVal = val;
      if (typeof val === 'object') displayVal = JSON.stringify(val);
      div.innerHTML = `<span class="key">${displayKey}</span><span class="value">${displayVal}</span>`;
      gameEl.appendChild(div);
    }

    const npcEl = document.getElementById('npc-list');
    npcEl.innerHTML = '';
    for (const npc of Object.values(npcs)) {
      const div = document.createElement('div');
      div.className = 'npc-item';
      div.innerHTML = `<h5>${npc.name}</h5><p>${npc.description || '暂无描述'}</p>`;
      npcEl.appendChild(div);
    }

    document.getElementById('summary-text').textContent = 
      this.state.gameState.summary?.currentSummary || '暂无总结';
  },

  getCategoryDisplayName(key) {
    const names = {
      health: '生命值',
      sanity: '理智值',
      strength: '力量',
      intelligence: '智力',
      charisma: '魅力',
      luck: '幸运',
      inventory: '物品栏',
      skills: '技能',
      description: '描述',
      day: '天数',
      location: '地点',
      timeOfDay: '时间段',
      quests: '任务',
      reputation: '声望',
      flags: '标记',
      name: '名字',
    };
    return names[key] || key;
  },
};

document.addEventListener('DOMContentLoaded', () => App.init());
```

---

## 第六阶段：Linux 一键启动与部署

### 6.1 启动脚本 (scripts/start.sh)

```bash
#!/bin/bash
set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$PROJECT_DIR"

echo "=========================================="
echo "   AI 文字冒险游戏 - 启动脚本"
echo "=========================================="
echo ""

check_node() {
    if ! command -v node &> /dev/null; then
        echo "❌ 未检测到 Node.js，请先安装 Node.js 16+"
        echo "   推荐使用 nvm: curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash"
        exit 1
    fi
    echo "✅ Node.js 版本: $(node --version)"
}

check_npm() {
    if ! command -v npm &> /dev/null; then
        echo "❌ 未检测到 npm"
        exit 1
    fi
    echo "✅ npm 版本: $(npm --version)"
}

install_deps() {
    if [ ! -d "node_modules" ]; then
        echo ""
        echo "📦 正在安装依赖..."
        npm install --production
        echo "✅ 依赖安装完成"
    fi
}

check_env() {
    if [ ! -f ".env" ]; then
        echo ""
        echo "📝 未检测到 .env 文件，正在从 .env.example 创建..."
        if [ -f ".env.example" ]; then
            cp .env.example .env
            echo "⚠️  请编辑 .env 文件，填入你的 API Key"
            echo "   命令: nano .env"
            echo ""
            read -p "是否现在编辑 .env 文件？(y/n) " -n 1 -r
            echo
            if [[ $REPLY =~ ^[Yy]$ ]]; then
                nano .env
            fi
        fi
    fi
}

start_server() {
    echo ""
    echo "🚀 启动游戏服务器..."
    echo ""
    echo "=========================================="
    echo "   服务器启动中..."
    echo "   请在浏览器打开: http://localhost:3000"
    echo "   按 Ctrl+C 停止服务器"
    echo "=========================================="
    echo ""

    if command -v nodemon &> /dev/null; then
        npx nodemon server/index.js
    else
        node server/index.js
    fi
}

check_node
check_npm
install_deps
check_env
start_server
```

### 6.2 package.json 脚本

```json
{
  "name": "ai-text-game",
  "version": "1.0.0",
  "description": "AI 驱动的文字冒险游戏",
  "main": "server/index.js",
  "scripts": {
    "start": "node server/index.js",
    "dev": "nodemon server/index.js",
    "setup": "bash scripts/start.sh"
  },
  "dependencies": {
    "cors": "^2.8.5",
    "dotenv": "^16.3.1",
    "express": "^4.18.2",
    "openai": "^4.20.0"
  },
  "devDependencies": {
    "nodemon": "^3.0.1"
  },
  "engines": {
    "node": ">=16.0.0"
  }
}
```

### 6.3 使用步骤

```bash
# 1. 克隆或下载项目到本地
cd text-game

# 2. 给启动脚本加执行权限
chmod +x scripts/start.sh

# 3. 运行一键启动脚本
bash scripts/start.sh

# 或者使用 npm
npm run setup
```

### 6.4 完整启动流程

```
用户执行 start.sh
    │
    ├─ 检查 Node.js 是否已安装
    │   └─ 未安装 → 提示安装 nvm/node
    │
    ├─ 检查 npm
    │
    ├─ 检查 node_modules
    │   └─ 不存在 → 自动 npm install
    │
    ├─ 检查 .env 文件
    │   └─ 不存在 → 从 .env.example 复制
    │            → 询问是否编辑
    │
    └─ 启动服务器
        └─ 打开 http://localhost:3000
```

---

## 附录：完整目录结构与配置模板

### A.1 完整文件清单

```
text-game/
├── server/
│   ├── index.js                 # 入口
│   ├── config/
│   │   └── index.js             # 配置加载
│   ├── models/
│   │   ├── gameData.js          # 数据持久化
│   │   └── schema.js            # 数据结构
│   ├── services/
│   │   ├── llmService.js        # 大模型服务
│   │   ├── gameEngine.js        # 游戏引擎
│   │   └── summaryService.js    # 总结服务
│   ├── routes/
│   │   ├── game.js              # 游戏路由
│   │   └── config.js            # 配置路由
│   └── middleware/
│       └── sse.js               # SSE 中间件
├── public/
│   ├── index.html
│   ├── css/
│   │   └── style.css
│   └── js/
│       └── app.js
├── data/                        # 运行时生成
│   ├── game-state.json
│   └── config.json
├── scripts/
│   └── start.sh
├── .env.example
├── .gitignore
├── package.json
└── README.md
```

### A.2 .gitignore

```
node_modules/
data/
.env
*.log
.DS_Store
```

### A.3 实施阶段总结

| 阶段 | 产出 | 可验证标准 |
|------|------|------------|
| 第一阶段 | 项目骨架、Express 基础服务 | `npm start` 能启动，访问 `/api/health` 返回 ok |
| 第二阶段 | 配置系统、LLM 服务 | 填入 API Key 后，测试连接成功 |
| 第三阶段 | 数据三层架构、持久化 | 能创建/读取/保存游戏数据，JSON 文件正确生成 |
| 第四阶段 | 游戏引擎、总结服务 | 能进行一轮对话，状态正确更新，总结周期更新 |
| 第五阶段 | 移动端前端 | 手机浏览器打开 UI 正常，对话流畅 |
| 第六阶段 | 启动脚本 | 干净环境下执行 `bash scripts/start.sh` 能完成安装并启动 |

### A.4 扩展方向

1. **多存档系统**：支持多个游戏存档切换
2. **图片生成**：接入 DALL-E / Stable Diffusion 生成场景图
3. **语音合成**：TTS 朗读剧情
4. **导出功能**：导出整段剧情为小说格式
5. **多人联机**：支持多人协作文字冒险
6. **模板市场**：预设多种游戏背景模板供选择
