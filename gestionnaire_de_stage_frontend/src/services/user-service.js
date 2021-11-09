import {methods, requestInit, urlBackend} from "./serviceUtils";

export async function getSupervisors() {
    const response = await fetch(`${urlBackend}/supervisor`, requestInit(methods.GET));
    return await response.json();
}

export async function getUnassignedStudents() {
    const response = await fetch(`${urlBackend}/student/needAssignement`, requestInit(methods.GET));
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
