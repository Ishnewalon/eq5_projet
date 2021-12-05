import React, {useEffect, useState} from 'react';
import {getAllOffersNotYetApplied} from '../../services/offer-service';
import {useAuth} from "../../hooks/use-auth";
import {getCurrentAndFutureSession} from "../../services/session-service";
import {applyToOffer} from "../../services/offerAppService";
import OfferApp from "../../models/OfferApp";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {FormGroup} from "../SharedComponents/Form/FormGroup";
import OfferView from "./OfferView";
import {ContainerBox} from "../SharedComponents/ContainerBox";
import {Column} from "../SharedComponents/Column";

export default function OffersStudentApply() {
    let auth = useAuth();
    const [offers, setOffers] = useState([])
    const [sessions, setSessions] = useState([]);
    const [visibleOffers, setVisibleOffers] = useState([]);

    const setMyVisible = (idSession) =>
        setVisibleOffers(offers.filter(offer => offer.session.id === parseInt(idSession)))

    useEffect(() => {
        getAllOffersNotYetApplied(auth.user.id)
            .then(offers => {
                setOffers(offers)
            })
            .catch(e => {
                setOffers([])
                console.error(e);
            });
    }, [auth.user.id]);


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
        return <MessageNothingToShow message="Pour l'instant, aucune offre n'est disponible"/>
    }

    return (
        <ContainerBox>
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
                {visibleOffers.map((offer, index) =>
                    <Column col={{lg: 6}} key={index}>
                        <OfferApplication offer={offer}/>
                    </Column>
                )}
            </div>
        </ContainerBox>
    );
}

function OfferApplication({offer}) {
    let auth = useAuth();
    const apply = offerId => e => {
        e.preventDefault();
        applyToOffer(new OfferApp(offerId, auth.user.id)).then()
    }

    const button = (
        <div className={"card-footer d-flex justify-content-around align-content-center my-1"}>
            <button className="btn btn-outline-success fw-bold w-50 border-success" onClick={apply(offer.id)}>
                Postuler
            </button>
        </div>
    )
    return (<>
            <OfferView offer={offer} footers={button}/>
        </>
    );
}
