import {methods, requestInit, urlBackend} from "./serviceUtils";
import Offer from "../models/Offer";

class OfferService {

    async createOffer(offer) {
        if (!(offer instanceof Offer) || !offer)
            return;
        const response = await fetch(`${urlBackend}/offers/monitor/add`, requestInit(methods.POST, offer));
        return await response.json()
    }

}
const offerService = new OfferService();
Object.freeze(offerService);

export default offerService;
