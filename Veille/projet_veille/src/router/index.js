import {createRouter, createWebHistory} from 'vue-router'


const routes = [
    {
        path: '/home',
        name: 'Home',
        component: () => import('../components/HelloWorld.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../components/Register/Register.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../components/Login/Login.vue')
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('../components/Dashboard')
    },
    {
        path: '/monitor-view',
        name: 'MonitorView',
        component: () => import('../components/MonitorView/MonitorView')
    },
    {
        path: '/upload-cv',
        name: 'TeleverserCv',
        component: () => import('../components/StudentView/TeleverserCv')
    },
    {
        path: '/ValidationCv',
        name: 'ValidationCv',
        component: () => import('../components/ManagerView/ValidationCv')
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router