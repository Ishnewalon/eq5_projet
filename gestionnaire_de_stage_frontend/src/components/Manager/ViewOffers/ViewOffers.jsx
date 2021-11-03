import React, {useEffect, useState} from "react";
import './ViewOffers.css'
import {getAllOffersByDepartment} from '../../../services/offer-service'
import PreviewOffer from '../../PreviewOffer/PreviewOffer';
import AuthService from '../../../services/auth-service';

export default function ViewOffers() {

    const [offers, setOffers] = useState([])
    useEffect(() => {
        getAllOffersByDepartment(AuthService.user.department ? AuthService.user.department : 'informatique')//TODO: make view offer adaptable (dep or all)
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
                {offers.map((offer, index) => <li key={index}><PreviewOffer offer={offer}/></li>)}
            </ul>
        </div>
    )
}

