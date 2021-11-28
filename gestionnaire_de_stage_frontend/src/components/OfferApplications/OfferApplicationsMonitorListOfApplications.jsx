import React, {useEffect, useState} from 'react';
import {getAllApplicants} from '../../services/offerAppService';
import {useAuth} from "../../hooks/use-auth";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {downloadFile, toPdfBlob, uniqBy} from "../../utility";
import {BiTimeFive, FiDownload, GiCheckMark, GiCrossMark, MdDateRange, RiFolderReceivedFill} from "react-icons/all";
import OfferView from "../Offers/OfferView";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {Table, TableHeader, TableRow} from "../SharedComponents/Table/Table";

export default function OfferApplicationsMonitorListOfApplications() {
    let auth = useAuth();
    const [offerApplications, setOfferApplications] = useState([]);
    const [offers, setOffers] = useState([]);
    useEffect(() => {
        getAllApplicants(auth.user.email).then(offerApp => {
            setOfferApplications(offerApp);
            let o = [];
            offerApp.forEach(v1 => o.push(v1.offer))

            setOffers(uniqBy(o, v => v.id))
        })
    }, [auth.user.email]);

    if (offerApplications.length === 0) {
        return <MessageNothingToShow message="Au moment, aucun étudiant n'a postulé à cette offre"/>
    }

    const timeFormatMessage = (date) => {
        let dateTimeFormat = new Date(date);
        let day = dateTimeFormat.getDate();
        let month = dateTimeFormat.getMonth() + 1;
        let year = dateTimeFormat.getFullYear();
        let hours = dateTimeFormat.getHours();
        let minutes = dateTimeFormat.getMinutes();
        let seconds = dateTimeFormat.getSeconds();
        return `L'entrevue est le ${day}/${month}/${year} à ${hours}h${minutes}m${seconds}s`;
    };

    const setStatus = (offerApp) => {
        switch (offerApp.status) {
            case "CV_ENVOYE":
                return <RiFolderReceivedFill color={"gold"} title={"Cv envoyé"} size={27}/>
            case "STAGE_REFUSE":
                return <GiCrossMark color={"red"} title={"Stage refusé"} size={27}/>
            // ImCross ou FaTimes ou FaRegTimesCircle ou FaTimesCircle test les 2 icones durant le review
            case "STAGE_TROUVE":
                return <GiCheckMark color={"green"} title={"Stage refusé"} size={27}/>
            case "EN_ATTENTE_ENTREVUE":
                return <MdDateRange color={"black"} title={timeFormatMessage(offerApp.interviewDate)} size={27}/>
            case "EN_ATTENTE_REPONSE":
                return <BiTimeFive color={"black"} title={"En attente de votre réponse"} size={27}/>
            default:
                return "Aucun status"
        }
    }

    const getTableStudents = (offerApplications, offer) => {
        let id = offer.id;
        return <div className="card-footer">
            <div className="accordion accordion-flush" id={"accordionFlushExample" + id}>
                <div className="accordion-item">
                    <h2 className="accordion-header" id={"flush-headingOne" + id}>
                        <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                data-bs-target={"#flush-collapseOne" + id} aria-expanded="false"
                                aria-controls="flush-collapseOne">
                            Applications
                        </button>
                    </h2>
                    <div id={"flush-collapseOne" + id} className="accordion-collapse collapse"
                         aria-labelledby="flush-headingOne" data-bs-parent={"#accordionFlushExample" + id}>
                        <Table>
                            <TableHeader>
                                <th>Matricule</th>
                                <th>Nom complet</th>
                                <th>Email</th>
                                <th>Téléphone</th>
                                <th>Statut</th>
                                <th>Curriculum</th>
                            </TableHeader>
                            {offerApplications.filter(offerApp => offerApp.offer.id === offer.id).map(offerApp => {
                                    let curriculum = offerApp.curriculum;
                                    let student = curriculum.student;
                                    let lastName = student.lastName;
                                    let firstName = student.firstName;
                                    return <TableRow key={offerApp.id}>
                                        <td>{student.matricule}</td>
                                        <td>{`${lastName}, ${firstName}`}</td>
                                        <td>{student.email}</td>
                                        <td>{student.phone}</td>
                                        <td>{setStatus(offerApp)}</td>
                                        <td>
                                            <button className="link-button"
                                                    onClick={() => downloadFile(toPdfBlob(curriculum.data),
                                                        `${firstName}_${lastName}_${curriculum.id}.pdf`)}>
                                                <FiDownload color={"black"} title={"Téléchargez le cv"} size={27}/>
                                            </button>
                                        </td>
                                    </TableRow>
                                }
                            )}
                        </Table>
                    </div>
                </div>
            </div>
        </div>
    }
    return <>
        <ContainerBox>
            {offers.map((offer, index) =>
                <OfferView key={index} offer={offer} footers={getTableStudents(offerApplications, offer)}/>)}
        </ContainerBox>
    </>;
}
