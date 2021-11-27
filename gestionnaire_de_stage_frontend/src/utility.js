import Swal from "sweetalert2";

export const regexName = /^[a-zA-Z\-\s]+$/
// eslint-disable-next-line
export const regexEmail = /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
export const regexPhone = /^\(?([0-9]{3})\)?[- ]?([0-9]{3})[- ]?([0-9]{4})$/;
export const regexCodePostal = /^([A-Za-z]\s?[0-9]){3}$/;
export const regexMatricule = /^\d{5}$|^\d{7}$/i;
export const regexMatriculeEtudiant = /^\d{7}$/;
export const regexPassword = /^[0-9a-zA-Z]{8,64}/;

export const swalErr = Swal.mixin({
    icon: 'error',
    title: 'Oops...',
});
export const toast = Swal.mixin({
    toast: true,
    icon: 'success',
    position: 'bottom-end',
    showConfirmButton: false,
    timer: 3000,
});
export const toastErr = Swal.mixin({
    toast: true,
    icon: 'error',
    position: 'bottom-end',
    showConfirmButton: false,
    timer: 3000,
});

export const toPdfBlob = (pdfFile) => {
    if (!pdfFile)
        return null;

    const decodedChars = atob(pdfFile);
    const numBytes = new Array(decodedChars.length);
    for (let i = 0; i < numBytes.length; i++)
        numBytes[i] = decodedChars.charCodeAt(i);

    // noinspection JSCheckFunctionSignatures
    return new Blob([new Uint8Array(numBytes), {type: 'application/pdf'}]);
}

export const downloadFile = (blob, fileName) => {
    let myUrl = URL.createObjectURL(blob);

    const a = document.createElement('a')
    a.href = myUrl
    a.download = fileName;
    a.click();

    URL.revokeObjectURL(myUrl);
    toast.fire({title: 'Téléchargé!'}).then()
}
