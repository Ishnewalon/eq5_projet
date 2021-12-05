import React, {useEffect, useState} from 'react';
import {useAuth} from "../../hooks/use-auth";
import {getAllOfferAppReadyToSign, startSignerFetch} from "../../services/contrat-service";
import {Table, TableHeader, TableRow} from "../SharedComponents/Table/Table";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {BsFillPenFill} from "react-icons/all";

export default function StartContract() {
    const [offerApplications, setOfferApplications] = useState([])
    let auth = useAuth();
    useEffect(() => {
        if (!auth.user) {
            setOfferApplications([])
            return
        }
        getAllOfferAppReadyToSign(auth.user.id).then(
            data => {
                setOfferApplications(data)
            })
    }, [auth.user])


    const startSigner = (idOfferApp, idManager) => {
        startSignerFetch(idOfferApp, idManager).then((ok) => {
            if (ok)
                setOfferApplications((prevOffApp) => {
                    return prevOffApp.filter(offApp => offApp.id !== idOfferApp)
                })
        })
    };

    if (offerApplications.length === 0)
        return (
            <MessageNothingToShow message="Aucun contrat à lancer pour le moment..."/>
        )
    return (
        <Table>
            <TableHeader>
                <th>#</th>
                <th>Nom de l'étudiant</th>
                <th>Offre</th>
                <th>Date début de stage</th>
                <th><BsFillPenFill color={"blue"} size={25} title={"Commencer le processus de signature"}/></th>
            </TableHeader>
            {offerApplications.map(offerApplication => <TableRow key={offerApplication.id}>
                <td>{offerApplication.curriculum.student.id}</td>
                <td>{offerApplication.curriculum.student.firstName} {offerApplication.curriculum.student.lastName}</td>
                <td>{offerApplication.offer.title}</td>
                <td>{offerApplication.offer.dateDebut}</td>
                <td>
                    <div className="btn-group">
                        <button className="btn btn-outline-primary"
                                onClick={() => startSigner(offerApplication.id, auth.user.id)}>Lancer
                        </button>
                    </div>
                </td>
            </TableRow>)}
        </Table>
    )
}
