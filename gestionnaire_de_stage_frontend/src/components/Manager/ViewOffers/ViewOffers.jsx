import React, {useEffect, useState} from "react";
import './ViewOffers.css'
import {getAllOffersValid} from '../../../services/offer-service'
import PreviewOffer from '../../PreviewOffer/PreviewOffer';

export default function ViewOffers() {

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
                        <PreviewOffer offer={offer}/>
                    </li>
                )}
            </ul>
        </div>
    )
}

