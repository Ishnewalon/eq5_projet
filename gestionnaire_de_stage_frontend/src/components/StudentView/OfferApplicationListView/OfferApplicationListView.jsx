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
    if (offers.length === 0) {
        return <div className={'bg-secondary d-flex py-3 align-items-center justify-content-center text-white'}>
            Au moment, aucune offre n'a été trouvée...
        </div>
    }
    return <>
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
                    Confirmer votre date d'entrevue
                </button>
            </td>
        </TableRow>
    )
}
