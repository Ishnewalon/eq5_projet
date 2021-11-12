import React, {useEffect, useState} from "react";
import {
    downloadCV,
    getAllCurriculumsByStudentWithPrincipal,
    setPrincipalCurriculum
} from "../../../services/curriculum-service";
import {useAuth} from "../../../services/use-auth";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";
import {applyToOffer} from "../../../services/offerAppService";
import OfferApp from "../../../models/OfferApp";

export default function CurriculumTable() {
    let auth = useAuth();
    const [curriculumsWithPrincipal, setCurriculumsWithPrincipal] = useState([]);

    useEffect(() => {
        getAllCurriculumsByStudentWithPrincipal(auth.user.id)
            .then(curriculums => {
                setCurriculumsWithPrincipal(curriculums)
            })
            .catch(e => {
                setCurriculumsWithPrincipal([])
                console.error(e);
            });
    }, [auth.user.id])

    const getCurriculumList = () => {
        return curriculumsWithPrincipal.curriculumList ? curriculumsWithPrincipal.curriculumList : [];
    }

    const setPrincipal = curriculumId => e => {
        e.preventDefault();

        setPrincipalCurriculum(auth.user.id, curriculumId).then();
    }

    return (
        <div className='container'>
            <h2 className="text-center">Vos Curriculums</h2>
            <Table>
                <TableHeader>
                    <th>Nom</th>
                    <th>Status</th>
                    <th>Principal</th>
                </TableHeader>

                {getCurriculumList().map((cv, index) =>
                    <TableRow key={index}>
                        <td>{cv.name}</td>
                        <td>{cv.isValid ? "Valide" : "Invalide"}</td>
                        <td>
                            <div className="btn-group">
                                <button className={"btn " + cv.isValid ? "btn-success" : "btn-danger"}
                                        disabled={!cv.isValid}
                                        onClick={setPrincipal(cv.id)}>Définir comme principal
                                </button>
                            </div>
                        </td>
                    </TableRow>)}
            </Table>
        </div>
    )
}