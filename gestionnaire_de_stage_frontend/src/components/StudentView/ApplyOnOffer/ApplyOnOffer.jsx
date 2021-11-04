import React from 'react';
import {applyToOffer} from "../../../services/offerAppService";
import OfferApp from "../../../models/OfferApp";
import PreviewOffer from "../../PreviewOffer/PreviewOffer";
import {useAuth} from "../../../services/use-auth";

export default function ApplyOnOffer({offer}) {
    let auth = useAuth();
    const apply = offerId => e => {
        e.preventDefault();

        applyToOffer(new OfferApp(offerId, auth.user.id)).then();
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
