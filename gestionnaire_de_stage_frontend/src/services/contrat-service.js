import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr} from "../utility";
import Swal from "sweetalert2";
import {UserType} from "../enums/UserTypes";

const url = `${urlBackend}/contracts`;

export async function managerSignContract(managerSignature, managerId, contractId) {
    return await signContract(UserType.MANAGER[0], managerSignature, managerId, contractId);
}

export async function monitorSignContract(monitorSignature, contractId) {
    return await signContract(UserType.MONITOR[0], monitorSignature, undefined, contractId);
}

export async function studentSignContract(studentSignature, contractId){
    return await signContract(UserType.STUDENT[0], studentSignature, undefined, contractId);
}

async function signContract(userType, signature, userId, contractId) {
    let urlToAccess = `${url}/${userType}Sign/${signature}`;

    if(userId)
        urlToAccess += `/${userId}`;

    return await fetch(`${urlToAccess}/${contractId}`, requestInit(methods.PUT)).then(
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

export async function getAllContractsToBeSignedForMonitor(monitorId) {
    return await fetch(`${url}/monitor/${monitorId}`, requestInit(methods.GET)).then(response => {
            return response.json().then(
                body => {
                    if (response.ok) {
                        return body;
                    } else {
                        swalErr.fire({text: body.message})
                        return Promise.any([]);
                    }
                })
        }, err => console.error(err)
    );
}

export async function getContractForStudent(userId){
    return await fetch(`${url}/student/${userId}`, requestInit(methods.GET)).then(response => {
        return response.json().then(
            body => {
                if (response.ok) {
                    return body;
                } else {
                    swalErr.fire({text: body.message})
                    return Promise.any([]);
                }
            })
    }, err => console.error(err));
}

