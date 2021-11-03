import React, {useEffect, useState} from 'react';
import {getAllOffersByDepartment} from '../../../services/offer-service';
import AuthService from '../../../services/auth-service';
import ApplyOnOffer from "../ApplyOnOffer/ApplyOnOffer";

export default function ViewOffersAndApply() {//TODO: remove Offer after applied

    const [offers, setOffers] = useState([])

    useEffect(() => {
        getAllOffersByDepartment(AuthService.user.department)
            .then(offers => {
                console.log(offers)

                setOffers(offers)
            })
            .catch(e => {
                setOffers([])
                console.error(e);
            });
    }, [])


    return (
        <div className='container'>
            <h2 className="text-center">Offres de Stage</h2>
            <ul>
                {offers.map((offer, index) => <li key={index}><ApplyOnOffer offer={offer}/></li>)}
            </ul>
        </div>
    );
}
