import React, {useEffect, useState} from 'react';
import {getAllOffersByDepartment} from '../../../services/offer-service';
import OfferApplication from "../OfferApplication/OfferApplication";
import {useAuth} from "../../../services/use-auth";
import {getCurrentAndFutureSession} from "../../../services/session-service";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";

export default function ViewOffersAndApply() {//TODO: remove Offer after applied
    let auth = useAuth();
    const [offers, setOffers] = useState([])
    const [sessions, setSessions] = useState([]);
    const [visibles, setVisibles] = useState([]);


    const setMyVisible = (idSession) =>
        // eslint-disable-next-line
        setVisibles(offers.filter(offer => offer.session.id == idSession))



    useEffect(() => {
        getAllOffersByDepartment(auth.user.department)
            .then(offers => {
                setOffers(offers)
            })
            .catch(e => {
                setOffers([])
                console.error(e);
            });
    }, [auth.user.department]);

    useEffect(() => {
        getCurrentAndFutureSession()
            .then(sessions => {
                setSessions(sessions)
                // eslint-disable-next-line
                setVisibles(offers.filter(offer => offer.session.id == sessions[0].id))
            })
            .catch(e => {
                setSessions([]);
                console.error(e);
            })
    }, [offers])



    return (
        <div className='container'>
            <h2 className="text-center">Offres de Stage</h2>
            <FormGroup>
                <FormField>
                    <label/>
                    <select onChange={(e) => setMyVisible(e.target.value)}>
                        {sessions.map(session =>
                            <option key={session.id}
                                    value={session.id}>{session.typeSession + session.year}</option>)}
                    </select>
                </FormField>
            </FormGroup>
            <ul>
                {visibles.map((offer, index) => <li key={index}><OfferApplication offer={offer}/></li>)}
            </ul>
        </div>
    );
}
