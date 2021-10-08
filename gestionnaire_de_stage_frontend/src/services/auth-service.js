import {MonitorModel, Student, Supervisor} from "../models/User";

export const urlBackend = 'http://localhost:8181'
export const methods = {
    POST: 'POST',
    GET: 'GET'
}
export const requestInit = (method, body) => {
    let value = {
        method: method,
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        }
    }
    if (method === methods.POST)
        value['body'] = JSON.stringify(body)
    return value
}
export let user;

export async function signupMonitor(monitor) {
    if (!(monitor instanceof MonitorModel) || !monitor)
        return;
    const response = await fetch(`${urlBackend}/monitor/signup`, requestInit(methods.POST, monitor));
    return await response.json()
}

export async function signupSupervisor(supervisor) {
    if (!(supervisor instanceof Supervisor) || !supervisor)
        return;
    const response = await fetch(`${urlBackend}/supervisor/signup`, requestInit(methods.POST, supervisor));
    return await response.json()
}

export async function signupStudent(student) {
    if (!(student instanceof Student) || !student)
        return;
    const response = await fetch(`${urlBackend}/student/signup`, requestInit(methods.POST, student));
    return await response.json()
}

export async function signIn(userType, email, password) {
    const response = await fetch(`${urlBackend}/${userType}/${email}/${password}`, requestInit(methods.GET));
    return await response.json().then(
        value => user = value
    )
}
