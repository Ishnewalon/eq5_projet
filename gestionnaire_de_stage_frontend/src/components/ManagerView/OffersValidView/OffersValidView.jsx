import React, {useEffect, useState} from "react";
import {getAllOffersValid} from '../../../services/offer-service'
import OfferView from '../../OfferView/OfferView';
import {getCurrentAndFutureSession} from "../../../services/session-service";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";

export default function OffersValidView() {

    const [offers, setOffers] = useState([])
    const [sessions, setSessions] = useState([]);
    const [visibleOffers, setVisibleOffers] = useState([]);

    const setMyVisible = (idSession) =>
        setVisibleOffers(offers.filter(offer => offer.session.id === parseInt(idSession)))

    useEffect(() => {
        getAllOffersValid()
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
                {visibleOffers.length > 0 ? (
                        visibleOffers.map((offer, index) =>
                            <li className={"mb-4"} key={index}>
                                <OfferView offer={offer}/>
                            </li>
                        )) :
                    <h3 className={"text-center mt-4"}>Aucune offre à afficher</h3>}
            </ul>
        </>
    )
}

