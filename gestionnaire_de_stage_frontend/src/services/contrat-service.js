import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr} from "../utility";

const url = `${urlBackend}/contracts`;

export async function managerSignContract(managerSignature, managerId, contractId){
    return await fetch(`${url}/managerSign/${managerSignature}/${managerId}/${contractId}`, requestInit(methods.GET)).then(res => {
        let body = res.json();
        if(res.ok){
            alert(body.message);
        }
    });
}

export async function getAllContractsToBeStarted(){
    return await fetch(`${url}/ready_to_sign`, requestInit(methods.GET)).then(res => {
        let value = res.json();
        if(res.status === 400){
            swalErr().fire({text: value.message});
            return Promise.any([]);
        }
        return value;
    });
}

