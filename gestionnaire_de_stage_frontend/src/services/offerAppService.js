import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr, toast, toastErr} from "../utility";
import OfferApp from "../models/OfferApp";

export async function applyToOffer(offerApp) {//TODO: Should not be able to apply when his curriculum is not valid
    if (!_isApplicationValid(offerApp))
        return;

    return await fetch(`${urlBackend}/applications/apply`, requestInit(methods.POST, offerApp)).then(
        response => {
            return response.json().then(
                body => {
                    if (response.status === 201)
                        toast.fire({title: body.message});
                    if (response.status === 400)
                        swalErr.fire({title: body.message})
                    return response.status === 201;
                }
            )
        }
    );
}

export async function getAllApplicants(email) {
    return await fetch(`${urlBackend}/applications/applicants/${email}`, requestInit(methods.GET)).then(
        response => {
            return response.json().then((body) => {
                if (response.ok) {
                    return body;
                }
                if (response.status === 400) {
                    toastErr.fire({title: body.message})
                }
                return Promise.any([]);
            })
        }, err => console.error(err)
    );
}


export async function getStudentApplicationsOffer(id) {
    return await fetch(`${urlBackend}/applications/applicants/offerApp/student/${id}`, requestInit(methods.GET)).then(
        response => {
            return response.json().then((body) => {
                if (response.status === 200)
                    return body;
                if (response.status === 400)
                    toastErr.fire({title: body.message})
                return Promise.any([]);
            })
        }, err => console.error(err)
    );
}

export async function setApplicationsFinalStatus(idOfferApp, isAccepted) {
    return await fetch(`${urlBackend}/applications/student/update_status`, requestInit(methods.POST, {
        idOfferApplied: idOfferApp,
        isAccepted: isAccepted
    })).then(
        response => {
            return response.json().then((body) => {
                if (response.status === 200) {
                    toast.fire({title: body.message})
                    return true;
                }
                if (response.status === 400)
                    toastErr.fire({title: body.message})
                return false;
            })
        }, err => console.error(err)
    );
}

export async function setInterview(offerAppID, date) {
    return await fetch(`${urlBackend}/applications/setdate/${offerAppID}`, requestInit(methods.POST, date)).then(
        response => {
            return response.json().then((body) => {
                if (response.status === 200) {
                    toast.fire({title: "Date Ajoutée!"})
                    return body;
                }
                if (response.status === 400) {
                    toastErr.fire({title: body.message})
                }
                return Promise.any([]);
            })
        }
    );
}

export async function getAllOffersByStudentCvSent(id) {
    return await fetch(`${urlBackend}/applications/applicants/cv_sent/${id}`, requestInit(methods.GET)).then(
        response => {
            return response.json().then((body) => {
                if (response.status === 200) {
                    return body;
                }
                if (response.status === 400) {
                    toastErr.fire({title: body.message})
                }
                return Promise.any([]);
            })
        }
    );
}

function _isApplicationValid(offerApp) {
    return offerApp instanceof OfferApp &&
        offerApp.idOffer &&
        offerApp.idStudent;
}

