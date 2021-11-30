import React, {useEffect, useState} from "react";
import {getAllOffersInvalid} from '../../services/offer-service'
import OfferView from './OfferView';
import {getCurrentAndFutureSession} from "../../../../vue_frontend/src/services/session-service";
import {BtnBack} from "../SharedComponents/BtnBack";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {Column, FormGroupV2} from "../SharedComponents/FormGroup/FormGroupV2";

export default function OffersListNotValid() {

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

    if (offers.length === 0)
        return <>
            <MessageNothingToShow message="Toutes les offres sont validées"/>
            <BtnBack/>
        </>

    return (<>
            <ContainerBox>
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
                <div className="row">
                    {visibleOffers.map((offer, index) =>
                        <Column col={{lg: 6}} key={index}>
                            <OfferView offer={offer}/>
                        </Column>
                    )}
                </div>
            </ContainerBox>
            <BtnBack/>
        </>
    )
}

