import React, {useEffect, useState} from "react";
import {getAllStudentsNotYetEvaluated} from "../../../services/user-service";
import MessageNothingToShow from "../../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";
import {BtnBack} from "../../SharedComponents/BtnBack";

export default function StudentsNotYetEvaluated() {

    const [studentMonitorOfferDtoList, setStudentMonitorOfferDtoList] = useState([])

    useEffect(() => {
        getAllStudentsNotYetEvaluated()
            .then(dtoList => {
                setStudentMonitorOfferDtoList(dtoList)
            })
            .catch(e => {
                setStudentMonitorOfferDtoList([])
                console.error(e);
            })
    }, []);

    if (studentMonitorOfferDtoList.length === 0)
        return <MessageNothingToShow message="Tous les étudiants ont été évalués"/>

    return (
        <>
            <Table className={"w-75 mx-auto"}>
                <TableHeader>
                    <th>#</th>
                    <th>Étudiant</th>
                    <th>Matricule</th>
                    <th>Superviseur</th>
                    <th>Date de fin de stage</th>
                </TableHeader>
                {studentMonitorOfferDtoList.map((dto, index) =>
                    <TableRow key={index}>
                        <th>{dto.student.id}</th>
                        <td>{dto.student.firstName} {dto.student.lastName}</td>
                        <td>{dto.student.matricule}</td>
                        <td>{dto.student.supervisor.firstName} {dto.student.supervisor.lastName}</td>
                        <td>{dto.offer.dateFin}</td>
                    </TableRow>
                )}
            </Table>
            <BtnBack/>
        </>
    )
}
