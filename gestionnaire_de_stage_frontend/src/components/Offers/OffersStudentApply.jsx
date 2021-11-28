import React, {useEffect, useState} from 'react';
import {getAllOffersByDepartment} from '../../services/offer-service';
import {useAuth} from "../../hooks/use-auth";
import {getCurrentAndFutureSession} from "../../services/session-service";
import {applyToOffer} from "../../services/offerAppService";
import OfferApp from "../../models/OfferApp";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {BsClock, BsClockHistory, MdAttachMoney, MdLocationPin} from "react-icons/all";
import {Column, FormGroupV2} from "../SharedComponents/FormGroup/FormGroupV2";
import styles from "./OffersStudentApply.module.css";

export default function OffersStudentApply() {
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
        return <MessageNothingToShow message="Pour l'instant, aucune offre n'est disponible"/>
    }

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
            <div className="row">
                {visibleOffers.map((offer, index) =>
                    <Column col={{lg: 6}} key={index}>
                        <OfferApplication offer={offer} removeFromList={removeFromList}/>
                    </Column>
                )}
            </div>
        </>
    );
}

function OfferApplication({offer, removeFromList}) {
    let auth = useAuth();
    const apply = offerId => e => {
        e.preventDefault();
        applyToOffer(new OfferApp(offerId, auth.user.id)).then(
            valid => {
                if (valid) removeFromList(offerId)
            });
    }

    return (
        <div className={styles.cardHolder}>
            <div className="card">
                <div className="card-body">
                    <h5 className={`card-title ${styles.jobTitle}`}>{offer.title}</h5>
                    <div className={styles.cardCompanyGlassdoor}>
                        <p className={styles.cardCompanyName}>{offer.creator.companyName}</p>
                    </div>
                    <div className={styles.cardJobDetails}>
                        <p className={`d-flex align-items-center ${styles.cardCompanyLocation}`}>
                            <MdLocationPin/> {offer.address}
                        </p>
                        <p className={`d-flex align-items-center ${styles.cardJobDuration}`}>
                            <BsClock title="Durée du stage" className={"me-1"}/> {offer.nbSemaine}
                        </p>
                        <p className={`d-flex align-items-center ${styles.cardListingDate}`}>
                            <BsClockHistory className={"me-1"}/> Il y
                            a {Math.ceil((new Date().getTime() - new Date(offer.created).getTime()) / (1000 * 3600 * 24))} jour(s)
                        </p>
                        <p className={`d-flex align-items-center ${styles.cardSalaryRange}`}>
                            <MdAttachMoney/> {offer.salary}$/h
                        </p>
                    </div>
                    <div className={styles.cardJobSummary}>
                        <p className="card-text">{offer.description}</p>
                    </div>
                    <button className="btn btn-outline-success" onClick={apply(offer.id)}>
                        Postuler
                    </button>
                </div>
            </div>
        </div>
    );

}
