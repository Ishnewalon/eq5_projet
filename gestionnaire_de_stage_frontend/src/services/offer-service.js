import {methods, requestInit, urlBackend} from "./serviceUtils";
import OfferDTO from "../models/OfferDTO";
import {swalErr, toast} from "../utility";


export async function createOffer(offer) {
    if (!(offer instanceof OfferDTO) || !offer)
        return;
    return await fetch(`${urlBackend}/offers/add`, requestInit(methods.POST, offer)).then(
        response => {
            return response.json().then(
                body => {
                    if (response.status === 201) {
                        toast.fire({title: "Offre créé!"}).then()
                        return body
                    }
                    if (response.status === 400) {
                        swalErr.fire({text: body.message})
                    }
                    return null
                }
            );
        }, err => console.error(err))
}

export async function getAllOffersNotYetApplied(studentId) {
    return await fetch(`${urlBackend}/offers/${studentId}`, requestInit(methods.GET)).then(
        response => {
            return response.json().then(
                body => {
                    if (response.status === 200) {
                        return body
                    }
                    console.error(response)
                    return []
                })
        }, err => console.error(err)
    );
}

export async function getAllOffersInvalid() {
    return await fetch(`${urlBackend}/offers/not_validated`, requestInit(methods.GET)).then(
        response => {
            return response.json().then(
                body => {
                    if (response.status === 200) {
                        return body
                    }
                    console.error(response)
                    return []
                })
        }, err => console.error(err)
    );
}

export async function getAllOffersValid() {
    return await fetch(`${urlBackend}/offers/valid`, requestInit(methods.GET)).then(
        response => {
            return response.json().then(
                body => {
                    if (response.status === 200) {
                        return body
                    }
                    console.error(response)
                    return []
                })
        }, err => console.error(err)
    );
}

export async function validateOffer(offerId, isValid) {
    return await fetch(`${urlBackend}/offers/validate`, requestInit(methods.POST, {
        id: offerId,
        valid: isValid
    })).then(
        response => {
            return response.json().then(
                body => {
                    if (response.status === 200) {
                        let title = isValid ? 'Offre validée!' : 'Offre invalidée!'
                        toast.fire({title: title}).then()
                    }
                    if (response.status === 400)
                        swalErr.fire({text: body.message})
                }
            )
        }, err => {
            console.error(err);
        }
    );
}
