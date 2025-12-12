<template>
    <div>
        <div class="card" style=" margin-bottom: 5px ;">
            <el-input style="width: 240px;margin-right: 10px;" v-model="data.select" placeholder="请输入名称"
                prefix-icon="Search"></el-input>
            <el-button type="primary">查 询</el-button>
            <el-button type="primary">新 增</el-button>

        </div>
        <div class="card">
            <el-button type="primary">删 除</el-button>
            <el-button type="primary">导 入</el-button>
            <el-button type="primary">导 出</el-button>
        </div>
        <div class="card" style="margin-top: 10px;">
            <el-table :data="data.tables" style="width: 100%" stripe border>
                <el-table-column prop="date" label="日期" width="180" />
                <el-table-column prop="time" label="时间" width="180" />
                <el-table-column prop="address" label="地址" width="180" />
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
                        <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
                    </template>

                </el-table-column>
            </el-table>
        </div>
        <div style="margin-top: 10px;">
            <el-pagination v-model:current-page="data.currentPage" v-model:page-size="data.pageSize"
                :page-sizes="[5, 10, 15, 20]" background layout="prev, pager, next" :total="data.total" />
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
import { reactive } from 'vue';
import { Search } from '@element-plus/icons-vue'
const data = reactive({
    select: '',
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
    total:7,
    
})
data.tables = data.tables.slice(0, data.pageSize)

const del = (id) => {
    alert(id)
}
const edit = (row) => {
    data.row = row
    data.dialogVisible = true
}
</script>

<style scoped></style>