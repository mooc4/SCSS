<template>
    <div class="login-container">
        <div class="login-box">
            <div style="padding: 20px;background-color: white;border-radius: 10px;">
                <el-form ref="formRef" :rules="data.rules" :model="data.form" style="width: 300px;">
                    <div
                        style="font-size: 20px;color:lightpink;margin-bottom: 30px;font-weight: bolder;text-align: center;">
                        欢迎注册</div>
                    <el-form-item prop="name">
                        <el-input v-model="data.form.name" placeholder="请输入姓名" prefix-icon="User"></el-input>
                    </el-form-item>
                    <el-form-item prop="account">
                        <el-input v-model="data.form.account" placeholder="请输入账号" prefix-icon="User"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input v-model="data.form.password" placeholder="请输入密码" prefix-icon="Lock"
                            show-password></el-input>
                    </el-form-item>
                    <el-form-item prop="confirmPassword">
                        <el-input v-model="data.form.confirmPassword" placeholder="请确认密码" prefix-icon="Lock"
                            show-password></el-input>
                    </el-form-item>
                    <div style="text-align: center;margin-bottom: 20px;">
                        <el-button type="primary" style="width: 50%;" @click="regist">注 册</el-button>
                    </div>
                    <div style="text-align: right;">已有账号？请 <a style="color: aqua;text-decoration: none;"
                            href="/login">登录</a> </div>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ElMessage } from 'element-plus';
import request from '@/utils/request';
import { reactive, ref } from 'vue';

const validatePass = (rule, value, callback) => {
    if (!value) {
        callback(new Error('请确认密码'))
    } else if (value !== data.form.password) {
        callback(new Error("两次输入的密码不一致"))
    } else {
        callback()
    }
}
const data = reactive({
    form: {},
    rules: {
        name: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        account: [
            { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        confirmPassword: [
            { validator:validatePass ,  trigger: 'blur' }
        ]
    }
})
const formRef = ref()

const regist = () => {
    formRef.value.validate((valid) => {
        if (valid) {
            request.post('/regist', data.form).then(res => {
                if (res.code === 1) {
                    localStorage.setItem('User', JSON.stringify(res.data))
                    ElMessage.success('注册成功')
                    location.href = '/login'
                } else {
                    ElMessage.error(res.msg)
                }
            })
        }
    })
}


</script>

<style scoped>
.login-container {
    overflow: hidden;
    height: 100vh;
    background: url("@/assets/background1.jpg");
    background-size: cover;
}

.login-box {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    width: 100%;

}
</style>