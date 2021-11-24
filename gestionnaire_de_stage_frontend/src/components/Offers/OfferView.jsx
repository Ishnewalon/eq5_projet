import React from 'react';
import PropTypes from "prop-types";

export default function OfferView(props) {
    const {offer} = props;
    return <>
        <div className="shadow-lg rounded-top p-3 border-left border-right border-light">
            <div className={'d-flex align-items-center flex-column'}>
                <h2 className={'d-inline-block display-6 fst-italic'}>
                    <span className={"badge bg-secondary"}>{offer.title}</span></h2>
                <div className={'row mb-0'}>
                    <span className={"col badge text-dark rounded-pill px-2 py-1"}>{offer.department}</span>
                    <span className="ms-2 col badge bg-success px-2 py-1">{offer.salary + '$/header'}</span>
                </div>
                <p>Adresse : {offer.address}</p>
                <p className={'mb-0'}>Description:</p>
                <p>{offer.description}</p>
            </div>
        </div>
    </>
}
OfferView.propTypes = {
    offer: PropTypes.object.isRequired
}
