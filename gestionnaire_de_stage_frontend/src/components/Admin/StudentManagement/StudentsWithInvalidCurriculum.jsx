import React, {useEffect, useState} from "react";
import {getStudentsWithInvalidCv} from "../../../services/user-service";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";
import MessageNothingToShow from "../../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {BtnBack} from "../BtnBack";


export default function StudentsWithInvalidCurriculum() {

    const [studentList, setStudentList] = useState([])

    useEffect(() => {
        getStudentsWithInvalidCv()
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
            <MessageNothingToShow message="Tous les étudiants ont un CV valide"/>
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
                </TableHeader>
                {studentList.map((student, index) =>
                    <TableRow key={index}>
                        <th>{student.id}</th>
                        <td>{student.firstName} {student.lastName}</td>
                        <td>{student.matricule}</td>
                        <td>{student.email}</td>
                    </TableRow>
                )}
            </Table>
            <BtnBack/>
        </>
    )
}