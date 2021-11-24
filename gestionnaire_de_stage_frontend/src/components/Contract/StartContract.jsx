import React, {useEffect, useState} from 'react';
import {useAuth} from "../../services/use-auth";
import {getAllOfferAppReadyToSign, startSignerFetch} from "../../services/contrat-service";
import {Table, TableHeader, TableRow} from "../SharedComponents/Table/Table";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";

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
            <ContainerBox>
                <MessageNothingToShow message="Aucun contrat à lancer pour le moment..."/>
            </ContainerBox>)
    return (
        <Table>
            <TableHeader>
                <th>#</th>
                <th>Offre</th>
                <th>Commencer le processus de signature</th>
            </TableHeader>
            {offerApplications.map(offerApplication => <TableRow key={offerApplication.id}>
                <th>{offerApplication.id}</th>
                <td>{offerApplication.offer.title}</td>
                <td>
                    <div className="btn-group">
                        <button className="btn btn-outline-success"
                                onClick={() => startSigner(offerApplication.id, auth.user.id)}>Lancer
                        </button>
                    </div>
                </td>
            </TableRow>)}
        </Table>
    )
}
