import React, {useEffect, useState} from "react";
import {getStudentsStatus} from "../../../services/user-service";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";
import {useAuth} from "../../../services/use-auth";

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

    if (offerAppList.length === 0) {
        return <div className={'bg-secondary d-flex py-3 align-items-center justify-content-center text-white'}>
            Aucune candidature...
        </div>
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
                        <td>{offerApp.status}</td>
                    </TableRow>
                )}
            </Table>
        </div>
    )

}
