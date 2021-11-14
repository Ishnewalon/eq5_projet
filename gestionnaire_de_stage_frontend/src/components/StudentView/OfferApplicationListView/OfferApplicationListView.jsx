import React, {useEffect, useState} from "react";
import {getAllOffersByStudentAppliedOn} from "../../../services/offerAppService";
import {useAuth} from "../../../services/use-auth";
import OfferApplicationSetInterviewDate from "../OfferApplicationSetInterviewDate/OfferApplicationSetInterviewDate";

export default function OfferApplicationListView() {
    let auth = useAuth();
    const [offers, setOffers] = useState([])

    useEffect(() => {
        getAllOffersByStudentAppliedOn(auth.user.id)
            .then(offers => {
                    setOffers(offers);
            })
            .catch(e => {
                setOffers([])
                console.error(e);
            });
    }, [auth.user.id]);

    const setOfferValidated = (id) => {
        setOffers(prevOffers =>
            prevOffers.filter(items => items.id !== id)
        )
    }

    return (
        <div className='container'>
            <h2 className="text-center">Ajouter vos dates d'entrevue</h2>
            <ul>
                {offers.map((offer, index) => <li key={index}><OfferApplicationSetInterviewDate offerApp={offer} removeOffer={setOfferValidated}/></li>)}
            </ul>
        </div>
    )
}
