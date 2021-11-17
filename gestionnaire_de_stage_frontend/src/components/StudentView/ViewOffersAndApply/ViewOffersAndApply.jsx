import React, {useEffect, useState} from 'react';
import {getAllOffersByDepartment} from '../../../services/offer-service';
import {useAuth} from "../../../services/use-auth";
import {getCurrentAndFutureSession} from "../../../services/session-service";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {applyToOffer} from "../../../services/offerAppService";
import OfferApp from "../../../models/OfferApp";
import OfferView from "../../OfferView/OfferView";

export default function ViewOffersAndApply() {
    let auth = useAuth();
    const [offers, setOffers] = useState([])
    const [sessions, setSessions] = useState([]);
    const [visibleOffers, setVisibleOffers] = useState([]);

    const setMyVisible = (idSession) =>
        setVisibleOffers(offers.filter(offer => offer.session.id === parseInt(idSession)))

    const removeFromList = (id) => {
        setOffers(prev => prev.filter(items => items.id !== id))
    }

    useEffect(() => {
        getAllOffersByDepartment(auth.user.department)
            .then(offers => {
                setOffers(offers)
            })
            .catch(e => {
                setOffers([])
                console.error(e);
            });
    }, [auth.user.department]);

    useEffect(() => {
        getCurrentAndFutureSession()
            .then(sessions => {
                setSessions(sessions)
                setVisibleOffers(offers.filter(offer => offer.session.id === parseInt(sessions[0].id)))
            })
            .catch(e => {
                setSessions([]);
                console.error(e);
            })
    }, [offers])

    if (offers.length === 0) {
        return <div className={'bg-secondary d-flex py-3 align-items-center justify-content-center text-white'}>
            Au moment, aucune offre n'est disponible
        </div>
    }

    return (
        <>
            <FormGroup>
                <FormField>
                    <label/>
                    <select onChange={(e) => setMyVisible(e.target.value)}>
                        {sessions.map(session =>
                            <option key={session.id}
                                    value={session.id}>{session.typeSession + session.year}</option>)}
                    </select>
                </FormField>
            </FormGroup>
            <ul>
                {visibleOffers.map((offer, index) => <li key={index}><OfferApplication offer={offer}
                                                                                       removeFromList={removeFromList}/>
                </li>)}
            </ul>
        </>
    );
}

function OfferApplication({offer, removeFromList}) {
    let auth = useAuth();
    const apply = offerId => e => {
        e.preventDefault();
        applyToOffer(new OfferApp(offerId, auth.user.id)).then(
            () => removeFromList(offerId));
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
