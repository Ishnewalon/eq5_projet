import React, {createContext, useContext, useEffect, useState} from 'react';
import {Redirect} from "react-router-dom";
import {ManagerModel, MonitorModel, Student, Supervisor} from "../models/User";
import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr, toast} from "../utility";
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
    const [user, setUser] = useState(() => {
        if (sessionStorage.length === 0) return null;
        let item = sessionStorage.getItem('user');
        if (item === "undefined" || item === "null") return null;
        let parse = JSON.parse(item);

        let type = sessionStorage.getItem('type');
        if (type === Student.prototype.constructor.name)
            return Object.setPrototypeOf(parse, Student.prototype);
        if (type === Supervisor.prototype.constructor.name)
            return Object.setPrototypeOf(parse, Supervisor.prototype);
        if (type === ManagerModel.prototype.constructor.name)
            return Object.setPrototypeOf(parse, ManagerModel.prototype);
        if (type === MonitorModel.prototype.constructor.name)
            return Object.setPrototypeOf(parse, MonitorModel.prototype);
        return null;
    });


    useEffect(() => {
        sessionStorage.setItem('user', JSON.stringify(user));
        if (isSupervisor())
            sessionStorage.setItem('type', Supervisor.prototype.constructor.name);
        else if (isStudent())
            sessionStorage.setItem('type', Student.prototype.constructor.name);
        else if (isManager())
            sessionStorage.setItem('type', ManagerModel.prototype.constructor.name);
        else if (isMonitor())
            sessionStorage.setItem('type', MonitorModel.prototype.constructor.name);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [user]);

    const signupMonitor = async (monitor) => {
        if (!(monitor instanceof MonitorModel) || !monitor)
            return;
        return await fetch(`${urlBackend}/monitor/signup`, requestInit(methods.POST, monitor)).then(
            response => {
                response.json().then(
                    body => {
                        if (response.status === 400)
                            swalErr.fire({text: body.message})
                        if (response.status === 201)
                            toast.fire({title: "Compte crée"}).then()
                        else
                            console.error(response)
                    })
                return response.ok
            }
        ).catch(err => console.log(err));
    }


    const signupSupervisor = async (supervisor) => {
        if (!(supervisor instanceof Supervisor) || !supervisor)
            return;
        return await fetch(`${urlBackend}/supervisor/signup`, requestInit(methods.POST, supervisor)).then(
            response => {
                response.json().then(
                    body => {
                        if (response.status === 400)
                            swalErr.fire({text: body.message})
                        if (response.status === 201)
                            toast.fire({title: "Compte crée"}).then()
                        else
                            console.error(response)
                    })
                return response.ok
            }
        ).catch(err => console.log(err));
    }


    const signupStudent = async (student) => {
        if (!(student instanceof Student) || !student)
            return;
        return await fetch(`${urlBackend}/student/signup`, requestInit(methods.POST, student)).then(
            response => {
                response.json().then(
                    body => {
                        if (response.status === 400)
                            swalErr.fire({text: body.message})
                        if (response.status === 201)
                            toast.fire({title: "Compte crée"}).then()
                        else
                            console.error(response)
                    })
                return response.ok
            }
        ).catch(err => console.log(err));
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
        sessionStorage.removeItem("user");
        sessionStorage.removeItem("type");
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
