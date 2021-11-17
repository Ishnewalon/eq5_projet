import React, {useEffect, useState} from "react";
import {getAllOffersByStudentAppliedOn, setInterview} from "../../../services/offerAppService";
import {useAuth} from "../../../services/use-auth";
import {swalErr} from "../../../utility";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";

export default function OfferApplicationListView() {
    let auth = useAuth();
    const [offers, setOffers] = useState([])

    useEffect(() => {
        getAllOffersByStudentAppliedOn(auth.user.id)
            .then(offers => {
                setOffers(offers);
                console.log(offers.length);
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

    return <>
        <h2 className="text-center">Ajouter vos dates d'entrevue</h2>
        <Table>
            <TableHeader>
                <th>#</th>
                <th>Titre</th>
                <th>Selection de dates</th>
                <th>Confirmer</th>
            </TableHeader>
            {offers.map((offer, index) =>
                <OfferApplicationSetInterviewDate key={index} offerApp={offer}
                                                  removeOffer={setOfferValidated}/>
            )}
        </Table>
    </>
}


function OfferApplicationSetInterviewDate({offerApp, removeOffer}) {

    const [date, setDate] = useState('')

    const min = new Date();
    const max = new Date();
    max.setMonth(min.getMonth() + 2)

    const setInterviewDate = offerAppID => e => {
        e.preventDefault();

        if (date === '') {
            swalErr.fire({title: "Vous devez selectionner une date!"}).then();
            return;
        }

        setInterview(offerAppID, date).then(
            () => removeOffer(offerAppID));
    }

    return (
        <TableRow>
            <th>{offerApp.id}</th>
            <td>{offerApp.offer.title}</td>
            <td>
                <input
                    onChange={(e) => setDate(e.target.value)}
                    id="date"
                    min={min.toISOString().slice(0, -8)}
                    max={max.toISOString().slice(0, -8)}
                    type="datetime-local"/>
            </td>
            <td>
                <button className="btn btn-primary" onClick={setInterviewDate(offerApp.id)}>
                    Setter votre date d'entrevue
                </button>
            </td>
        </TableRow>
    )
}
