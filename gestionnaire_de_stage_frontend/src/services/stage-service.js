import {methods, requestInit, urlBackend} from "./serviceUtils";
import Swal from "sweetalert2";
import {swalErr} from "../utility";

export async function createStage(stage){
    return await fetch(`${urlBackend}/stages`, requestInit(methods.POST, stage)).then(
        response => {
            return response.json().then(
                body => {
                    if (response.ok)
                        Swal.fire({text: body.message, icon: 'success'});

                    if (response.status === 400)
                        swalErr.fire({text: body.message})
                })
        }, err => console.error(err)
    );
}
