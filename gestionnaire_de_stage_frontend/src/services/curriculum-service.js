import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr, toast, toastErr} from "../utility";

export async function uploadFile(file, id) {
    let formData = new FormData();
    formData.append("file", file[0]);

    await fetch(`${urlBackend}/curriculum/upload?id=${id}`, {
        mode: 'cors',
        method: "POST",
        body: formData
    }).then(response => {
        if (response.status === 201) {
            toast.fire({title: `${file[0].name} a été téléversé avec succès!`}).then();
            return
        } else if (response.status === 400) {
            toastErr.fire({title: `${file[0].name} n'a pas pu être téléversé...`}).then();
        }
        response.json().then(data =>
            console.log(data.message));
    }, err => console.error(err));


}

export async function getAllCurriculumsByStudentWithPrincipal(studentID) {
    return await fetch(`${urlBackend}/curriculum/all_student/${studentID}`,
        requestInit(methods.GET)).then(
        response => {
            return response.json().then(
                body => {
                    if (response.status === 200) {
                        return body
                    }
                    if (response.status === 400) {
                        swalErr.fire({text: body.message})
                    }
                    return Promise.any([])
                })
        }, err => console.error(err)
    );
}

export async function setPrincipalCurriculum(studentID, curriculumID) {
    return await fetch(`${urlBackend}/student/set_principal/${studentID}/${curriculumID}`,
        requestInit(methods.GET)).then(
        response => {
            return response.json().then(
                body => {
                    if (response.status === 200)
                        toast.fire({title: body.message, icon: 'success'})
                    else if (response.status === 400)
                        toast.fire({title: body.message, icon: 'error'})
                    return response.ok
                })
        }, err => console.error(err)
    );
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




