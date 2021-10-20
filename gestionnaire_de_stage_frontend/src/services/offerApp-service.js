import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr, toast} from "../utility";
import OfferApp from "../models/OfferApp";

class OfferAppService {

    async apply(offerApp) {
        if (!this._isApplicationValid(offerApp))
            return;

        const response = await fetch(`${urlBackend}/applications/apply`,
            requestInit(methods.POST, offerApp));
        return await response.json().then(value => {
            console.log(value);
        });
    }

    _isApplicationValid(offerApp) {
        if (offerApp instanceof OfferApp &&
            offerApp.idOffer &&
            offerApp.idCurriculum)
            return true;
        return false;
    }
}

const offerAppService = new OfferAppService();
export default offerAppService;