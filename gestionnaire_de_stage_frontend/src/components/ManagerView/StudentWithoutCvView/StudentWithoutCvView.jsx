import React, {useEffect, useState} from "react";
import {getStudentsWithoutCv} from "../../../services/user-service";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";


export default function StudentWithoutCvView() {

    const [studentList, setStudentList] = useState([])

    useEffect(() => {
        getStudentsWithoutCv()
            .then(studentList => {
                setStudentList(studentList)
            })
            .catch(e => {
                setStudentList([])
                console.error(e);
            })
    }, [])


    return (
        <div className='container'>
            <h2 className="text-center mb-4">Liste des étudiants sans Cv</h2>
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
        </div>
    )
}
