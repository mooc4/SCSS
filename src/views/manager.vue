<template>
  <div style="">
    <!-- 头部 -->
    <div style="height: 60px;background-color:deepskyblue;display: flex;align-items: center;">
      <div style="display: flex;align-items: center;color: white;width: 200px;font-size: 20px;margin-left: 10px;">后台管理系统
      </div>
      <div style="flex:1">
      </div>
      <div style="width: 150px;display:flex ; align-items: center;">
        <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" alt=""
          style="height: 40px;width: 40px;">
        <span style="margin-left: 10px;color: white;">{{ data.user.name }}</span>
      </div>
    </div>
    <!-- 下面部分 -->
    <div style="display: flex;">
      <!-- 左侧菜单栏开始 -->
      <div style="width: 200px;border-right: 1px solid #ccc;background-color:azure;min-height: calc(100vh - 60px);">
        <el-menu router :default-active="router.currentRoute.value.path">
          <el-menu-item index="/manager/home">
            <el-icon>
              <House />
            </el-icon>
            首 页
          </el-menu-item>
          <el-menu-item index="/manager/Employee">
            <el-icon>
              <User />
            </el-icon>
            用户管理
          </el-menu-item>
          <el-sub-menu index="1">
            <template #title>
              <el-icon>
                <DataBoard />
              </el-icon>
              <span>选课信息</span>
            </template>
            <!-- <el-menu-item index="/manager/data">用户管理</el-menu-item> -->
            <el-menu-item index="/manager/course">课程管理</el-menu-item>
            <el-menu-item index="/manager/student">学生管理</el-menu-item>
            <el-menu-item index="/manager/SC">选课管理</el-menu-item>
          </el-sub-menu>
          <!-- <el-menu-item index="/manager/test1">测试页面</el-menu-item> -->
          <el-menu-item index="/manager/person">
            <el-icon>
              <UserFilled />
            </el-icon>
            个人信息
          </el-menu-item>
          <el-menu-item @click="logout">
            <el-icon>
              <SwitchButton />
            </el-icon>
            退出登录
          </el-menu-item>
        </el-menu>
      </div>
      <!-- 左侧菜单栏结束 -->
      <!-- 右侧内容开始 -->
      <div style="flex:1;padding:10px;">
        <RouterView @change="updateUser" />
      </div>
      <!-- 右侧内容结束 -->
    </div>
  </div>


</template>
<script setup>
import router from '@/router';
import { reactive } from 'vue';

const data = reactive({
  user:JSON.parse(localStorage.getItem('User'))
})
const logout = () => {
  localStorage.removeItem('User')
  location.href = '/login'
  
}
const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem('User'))
}
</script>