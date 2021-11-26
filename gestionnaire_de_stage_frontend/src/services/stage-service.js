import {methods, requestInit, urlBackend} from "./serviceUtils";

export async function supervisorCreateForm(stage) {
    return createForm('supervisor', stage);
}


export async function monitorCreateForm(stage) {
    return createForm('monitor', stage);
}


export async function createForm(userType, stage) {
    return await fetch(`${urlBackend}/stages/${userType}/fill_form`, requestInit(methods.POST, stage));
}
