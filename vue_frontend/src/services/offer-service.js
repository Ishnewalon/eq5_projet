import {methods, requestInit, urlBackend} from "./serviceUtils";
import OfferDTO from "../models/OfferDTO";
import Swal from "sweetalert2";

class OfferService {

    async createOffer(offer) {
        if (!(offer instanceof OfferDTO) || !offer)
            return;
        const response = await fetch(`${urlBackend}/offers/add`, requestInit(methods.POST, offer));
        return await this.promisedReturn(response, 'Offre créé', []);
    }

    async getAllOffersByDepartment(department) {
        const response = await fetch(`${urlBackend}/offers/${department}`, requestInit(methods.GET));
        return await this.promisedReturn(response, undefined, []);
    }

    async getAllOffers() {
        const response = await fetch(`${urlBackend}/offers`, requestInit(methods.GET));
        return await this.promisedReturn(response, undefined, []);
    }

    async validateOffer(offer) {
        const response = await fetch(`${urlBackend}/offers/validate`, requestInit(methods.PUT, offer));
        return await this.promisedReturn(response, 'Offre validé', []);
    }


    async promisedReturn(response, successMessage, defaultValue) {
        return await response.json().then(value => {
            if (value.message) {
                Swal.fire({
                    title: value.message,
                    icon: 'error'
                });
                if (defaultValue)
                    return Promise.any(defaultValue);
            }
            if (successMessage) {
                Swal.fire({
                    title: successMessage,
                    icon: 'success'
                });
            }
            return value
        }).catch(err =>
            Swal.fire({
                title: err,
                icon: 'error'
            })
        );
    }
}

const offerService = new OfferService();
export default offerService;
