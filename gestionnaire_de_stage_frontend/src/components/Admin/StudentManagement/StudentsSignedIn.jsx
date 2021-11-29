import React, {useEffect, useState} from "react";
import {getAllStudents} from "../../../services/user-service";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";
import MessageNothingToShow from "../../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {BtnBack} from "../../SharedComponents/BtnBack";


export default function StudentsSignedIn() {

    const [studentList, setStudentList] = useState([])

    useEffect(() => {
        getAllStudents()
            .then(studentList => {
                setStudentList(studentList)
            })
            .catch(e => {
                setStudentList([])
                console.error(e);
            })
    }, [])

    if (studentList.length === 0) {
        return <>
            <MessageNothingToShow message="Aucun étudiant inscrit"/>
            <BtnBack/>
        </>

    }
    return (
        <>
            <Table className={"w-75 mx-auto"}>
                <TableHeader>
                    <th>#</th>
                    <th>Étudiant</th>
                    <th>Matricule</th>
                    <th>Adresse électronique</th>
                    <th>Téléphone</th>
                </TableHeader>
                {studentList.map((student, index) =>
                    <TableRow key={index}>
                        <th>{student.id}</th>
                        <td>{student.firstName} {student.lastName}</td>
                        <td>{student.matricule}</td>
                        <td>{student.email}</td>
                        <td>{student.phone}</td>
                    </TableRow>
                )}
            </Table>
            <BtnBack/>
        </>

    )
}
