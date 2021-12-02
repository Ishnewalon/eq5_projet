import {useLocation} from "react-router-dom";
import React from "react";
import {Table, TableHeader, TableRow} from "../SharedComponents/Table/Table";
import {Title} from "../SharedComponents/Title";
import {BtnBack} from "../SharedComponents/BtnBack";
import {AiOutlineCheckCircle, BiTimeFive, FaRegTimesCircle, MdDateRange, RiFolderReceivedFill} from "react-icons/all";
import {timeFormatMessage} from "../Admin/StudentManagement/StudentStatusView";

export default function OfferApplicationsList() {

    const location = useLocation();
    const offerApplications = location.state.offerApp;
    const student = location.state.student;

    const getViewFromStatus = (offerApp) => {
        switch (offerApp.status) {
            case "CV_ENVOYE":
                return <RiFolderReceivedFill color={"orange"} title={"L'étudiant a envoyé son cv"} size={27}/>
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

    return (<>
            <Title>
                Le status des applications de: {student.firstName} {student.lastName}
            </Title>
            <Table className={"w-75 mx-auto"}>
                <TableHeader>
                    <th>#</th>
                    <th>Offre</th>
                    <th>Status</th>
                </TableHeader>
                {offerApplications.map(offerApplication => (
                    <TableRow key={offerApplication.id}>
                        <td>{offerApplication.id}</td>
                        <td>{offerApplication.offer.title}</td>
                        <td>{getViewFromStatus(offerApplication)}</td>
                    </TableRow>
                ))}
            </Table>
            <BtnBack/>
        </>
    )
}
