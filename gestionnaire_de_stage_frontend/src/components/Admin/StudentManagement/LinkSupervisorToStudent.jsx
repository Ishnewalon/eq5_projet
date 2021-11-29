import React, {useEffect, useMemo, useState} from "react";
import {assignStudentToSupervisor, getSupervisors, getUnassignedStudents} from "../../../services/user-service";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";
import MessageNothingToShow from "../../SharedComponents/MessageNothingToShow/MessageNothingToShow";

export default function LinkSupervisorToStudent() {
    const [studentList, setStudentList] = useState([])
    const [supervisorList, setSupervisorList] = useState([])

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
            })
            .catch(e => {
                setSupervisorList([])
                console.error(e);
            });
    }, [])

    const removeFromList = (studentID) =>
        setStudentList(studentList.filter(student => student.id !== studentID))

    if (studentList.length === 0)
        return <MessageNothingToShow message="Aucun étudiant à associer pour le moment..."/>

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
                    <RowStudent key={index} student={student} list={supervisorList} removeFromList={removeFromList}/>
                )}
            </Table>
        </div>
    )

}

function RowStudent({student, list, removeFromList}) {
    const [supervisorID, setSupervisorID] = useState(null)
    let memoSupervisorID = useMemo(() => supervisorID, [supervisorID]);

    useEffect(() => {
        if (!memoSupervisorID)
            setSupervisorID(list.length > 0 ? list[0].id : null)
    }, [list, memoSupervisorID])

    const assign = (idStudent, idSupervisor) => e => {
        e.preventDefault();
        assignStudentToSupervisor(idStudent, idSupervisor)
            .then(
                () => removeFromList(idStudent))
            .catch(e => {
                console.error(e);
            })
    }

    return (
        <TableRow>
            <th>{student.id}</th>
            <td>{student.firstName} {student.lastName}</td>
            <td>
                <div className="form-group">
                    <select disabled={list.length === 0} className="form-select"
                            onChange={e => setSupervisorID(e.target.value)} defaultValue="">
                        {list.length > 0 ? list.map((supervisor, indexSupervisor) =>
                            <option key={indexSupervisor} value={supervisor.id}>
                                {supervisor.lastName}, {supervisor.firstName}
                            </option>
                        ) : <option disabled value="">Aucun superviseur à assigner</option>}
                    </select>
                </div>
            </td>
            <td>
                <button disabled={list.length === 0} className="btn btn-outline-primary"
                        onClick={assign(student.id, supervisorID)}>Accepter
                </button>
            </td>
        </TableRow>
    )
}