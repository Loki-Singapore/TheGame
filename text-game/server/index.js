const express = require('express');
const cors = require('cors');
const path = require('path');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

// 中间件
app.use(cors());
app.use(express.json());

// 静态文件服务
app.use(express.static(path.join(__dirname, '../public')));

// 健康检查
app.get('/api/health', (req, res) => {
  res.json({ 
    status: 'ok', 
    timestamp: Date.now(),
    message: 'AI文字冒险游戏服务器运行中'
  });
});

app.listen(PORT, () => {
  console.log(`==========================================`);
  console.log(`   AI 文字冒险游戏服务器`);
  console.log(`==========================================`);
  console.log(`   访问地址: http://localhost:${PORT}`);
  console.log(`   健康检查: http://localhost:${PORT}/api/health`);
  console.log(`==========================================`);
  console.log(`   停止服务器: Ctrl+C`);
  console.log(`==========================================`);
});
