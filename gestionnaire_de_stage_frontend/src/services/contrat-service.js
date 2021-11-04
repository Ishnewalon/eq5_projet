import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr} from "../utility";
import Swal from "sweetalert2";
import {UserType} from "../enums/UserTypes";

const url = `${urlBackend}/contracts`;

export async function managerSignContract(managerSignature, managerId, contractId) {
    return await signContract(UserType.MANAGER[0], managerSignature, managerId, contractId);
}

async function signContract(userType, signature, userId, contractId) {
    return await fetch(`${url}/${userType}Sign/${signature}/${userId}/${contractId}`, requestInit(methods.GET)).then(
        response => {
            return response.json().then(
                body => {
                    if (response.ok) {
                        Swal.fire({text: body.message, icon: 'success'});
                    }

                    if (response.status === 400) {
                        swalErr.fire({text: body.message})
                    }

                    return response.ok;
                })
        }, err => console.error(err)
    );
}

export async function getAllContractsToBeStarted() {
    return await fetch(`${url}/ready_to_sign`, requestInit(methods.GET)).then(res => res.json());
}

