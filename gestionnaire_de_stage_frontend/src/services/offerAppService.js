import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr, toast, toastErr} from "../utility";
import OfferApp from "../models/OfferApp";

export async function applyToOffer(offerApp) {
    if (!_isApplicationValid(offerApp))
        return;

    return await fetch(`${urlBackend}/applications/apply`, requestInit(methods.POST, offerApp)).then(
        response =>
            response.json().then(
                body => {
                    if (response.status === 201)
                        toast.fire({title: body.message});
                    else if (response.status === 400)
                        swalErr.fire({title: body.message})
                    return response.ok;
                }
            )
    );
}

export async function getAllApplicants(email) {
    return await fetch(`${urlBackend}/applications/applicants/${email}`, requestInit(methods.GET)).then(
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


export async function getStudentApplicationsOffer(id) {
    return await fetch(`${urlBackend}/applications/applicants/offerApp/student/${id}`, requestInit(methods.GET)).then(
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

export async function setApplicationsFinalStatus(idOfferApp, status) {
    return await fetch(`${urlBackend}/applications/student/update_status`, requestInit(methods.POST, {
        idOfferApplied: idOfferApp,
        status: status
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

export async function setInterview(offerAppID, date) {
    return await fetch(`${urlBackend}/applications/setdate/${offerAppID}`, requestInit(methods.POST, date)).then(
        response =>
            response.json().then((body) => {
                if (response.status === 200) {
                    toast.fire({title: "Date Ajoutée!"})
                    return body;
                } else if (response.status === 400)
                    toastErr.fire({title: body.message})
                return [];
            })
    );
}

export async function getAllOffersByStudentCvSent(id) {
    return await fetch(`${urlBackend}/applications/applicants/cv_sent/${id}`, requestInit(methods.GET)).then(
        response =>
            response.json().then((body) => {
                if (response.status === 200)
                    return body;
                else if (response.status === 400)
                    toastErr.fire({title: body.message})
                return [];
            })
    );
}

function _isApplicationValid(offerApp) {
    return offerApp instanceof OfferApp &&
        offerApp.idOffer &&
        offerApp.idStudent;
}

