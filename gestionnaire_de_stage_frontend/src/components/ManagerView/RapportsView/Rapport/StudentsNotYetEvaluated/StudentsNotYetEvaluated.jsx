import React, {useEffect, useState} from "react";
import {getAllStudentsNotYetEvaluated} from "../../../../../services/user-service";
import MessageNothingToShow from "../../../../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {Table, TableHeader, TableRow} from "../../../../SharedComponents/Table/Table";

export default function StudentsNotYetEvaluated() {

    const [studentList, setStudentList] = useState([])
    useEffect(() => {
        getAllStudentsNotYetEvaluated()
            .then(studentList => {
                setStudentList(studentList)
            })
            .catch(e => {
                setStudentList([])
                console.error(e);
            })
    }, []);

    if (studentList.length === 0)
        return <MessageNothingToShow message="Tous les étudiants ont été évalués"/>

    return (
        <>
            <Table className={"w-75 mx-auto"}>
                <TableHeader>
                    <th>#</th>
                    <th>Étudiant</th>
                    <th>Matricule</th>
                    <th>Superviseur</th>
                </TableHeader>
                {studentList.map((student, index) =>
                    <TableRow key={index}>
                        <th>{student.id}</th>
                        <td>{student.firstName} {student.lastName}</td>
                        <td>{student.matricule}</td>
                        <td>{student.supervisor.firstName} {student.supervisor.lastName}</td>
                    </TableRow>
                )}
            </Table>
        </>
    )
}
