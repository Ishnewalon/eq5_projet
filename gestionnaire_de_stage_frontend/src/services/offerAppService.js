import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr, toast} from "../utility";
import OfferApp from "../models/OfferApp";
import Swal from "sweetalert2";

class OfferAppService {

    async apply(offerApp) {
        if (!this._isApplicationValid(offerApp))
            return;

        const response = await fetch(`${urlBackend}/applications/apply`,
            requestInit(methods.POST, offerApp));
        return await response.json().then(value => {
            if (value.message) {
                const valid = value.message.contain("Candidature envoyé!");
                Swal.fire({title: value.message, icon: valid ? 'success' : 'error'});
            }
        });
    }

    _isApplicationValid(offerApp) {
        if (offerApp instanceof OfferApp &&
            offerApp.idOffer &&
            offerApp.idStudent)
            return true;
        return false;
    }
}

const offerAppService = new OfferAppService();
export default offerAppService;