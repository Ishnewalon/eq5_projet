import React, {useEffect, useState} from 'react';
import {getAllOffersInvalid, validateOffer} from '../../services/offer-service';
import OfferView from "./OfferView";
import {getCurrentAndFutureSession} from "../../services/session-service";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {FormGroup} from "../SharedComponents/Form/FormGroup";
import {Column} from "../SharedComponents/Column";

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
            <FormGroup>
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
            </FormGroup>
            <div className="row">
                {visibleOffers.map(offer =>
                    <Column col={{lg: 6}} key={offer.id}>
                        <OfferValidation offer={offer} removeFromList={removeFromList}/>
                    </Column>
                )}
            </div>
        </>
    );
}

function OfferValidation({offer, removeFromList}) {
    const buttons = (
        <div className={"card-footer d-flex justify-content-around align-content-center"}>
            <button id="validateBtn" className="btn btn-outline-success fw-bold  border-success"
                    onClick={() => validate(offer, true)}>Valide
            </button>
            <button id="invalidateBtn" className="btn btn-outline-danger fw-bold  border-danger"
                    onClick={() => validate(offer, false)}>Invalide
            </button>
        </div>
    )
    const validate = (offer, isValid) => {
        validateOffer(offer.id, isValid).then(
            () => removeFromList(offer.id))
    }
    return <>
        <OfferView offer={offer} footers={buttons}/>
    </>
}

