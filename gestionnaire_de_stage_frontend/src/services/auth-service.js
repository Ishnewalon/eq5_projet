import {MonitorModel, Student, Supervisor} from "../models/User";
import {methods, requestInit, urlBackend} from "./serviceUtils";

class AuthService {
    user;
    _isAuthenticated = false;

    isAuthenticated() {
        return this._isAuthenticated;
    }

    async signupMonitor(monitor) {
        if (!(monitor instanceof MonitorModel) || !monitor)
            return;
        const response = await fetch(`${urlBackend}/monitor/signup`, requestInit(methods.POST, monitor));
        return await response.json()
    }

    async signupSupervisor(supervisor) {
        if (!(supervisor instanceof Supervisor) || !supervisor)
            return;
        const response = await fetch(`${urlBackend}/supervisor/signup`, requestInit(methods.POST, supervisor));
        return await response.json()
    }

    async signupStudent(student) {
        if (!(student instanceof Student) || !student)
            return;
        const response = await fetch(`${urlBackend}/student/signup`, requestInit(methods.POST, student));
        return await response.json()
    }

    async signIn(userType, email, password) {
        const response = await fetch(`${urlBackend}/${userType}/${email}/${password}`, requestInit(methods.GET));
        return await response.json().then(
            value => {
                this.user = value
                this._isAuthenticated = true
                console.log(value)
            },
            err => {
                console.error(err)
            }
        )
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
Object.freeze(authService);

export default authService;

