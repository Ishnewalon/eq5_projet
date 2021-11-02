import { createRouter, createWebHistory } from 'vue-router'
import Register from "../views/Register";
import Login from "../views/Login";
import LoggedIn from "@/views/LoggedIn";
import RegisterStudent from "@/views/RegisterStudent";
import RegisterSupervisor from "@/views/RegisterSupervisor";
import RegisterMonitor from "@/views/RegisterMonitor";
import authService from "@/services/auth-service";

function authenticationCheck(to, from, next)
{
  if(authService.isAuthenticated())
    next();
  else
    next('/login');
}



const routes = [
  {
    path: '/register',
    name: 'Créer un compte',
    component: Register
  },
  {
    path: '/login',
    name: 'Se connecter',
    props:true,
    component: Login
  },
  {
    path:'/logged-in',
    name: 'Connecté',
    beforeEnter: authenticationCheck,
    component: LoggedIn
  },
  {
    path:'/register/supervisor',
    name: 'Inscription superviseur',
    component: RegisterSupervisor
  },
  {
    path:'/register/student',
    name: 'Inscription student',
    component: RegisterStudent
  },
  {
    path:'/register/monitor',
    name: 'Inscription monitor',
    component: RegisterMonitor
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
