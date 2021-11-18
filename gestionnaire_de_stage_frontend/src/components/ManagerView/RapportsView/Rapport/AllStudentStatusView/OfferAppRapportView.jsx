import {Link, useLocation} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {getStudentApplicationsOffer} from "../../../../../services/offerAppService";
import {Table, TableHeader, TableRow} from "../../../../SharedComponents/Table/Table";
import {Title} from "../../../../SharedComponents/Title/Title";

export default function OfferAppRapportView() {

    const location = useLocation();
    const [offerApplications, setOfferApplications] = useState([])
    const student = location.state.student;

    useEffect(() => {
        getStudentApplicationsOffer(student.id).then(
            data => {
                setOfferApplications(data)
            })
    }, [student.id])

    return (<>
            <Title>
                Le status des applications de: {student.firstName} {student.lastName}
            </Title>
            <Table>
                <TableHeader>
                    <th>#</th>
                    <th>Offre</th>
                    <th>Status</th>
                </TableHeader>
                {offerApplications.map(offerApplication => (
                    <TableRow key={offerApplication.id}>
                        <td>{offerApplication.id}</td>
                        <td>{offerApplication.offer.title}</td>
                        <td>{offerApplication.status}</td>
                    </TableRow>
                ))}
            </Table>
            <Link to="/dashboard/rapports/6" className="btn btn-primary">Retour</Link>
        </>
    )
}
