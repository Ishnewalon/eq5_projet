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

export async function getAllStudents(){
    const response = await fetch(`${urlBackend}/student`, requestInit(methods.GET));
    return await response.json();
}

export async function getStudentsWithInvalidCV(){
    const response = await fetch(`${urlBackend}/student/invalid`, requestInit(methods.GET));
    return await response.json();
}

export async function validateCV(file){
    const response = await fetch(`${urlBackend}/validate_curriculum`, requestInit(methods.POST, file));
    return await response.json();
}
