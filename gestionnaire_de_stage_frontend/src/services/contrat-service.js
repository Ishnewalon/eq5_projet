import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr, toast, toastErr} from "../utility";
import Swal from "sweetalert2";
import {UserType} from "../enums/UserTypes";

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
                    if (response.status === 200)
                        Swal.fire({text: body.message, icon: 'success'});
                    else if (response.status === 400)
                        swalErr.fire({text: body.message})
                    return response.ok;
                })
        }, err => console.error(err)
    );
}

export async function getAllContractsToBeStarted() {
    return await fetch(`${url}/ready_to_sign`, requestInit(methods.GET)).then(res => res.json());
}

export async function getAllContractsToBeSignedForMonitor(monitorId) {
    return await fetch(`${url}/monitor/${monitorId}`, requestInit(methods.GET)).then(
        response =>
            response.json().then(
                body => {
                    if (response.status === 400)
                        return body;
                    else if (response.status === 400)
                        swalErr.fire({text: body.message})
                    return [];
                }), err => console.error(err)
    );
}

export async function getContractForStudent(userId) {
    return await fetch(`${url}/student/${userId}`, requestInit(methods.GET)).then(
        response =>
            response.json().then(
                body => {
                    if (response.status === 200)
                        return body;
                    else if (response.status === 400)
                        swalErr.fire({text: body.message})
                    return [];
                }), err => console.error(err));
}

export async function getAllOfferAppReadyToSign(idOfferApplication) {
    return await fetch(`${urlBackend}/applications/applicants/manager/${idOfferApplication}`, requestInit(methods.GET)).then(
        response =>
            response.json().then((body) => {
                if (response.status === 200)
                    return body;
                else if (response.status === 400)
                    toastErr.fire({title: body.message})
                return [];
            }), err => console.error(err)
    );
}

export async function startSignerFetch(idOfferApplication, idManager) {
    return await fetch(`${urlBackend}/contracts/start`, requestInit(methods.POST, {
        idOfferApplication: idOfferApplication,
        idManager: idManager
    })).then(
        response =>
            response.json().then((body) => {
                if (response.status === 200)
                    toast.fire({title: body.message})
                else if (response.status === 400)
                    toastErr.fire({title: body.message})
                return response.ok;
            }), err => console.error(err)
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
        response =>
            response.json().then((body) => {
                if (response.ok)
                    return body;
                else if (response.status === 400)
                    swalErr.fire({text: body.message})
                return null;
            }), err => console.error(err)
    );
}

export async function getOneOrMoreContracts(id, userType) {
    return await fetch(`${urlBackend}/contracts/${userType}/signed/${id}`, requestInit(methods.GET));
}
