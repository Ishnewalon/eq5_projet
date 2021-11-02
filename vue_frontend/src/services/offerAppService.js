import {methods, requestInit, urlBackend} from "./serviceUtils";
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
                const valid = response.status === 201;
                Swal.fire({title: value.message, icon: valid ? 'success' : 'error'});
            }
        });
    }

    async getAllApplicants(email) {
        return await fetch(`${urlBackend}/applications/applicants/${email}`, requestInit(methods.GET))
            .then(res => {
                if(res.status === 400) {
                    res.json().then(err => Swal.fire({title: err.message, icon: 'error'}))
                    return Promise.any([]);
                }else{
                    return res.json();
                }
            });
    }

    _isApplicationValid(offerApp) {
        return offerApp instanceof OfferApp &&
            offerApp.idOffer &&
            offerApp.idStudent;
    }
}

const offerAppService = new OfferAppService();
export default offerAppService;