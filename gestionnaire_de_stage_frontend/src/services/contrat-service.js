import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr, toast} from "../utility";

const url = `${urlBackend}/contracts`;

export async function managerSignContract(managerSignature, managerId, contractId){

    return await fetch(`${url}/managerSign/${managerSignature}/${managerId}/${contractId}`, requestInit(methods.GET)).then(
        response => {
            return response.json().then(
                body => {
                    if (response.status === 201) {
                        toast.fire({title: 'Contrat signé'});
                    }

                    if (response.status === 400) {
                        swalErr.fire({text: body.message})
                    }
                })
        }, err => console.error(err)
    );
}

export async function getAllContractsToBeStarted(){
    return await fetch(`${url}/ready_to_sign`, requestInit(methods.GET)).then(res => res.json());
}

