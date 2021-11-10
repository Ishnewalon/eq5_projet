import React, {useEffect, useState} from "react";
import {getStudentsStatus} from "../../../services/user-service";
import {Table} from "../../SharedComponents/Table/Table";
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

    return (
        <div>
            <h2 className="text-center">Status des étudiants qui vous sont attribués</h2>
            <Table className={"w-75 mx-auto"}>
                <thead>
                    <tr>
                        <th scope='col'>#</th>
                        <th scope='col'>Étudiant</th>
                        <th scope='col'>Offre</th>
                        <th scope='col'>Status</th>
                    </tr>
                </thead>
                <tbody>
                {offerAppList.map((offerApp, index) =>
                    <tr key={index}>
                        <td>{offerApp.curriculum.student.id}</td>
                        <td>{offerApp.curriculum.student.firstName} {offerApp.curriculum.student.lastName}</td>
                        <td>{offerApp.offer.title}</td>
                        <td>{offerApp.status}</td>
                    </tr>
                )}
                </tbody>
            </Table>
        </div>
    )

}