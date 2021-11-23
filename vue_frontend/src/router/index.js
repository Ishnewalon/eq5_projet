import {createRouter, createWebHistory} from 'vue-router'
import Register from "../views/Register";
import Login from "../views/Login";
import LoggedIn from "../views/LoggedIn";
import RegisterStudent from "../views/RegisterStudent";
import RegisterSupervisor from "../views/RegisterSupervisor";
import RegisterMonitor from "../views/RegisterMonitor";
import TeleverserCv from "../views/TeleverserCv";
import authService from "../services/auth-service";
import MonitorCreateOffer from "../views/MonitorCreateOffer";
import ManagerCreateOffer from "../views/ManagerCreateOffer";
import ReviewOffers from "../views/ReviewOffers";
import ViewOffers from "@/views/ViewOffers";
import ManagerResumeValidation from "@/views/ManagerResumeValidation";
import ViewAppliedStudents from "@/views/ViewAppliedStudents";
import StartContracts from "@/views/StartContracts";
import ContractToSignStudent from "@/views/ContractToSignStudent";
import ContractsToSign from "@/views/ContractsToSign";


function guardRoute(to, from, next) {
    if (!authService.isAuthenticated())
        next('/login');
    else
        next();
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
        component: Login
    },
    {
        path: '/logged-in',
        name: 'Connecté',
        component: LoggedIn,
        beforeEnter: guardRoute
    },
    {
        path: '/register/supervisor',
        name: 'Inscription superviseur',
        component: RegisterSupervisor
    },
    {
        path: '/register/student',
        name: 'Inscription student',
        component: RegisterStudent
    },
    {
        path: '/register/monitor',
        name: 'Inscription monitor',
        component: RegisterMonitor
    },
    {
        path: '/student/upload_resume',
        name: 'Televerser CV',
        component: TeleverserCv,
        beforeEnter: guardRoute
    },
    {
        path: '/student/view_offers',
        name: 'Étudiant voit offres',
        component: ViewOffers,
        beforeEnter: guardRoute
    },
    {
        path:'/student/sign_contract',
        name: 'Signature du contrat',
        component: ContractToSignStudent,
        beforeEnter: guardRoute
    },
    {
        path: '/monitor/create_offer',
        name: 'Créer une offre en tant que Moniteur',
        component: MonitorCreateOffer,
        beforeEnter: guardRoute
    },
    {
        path: '/monitor/view_applied_students',
        name: 'Moniteur voit les étudiants qui lui ont postulés',
        component: ViewAppliedStudents,
        beforeEnter: guardRoute
    },
    {
        path:'/monitor/sign_contracts',
        name: 'Signature des contrats',
        component: ContractsToSign,
        props:{
            userType: 'monitor'
        },
        beforeEnter: guardRoute
    },
    {
        path: '/manager/create_offer',
        name: 'Créer une offre en tant que Gestionnaire',
        component: ManagerCreateOffer,
        beforeEnter: guardRoute
    },
    {
        path: '/manager/review_offers',
        name: 'Valider les offres',
        component: ReviewOffers,
        beforeEnter: guardRoute
    },
    {
        path:'/manager/sign_contracts',
        name: 'Signature des contrats',
        component: ContractsToSign,
        props:{
            userType: 'manager'
        },
        beforeEnter: guardRoute
    },
    {
        path: '/manager/curriculums/validate',
        name: 'Valider les curriculums en tant que Gestionnaire',
        component: ManagerResumeValidation,
        beforeEnter: guardRoute
    },
    {
        path: '/manager/start_contracts',
        name:'Démarrage des contrats',
        component: StartContracts,
        beforeEnter: guardRoute
    },
    {
        path: '/',
        redirect: '/login'
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
