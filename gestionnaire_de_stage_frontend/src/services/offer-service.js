import {methods, requestInit, urlBackend} from "./auth-service";
import Offer from "../models/Offer";

export default class OfferService {
    static instance = null

    async createOffer(offer) {
        if (!(offer instanceof Offer) || !offer)
            return;
        const response = await fetch(`${urlBackend}/offers/monitor/add`, requestInit(methods.POST, offer));
        return await response.json()
    }

    static getInstance() {
        if (!this.instance) {
            this.instance = new OfferService()
        }
        return this.instance
    }
}

