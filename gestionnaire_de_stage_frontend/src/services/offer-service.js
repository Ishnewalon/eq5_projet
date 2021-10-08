import {methods, requestInit, urlBackend} from "./serviceUtils";
import Offer from "../models/Offer";

class OfferService {

    async _createOffer(offer, userType) {
        if (!(offer instanceof Offer) || !offer)
            return;
        const response = await fetch(`${urlBackend}/offers/${userType}/add`, requestInit(methods.POST, offer));
        return await response.json()
    }

    async createOfferMonitor(offer) {
        return await this._createOffer(offer, "monitor")
    }

    async createOfferManager(offer) {
        return await this._createOffer(offer, "manager")
    }
}

const offerService = new OfferService();
export default offerService;
