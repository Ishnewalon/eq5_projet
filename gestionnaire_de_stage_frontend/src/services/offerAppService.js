import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr, toast} from "../utility";
import OfferApp from "../models/OfferApp";


export async function applyToOffer(offerApp) {
    if (!_isApplicationValid(offerApp))
        return;

    return await fetch(`${urlBackend}/applications/apply`, requestInit(methods.POST, offerApp)).then(
        response => {
            response.json().then(
                body => {
                    if (response.status === 201)
                        toast.fire({title: body.message});
                    if (response.status === 400)
                        swalErr.fire({text: body.message})
                }
            )
        });

}

export async function getAllApplicants(email) {
    return await fetch(`${urlBackend}/applications/applicants/${email}`, requestInit(methods.GET)).then(
        response => {
            let body = response.json();
            if (response.status === 200) {
                return body;
            }
            if (response.status === 400) {
                swalErr.fire({text: body.message})
                return Promise.any([]);
            }
        });
}

function _isApplicationValid(offerApp) {
    return offerApp instanceof OfferApp &&
        offerApp.idOffer &&
        offerApp.idStudent;
}

