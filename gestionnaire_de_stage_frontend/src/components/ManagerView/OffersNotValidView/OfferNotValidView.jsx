import React, {useEffect, useState} from "react";
import {getAllOffersInvalid} from '../../../services/offer-service'
import OfferView from '../../OfferView/OfferView';

export default function OfferNotValidView() {

    const [offers, setOffers] = useState([])
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

// TODO: show msg when no offers
    return (
        <div className='container'>
            <h2 className="text-center">Offres de Stage non validées</h2>
            <ul>
                {offers.map((offer, index) =>
                    <li key={index}>
                        <OfferView offer={offer}/>
                    </li>
                )}
            </ul>
        </div>
    )
}

