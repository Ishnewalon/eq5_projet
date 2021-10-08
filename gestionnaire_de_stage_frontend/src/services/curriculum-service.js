import {urlBackend} from "./serviceUtils";

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
