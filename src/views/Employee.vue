<template>
    <div>
        <div class="card" style=" margin-bottom: 5px ;">
            <el-input style="width: 240px;margin-right: 10px;" v-model="data.name" placeholder="请输入名称"
                prefix-icon="Search"></el-input>
            <el-button type="primary" @click="load">查 询</el-button>
            <!-- <el-button type="primary" @click="handleAdd">新 增</el-button>
            <el-button type="warning" @click="delBatch">批量删除</el-button> -->
            <el-button type="success" @click="reset">重 置</el-button>
        </div>

        <div class="card" style="margin-top: 10px;">
            <el-table :data="data.tables" stripe border @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" />
                <el-table-column prop="account" label="账号" width="180" />
                <el-table-column prop="password" label="密码" width="180" />
                <el-table-column prop="name" label="姓名" width="180" />
                <el-table-column prop="sex" label="性别" width="180" />
                <el-table-column prop="age" label="年龄" width="180" />
                <el-table-column label="操作">
                    <template #default="scope">
                        <!-- <el-button type="primary" @click="handleUpdata(scope.row)">编辑</el-button> -->
                        <!-- <el-button type="danger" @click="del(scope.row.id)">删除</el-button> -->
                    </template>

                </el-table-column>
            </el-table>
        </div>
        <div style="margin-top: 10px;">
            <el-pagination @current-change="load" v-model:current-page="data.currentPage"
                v-model:page-size="data.pageSize" :page-sizes="[5, 10, 15, 20]" background
                layout="prev, pager, next,total" :total="data.total" />
        </div>
        <!-- 新增对话框 -->
        <el-dialog v-model="data.formVisible" title="请输入修改数据" width="500">
            <el-form ref="FormRef" :rules="data.rules" :model="data.form" label-width="80px"
                style="padding-right: 50px;padding-top: 20px;">
                <el-form-item label="账号" prop="account">
                    <el-input v-model="data.form.account" autocomplete="off" />
                </el-form-item>
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="data.form.name" autocomplete="off" />
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-radio-group v-model="data.form.sex">
                        <el-radio value="男">男</el-radio>
                        <el-radio value="女">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="年龄" prop="age">
                    <el-input-number style="width: 180px ;" :min="0" v-model="data.form.age" autocomplete="off"
                        placeholder="请输入年龄" />
                </el-form-item>

            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="data.formVisible = false">取消</el-button>
                    <el-button type="primary" @click="save">
                        保存
                    </el-button>
                </div>
            </template>
        </el-dialog>

    </div>
</template>

<script setup>
import { reactive ,ref} from 'vue';
import { Search } from '@element-plus/icons-vue'
import request from '../utils/request'
import { ElMessage, ElMessageBox } from 'element-plus';
const FormRef = ref()
const data = reactive({
    tables: [],
    currentPage: 1,
    pageSize: 3,
    dialogVisible: false,
    row: null,
    total: 0,
    name: null,
    formVisible: false,
    form: {},
    sex: ['男', '女'],
    ids: [],
    rules: {
        account: [
            { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        name: [
            { required: true, message: '请输入名称', trigger: 'blur' }
        ]
    }

})
data.tables = data.tables.slice(0, data.pageSize)

const load = () => {
    request.get('/employee/selectPage', {
        params: {
            currentPage: data.currentPage,
            pageSize: data.pageSize,
            name: data.name,
        }
    }).then(res => {
        data.tables = res.data.list
        data.total = res.data.total
    })
}
const reset = () => {
    data.name = null
    load()
}

const handleAdd = () => {
    data.form = {}
    data.formVisible = true
}
const handleUpdata = (row) => {
    data.form = JSON.parse(JSON.stringify(row)) //深拷贝一个新的对象 用于编辑 这样就不会影响行对象数据
    data.formVisible = true
}

const save = () => {                //两种操作 1、新增 2、编辑
    data.form.id ? update() : add()
}
const add = () => {
    FormRef.value.validate((valid, errorFields) => {
        if (valid) {
            request.post('/employee/add', data.form).then(res => {
                if (res.code === 1) {            // === 严格等于包括数据类型    == 会先转型在比较
                    ElMessage.success('保存成功')
                    load()   //刷新表格
                    data.formVisible = false
                } else {
                    ElMessage.error(res.errorFields)
                }
            })
        } else {
            ElMessage.error(res.msg)
        }
    })    
}

const update = () => {
    request.put('/employee/update', data.form).then(res => {
        if (res.code === 1) {            // === 严格等于包括数据类型    == 会先转型在比较
            ElMessage.success('保存成功')
            load()   //刷新表格
            data.formVisible = false
        } else {
            ElMessage.error(res.msg)
        }
    })
}
const del = (id) => {
    ElMessageBox.confirm('确认要删除吗', '删除确认',{ type: 'warning' }).then(() => {
        request.delete('/employee/delete?id=' + id).then(res => {
            if (res.code === 1) {            // === 严格等于包括数据类型    == 会先转型在比较
                ElMessage.success('删除成功')
                load()   //刷新表格
                data.formVisible = false
            } else {
                ElMessage.error(res.msg)
            }
        })
    }).catch()
}

const handleSelectionChange = (rows) => {
    console.log(rows)
    //从选中的行数组里面取出id组成一个数组
    data.ids = rows.map(row => row.id)
    console.log(data.ids)
}

const delBatch = () => {
    if (data.ids.length === 0) {
        ElMessage.warning('请选择要删除的数据')
        return
    }
    ElMessageBox.confirm('确认要删除吗', '删除确认', { type: 'warning' }).then(() => {
    request.delete('/employee/deleteBatch', { data: data.ids }).then(res => {
        if (res.code === 1) {            // === 严格等于包括数据类型    == 会先转型在比较
            ElMessage.success('删除成功')
            load()   //刷新表格
            data.formVisible = false
        } else {
            ElMessage.error(res.msg)
        }
    })
    }).catch()
    }

load()
</script>

<style scoped></style>