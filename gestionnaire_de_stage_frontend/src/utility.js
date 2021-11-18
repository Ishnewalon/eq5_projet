import Swal from "sweetalert2";

export const regexName = /^[a-zA-Z\-\s]+$/
// eslint-disable-next-line
export const regexEmail = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
export const regexPhone = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
export const regexCodePostal = /^([A-Za-z]\s?[0-9]){3}$/;
export const regexMatriculeEtudiant = /^\d{7}$/;

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
