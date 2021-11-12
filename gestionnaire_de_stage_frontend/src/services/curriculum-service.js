import {methods, requestInit, urlBackend} from "./serviceUtils";
import {toast} from "../utility";

export async function uploadFile(file, id) {
    let formData = new FormData();
    formData.append("file", file[0]);

    const response = await fetch(`${urlBackend}/curriculum/upload?id=${id}`, {
        mode: 'cors',
        method: "POST",
        body: formData
    });
    return await response.json().then(
        success => {
            console.log(success)
            // return success
        }
    )
}

export async function getAllCurriculumsByStudentWithPrincipal(studentID) {
    const response = await fetch(`${urlBackend}/curriculum/all_student/${studentID}`,
        requestInit(methods.GET));
    return await response.json();
}

export async function setPrincipalCurriculum(studentID, curriculumID) {
    const response = await fetch(`${urlBackend}/student/set_principal/${studentID}/${curriculumID}`,
        requestInit(methods.GET));
    return await response.json();
}

export async function getCurriculumWithInvalidCV() {
    const response = await fetch(`${urlBackend}/curriculum/invalid/students`, requestInit(methods.GET));
    return await response.json();
}

export async function getCurriculumWithValidCV() {
    const response = await fetch(`${urlBackend}/curriculum/valid/students`, requestInit(methods.GET));
    return await response.json();
}

export async function validateCurriculum(id, valid) {
    let obj = {
        id,
        valid
    };
    return await fetch(`${urlBackend}/curriculum/validate`, requestInit(methods.POST, obj)).then(
        response => {
            return response.json().then(
                body => {
                    if (response.status === 200)
                        toast.fire({title: body.message, icon: 'success'})
                    else if (response.status === 400)
                        toast.fire({title: body.message, icon: 'error'})
                }
            )
        }
    );
}

export async function downloadCV(id) {
    let requestInit1 = requestInit(methods.GET);
    requestInit1.headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/octet-stream'
    }
    return (await fetch(`${urlBackend}/curriculum/download/${id}`)).blob();
}




