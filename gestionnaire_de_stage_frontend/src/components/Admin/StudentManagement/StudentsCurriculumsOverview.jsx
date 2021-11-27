import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";
import React, {useEffect, useState} from "react";
import {useAuth} from "../../../hooks/use-auth";
import {getAllStudents} from "../../../services/user-service";
import {getAllCurriculumsByStudent} from "../../../services/curriculum-service";
import {useHistory, useRouteMatch} from "react-router-dom";

export default function StudentsCurriculumsOverview() {
    const [students, setStudents] = useState([]);
    const [curriculums, setCurriculums] = useState([]);
    const {path} = useRouteMatch();
    const history = useHistory();
    const auth = useAuth()

    useEffect(() => {
        getAllStudents()
            .then(studentList => {
                setStudents(studentList)
                studentList.forEach(student => {
                    if (!student) return
                    getAllCurriculumsByStudent(student.id)
                        .then(curriculums => {
                            setCurriculums(prev => [...prev, curriculums || []])
                        })
                        .catch(e => {
                            setCurriculums([])
                            console.error(e);
                        })
                })
            })
            .catch(e => {
                setStudents([])
                console.error(e);
            })
    }, [auth, path]);

    if (students.length === 0 || !students || curriculums.length === 0 || !curriculums) {
        return <div>Loading...</div>
    }
    const getNbOfCurriculumsWithState = (curriculums, state) => {
        if (!curriculums) return 0
        return curriculums.filter(curriculum => curriculum.isValid === state).length
    }
    return <>
        <Table>
            <TableHeader>
                <th>#</th>
                <th>Étudiant</th>
                <th>Matricule</th>
                <th>Adresse électronique</th>
                <th>Validé</th>
                <th>Invalide</th>
                <th>À valider</th>
                <th>Voir</th>
            </TableHeader>
            {students.map((student, index) => {
                    let cur = curriculums.find(curriculums => curriculums.length > 0 && curriculums[0].student.id === student.id)
                    return <TableRow key={index}>
                        <th>{student.id}</th>
                        <td>{student.firstName} {student.lastName}</td>
                        <td>{student.matricule}</td>
                        <td>{student.email}</td>
                        <td>{getNbOfCurriculumsWithState(cur, true)}</td>
                        <td>{getNbOfCurriculumsWithState(cur, false)}</td>
                        <td>{getNbOfCurriculumsWithState(cur, null)}</td>
                        <td>
                            <button className="btn btn-outline-primary" onClick={() => history.push({
                                pathname: `${path}/student`,
                                state: {student: student}
                            })}>Voir
                            </button>
                        </td>
                    </TableRow>
                }
            )}
        </Table>
    </>
}
