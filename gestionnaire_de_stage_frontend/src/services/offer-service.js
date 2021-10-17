import {methods, requestInit, urlBackend} from "./serviceUtils";
import OfferDTO from "../models/OfferDTO";
import {swalErr, toast} from "../utility";

class OfferService {

    async _createOffer(offer, userType) {
        if (!(offer instanceof OfferDTO) || !offer)
            return;
        const response = await fetch(`${urlBackend}/offers/${userType}/add`, requestInit(methods.POST, offer));
        return await response.json().then(value => {
                if (value.message) {
                    swalErr(value.message).fire({}).then()
                    return
                }
                toast.fire({title: "Offre créé!"}).then()
            },
            err => {
                swalErr(err).fire({}).then()
            })
    }

    async createOfferMonitor(offer) {
        return await this._createOffer(offer, "monitor")
    }

    async createOfferManager(offer) {
        return await this._createOffer(offer, "manager")
    }

    async getAllOffersByDepartment(department){
        const response = await fetch(`${urlBackend}/offers/${department}`, requestInit(methods.GET));
        return await response.json();
    }

    async getAllOffers(){
        const response = await fetch(`${urlBackend}/offers`, requestInit(methods.GET));
        return await response.json();
    }

    async validateOffer(offer) {
        const response = await fetch(`${urlBackend}/offers/validate`, requestInit(methods.PUT, offer));
        return await response.json();
    }
}

const offerService = new OfferService();
export default offerService;
