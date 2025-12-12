
<template>
  <div style="display: flex;padding: 10px;justify-content: center;">
  <div style = "color:red;"><h1>{{data.name }}</h1></div>
  <div style="margin: 35px;">{{ data.des }}</div>
  <div style="margin: 35px;text-align: center;"><el-button type="success" >增加一个数</el-button></div>
  <div style="margin: 35px;text-align: center;"><el-button type="danger">减少一个数</el-button></div>
  <div style="margin: 35px;text-align: center;"><el-button @click = "start" type="primary">开始计算</el-button></div>
  <div style="text-align: center;"><el-icon color="#409efc" size = 40 class="no-inherit"> <Share /> </el-icon></div>
  <div style="text-align: center;"><el-button type="danger" :icon="Delete" circle /></div>
  <div ><el-input style="width: 150px;"
          v-model="data.input1"
          class="responsive-input"
          placeholder="Pick a date"
          :suffix-icon="Calendar"
        /></div>
    </div>
    
</template>

<script setup>
import { ref, reactive } from 'vue';
import { Delete, Calendar, } from '@element-plus/icons-vue'
import router from '@/router/index.js'
const data = reactive({
    name: "严教:",
    name1:router.currentRoute.value.query.name1,
    des: "请将这些牌分成点数之和相等的两组（可以不使用上全部牌）",
    input1: "",
    id: router.currentRoute.value.query.id,
})
const start = ()=>{
    alert("计算开始");
}
console.log('获取到id=' + data.id)
console.log('获取到name1=' + data.name1)

</script>
