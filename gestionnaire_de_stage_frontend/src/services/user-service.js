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

export async function checkMatricule(matricule) {
    console.log(matricule);
    let type = matricule.length === 7 ? "student" : "supervisor";
    return await fetch(`${urlBackend}/${type}/matricule/${matricule}`, requestInit(methods.GET)).then(
        response => {
            return response.json().then((body) => {
                return body;
            })
        }
    );
}

export async function checkEmail(email) {
    console.log(email);
    let valid = await fetch(`${urlBackend}/student/email/${email}`, requestInit(methods.GET)).then(
        response =>
            response.json().then(body => body)
    );
    if (!valid)
        return valid;

    valid = await fetch(`${urlBackend}/supervisor/email/${email}`, requestInit(methods.GET)).then(
        response =>
            response.json().then(body => body)
    );
    if (!valid)
        return valid;

    valid = await fetch(`${urlBackend}/monitor/email/${email}`, requestInit(methods.GET)).then(
        response =>
            response.json().then(body => body)
    );
    if (!valid)
        return valid;

    valid = await fetch(`${urlBackend}/manager/email/${email}`, requestInit(methods.GET)).then(
        response =>
            response.json().then(body => body)
    );
    if (!valid)
        return valid;

    return true;
}

export async function getUnassignedStudents() {
    const response = await fetch(`${urlBackend}/applications/applicants/supervisor`, requestInit(methods.GET));
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

export async function assignStudentToSupervisor(idStudent, idSupervisor) {
    let obj = {
        idStudent,
        idSupervisor
    };
    const response = await fetch(`${urlBackend}/supervisor/assign/student`, requestInit(methods.POST, obj));
    return await response.json().then(
        body => {
            if (response.status === 200)
                toast.fire({title: body.message})
            else if (response.status === 400)
                toastErr.fire({title: body.message})
            return response.ok;
        })
}

export async function getStudentsStatus(idSupervisor) {
    const response = await fetch(`${urlBackend}/supervisor/students_status/${idSupervisor}`, requestInit(methods.GET));
    return await response.json();
}

export async function getAllStudentsStatus() {
    const response = await fetch(`${urlBackend}/applications`, requestInit(methods.GET));
    return await response.json();
}

