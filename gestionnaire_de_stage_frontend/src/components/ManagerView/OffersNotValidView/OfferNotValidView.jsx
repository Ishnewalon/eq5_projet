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

    return (
        <div className='container'>
            <ul>
                {offers.length > 0 ? (
                        offers.map((offer, index) =>
                            <li className={"mb-4"} key={index}>
                                <OfferView offer={offer}/>
                            </li>
                        )) :
                    <h3 className={"text-center mt-4"}>Aucune offre à afficher</h3>}
            </ul>
        </div>
    )
}

