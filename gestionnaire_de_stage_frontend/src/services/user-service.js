import {methods, requestInit, urlBackend} from "./serviceUtils";
import {toast, toastErr} from "../utility";

export async function getNotificationsByUser(userId) {
    const response = await fetch(`${urlBackend}/notification/all/${userId}`, requestInit(methods.GET));
    return await response.json();
}

export async function updateSeen(notificationId) {
    return await fetch(`${urlBackend}/notification/set_seen/${notificationId}`, requestInit(methods.GET)).then(response => {
        return response.json().then(body => {
            if (response.status === 200)
                toast.fire({title: "Vue!"});
            else if (response.status === 400)
                toastErr.fire({title: body.message,});
            return response.ok
        })
    })
}

export async function getSupervisors() {
    const response = await fetch(`${urlBackend}/supervisor`, requestInit(methods.GET));
    return await response.json();
}

export async function changePassword(typeUser, id, password) {
    return await fetch(`${urlBackend}/${typeUser}/change_password/${id}`, requestInit(methods.PUT, password)).then(response => {
        return response.json().then(body => {
            if (response.status === 200)
                toast.fire({title: body.message,});
            else if (response.status === 400)
                toastErr.fire({title: body.message,});
            return response.ok
        })
    })
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
    if (!(await checkEmailExistStudent(email)))
        return false;
    if (!(await checkEmailExistSupervisor(email)))
        return false;
    if (!(await checkEmailExistManager(email)))
        return false;
    return await checkEmailExistMonitor(email);
}

export async function checkEmailExistStudent(email) {
    return await fetch(`${urlBackend}/student/email/${email}`, requestInit(methods.GET)).then(
        response => response.json().then(body => body)
    );
}

async function checkEmailExistSupervisor(email) {
    return await fetch(`${urlBackend}/supervisor/email/${email}`, requestInit(methods.GET)).then(
        response => response.json().then(body => body)
    );
}

async function checkEmailExistManager(email) {
    return await fetch(`${urlBackend}/manager/email/${email}`, requestInit(methods.GET)).then(
        response => response.json().then(body => body)
    );
}

export async function checkEmailExistMonitor(email) {
    return await fetch(`${urlBackend}/monitor/email/${email}`, requestInit(methods.GET)).then(
        response => response.json().then(body => body)
    );
}

export async function getUnassignedStudents() {
    const response = await fetch(`${urlBackend}/applications/applicants/supervisor`, requestInit(methods.GET));
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
