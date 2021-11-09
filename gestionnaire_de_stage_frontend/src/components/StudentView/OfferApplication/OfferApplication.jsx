import React from 'react';
import {applyToOffer} from "../../../services/offerAppService";
import OfferApp from "../../../models/OfferApp";
import OfferView from "../../OfferView/OfferView";
import {useAuth} from "../../../services/use-auth";

export default function OfferApplication({offer}) {
    let auth = useAuth();
    const apply = offerId => e => {
        e.preventDefault();

        applyToOffer(new OfferApp(offerId, auth.user.id)).then();
    }

    return (
        <div>
            <OfferView offer={offer}/>
            <div className="form-group text-center mt-2">
                <label/>
                <button className="btn btn-primary" onClick={apply(offer.id)}>Soumettre votre candidature
                </button>
            </div>
        </div>
    );

}
