import './ReviewOffers.css'
import React, {useEffect, useState} from 'react';
import {getAllOffersInvalid} from '../../../services/offer-service';
import ValidateOffer from "../ValidateOffer/ValidateOffer";
import {swalErr} from "../../../utility";

export default function ReviewOffers() {

    const [offers, setOffers] = useState([])
    useEffect(() => {
        getAllOffersInvalid()
            .then(offers => {
                setOffers(offers)
            })
            .catch(e => {
                setOffers([]);
                console.error(e);
                swalErr.fire({text: e}).then();
            })
    }, [])

    const setOfferValidated = (id) => {
        setOffers(offers.filter(items => items.id !== id))
    }
    return (
        <>
            <h1 className="text-center">Review Offers</h1>
            <div className='container'>
                <ul>
                    {offers.map(offer =>
                        <li key={offer.id}>
                            <ValidateOffer offer={offer} removeFromList={setOfferValidated}/>
                        </li>
                    )}
                </ul>
            </div>
        </>
    );
}
