import React, {useEffect, useState} from "react";
import {getStudentsStatus} from "../../../services/user-service";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";
import {useAuth} from "../../../hooks/use-auth";
import MessageNothingToShow from "../../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {AiOutlineCheckCircle, BiTimeFive, FaRegTimesCircle, MdDateRange, RiFolderReceivedFill} from "react-icons/all";

export default function StudentStatusView() {

    const [offerAppList, setOfferAppList] = useState([])
    const auth = useAuth();

    useEffect(() => {
        getStudentsStatus(auth.user.id)
            .then(offerAppList => {
                setOfferAppList(offerAppList)
            })
            .catch(e => {
                setOfferAppList([])
                console.error(e);
            })
    }, [auth.user.id])

    const setStatus = (offerApp) => {
        switch (offerApp.status) {
            case "CV_ENVOYE":
                return <RiFolderReceivedFill color={"gold"} title={"L'étudiant a envoyé son cv"} size={27}/>
            case "STAGE_REFUSE":
                return <FaRegTimesCircle color={"red"} title={"L'étudiant a été refusé"} size={27}/>
            case "STAGE_TROUVE":
                return <AiOutlineCheckCircle color={"green"} title={"L'étudiant a été accepté"} size={27}/>
            case "EN_ATTENTE_ENTREVUE":
                return <MdDateRange color={"black"} title={timeFormatMessage(offerApp.interviewDate)} size={27}/>
            case "EN_ATTENTE_REPONSE":
                return <BiTimeFive color={"black"} title={"L'étudiant est en attente de réponse"} size={27}/>
            default:
                return "Aucun statut"
        }
    }

    if (offerAppList.length === 0) {
        return <MessageNothingToShow message="Aucune candidature..."/>
    }
    return (
        <div>
            <Table className={"w-75 mx-auto"}>
                <TableHeader>
                    <th>#</th>
                    <th>Étudiant</th>
                    <th>Matricule</th>
                    <th>Offre</th>
                    <th>Status</th>
                </TableHeader>
                {offerAppList.map((offerApp, index) =>
                    <TableRow key={index}>
                        <td>{offerApp.curriculum.student.id}</td>
                        <td>{offerApp.curriculum.student.firstName} {offerApp.curriculum.student.lastName}</td>
                        <td>{offerApp.curriculum.student.matricule}</td>
                        <td>{offerApp.offer.title}</td>
                        <td>{setStatus(offerApp)}</td>
                    </TableRow>
                )}
            </Table>
        </div>
    )

}

export function timeFormatMessage(date) {
    let dateTimeFormat = new Date(date);
    let day = dateTimeFormat.getDate();
    let month = dateTimeFormat.getMonth() + 1;
    let year = dateTimeFormat.getFullYear();
    let hours = dateTimeFormat.getHours();
    let minutes = dateTimeFormat.getMinutes();
    let seconds = dateTimeFormat.getSeconds();
    return `L'entrevue est le ${day}/${month}/${year} à ${hours}h${minutes}m${seconds}s`;
}
