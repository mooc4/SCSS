<template>
    <div>
        <RouterLink to="/manager/text"> 跳转到text页面</RouterLink>
        <el-button type="primary" @click="router.push('/manager/text')">通过router.push()点击事件跳转</el-button>
        <el-button type="primary" @click="router.replace('/manager/text')">通过router.replace()点击事件跳转</el-button>
        <!-- <el-button type="primary" @click="router.push('/text?id=1')" >获取到id</el-button> -->
        <!-- <el-button type="primary" @click="router.push('/text?id=1&name1=zhangsan')" >获取到id和name1</el-button> -->
        <el-button type="primary"
            @click="router.push({ path: '/manager/text', query: { id: 1, name1: 'zhangsan' } })">获取到id和name1</el-button>
    </div>
    <div style="margin-bottom: 20px;">
        <div> <el-input v-model="input" style="width: 240px" placeholder="请输入" :prefix-icon="Search" /> {{ input }}
        </div>
        <div> <el-input v-model="input1" style="width: 240px" placeholder="请输入" :suffix-icon="Search" /> {{ input1 }}
        </div>
    </div>
    <div>
        <div>
            <el-select clearable v-model="data.option" placeholder="请选择难度" style="width: 240px">
                <el-option v-for="item in data.options" :key="item.id" :label="item.label" :value="item.name" />
            </el-select>
        </div>
        <div>
            <el-radio-group v-model="data.radio">
                <el-radio value="1">Option 1</el-radio>
                <el-radio value="2">Option 2</el-radio>
            </el-radio-group> {{ data.radio }}
        </div>
        <div>
            <el-checkbox-group v-model="data.checkList">
                <el-checkbox v-for="item in data.options" :key="item.id" :value="item.name" :label="item.label" />
            </el-checkbox-group> {{ data.checkList }}
        </div>
    </div>
    <div style="display: flex;">
        <!--      图片
    <div>
    <img src="@/assets/p1.jpg" alt=""> 
    <el-image :src="img" style="height: 300px;width: 400px;"></el-image>
</div> -->
        <!--     轮播图
<div>
    <el-carousel height="300px" style="width: 450px;">
      <el-carousel-item v-for="item in data.imgs" :key="item">
        <img :src="item" alt="" style="width: 100%;height: 100%">
      </el-carousel-item>
    </el-carousel>
</div> -->
    </div>
    <div><el-date-picker v-model="data.value" type="date" placeholder="选择日期" size="default" format="YYYY-MM-DD"
            value-format="YYYY-MM-DD" /> {{ data.value }} </div>
    <div>
        <el-table :data="data.tables" style="width: 100%" stripe border>
            <el-table-column prop="date" label="日期" width="180" />
            <el-table-column prop="time" label="时间" width="180" />
            <el-table-column prop="address" label="地址" width="180" />
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button type="primary" @click="edit(scope.row)">编辑</el-button> <!--   -->
                    <el-button type="danger" @click="del(scope.row.id)">删除</el-button> <!--  取得行数据{{scope.row}} -->
                </template>

            </el-table-column>
        </el-table>
        <div>
            <!-- <el-pagination size="small" layout="prev, pager, next" :total="50" /> -->
            <el-pagination v-model:current-page="data.currentPage" v-model:page-size="data.pageSize"
                :page-sizes="[5, 10, 15, 20]" background layout="prev, pager, next" :total="7" />
        </div>
        <el-dialog v-model="data.dialogVisible" title="编辑行对象" width="500">
            <div>
                <div style="margin-bottom: 10px;">日期：{{ data.row.date }}</div>
                <div style="margin-bottom: 10px;">时间：{{ data.row.time }}</div>
                <div>地址：{{ data.row.address }}</div>
            </div>
        </el-dialog>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import router from '@/router/index.js'
import request from '@/utils/request'
import img from '@/assets/p1.jpg'
import img1 from '@/assets/p2.jpg'
import img2 from '@/assets/p3.jpg'
import { Search } from '@element-plus/icons-vue'
const input = ref('')
const input1 = ref('')
const data = reactive({

    options: [{ id: 1, label: '简单', name: '简单' },
    { id: 2, label: '中等', name: '中等' },
    { id: 3, label: '困难', name: '困难' }],
    option: '',
    radio: '2',
    checkList: ['简单'],
    imgs: [img, img1, img2],
    value: '',
    tables: [
        { id: 1, date: '2023-01-01', time: '12:00', address: '北京' },
        { id: 2, date: '2023-01-02', time: '12:00', address: '上海' },
        { id: 3, date: '2023-01-03', time: '12:00', address: '广州' },
        { id: 4, date: '2023-01-04', time: '12:00', address: '深圳' },
        { id: 5, date: '2023-01-05', time: '12:00', address: '成都' },
        { id: 6, date: '2023-01-06', time: '12:00', address: '杭州' },
        { id: 7, date: '2023-01-07', time: '12:00', address: '武汉' },
    ],
    currentPage: 1,
    pageSize: 3,
    dialogVisible: false,
    row: null,
    employeeList: [],
})
data.tables = data.tables.slice(0, data.pageSize)

const del = (id) => {
    alert(id)
}
const edit = (row) => {
    data.row = row
    data.dialogVisible = true
}
request.get('/employee/selectAll').then(res => {
    console.log(res)
    data.employeeList = res.data
    console.log(data.employeeList)
})

</script>