import React, {useEffect, useState} from "react";
import {assignStudentToSupervisor, getSupervisors, getUnassignedStudents} from "../../../services/user-service";
import {toast} from "../../../utility";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";
import MessageNothingToShow from "../../SharedComponents/MessageNothingToShow/MessageNothingToShow";

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
    if (studentList.length === 0)
        return <MessageNothingToShow message="Aucun étudiant à associer pour le moment...">
            Bonne<span className="color-emphasis-1"> nouvelle!</span><br/>
            Aucune offre <span className="color-emphasis-1">à valider.</span>
        </MessageNothingToShow>

    return (
        <div>
            <Table className={"w-75 mx-auto"}>
                <TableHeader>
                    <th>#</th>
                    <th>Étudiant</th>
                    <th>Superviseur</th>
                    <th>Accepter</th>
                </TableHeader>
                {studentList.map((student, index) =>
                    <TableRow key={index}>
                        <th>{student.id}</th>
                        <td>{student.firstName} {student.lastName}</td>
                        <td>
                            <FormField>
                                <select onChange={() => setSupervisorId('supervisorID')}>
                                    {supervisorList.map((supervisor, indexSupervisor) =>
                                        <option key={indexSupervisor} value={supervisor.id}>
                                            {supervisor.lastName}, {supervisor.firstName}
                                        </option>
                                    )}
                                </select>
                            </FormField>
                        </td>
                        <td>
                            <button className="btn btn-outline-success" onClick={assign(student.id)}>Accepter</button>
                        </td>
                    </TableRow>
                )}
            </Table>
        </div>
    )

}


