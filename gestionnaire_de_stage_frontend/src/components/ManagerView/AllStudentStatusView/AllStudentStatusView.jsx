import React, {useEffect, useState} from "react";
import {getAllStudentsStatus} from "../../../services/user-service";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";
import {useAuth} from "../../../services/use-auth";

export default function AllStudentStatusView() {

    const [offerAppList, setOfferAppList] = useState([])
    const auth = useAuth();

    useEffect(() => {
        getAllStudentsStatus(auth.user.id)
            .then(offerAppList => {
                setOfferAppList(offerAppList)
            })
            .catch(e => {
                setOfferAppList([])
                console.error(e);
            })
    }, [auth.user.id])

    return (
        <div>
            <h2 className="text-center">Status de tous les étudiants</h2>
            <Table className={"w-75 mx-auto"}>
                <TableHeader>
                    <th>#</th>
                    <th>Étudiant</th>
                    <th>Offre</th>
                    <th>Status</th>
                </TableHeader>

                {offerAppList.map((offerApp, index) =>
                    <TableRow key={index}>
                        <td>{offerApp.curriculum.student.id}</td>
                        <td>{offerApp.curriculum.student.firstName} {offerApp.curriculum.student.lastName}</td>
                        <td>{offerApp.offer.title}</td>
                        <td>{offerApp.status}</td>
                    </TableRow>
                )}
            </Table>
        </div>
    )

}