import React, {createContext, useContext, useState} from 'react';
import {Redirect} from "react-router-dom";
import {ManagerModel, MonitorModel, Student, Supervisor} from "../models/User";
import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr, toast} from "../../../vue_frontend/src/services/utility";
import {UserType} from "../enums/UserTypes";

const authContext = createContext({});

export function AuthProvider({children}) {
    const auth = useProvideAuth();
    return <authContext.Provider value={auth}>{children}</authContext.Provider>;
}


export const useAuth = () => {
    return useContext(authContext);
}

export function RequireAuth({children}) {
    let auth = useAuth();

    if (!auth.user) return <Redirect to="/login"/>

    return children;
}

export function RequireNoAuth({children}) {
    let auth = useAuth();

    if (auth.user) return <Redirect to="/dashboard"/>

    return children;
}


function useProvideAuth() {
    const [user, setUser] = useState(null);

    const signupMonitor = async (monitor) => {
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


    const signupSupervisor = async (supervisor) => {
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


    const signupStudent = async (student) => {
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

    const signIn = async (userType, email, password) => {
        return await fetch(`${urlBackend}/${userType}/${email}/${password}`, requestInit(methods.GET)).then(
            response => {
                return response.json().then(
                    body => {
                        if (response.status === 200) {
                            if (userType === UserType.MONITOR[0]) {
                                setUser(Object.setPrototypeOf(body, MonitorModel.prototype))
                            } else if (userType === UserType.STUDENT[0]) {
                                setUser(Object.setPrototypeOf(body, Student.prototype))
                            } else if (userType === UserType.SUPERVISOR[0]) {
                                setUser(Object.setPrototypeOf(body, Supervisor.prototype))
                            } else if (userType === UserType.MANAGER[0]) {
                                setUser(Object.setPrototypeOf(body, ManagerModel.prototype))
                            }
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
    const signOut = () => {
        setUser(false);
    };
    const isMonitor = () => {
        return user instanceof MonitorModel;
    }

    const isManager = () => {
        return user instanceof ManagerModel;
    }

    const isStudent = () => {
        return user instanceof Student;
    }

    const isSupervisor = () => {
        return user instanceof Supervisor;
    }

    return {
        user,
        signupMonitor,
        signupSupervisor,
        signupStudent,
        signIn,
        signOut,
        isMonitor,
        isManager,
        isStudent,
        isSupervisor,
    };
}
