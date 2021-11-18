import React, {useEffect, useState} from "react";
import {getAllStudentsWithCompanyNotYetEvaluated} from "../../../../../services/user-service";
import MessageNothingToShow from "../../../../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {Table, TableHeader, TableRow} from "../../../../SharedComponents/Table/Table";

export default function StudentsWithCompanyNotYetEvaluated() {

    const [studentMonitorOfferDtoList, setStudentMonitorOfferDtoList] = useState([])

    useEffect(() => {
        getAllStudentsWithCompanyNotYetEvaluated()
            .then(dtoList => {
                console.log(dtoList);
                setStudentMonitorOfferDtoList(dtoList)
            })
            .catch(e => {
                setStudentMonitorOfferDtoList([])
                console.error(e);
            })
    }, [])

    if (studentMonitorOfferDtoList.length === 0) {
        return <MessageNothingToShow message="Tous les compagnies ont été évaluées"/>
    }

    return (
        <>
            <Table className={"w-75 mx-auto"}>
                <TableHeader>
                    <th>#</th>
                    <th>Étudiant</th>
                    <th>Matricule</th>
                    <th>Monitor</th>
                    <th>Date de fin de stage</th>
                </TableHeader>
                {studentMonitorOfferDtoList.map((dto, index) =>
                    <TableRow key={index}>
                        console.log(dto)
                        <th>{dto.student.id}</th>
                        <td>{dto.student.firstName} {dto.student.lastName}</td>
                        <td>{dto.student.matricule}</td>
                        <td>{dto.monitor.firstName} {dto.monitor.lastName}</td>
                        <td>{dto.offer.dateFin}</td>
                    </TableRow>
                )}
            </Table>
        </>
    )
}
