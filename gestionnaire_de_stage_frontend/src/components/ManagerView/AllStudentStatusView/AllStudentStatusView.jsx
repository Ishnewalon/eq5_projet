import React, {useEffect, useState} from "react";
import {getAllStudents} from "../../../services/user-service";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";
import {useAuth} from "../../../services/use-auth";
import {getStudentApplicationsOffer} from "../../../services/offerAppService";

export default function AllStudentStatusView() {

    const [studentList, setStudentList] = useState([])
    const [offerList, setOfferList] = useState([])
    const auth = useAuth();

    useEffect(() => {
        getAllStudents(auth.user.id)
            .then(studentList => {
                setStudentList(studentList)
                studentList.forEach(student => {
                    getStudentApplicationsOffer(student.id)
                        .then(offerList => {
                            setOfferList(prev => [...prev, offerList || []])
                        })
                        .catch(e => {
                            setOfferList([])
                            console.error(e);
                        })
                })
            })
            .catch(e => {
                setStudentList([])
                console.error(e);
            })
    }, [auth.user.id]);

    return (
        <div>
            <h2 className="text-center">Status de tous les étudiants</h2>
            <Table className={"w-75 mx-auto"}>
                <TableHeader>
                    <th>#</th>
                    <th>Étudiant</th>
                    <th>Nombre d'application</th>
                    <th>Voir les applications</th>
                </TableHeader>
                {studentList.map((student, index) =>
                    <TableRow key={index}>
                        <td>{student.id}</td>
                        <td>{student.firstName} {student.lastName}</td>
                        <td>{offerList.length > 0 ? offerList[index].length : 0}</td>
                        <td>
                            <button className="btn btn-primary">Voir</button>
                        </td>
                    </TableRow>
                )}
            </Table>
        </div>
    )
}