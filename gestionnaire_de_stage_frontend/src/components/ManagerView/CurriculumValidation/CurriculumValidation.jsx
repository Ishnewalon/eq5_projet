import React, {useEffect, useState} from "react";
import {getCurriculumWithInvalidCV, validateCurriculum} from "../../../services/curriculum-service";
import {downloadFile} from "../../../utility";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";
import MessageNothingToShow from "../../SharedComponents/MessageNothingToShow/MessageNothingToShow";


export default function CurriculumValidation() {

    const [curriculumList, setCurriculumList] = useState([]);
    useEffect(() => {
        getCurriculumWithInvalidCV()
            .then(curriculumList => {
                setCurriculumList(curriculumList)
            })
            .catch(e => {
                setCurriculumList([])
                console.error(e);
            });
    }, [])

    const removeFromList = (id) => {
        setCurriculumList(prev => prev.filter(items => items.id !== id))
    }



    const validateCv = (id, valid) => {
        validateCurriculum(id, valid).then(
            () => removeFromList(id));
    }

    if (curriculumList.length === 0)
        return <MessageNothingToShow message="Aucun curriculum à valider pour le moment..."/>

    return (
        <Table>
            <TableHeader>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Télécharger CV</th>
                <th>Validation</th>
            </TableHeader>

            {curriculumList.map((cv, index) =>
                <TableRow key={index}>
                    <td>{cv.student.lastName}</td>
                    <td>{cv.student.firstName}</td>
                    <td>
                        <button className="btn btn-primary"
                                onClick={() => downloadFile(cv.data, `${cv.student.firstName}_${cv.student.lastName}_${cv.id}.pdf`)}>Télécharger
                            Cv
                        </button>
                    </td>
                    <td>
                        <div className="btn-group">
                            <button className="btn btn-outline-success"
                                    onClick={() => validateCv(cv.id, true)}>Valide
                            </button>
                            <button className="btn btn-outline-danger"
                                    onClick={() => validateCv(cv.id, false)}>Invalide
                            </button>
                        </div>
                    </td>
                </TableRow>)}
        </Table>
    )
}
