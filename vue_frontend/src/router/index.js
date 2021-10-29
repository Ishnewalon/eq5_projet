import { createRouter, createWebHistory } from 'vue-router'
import Register from "../views/Register";
import Login from "../views/Login";

const routes = [
  {
    path: '/register',
    name: 'Créer un compte',
    component: Register
  },
  {
    path: '/login',
    name: 'Se connecter',
    component: Login
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
