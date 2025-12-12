import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/game', name: 'game', meta: { title: '游戏' }, component: () => import('../views/game.vue') },
    { path: '/login', name: 'login', meta: { title: '请登录' }, component: () => import('../views/login.vue') },
    { path: '/regist', name: 'regist', meta: { title: '请注册' }, component: () => import('../views/regist.vue') },
    
    { path: '/manager', component: () => import('../views/manager.vue'), children:[
      { path: 'home', name: 'home', meta: { title: '首页' ,padding: '0px' }, component: () => import('../views/home.vue') },
      { path: 'test1', name: 'test1', meta: { title: '测试页面' }, component: () => import('../views/test1.vue') },
      { path: 'data', name: 'data', meta: { title: '数据展示页面',padding: '10px'}, component: () => import('../views/data.vue') },
      { path: 'Employee', name: 'Employee', meta: { title: '用户页面' }, component: () => import('../views/Employee.vue') },
      { path: 'course', name: 'course', meta: { title: '课程管理' }, component: () => import('../views/course.vue') },
      { path: 'student', name: 'student', meta: { title: '学生管理' }, component: () => import('../views/student.vue') },
      { path: 'SC', name: 'SC', meta: { title: '选课管理' }, component: () => import('../views/SC.vue') },
      { path: 'person', name: 'person', meta: { title: '修改个人信息' }, component: () => import('../views/person.vue') },
   ]} ,  
  ],
})

export default router
