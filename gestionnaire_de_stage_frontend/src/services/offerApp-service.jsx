import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr, toast} from "../utility";

class OfferAppService {

    async apply(idOffer, idCurriculum) {
        if (!this._isIdValid(idOffer) || !this._isIdValid(idCurriculum)){
            return;
        }
    }

    _isIdValid(id) {
        if (id && id > 0)
            return true;
        return false;
    }
}

const offerAppService = new OfferAppService();
export default offerAppService;