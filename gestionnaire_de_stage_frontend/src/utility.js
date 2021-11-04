import Swal from "sweetalert2";

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
