import {createRouter, createWebHistory} from 'vue-router'


const routes = [
    {
        path: '/',
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
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router