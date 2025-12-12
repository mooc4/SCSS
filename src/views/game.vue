<template>
    <div class="game-container">
        <!-- 游戏标题和状态 -->
        <div class="game-header">
            <h2>卡牌分组游戏</h2>
            <div class="game-info">
                <span>剩余卡牌: {{ remainingCardsCount }}张</span>
                <span>当前组数: {{ gameState.gameCount || 0 }}</span>
                <span>得分: {{ gameState.score || 0 }}</span>
            </div>
        </div>

        <!-- 抽取卡牌区域 -->
        <div class="draw-section">
            <div class="card-count-selector">
                <span>抽取张数:</span>
                <el-radio-group v-model="gameState.drawCount">
                    <el-radio :label="4">4张</el-radio>
                    <el-radio :label="5">5张</el-radio>
                    <el-radio :label="6">6张</el-radio>
                    <el-radio :label="7">7张</el-radio>
                </el-radio-group>
                <el-button type="primary" @click="drawCards" :disabled="gameState.isPlaying">
                    抽取卡牌
                </el-button>
            </div>

            <!-- 抽中的卡牌展示 -->
            <div class="drawn-cards">
                <template v-if="currentCardsLength > 0">
                    <div v-for="card in gameState.currentCards" :key="card.id" class="card-item"
                        :class="{ selected: isCardSelected(card.id) }" @click="toggleSelect(card.id)">
                        <div class="card-point">{{ card.point }}</div>
                        <div class="card-suit">{{ getSuitName(card.suit) }}</div>
                    </div>
                </template>
                <div v-else class="empty-cards">
                    点击"抽取卡牌"开始游戏
                </div>
            </div>
        </div>

        <!-- 分组操作区域 -->
        <div class="group-section" v-if="currentCardsLength > 0">
            <div class="group-container">
                <!-- 组A -->
                <div class="group-box group-a">
                    <h3>组A
                        <span class="sum">(和: {{ sumGroupA }})</span>
                        <el-tag :type="sumGroupA === sumGroupB ? 'success' : 'danger'">
                            {{ sumGroupA === sumGroupB ? '✓ 相等' : '✗ 不等' }}
                        </el-tag>
                    </h3>
                    <div class="group-cards">
                        <div v-for="card in gameState.groupA" :key="card.id" class="card-item">
                            <div class="card-point">{{ card.point }}</div>
                        </div>
                    </div>
                </div>

                <!-- 操作按钮 -->
                <div class="group-actions">
                    <el-button @click="assignToGroup('A')" :disabled="!hasSelectedCards">
                        分配到组A →
                    </el-button>
                    <el-button @click="assignToGroup('B')" :disabled="!hasSelectedCards">
                        ← 分配到组B
                    </el-button>
                    <el-button @click="resetGrouping">
                        重置分组
                    </el-button>
                </div>

                <!-- 组B -->
                <div class="group-box group-b">
                    <h3>组B
                        <span class="sum">(和: {{ sumGroupB }})</span>
                        <el-tag :type="sumGroupA === sumGroupB ? 'success' : 'danger'">
                            {{ sumGroupA === sumGroupB ? '✓ 相等' : '✗ 不等' }}
                        </el-tag>
                    </h3>
                    <div class="group-cards">
                        <div v-for="card in gameState.groupB" :key="card.id" class="card-item">
                            <div class="card-point">{{ card.point }}</div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 验证和提交 -->
            <div class="action-buttons">
                <el-button type="success" @click="checkSolution" :disabled="!canSubmit">
                    验证答案
                </el-button>
                <el-button type="info" @click="showHint">
                    提示
                </el-button>
                <el-button type="warning" @click="skipPuzzle">
                    跳过此题
                </el-button>
            </div>
        </div>

        <!-- 游戏记录 -->
        <div class="history-section">
            <h3>游戏记录</h3>
            <el-table :data="gameState.gameHistory" style="width: 100%">
                <el-table-column prop="round" label="轮次" width="80" />
                <el-table-column prop="cardsCount" label="卡牌数" width="100" />
                <el-table-column prop="success" label="结果" width="100">
                    <template #default="scope">
                        <el-tag :type="scope.row.success ? 'success' : 'danger'">
                            {{ scope.row.success ? '成功' : '失败' }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="timeSpent" label="用时(秒)" width="120" />
                <el-table-column prop="createdAt" label="时间" />
            </el-table>
        </div>

        <!-- 提示对话框 -->
        <el-dialog v-model="gameState.hintVisible" title="提示">
            <div v-html="gameState.currentHint"></div>
        </el-dialog>
    </div>
</template>

<script setup>
import { ref, computed, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/utils/request'

// 初始化完整的游戏状态
const gameState = reactive({
    allCards: [],
    remainingCards: [],
    currentCards: [],
    selectedCards: [],
    groupA: [],
    groupB: [],
    drawCount: 4,
    isPlaying: false,
    startTime: null,
    gameHistory: [],
    score: 0,
    gameCount: 0,
    hintVisible: false,
    currentHint: ''
})

// 计算属性 - 使用安全的访问方式
const remainingCardsCount = computed(() =>
    (gameState.remainingCards && gameState.remainingCards.length) || 0
)

const currentCardsLength = computed(() =>
    (gameState.currentCards && gameState.currentCards.length) || 0
)

const sumGroupA = computed(() =>
    (gameState.groupA && gameState.groupA.reduce((sum, card) => sum + card.point, 0)) || 0
)

const sumGroupB = computed(() =>
    (gameState.groupB && gameState.groupB.reduce((sum, card) => sum + card.point, 0)) || 0
)

const hasSelectedCards = computed(() =>
    (gameState.selectedCards && gameState.selectedCards.length > 0) || false
)

const canSubmit = computed(() =>
    currentCardsLength.value === 0 &&
    gameState.groupA.length > 0 &&
    gameState.groupB.length > 0
)

// 辅助方法
const isCardSelected = (cardId) =>
    (gameState.selectedCards && gameState.selectedCards.includes(cardId)) || false

const getSuitName = (suit) => {
    const suits = ['♠', '♥', '♣', '♦']
    return suits[suit] || '?'
}

// 初始化卡牌
const initCards = () => {
    gameState.allCards = []
    for (let suit = 0; suit < 4; suit++) {
        for (let point = 1; point <= 13; point++) {
            for (let i = 0; i < 2; i++) {
                gameState.allCards.push({
                    id: `${suit}-${point}-${i}`,
                    point: point,
                    suit: suit,
                    name: `${point}点`
                })
            }
        }
    }
    resetDeck()
}

// 重置牌堆
const resetDeck = () => {
    if (!gameState.allCards) return
    gameState.remainingCards = [...gameState.allCards]
    shuffleArray(gameState.remainingCards)
}

// 洗牌算法
const shuffleArray = (array) => {
    if (!array) return
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1))
            ;[array[i], array[j]] = [array[j], array[i]]
    }
    return array
}

