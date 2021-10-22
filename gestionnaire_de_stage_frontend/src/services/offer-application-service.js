import {methods, requestInit, urlBackend} from "./serviceUtils";
import {swalErr} from "../utility";

class OfferApplicationService{
    async getAllApplicants(email) {
        return await fetch(`${urlBackend}/applications/applicants/${email}`, requestInit(methods.GET))
            .then(res => {
                if(res.status === 400) {
                    res.json().then(err => swalErr().fire({text: err.message}))
                    return Promise.any([]);
                }else{
                    return res.json();
                }
            });
    }
}


const offerApplicationService = new OfferApplicationService();
export default offerApplicationService;

