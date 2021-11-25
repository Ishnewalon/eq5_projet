import React, {useEffect, useState} from 'react';
import {useAuth} from "../../services/use-auth";
import {getStudentApplicationsOffer, setApplicationsFinalStatus} from "../../services/offerAppService";
import {Table, TableHeader, TableRow} from "../SharedComponents/Table/Table";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {AiOutlineProfile} from "react-icons/all";

export default function OfferApplicationsStudentSetStatusFinal() {
    const [offerApplications, setOfferApplications] = useState([])
    let auth = useAuth();
    useEffect(() => {
        if (!auth.user) {
            setOfferApplications([])
            return
        }
        getStudentApplicationsOffer(auth.user.id).then(
            data => {
                setOfferApplications(data)
            })
    }, [auth.user])


    const updateStatus = (idOfferApp, isAccepted) => {
        setApplicationsFinalStatus(idOfferApp, isAccepted).then((ok) => {
            if (ok)
                setOfferApplications((prevOffApp) => {
                    return prevOffApp.filter(offApp => offApp.id !== idOfferApp)
                })
        })
    };

    const setStatus = (offerApp) => {
        switch (offerApp.status) {
            case "CV_ENVOYE":
                return "Cv envoyé"
            case "STAGE_REFUSE":
                return "Stage refusé"
            case "STAGE_TROUVE":
                return "Stage trouvé"
            case "EN_ATTENTE_ENTREVUE":
                return "En attente d'entrevue"
            case "EN_ATTENTE_REPONSE":
                return "En attente de réponse"
            default:
                return "Aucun status"
        }
    }


    const setStatusMessage = (offerApp) => {
        switch (offerApp.status) {
            case "CV_ENVOYE":
                return "N'oubliez pas de confirmer votre date d'entrevue"
            case "STAGE_REFUSE":
                return "Plus de chance la prochaine fois!"
            case "STAGE_TROUVE":
                return "Contactez votre gestionnaire de stage pour signer votre contrat"
            case "EN_ATTENTE_ENTREVUE":
                return timeFormat(offerApp.interviewDate)
            case "EN_ATTENTE_REPONSE":
                return changeStatus(offerApp)
            default:
                return "Vous n'avez pas encore de réponse"
        }
    }

    const changeStatus = (offerApp) => {
        return (<div className="btn-group">
            <button className="btn btn-outline-success"
                    onClick={() => updateStatus(offerApp.id, true)}>Trouvé
            </button>
            <button className="btn btn-outline-danger"
                    onClick={() => updateStatus(offerApp.id, false)}>Refusé
            </button>
        </div>)
    }

    const timeFormat = (date) => {
        let dateTimeFormat = new Date(date);
        let day = dateTimeFormat.getDate();
        let month = dateTimeFormat.getMonth() + 1;
        let year = dateTimeFormat.getFullYear();
        let hours = dateTimeFormat.getHours();
        let minutes = dateTimeFormat.getMinutes();
        let seconds = dateTimeFormat.getSeconds();
        return <> Votre entrevue est le
            <span className={"fw-bold"}>{` ${day}/${month}/${year}`}</span> à
            <span className={"fw-bold"}>{` ${hours}:${minutes}:${seconds}`}</span></>
    };


    if (offerApplications.length === 0)
        return <MessageNothingToShow message="Vous n'avez pas d'offre de stage en attente de réponse"/>

    return (<>
        <Table>
            <TableHeader>
                <th>#</th>
                <th>Offre</th>
                <th>Status</th>
                <th><AiOutlineProfile size={25}/></th>
            </TableHeader>
            {offerApplications.map(offerApplication => (
                <TableRow key={offerApplication.id}>
                    <th>{offerApplication.id}</th>
                    <td>{offerApplication.offer.title}</td>
                    <td>{setStatus(offerApplication)}</td>
                    <td>
                        {setStatusMessage(offerApplication)}
                    </td>
                    }
                </TableRow>
            ))}
        </Table>
    </>)
}