// 抽取卡牌
const drawCards = () => {
    if (!gameState.remainingCards || gameState.remainingCards.length < gameState.drawCount) {
        ElMessage.warning('牌堆不足，重新洗牌')
        resetDeck()
    }

    gameState.isPlaying = true
    gameState.startTime = Date.now()

    // 抽取指定数量的牌
    gameState.currentCards = gameState.remainingCards.splice(0, gameState.drawCount) || []
    gameState.selectedCards = []
    gameState.groupA = []
    gameState.groupB = []

    gameState.gameCount = (gameState.gameCount || 0) + 1
}

// 选择/取消选择卡牌
const toggleSelect = (cardId) => {
    if (!gameState.selectedCards) {
        gameState.selectedCards = []
    }

    const index = gameState.selectedCards.indexOf(cardId)
    if (index === -1) {
        gameState.selectedCards.push(cardId)
    } else {
        gameState.selectedCards.splice(index, 1)
    }
}

// 分配到组
const assignToGroup = (group) => {
    const selectedCards = (gameState.currentCards || []).filter(card =>
        (gameState.selectedCards || []).includes(card.id)
    )

    if (group === 'A') {
        gameState.groupA = [...(gameState.groupA || []), ...selectedCards]
    } else {
        gameState.groupB = [...(gameState.groupB || []), ...selectedCards]
    }

    // 从当前牌中移除已分配的
    gameState.currentCards = (gameState.currentCards || []).filter(card =>
        !(gameState.selectedCards || []).includes(card.id)
    )

    gameState.selectedCards = []
}

// 重置分组
const resetGrouping = () => {
    gameState.currentCards = [
        ...(gameState.currentCards || []),
        ...(gameState.groupA || []),
        ...(gameState.groupB || [])
    ]
    gameState.groupA = []
    gameState.groupB = []
    gameState.selectedCards = []
}

