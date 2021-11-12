import {methods, requestInit, urlBackend} from "./serviceUtils";

export async function getSupervisors() {
    const response = await fetch(`${urlBackend}/supervisor`, requestInit(methods.GET));
    return await response.json();
}

export async function getUnassignedStudents() {
    const response = await fetch(`${urlBackend}/student/needAssignement`, requestInit(methods.GET));
    return await response.json();
}

export async function getStudentsWithoutCv() {
    const response = await fetch(`${urlBackend}/student/no_cv`, requestInit(methods.GET));
    return await response.json();
}

export async function getStudentsWithInvalidCv() {
    const response = await fetch(`${urlBackend}/student/cv_invalid`, requestInit(methods.GET));
    return await response.json();
}

export async function getAllStudents() {
    const response = await fetch(`${urlBackend}/student`, requestInit(methods.GET));
    return await response.json();
}

export async function assignStudentToSupervisor(idStudent, idSupervisor) {//TODO BACKEND cant assign twice
    let obj = {
        idStudent,
        idSupervisor
    };
    const response = await fetch(`${urlBackend}/supervisor/assign/student`,
        requestInit(methods.POST, obj));
    return await response.json();
}

export async function getStudentsStatus(idSupervisor) {
    const response = await fetch(`${urlBackend}/supervisor/students_status/${idSupervisor}`, requestInit(methods.GET));
    return await response.json();
}
