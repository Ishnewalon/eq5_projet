import {methods, requestInit, urlBackend} from "./serviceUtils";

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

export async function getCurriculumWithInvalidCV() {
    const response = await fetch(`${urlBackend}/curriculum/invalid/students`, requestInit(methods.GET));
    return await response.json();
}

export async function validateCV(id, valid) {
    let obj = {
        id,
        valid
    };
    const response = await fetch(`${urlBackend}/curriculum/validate`,
        requestInit(methods.POST, obj));
    return await response.json();
}

export async function downloadCV(id) {
    let requestInit1 = requestInit(methods.GET);
    requestInit1.headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/octet-stream'
    }
    return (await fetch(`${urlBackend}/curriculum/download/${id}`)).blob();
}
