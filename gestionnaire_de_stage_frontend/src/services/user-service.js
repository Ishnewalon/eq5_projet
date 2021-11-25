import {methods, requestInit, urlBackend} from "./serviceUtils";
import {toast, toastErr} from "../utility";

export async function getSupervisors() {
    const response = await fetch(`${urlBackend}/supervisor`, requestInit(methods.GET));
    return await response.json();
}

export async function forgotPassword(typeUser, email) {
    return await fetch(`${urlBackend}/forgot_password/${typeUser}/${email}`, requestInit(methods.POST, null))
}

export async function resetPassword(token, password) {
    return await fetch(`${urlBackend}/forgot_password/reset`, requestInit(methods.POST, {
        token: token,
        password: password
    })).then(
        response => {
            return response.json().then((body) => {
                if (response.status === 200)
                    toast.fire({title: body.message})
                if (response.status === 400)
                    toastErr.fire({title: body.message})
                return response.ok;
            })
        }
    );
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

export async function getAllStudentsNotYetEvaluated() {
    const response = await fetch(`${urlBackend}/student/not_evaluated`, requestInit(methods.GET));
    return await response.json();
}

export async function getAllStudentsWithCompanyNotYetEvaluated() {
    const response = await fetch(`${urlBackend}/student/company_not_evaluated`, requestInit(methods.GET));
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

export async function getAllStudentsStatus() {
    const response = await fetch(`${urlBackend}/applications`, requestInit(methods.GET));
    return await response.json();
}

