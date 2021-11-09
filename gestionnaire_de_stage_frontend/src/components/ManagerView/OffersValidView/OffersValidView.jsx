import React, {useEffect, useState} from "react";
import {getAllOffersValid} from '../../../services/offer-service'
import OfferView from '../../OfferView/OfferView';

export default function OffersValidView() {

    const [offers, setOffers] = useState([])
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


    return (
        <div className='container'>
            <h2 className="text-center">Offres de Stage</h2>
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

