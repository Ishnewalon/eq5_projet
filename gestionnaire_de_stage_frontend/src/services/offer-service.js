import {methods, requestInit, urlBackend} from "./serviceUtils";
import OfferDTO from "../models/OfferDTO";
import {swalErr, toast} from "../utility";

class OfferService {

    async createOffer(offer) {
        if (!(offer instanceof OfferDTO) || !offer)
            return;
        const response = await fetch(`${urlBackend}/offers/add`, requestInit(methods.POST, offer));
        return await response.json().then(value => {
                if (value.message) {
                    swalErr(value.message).fire({}).then()
                    return
                }
                toast.fire({title: "Offre créé!"}).then()
                return value
            },
            err => {
                swalErr(err).fire({}).then()
            })
    }

    async getAllOffersByDepartment(department) {
        const response = await fetch(`${urlBackend}/offers/${department}`, requestInit(methods.GET));
        return await response.json();
    }

    async getAllOffersInvalid() {
        const response = await fetch(`${urlBackend}/offers/not_validated`, requestInit(methods.GET));
        return await response.json();
    }
    async getAllOffersValid() {
        const response = await fetch(`${urlBackend}/offers/valid`, requestInit(methods.GET));
        return await response.json();
    }

    async validateOffer(offer) {
        const response = await fetch(`${urlBackend}/offers/validate`, requestInit(methods.PUT, offer));
        return await response.json();
    }
}

const offerService = new OfferService();
export default offerService;
