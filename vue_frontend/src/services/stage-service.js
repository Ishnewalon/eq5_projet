import {methods, requestInit, urlBackend} from "./serviceUtils";
import Swal from "sweetalert2";
import {swalErr} from "@/services/utility";

export async function supervisorCreateForm(stage) {
    return createForm('supervisor', stage);
}


export async function monitorCreateForm(stage) {
    return createForm('monitor', stage);
}


export async function createForm(userType, stage) {
    return await fetch(`${urlBackend}/stages/${userType}/fill_form`, requestInit(methods.POST, stage)).then(
        response =>
            response.json().then(
                body => {
                    if (response.status === 200)
                        Swal.fire({text: body.message, icon: 'success'});
                    else if (response.status === 400)
                        swalErr.fire({text: body.message})
                }), err => console.error(err)
    );
}
