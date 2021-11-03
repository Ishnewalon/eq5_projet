import {ManagerModel, MonitorModel, Student, Supervisor} from "../models/User";
import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr, toast} from "../utility";
import {UserType} from "../enums/UserTypes";

class AuthService { //TODO Hook auth
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
        return await fetch(`${urlBackend}/monitor/signup`, requestInit(methods.POST, monitor)).then(
            response => {
                response.json().then(
                    body => {
                        if (response.status === 201)
                            toast.fire({title: "Compte crée"}).then()
                        if (response.status === 400)
                            swalErr.fire({text: body.message})
                    }
                )
            }, err => console.log(err)
        );
    }

    async signupSupervisor(supervisor) {
        if (!(supervisor instanceof Supervisor) || !supervisor)
            return;
        return await fetch(`${urlBackend}/supervisor/signup`, requestInit(methods.POST, supervisor)).then(
            response => {
                response.json().then(
                    body => {
                        if (response.status === 201)
                            toast.fire({title: "Compte crée"}).then()
                        if (response.status === 400)
                            swalErr.fire({text: body.message})
                    }
                )
            }, err => console.log(err)
        );
    }

    async signupStudent(student) {
        if (!(student instanceof Student) || !student)
            return;
        return await fetch(`${urlBackend}/student/signup`, requestInit(methods.POST, student)).then(
            response => {
                response.json().then(
                    body => {
                        if (response.status === 201)
                            toast.fire({title: "Compte crée"}).then()
                        if (response.status === 400)
                            swalErr.fire({text: body.message})
                    }
                )
            }, err => console.log(err)
        );
    }

    async signIn(userType, email, password) {
        return await fetch(`${urlBackend}/${userType}/${email}/${password}`, requestInit(methods.GET)).then(
            response => {
                return response.json().then(
                    body => {
                        if (response.status === 200) {
                            this.user = body
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
                            toast.fire({title: "Connexion réussie!"}).then()
                            return true
                        }
                        if (response.status === 400) {
                            swalErr.fire({text: body.message})
                        }
                        return false
                    }
                )
            }, err => console.log(err)
        );
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

