import React, {useEffect, useState} from "react";
import {getCurriculumWithValidCV} from "../../../services/curriculum-service";
import {assignStudentToSupervisor, getSupervisors, getUnassignedStudents} from "../../../services/user-service";
import {toast} from "../../../utility";
import {InputGroup} from "../../SharedComponents/InputGroup/InputGroup";
import {Table} from "../../SharedComponents/Table/Table";

export default function LinkSupervisorToStudent() {// TODO: field is linked to supervisor or something

    const [studentList, setStudentList] = useState([])
    const [supervisorList, setSupervisorList] = useState([])
    const [supervisorID, setSupervisorId] = useState(null)

    useEffect(() => {
        getUnassignedStudents()
            .then(studentList => {
                setStudentList(studentList)
            })
            .catch(e => {
                setStudentList([])
                console.error(e);
            })
        getSupervisors()
            .then(supervisorList => {
                setSupervisorList(supervisorList)
                setSupervisorId(supervisorList[0].id)
            })
            .catch(e => {
                setSupervisorList([])
                console.error(e);
            });

    }, [])

    const assign = (idStudent) => e => {
        e.preventDefault();
        assignStudentToSupervisor(idStudent, supervisorID).then(
            responseMessage => {
                toast.fire({title: responseMessage.message}).then();
            }
        )
    }

    return (
        <div>
            <h2 className="text-center">Attribuer des superviseurs aux étudiants</h2>
            <Table className={"w-75 mx-auto"}>
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Étudiant</th>
                    <th scope="col">Enseignant</th>
                    <th scope="col">Accepter</th>
                </tr>
                </thead>
                <tbody>
                {studentList.map((student, index) =>

                    <tr key={index}>
                        <th scope="row">{student.id}</th>
                        <td>{student.firstName} {student.lastName}</td>
                        <td>
                            <InputGroup>
                                <select onChange={() => setSupervisorId('supervisorID')}>
                                    {supervisorList.map((supervisor, indexSupervisor) =>
                                        <option key={indexSupervisor} value={supervisor.id}>
                                            {supervisor.lastName}, {supervisor.firstName}
                                        </option>
                                    )}
                                </select>
                            </InputGroup>
                        </td>
                        <td>
                            <button className="btn btn-success" onClick={assign(student.id)}>Accepter</button>
                        </td>
                    </tr>
                )}
                </tbody>
            </Table>
        </div>
    )

}


