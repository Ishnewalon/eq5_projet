import {ManagerModel, MonitorModel, Student, Supervisor} from "@/models/User";
import {methods, requestInit, urlBackend} from "./serviceUtils";
import {UserType} from "../views/Register";
import Swal from "sweetalert2";

class AuthService {
    user;
    _isAuthenticated = false;

    isAuthenticated() {
        return this._isAuthenticated;
    }

    isMonitor() {
        return this.user instanceof MonitorModel;
    }

    isManager() {
        return this.user instanceof ManagerModel;
    }

    isStudent() {
        return this.user instanceof Student;
    }

    isSupervisor() {
        return this.user instanceof Supervisor;
    }

    async signupMonitor(monitor) {
        if (!(monitor instanceof MonitorModel) || !monitor)
            return;
        const response = await fetch(`${urlBackend}/monitor/signup`, requestInit(methods.POST, monitor));
        return await response.json().then(value => {
            if (value.message) {
                Swal.fire({title: value.message, icon: 'error'})
            } else {
                Swal.fire({title: "Compte crée", icon: 'success'})
            }
        })
    }

    async signupSupervisor(supervisor) {
        if (!(supervisor instanceof Supervisor) || !supervisor)
            return;
        const response = await fetch(`${urlBackend}/supervisor/signup`, requestInit(methods.POST, supervisor));
        return await response.json().then(value => {
            if (value.message) {
                Swal.fire({title: value.message, icon: 'error'})
            } else {
                Swal.fire({title: "Compte crée", icon: 'success'})
            }
        })
    }

    async signupStudent(student) {
        if (!(student instanceof Student) || !student)
            return;
        const response = await fetch(`${urlBackend}/student/signup`, requestInit(methods.POST, student));
        return await response.json().then(value => {
            if (value.message) {
                Swal.fire({title: value.message, icon: 'error'})
            } else {
                Swal.fire({title: "Compte crée", icon: 'success'})
            }

        })
    }

    async signIn(userType, email, password) {
        const response = await fetch(`${urlBackend}/${userType}/${email}/${password}`, requestInit(methods.GET));
        return await response.json().then(
            (value) => {
                if (value.message) {
                    Swal.fire({title: value.message, icon: 'error'})
                    return
                }

                this.user = value
                if (userType === UserType.MONITOR[0]) {
                    Object.setPrototypeOf(this.user, MonitorModel.prototype)
                } else if (userType === UserType.STUDENT[0]) {
                    Object.setPrototypeOf(this.user, Student.prototype)
                } else if (userType === UserType.SUPERVISOR[0]) {
                    Object.setPrototypeOf(this.user, Supervisor.prototype)
                } else if (userType === UserType.MANAGER[0]) {
                    Object.setPrototypeOf(this.user, ManagerModel.prototype)
                }
                this._isAuthenticated = true
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
        this.user = null
        this._isAuthenticated = false
    }

    getUserId() {
        if (this.user)
            return this.user.id
    }


}

const authService = new AuthService();
export default authService;

