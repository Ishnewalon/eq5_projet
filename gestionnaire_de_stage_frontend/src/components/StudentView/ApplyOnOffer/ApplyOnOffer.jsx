import React from 'react';
import AuthService from "../../../services/auth-service";
import {applyToOffer} from "../../../services/offerAppService";
import OfferApp from "../../../models/OfferApp";
import PreviewOffer from "../../PreviewOffer/PreviewOffer";

export default function ApplyOnOffer({offer}) {

    const apply = offerId => e => {
        e.preventDefault();

        applyToOffer(new OfferApp(offerId, AuthService.getUserId())).then();
    }

    return (
        <div>
            <PreviewOffer offer={offer}/>
            <div className="form-group text-center mt-2">
                <label/>
                <button className="btn btn-primary" onClick={apply(offer.id)}>Soumettre votre candidature
                </button>
            </div>
        </div>
    );

}
