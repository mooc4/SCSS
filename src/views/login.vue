<template>
    <div class="login-container">
        <div class="login-box" >
            <div style="padding: 20px;background-color: white;border-radius: 10px;">
                <el-form ref="formRef" :rules="data.rules" :model="data.form" style="width: 300px;">
                    <div
                        style="font-size: 20px;color:lightpink;margin-bottom: 30px;font-weight: bolder;text-align: center;">
                        欢迎登入</div>
                    <el-form-item prop="account">
                        <el-input v-model="data.form.account" placeholder="请输入账号" prefix-icon="User"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input v-model="data.form.password" placeholder="请输入密码" prefix-icon="Lock" show-password></el-input>
                    </el-form-item>
                    <div style="text-align: center;margin-bottom: 20px;">
                        <el-button type="primary" style="width: 50%;" @click="login">登 入</el-button>
                    </div>
                    <div style="text-align: right;">还没有账号？请 <a style="color: aqua;text-decoration: none;" href="/regist">注册</a> </div>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ElMessage } from 'element-plus';
import request from '@/utils/request';
import { reactive ,ref } from 'vue';

const data = reactive({
    form: {},
    rules: {
        account: [
            { required: true, message: '请输入账号', trigger: 'blur' },        
        ],
        password: [       
            { required: true, message: '请输入密码', trigger: 'blur' }
        ]
    }
})
const formRef = ref()

const login = () => {
    formRef.value.validate((valid) => {
        if (valid) {
            
            request.post('/login', data.form).then(res => {
                if (res.code === 1) {
                    localStorage.setItem('User', JSON.stringify(res.data))
                    ElMessage.success('登录成功')
                    location.href = '/manager/home'
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
.login-box{
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    width: 100%;

}
</style>