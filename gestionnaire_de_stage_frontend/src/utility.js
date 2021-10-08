import Swal from "sweetalert2";

export const swalErr = (err) => Swal.mixin({
    icon: 'error',
    title: 'Oops...',
    text: err
});
export const toast = Swal.mixin({
    toast: true,
    icon: 'success',
    position: 'bottom-end',
    showConfirmButton: false,
    timer: 3000,
});
