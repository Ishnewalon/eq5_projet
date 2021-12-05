import React from 'react';
import PropTypes from "prop-types";
import styles from "./OffersView.module.css";
import {BsClock, BsClockHistory, BsTelephone, FaUserTie, MdAttachMoney, MdEmail, MdLocationPin} from "react-icons/all";

export default function OfferView(props) {
    const {offer, footers} = props;
    return <>
        <div className={styles.cardHolder}>
            <div className="card">
                <div className="card-body">
                    <h5 className={`card-title ${styles.jobTitle}`}>{offer.title}</h5>
                    <div className="d-flex justify-content-around">
                        <p>
                            <FaUserTie/> {offer.creator.firstName} {offer.creator.lastName}
                        </p>
                        <p>
                            <BsTelephone/> {offer.creator.phone}
                        </p>
                        <p>
                            <MdEmail/> {offer.creator.email}
                        </p>
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
                </div>
                {footers}
            </div>
        </div>
    </>
}

OfferView.propTypes = {
    offer: PropTypes.object.isRequired
}