// 验证答案
const checkSolution = async () => {
    const timeSpent = Math.round((Date.now() - gameState.startTime) / 1000)
    const isSuccess = sumGroupA.value === sumGroupB.value

    if (isSuccess) {
        const scoreEarned = calculateScore(gameState.drawCount, timeSpent)
        gameState.score = (gameState.score || 0) + scoreEarned
        ElMessage.success(`成功！用时${timeSpent}秒，得分+${scoreEarned}`)
    } else {
        ElMessage.error('两组点数和不等，请重新分配')
        return
    }

    // 保存游戏记录
    const gameRecord = {
        round: gameState.gameCount || 0,
        cardsCount: gameState.drawCount || 4,
        cards: [...(gameState.groupA || []), ...(gameState.groupB || [])].map(card => card.point),
        groupA: (gameState.groupA || []).map(card => card.point),
        groupB: (gameState.groupB || []).map(card => card.point),
        success: isSuccess,
        timeSpent: timeSpent,
        score: calculateScore(gameState.drawCount, timeSpent)
    }

    try {
        await request.post('/game/record', gameRecord)
        gameState.gameHistory = [{
            ...gameRecord,
            createdAt: new Date().toLocaleString()
        }, ...(gameState.gameHistory || [])]
    } catch (error) {
        console.error('保存记录失败:', error)
    }

    gameState.isPlaying = false
}

// 计算得分
const calculateScore = (cardsCount, timeSpent) => {
    const baseScore = (cardsCount || 4) * 10
    const timeBonus = Math.max(0, 60 - (timeSpent || 0)) * 2
    return baseScore + timeBonus
}

// 提示功能
const showHint = () => {
    const totalSum = (gameState.currentCards || []).reduce((sum, card) => sum + card.point, 0)
    const targetSum = totalSum / 2

    if (totalSum % 2 !== 0) {
        gameState.currentHint = `所有卡牌点数和为${totalSum}，是奇数，无法平分！`
    } else {
        gameState.currentHint = `
      <p>提示：</p>
      <p>所有卡牌点数和：${totalSum}</p>
      <p>目标每组点数和：${targetSum}</p>
      <p>当前组A：${sumGroupA.value}，组B：${sumGroupB.value}</p>
      <p>还需要：组A差${targetSum - sumGroupA.value}，组B差${targetSum - sumGroupB.value}</p>
    `
    }

    gameState.hintVisible = true
}

// 跳过题目
const skipPuzzle = async () => {
    try {
        await ElMessageBox.confirm('确定要跳过此题吗？', '提示', {
            type: 'warning'
        })

        const timeSpent = Math.round((Date.now() - gameState.startTime) / 1000)
        const gameRecord = {
            round: gameState.gameCount || 0,
            cardsCount: gameState.drawCount || 4,
            success: false,
            timeSpent: timeSpent,
            skipped: true
        }

        await request.post('/game/record', gameRecord)
        gameState.gameHistory = [{
            ...gameRecord,
            createdAt: new Date().toLocaleString()
        }, ...(gameState.gameHistory || [])]

        gameState.isPlaying = false
    } catch {
        // 用户取消
    }
}

// 初始化
onMounted(() => {
    initCards()
    // 加载历史记录
    loadHistory()
})

const loadHistory = async () => {
    try {
        const res = await request.get('/game/history')
        gameState.gameHistory = res.data || []
    } catch (error) {
        console.error('加载历史记录失败:', error)
    }
}
</script>

<style scoped>
/* 样式保持不变 */
.game-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

.game-header {
    text-align: center;
    margin-bottom: 30px;
}

.game-info {
    display: flex;
    justify-content: center;
    gap: 20px;
    margin-top: 10px;
}

.drawn-cards {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    justify-content: center;
    margin: 20px 0;
    min-height: 120px;
}

.card-item {
    width: 80px;
    height: 100px;
    border: 2px solid #ddd;
    border-radius: 8px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s;
    background: white;
}

.card-item:hover {
    transform: translateY(-5px);
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.card-item.selected {
    border-color: #409eff;
    background-color: #f0f7ff;
}

.card-point {
    font-size: 24px;
    font-weight: bold;
}

.card-suit {
    font-size: 20px;
    color: #666;
}

.group-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 30px 0;
}

.group-box {
    flex: 1;
    padding: 20px;
    border: 2px solid #ddd;
    border-radius: 10px;
    min-height: 200px;
}

.group-a {
    border-color: #67c23a;
    background-color: #f0f9eb;
}

.group-b {
    border-color: #f56c6c;
    background-color: #fef0f0;
}

.group-actions {
    display: flex;
    flex-direction: column;
    gap: 10px;
    margin: 0 20px;
}

.group-cards {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    margin-top: 15px;
}

.action-buttons {
    text-align: center;
    margin: 20px 0;
}

.history-section {
    margin-top: 40px;
}

.sum {
    font-size: 14px;
    color: #666;
    margin-left: 10px;
}

.empty-cards {
    text-align: center;
    color: #999;
    font-style: italic;
    padding: 40px;
    border: 2px dashed #ddd;
    border-radius: 8px;
    width: 100%;
}
</style>