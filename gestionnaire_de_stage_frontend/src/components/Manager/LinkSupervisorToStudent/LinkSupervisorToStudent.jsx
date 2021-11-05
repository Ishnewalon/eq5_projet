import React, {useEffect, useState} from "react";
import ListStudentValidCVView from "./ListStudentValidCVView/ListStudentValidCVView";
import {getCurriculumWithValidCV} from "../../../services/curriculum-service";
import {assignStudentToSupervisor, getSupervisors} from "../../../services/user-service";
import {toast} from "../../../utility";

export default function LinkSupervisorToStudent() {// TODO: field is linked to supervisor or something

    const [cvList, setCvList] = useState([])
    const [supervisorList, setSupervisorList] = useState([])
    const [supervisorID, setSupervisorId] = useState(null)

    useEffect(() => {
        getCurriculumWithValidCV()
            .then(cvList => {
                setCvList(cvList)
            })
            .catch(e => {
                setCvList([])
                console.error(e);
            });
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
        <div className="container">
            <h2 className="text-center">Attribuer des superviseurs aux étudiants</h2>
            {cvList.map((cv, index) =>
                <div key={index}>
                    <ul>
                        <li>
                            <ListStudentValidCVView student={cv.student}/>
                        </li>
                    </ul>

                    <div className="text-center input-group">
                        <select className="form-control" onChange={() => setSupervisorId('supervisorID')}>
                            {supervisorList.map((supervisor, indexSupervisor) =>
                                <option key={indexSupervisor} value={supervisor.id}>
                                    {supervisor.lastName}, {supervisor.firstName}
                                </option>
                            )}
                        </select>
                    </div>

                    <div className="form-group text-center">
                        <label/>
                        <div>
                            <button className="btn btn-success" onClick={assign(cv.student.id)}>Accepter</button>
                        </div>
                    </div>
                </div>)}
        </div>
    )

}
