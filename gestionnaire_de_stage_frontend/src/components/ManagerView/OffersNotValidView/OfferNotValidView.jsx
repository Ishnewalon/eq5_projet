import React, {useEffect, useState} from "react";
import {getAllOffersInvalid} from '../../../services/offer-service'
import OfferView from '../../OfferView/OfferView';
import {getCurrentAndFutureSession} from "../../../services/session-service";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";

export default function OfferNotValidView() {

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
                setOffers([])
                console.error(e);
            });
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


// TODO: show msg when no offers
    return (
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
                {visibleOffers.map((offer, index) =>
                    <li className={"mb-3"} key={index}>
                        <OfferView offer={offer}/>
                    </li>
                )}
            </ul>
        </div>
    )
}

