import React, {useEffect, useState} from "react";
import {getStudentsWithInvalidCv} from "../../../services/user-service";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";


export default function StudentWithInvalidCv() {

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


    return (
        <div className='container'>
            {studentList.length > 0 ?
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
                            </TableRow>)}
                    </Table>
                </>
                :
                <h3 className="text-center">Aucun étudiant avec un Cv invalide</h3>
            }
        </div>
    )
}
