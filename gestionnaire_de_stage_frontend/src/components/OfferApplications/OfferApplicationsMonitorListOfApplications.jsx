import React, {useEffect, useState} from 'react';
import {getAllApplicants} from '../../services/offerAppService';
import {useAuth} from "../../hooks/use-auth";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {downloadFile, toPdfBlob, uniqBy} from "../../utility";
import {FiDownload} from "react-icons/all";
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

    const getTableStudents = (index, offerApplications, offer) => {
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
                                <th>Statut de l'application</th>
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
                                        <td>{offerApp.status}</td>
                                        <td>
                                            <button className="link-button"
                                                    onClick={() => downloadFile(toPdfBlob(curriculum.data), `${firstName}_${lastName}_${curriculum.id}.pdf`)}>
                                                <FiDownload/>
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
                <OfferView key={index} offer={offer} footers={getTableStudents(index, offerApplications, offer)}/>)}
        </ContainerBox>
    </>;
}
