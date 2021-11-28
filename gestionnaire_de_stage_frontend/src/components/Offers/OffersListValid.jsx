import React, {useEffect, useState} from "react";
import {getAllOffersValid} from '../../services/offer-service'
import OfferView from './OfferView';
import {getCurrentAndFutureSession} from "../../services/session-service";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {BtnBack} from "../SharedComponents/BtnBack";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {Column, FormGroupV2} from "../SharedComponents/FormGroup/FormGroupV2";

export default function OffersListValid() {

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

    if (offers.length === 0)
        return <>
            <MessageNothingToShow message="Aucune offre valide à afficher"/>
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

