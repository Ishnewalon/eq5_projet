import React, {useEffect, useState} from 'react';
import {getAllOffersInvalid, validateOffer} from '../../services/offer-service';
import OfferView from "./OfferView";
import {getCurrentAndFutureSession} from "../../services/session-service";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {Column, FormGroupV2} from "../SharedComponents/FormGroup/FormGroupV2";

export default function OffersManagerValidation() {

    const [offers, setOffers] = useState([])
    const [sessions, setSessions] = useState([]);
    const [visibleOffers, setVisibleOffers] = useState([]);

    const setMyVisible = (idSession) =>
        setVisibleOffers(offers.filter(offer => offer.session.id === parseInt(idSession)))


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
                setVisibleOffers(offers.filter(offer => offer.session.id === parseInt(sessions[0].id)))
            })
            .catch(e => {
                setSessions([]);
                console.error(e);
            })
    }, [offers])

    const removeFromList = (id) => {
        setOffers(prev => prev.filter(items => items.id !== id))
    }
    if (offers.length === 0)
        return <MessageNothingToShow message="Aucune offres à valider pour le moment..."/>
    return (
        <>
            <FormGroupV2>
                <Column>
                    <div className="form-floating">
                        <select id="session" className="form-select" onChange={e => setMyVisible(e.target.value)}>
                            {sessions.map(session =>
                                <option key={session.id}
                                        value={session.id}>{session.typeSession + session.year}</option>)}
                        </select>
                        <label htmlFor="session">Session</label>
                    </div>
                </Column>
            </FormGroupV2>
            <div className="container">
                <ul>
                    {visibleOffers.map(offer =>
                        <li key={offer.id}>
                            <OfferValidation offer={offer} removeFromList={removeFromList}/>
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
            () => removeFromList(offer.id))
    }
    return <>
        <OfferView offer={offer}/>
        <div className="d-flex justify-content-between align-items-center mb-4">
            <button id="validateBtn" className="btn btn-success fw-bold w-50 border-success"
                    onClick={() => validate(offer, true)}>Valide
            </button>
            <button id="invalidateBtn" className="btn btn-danger fw-bold w-50 border-danger"
                    onClick={() => validate(offer, false)}>Invalide
            </button>
        </div>
    </>
}

