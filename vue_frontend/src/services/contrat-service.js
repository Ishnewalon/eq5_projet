import {methods, requestInit, urlBackend} from "./serviceUtils";
import Swal from "sweetalert2";
import {UserType} from "@/models/RegisterVars";

const url = `${urlBackend}/contracts`;

export async function managerSignContract(managerSignature, contractId) {
    return await signContract(UserType.MANAGER[0], managerSignature, contractId);
}

export async function monitorSignContract(monitorSignature, contractId) {
    return await signContract(UserType.MONITOR[0], monitorSignature, contractId);
}

export async function studentSignContract(studentSignature, contractId) {
    return await signContract(UserType.STUDENT[0], studentSignature, contractId);
}

async function signContract(userType, signature, contractId) {
    return await fetch(`${url}/${userType}Sign/${signature}/${contractId}`, requestInit(methods.PUT)).then(
        response => {
            return response.json().then(
                body => {
                    if (response.ok) {
                        Swal.fire({text: body.message, icon: 'success'});
                    }

                    if (response.status === 400) {
                        Swal.fire({text: body.message, icon:'error'})
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
                        Swal.fire({text: body.message, icon: 'error'})
                        return Promise.any([]);
                    }
                })
        }, err => console.error(err)
    );
}

export async function getContractForStudent(userId) {
    return await fetch(`${url}/student/${userId}`, requestInit(methods.GET)).then(response => {
        return response.json().then(
            body => {
                if (response.ok) {
                    return body;
                } else {
                    Swal.fire({text: body.message, icon: 'error'});
                    return Promise.any([]);
                }
            })
    }, err => console.error(err));
}

export async function getAllOfferAppReadyToSign(idOfferApplication) {
    return await fetch(`${urlBackend}/applications/applicants/manager/${idOfferApplication}`, requestInit(methods.GET)).then(
        response => {
            return response.json().then((body) => {
                if (response.ok)
                    return body;
                if (response.status === 400)
                    Swal.fire({title: body.message, icon: 'error'})
                return Promise.any([]);
            })
        }, err => console.error(err)
    );
}

export async function startSignerFetch(idOfferApplication, idManager) {
    return await fetch(`${urlBackend}/contracts/start`, requestInit(methods.POST, {
        idOfferApplication: idOfferApplication,
        idManager: idManager
    })).then(
        response => {
            return response.json().then((body) => {
                if (response.ok) {
                    Swal.fire({title: body.message, icon:'success'})
                    return true;
                }
                if (response.status === 400)
                    Swal.fire({title: body.message, icon:'error'})
                return false;
            })
        }, err => console.error(err)
    );
}

export async function getAllSignedContractsForManager(idManager) {
    return await getOneOrMoreContracts(idManager, UserType.MANAGER[0]).then(res => res.json());
}

export async function getAllSignedContractsForMonitor(idMonitor) {
    return await getOneOrMoreContracts(idMonitor, UserType.MONITOR[0]).then(res => res.json());
}

export async function getSignedContractForStudent(idStudent) {
    return await getOneOrMoreContracts(idStudent, UserType.STUDENT[0]).then(
        response => {
            return response.json().then((body) => {
                if (response.ok)
                    return body;
                else
                    swalErr.fire({text: body.message})
                return Promise.any(null);
            })
        }, err => console.error(err)
    );
}

export async function getOneOrMoreContracts(id, userType) {
    return await fetch(`${urlBackend}/contracts/${userType}/signed/${id}`, requestInit(methods.GET));
}
