import React, {useEffect, useState} from "react";
import {getStudentsWithoutCv} from "../../../services/user-service";


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
            <h2 className="text-center">Liste des étudiants sans Cv</h2>
            {studentList.map((student, index) =>
                <div key={index}>
                    <div className="shadow-lg rounded-top p-3 mt-3 border-left border-right">
                        <div className={'d-flex align-items-center flex-column'}>
                            <h3 className={'d-inline-block text-dark'}>
                            <span className={"badge rounded-pill"}>
                                 {student.firstName} {student.lastName}
                            </span>
                            </h3>
                        </div>
                    </div>
                </div>)}
        </div>
    )
}
