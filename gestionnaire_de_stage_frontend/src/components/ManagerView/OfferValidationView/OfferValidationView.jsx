import "./OfferValidationView.css"
import React, {useEffect, useState} from 'react';
import {getAllOffersInvalid, validateOffer} from '../../../services/offer-service';
import OfferView from "../../OfferView/OfferView";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {getCurrentAndFutureSession} from "../../../services/session-service";

export default function OfferValidationView() {

    const [offers, setOffers] = useState([])
    const [sessions, setSessions] = useState([]);
    const [visibles, setVisibles] = useState([]);

    const setMyVisible = (idSession) =>
        // eslint-disable-next-line
        setVisibles(offers.filter(offer => offer.session.id == idSession))


    useEffect(() => {
        getAllOffersInvalid()
            .then(offers => {
                setOffers(offers)
            })
            .catch(e => {
                setOffers([]);
                console.error(e);
            })
    }, [])
    useEffect(() => {
        getCurrentAndFutureSession()
            .then(sessions => {
                setSessions(sessions)
                // eslint-disable-next-line
                setVisibles(offers.filter(offer => offer.session.id == sessions[0].id))
            })
            .catch(e => {
                setSessions([]);
                console.error(e);
            })
    }, [offers])

    const setOfferValidated = (id) => {
        setOffers(offers.filter(items => items.id !== id))
    }

    return (
        <>
            <h1 className="text-center">Validation des offres</h1>
            <div className='container'>
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
                    {visibles.map(offer =>
                        <li key={offer.id}>
                            <OfferValidation offer={offer} removeFromList={setOfferValidated}/>
                        </li>
                    )}
                </ul>
            </div>
        </>
    );
}

function OfferValidation({offer, removeFromList}) {
    const validate = (offer, isValid) => {
        validateOffer(offer.id, isValid).then(
            () => removeFromList(offer.id)
        )
    }
    return <>
        <OfferView offer={offer}/>
        <div className="d-flex mb-3 justify-content-between align-items-center">
            <button id="validateBtn" className="btn btn-success fw-bold text-white w-50"
                    onClick={() => validate(offer, true)}>Valide
            </button>
            <button id="invalidateBtn" className="btn btn-danger fw-bold text-white w-50"
                    onClick={() => validate(offer, false)}>Invalide
            </button>
        </div>
    </>
}

