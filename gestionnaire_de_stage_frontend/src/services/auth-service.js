import {MonitorModel, Student, Supervisor} from "../models/user";

const urlBackend = 'http://localhost:8181'
const methods = {
    POST: 'POST',
    GET: 'GET'
}
const requestInit = (method, body) => {
    return {
        method: method,
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    }
}

export async function getData(user) {
    const response = await fetch("http://localhost:4000/posts", requestInit(methods.POST, user));
    const data = await response.json();

    console.log(data)
    return data
}

export async function signupMonitor(monitor) {
    if (!(monitor instanceof MonitorModel) || !monitor)
        return;
    const response = await fetch(urlBackend + "/monitor/signup", requestInit(methods.POST, monitor));

    const data = await response.json();

    console.log(data)
    return data
}

export async function signupSupervisor(supervisor) {
    if (!(supervisor instanceof Supervisor) || !supervisor)
        return;
    const response = await fetch(urlBackend + "/supervisor/signup", requestInit(methods.POST, supervisor));

    const data = await response.json();

    console.log(data)
    return data
}

export async function signupStudent(student) {
    if (!(student instanceof Student) || !student)
        return;
    const response = await fetch(urlBackend + "/student/signup", requestInit(methods.POST, student));

    const data = await response.json();

    console.log(data)
    return data
}
