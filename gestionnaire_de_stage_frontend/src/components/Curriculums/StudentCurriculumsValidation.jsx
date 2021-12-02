import React, {useEffect, useState} from "react";
import {getAllCurriculumsByStudent, validateCurriculum} from "../../services/curriculum-service";
import {downloadFile, toPdfBlob} from "../../utility";
import {Table, TableHeader, TableRow} from "../SharedComponents/Table/Table";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {useLocation} from "react-router-dom";
import {AiOutlineCloseCircle, BiCheck, FiDownload} from "react-icons/all";
import {Title} from "../SharedComponents/Title";


export default function StudentCurriculumValidation() {
    const location = useLocation();
    let student = location.state.student;
    const [curriculums, setCurriculums] = useState([]);

    useEffect(() => {
        getAllCurriculumsByStudent(student.id).then(curriculums => {
            setCurriculums(curriculums.reverse(curriculum => curriculum.id));
        });
    }, [student]);


    const validateCv = (id, valid) => {
        validateCurriculum(id, valid).then(
            () => setCurriculums(curriculum =>
                curriculum.map(curriculum => {
                    if (curriculum.id === id) curriculum.isValid = valid;
                    return curriculum;
                }))
        )
    }

    if (curriculums.length === 0)
        return <MessageNothingToShow message="Aucun curriculum à valider pour le moment..."/>


    function getOptions(cv) {
        if (cv.isValid)
            return <BiCheck title="Validé" color="green" size="40"/>
        if (cv.isValid === false)
            return <AiOutlineCloseCircle color="D00" title="Invalid" size="30"/>
        return <div className="btn-group">
            <button className="btn btn-outline-success"
                    onClick={() => validateCv(cv.id, true)}>Valide
            </button>
            <button className="btn btn-outline-danger"
                    onClick={() => validateCv(cv.id, false)}>Invalide
            </button>
        </div>
    }

    return (<>
            <Title>Validation des curriculums du l'étudiant(e) {student.firstName} {student.lastName}</Title>
            <Table>
                <TableHeader>
                    <th>#</th>
                    <th>Nom du fichier</th>
                    <th>Télécharger CV</th>
                    <th>Validation</th>
                </TableHeader>

                {curriculums.map((cv, index) =>
                    <TableRow key={index}>
                        <th>{cv.id}</th>
                        <td>{cv.name}</td>
                        <td>
                            <div
                                onClick={() => downloadFile(toPdfBlob(cv.data), `${cv.student.firstName}_${cv.student.lastName}_${cv.id}.pdf`)}>
                                <FiDownload color={"black"} title={"Téléchargez le cv"} size={27}/>
                            </div>
                        </td>
                        <td>
                            {getOptions(cv)}
                        </td>
                    </TableRow>)}
            </Table>
        </>
    )
}
