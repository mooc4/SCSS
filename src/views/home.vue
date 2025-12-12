<template>
    <div class="container">
        <!-- 背景层 -->
        <div class="background"></div>
        <!-- 内容层 -->
        <div class="hello">欢迎你:{{ data.user.name }}</div>
    </div>
</template>

<script setup>
import { reactive } from 'vue'
const data = reactive({
    user: JSON.parse(localStorage.getItem('User'))
})
</script>

<style scoped>
.container {
    width: calc(100% + 20px);
    height: calc(100% + 20px);
    margin: -10px;
    position: relative;
}

.background {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: url("@/assets/2.jpg");
    background-size: cover;
    opacity: 0.7;
    /* 只让背景透明 */
}

.hello {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    width: 100%;
    font-size: 30px;
    color: rgb(255, 1, 1);
    z-index: 1;
    font-style: italic;
    margin-left: -50px;
    
    /* 确保在背景之上 */
}
</style>