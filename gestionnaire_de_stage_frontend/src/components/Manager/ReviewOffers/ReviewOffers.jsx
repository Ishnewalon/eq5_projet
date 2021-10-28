import './ReviewOffers.css'
import React, {useEffect, useState} from 'react';
import offerService from '../../../services/offer-service';
import ValidateOffer from "../ValidateOffer/ValidateOffer";
import {swalErr} from "../../../utility";

export default function ReviewOffers() {

    const [offers, setOffers] = useState([])
    useEffect(() => {
        offerService.getAllOffersInvalid()
            .then(offers => {
                console.log(offers)
                setOffers(offers)
            })
            .catch(e => {
                setOffers([]);
                console.error(e);
                swalErr(e).fire({}).then();
            })
    }, [])


    return (
        <>
            <h1 className="text-center">Review Offers</h1>
            <div className='container'>
                <ul>
                    {offers.map((offer, index) => <li key={index}><ValidateOffer offer={offer}/></li>)}
                </ul>
            </div>
        </>
    );
}
