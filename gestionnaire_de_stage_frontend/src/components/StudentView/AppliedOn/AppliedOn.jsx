import React, {useEffect, useState} from "react";
import {getAllOffersByStudentAppliedOn} from "../../../services/offerAppService";
import {useAuth} from "../../../services/use-auth";
import SetInterviewDate from "../SetInterviewDate/SetInterviewDate";

export default function AppliedOn() {
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

    return (
        <div className='container'>
            <h2 className="text-center">Ajouter vos dates d'entrevue</h2>
            <ul>
                {offers.map((offer, index) => <li key={index}><SetInterviewDate offerApp={offer} studentID={auth.user.id}/></li>)}
            </ul>
        </div>
    )
}