import {ManagerModel, MonitorModel, Student, Supervisor} from "@/models/User";
import {methods, requestInit, urlBackend} from "./serviceUtils";
import Swal from "sweetalert2";

class AuthService {
    isAuthenticated() {
        return localStorage.getItem("user") != null;
    }

    isMonitor() {
        return JSON.parse(localStorage.getItem("user")) instanceof MonitorModel;
    }

    isManager() {
        return JSON.parse(localStorage.getItem("user")) instanceof ManagerModel;
    }

    isStudent() {
        return JSON.parse(localStorage.getItem("user")) instanceof Student;
    }

    isSupervisor() {
        return JSON.parse(localStorage.getItem("user")) instanceof Supervisor;
    }

    async signupMonitor(monitor) {
        localStorage.clear();
        if (!(monitor instanceof MonitorModel) || !monitor)
            return;
        const response = await fetch(`${urlBackend}/monitor/signup`, requestInit(methods.POST, monitor));
        return await response.json().then(value => {
            if (value.message) {
                Swal.fire({title: value.message, icon: 'error'})
            } else {
                localStorage.setItem("user", JSON.stringify(value));
                Swal.fire({title: "Compte crée", icon: 'success'})
            }
        })
    }

    async signupSupervisor(supervisor) {
        localStorage.clear();
        if (!(supervisor instanceof Supervisor) || !supervisor)
            return;
        const response = await fetch(`${urlBackend}/supervisor/signup`, requestInit(methods.POST, supervisor));
        return await response.json().then(value => {
            if (value.message) {
                Swal.fire({title: value.message, icon: 'error'})
            } else {
                localStorage.setItem("user", JSON.stringify(value));
                Swal.fire({title: "Compte crée", icon: 'success'})
            }
        })
    }

    async signupStudent(student) {
        localStorage.clear();
        if (!(student instanceof Student) || !student)
            return;
        const response = await fetch(`${urlBackend}/student/signup`, requestInit(methods.POST, student));
        return await response.json().then(value => {
            if (value.message) {
                Swal.fire({title: value.message, icon: 'error'})
            } else {
                localStorage.setItem("user", JSON.stringify(value));
                Swal.fire({title: "Compte crée", icon: 'success'})
            }

        })
    }

    async signIn(userType, email, password) {
        localStorage.clear();
        const response = await fetch(`${urlBackend}/${userType}/${email}/${password}`, requestInit(methods.GET));
        return await response.json().then(
            (value) => {
                if (value.message) {
                    Swal.fire({title: value.message, icon: 'error'})
                    return;
                }

                localStorage.setItem("user", JSON.stringify(value))
                Swal.fire({title:"Connexion réussie!", icon: 'success'});
                console.log(value)
            },
            err => {
                Swal.fire({title: err, icon: 'error'})
            }
        )
    }

     async getSupervisors() {
        const response = await fetch(`${urlBackend}/supervisor`, requestInit(methods.GET));
        return await response.json();
    }

    async assign(idStudent, idSupervisor) {
        let obj = {
            idStudent,
            idSupervisor
        };
        const response = await fetch(`${urlBackend}/supervisor/assign/student`,
            requestInit(methods.POST, obj));
        return await response.json();
    }

    logOut() {
        localStorage.clear();
    }

    getUserId() {
        if (this.isAuthenticated())
            return parseInt(JSON.parse(localStorage.getItem("user")).id);
    }


}

const authService = new AuthService();
export default authService;

