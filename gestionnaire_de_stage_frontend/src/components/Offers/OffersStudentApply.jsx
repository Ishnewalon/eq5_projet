import React, {useEffect, useState} from 'react';
import {useAuth} from "../../services/use-auth";
import {getCurrentAndFutureSession} from "../../services/session-service";
import {FormField} from "../SharedComponents/FormField/FormField";
import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {applyToOffer} from "../../services/offerAppService";
import OfferApp from "../../models/OfferApp";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {BsClock, BsClockHistory, MdAttachMoney, MdLocationPin} from "react-icons/all";
import {getAllOffersNotYetApplied} from "../../services/offer-service";

export default function OffersStudentApply() {
    let auth = useAuth();
    const [offers, setOffers] = useState([])
    const [sessions, setSessions] = useState([]);
    const [visibleOffers, setVisibleOffers] = useState([]);

    const setMyVisible = (idSession) =>{
        console.log(visibleOffers);
        setVisibleOffers(offers.filter(offer => offer.session.id === parseInt(idSession)))
    }

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
                // console.log(offers)
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
            <div className="row">
                {visibleOffers.map((offer, index) =>
                    <div key={index} className="col-md-6 col-12">
                        <OfferApplication offer={offer}/>
                    </div>
                )}
            </div>
        </>
    );
}

function OfferApplication({offer}) {
    // console.log(offer)
    let auth = useAuth();
    const apply = offerId => e => {
        e.preventDefault();
        applyToOffer(new OfferApp(offerId, auth.user.id)).then(() => {
            }
        )
    }

    return (
        <div className="card-holder">
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title job-title">{offer.title}</h5>
                    <div className="card-job-details">
                        <p className="card-company-location d-flex align-items-center">
                            <MdLocationPin/> {offer.address}
                        </p>
                        <p className="card-job-duration d-flex align-items-center">
                            <BsClock title="Durée du stage" className={"me-1"}/> {offer.nbSemaine}
                        </p>
                        <p className="card-listing-date d-flex align-items-center">
                            <BsClockHistory className={"me-1"}/> Il y
                            a {Math.ceil((new Date().getTime() - new Date(offer.created).getTime()) / (1000 * 3600 * 24))} jour(s)
                        </p>
                        <p className="card-salary-range d-flex align-items-center">
                            <MdAttachMoney/> {offer.salary}$/h
                        </p>
                    </div>
                    <div className="card-job-summary">
                        <p className="card-text">{offer.description}</p>
                    </div>
                    <button className="btn btn btn-outline-success" onClick={apply(offer.id)}>
                        Postuler
                    </button>
                </div>
            </div>
        </div>
    );

}
