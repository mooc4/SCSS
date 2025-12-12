<template>
    <div class="personContainer">
        <div class="background">
            <div class="card" style="width: 500px;height: 300px;">
                <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px"
                    style="padding-right: 50px;padding-top: 20px;">
                    <el-form-item label="账号">
                        <el-input v-model="data.form.account" autocomplete="off" disabled />
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="data.form.password" autocomplete="off" />
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
                    <div style="text-align: center;"><el-button type="primary" @click="change">更新个人信息</el-button></div>
                </el-form>
            </div>
        </div>
    </div>

</template>

<script setup>
import request from '@/utils/request';
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
const formRef = ref()
const data = reactive({
    user: JSON.parse(localStorage.getItem('User')),
    name: null,
    form: {},
    rules: {
        name: [
            { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        sex: [
            { required: true, message: '请输入性别', trigger: 'blur' },
        ],
        age: [
            { required: true, message: '请输入年龄', trigger: 'blur' },
        ]
    }

})

const emit = defineEmits(['change'])

request.get('/employee/selectOne?id=' + data.user.id).then(res => {
    data.form = res.data
})
const change = () => {
    formRef.value.validate((valid, errorFields) => {
        if (valid) {
            request.put('/employee/update', data.form).then(res => {
                if (res.code === 1) {
                    ElMessage.success('更新成功')
                    localStorage.setItem('User', JSON.stringify(data.form))   //更新缓存数据
                    emit('change')  //出发从父级缓存更新最新的数据
                } else {
                    ElMessage.error(res.msg)
                }
            })
        } else {
            ElMessage.error("表单填写有误，请检查必填项！")
        }
    })
}
</script>

<style scoped>
.personContainer {
    width: calc(100% + 20px);
    height: calc(100% + 20px);
    margin: -10px;
    position: relative;   
}
.background {
    background: url("@/assets/5.jpg");
    height: 100%;
    background-size: cover;
    display: flex;
    justify-content: center;
    align-items: center;

}
</style>