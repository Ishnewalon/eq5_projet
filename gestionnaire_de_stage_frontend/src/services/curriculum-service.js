import {methods, requestInit, urlBackend} from "./auth-service";


export async function uploadFile(file,id) {
    const response = await fetch(`${urlBackend}/curriculum/upload/${id}`, requestInit(methods.POST, file));
    return await response.json().then(
        success => {
            console.log(success)
            // return success
        }
    )
}