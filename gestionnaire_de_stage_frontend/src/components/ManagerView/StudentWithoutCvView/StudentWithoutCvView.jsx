import React, {useEffect, useState} from "react";
import {getStudentsWithoutCv} from "../../../services/user-service";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";


export default function StudentWithoutCvView() {

    const [studentList, setStudentList] = useState([])

    useEffect(() => {
        getStudentsWithoutCv()
            .then(studentList => {
                setStudentList(studentList)
                console.log(studentList)
            })
            .catch(e => {
                setStudentList([])
                console.error(e);
            })
    }, [])


    return (
        <div className='container'>
            {studentList.length > 0 ?
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
                    </TableRow>)}
                </Table>
                :
                <h3 className="text-center">Aucun étudiant sans Cv</h3>
            }
        </div>
    )
}
