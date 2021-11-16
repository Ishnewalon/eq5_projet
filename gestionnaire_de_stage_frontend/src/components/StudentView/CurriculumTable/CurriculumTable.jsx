import React, {useEffect, useState} from "react";
import {
    getAllCurriculumsByStudentWithPrincipal,
    setPrincipalCurriculum
} from "../../../services/curriculum-service";
import {useAuth} from "../../../services/use-auth";
import {Table, TableHeader, TableRow} from "../../SharedComponents/Table/Table";

export default function CurriculumTable() {
    let auth = useAuth();
    const [curriculumsWithPrincipal, setCurriculumsWithPrincipal] = useState({});

    useEffect(() => {
        getAllCurriculumsByStudentWithPrincipal(auth.user.id)
            .then(curriculums => {
                setCurriculumsWithPrincipal(curriculums)
            })
            .catch(e => {
                setCurriculumsWithPrincipal({})
                console.error(e);
            });
    }, [auth.user.id])

    const getCurriculumList = () => {
        return curriculumsWithPrincipal.curriculumList ? curriculumsWithPrincipal.curriculumList : [];
    }

    const setPrincipal = cv => e => {
        e.preventDefault();

        setPrincipalCurriculum(auth.user.id, cv.id).then(
            () => setCurriculumsWithPrincipal(prev => {
                return {
                    ...prev,
                    ["principal"]: cv
                }
            })
        );
    }

    const isPrincipal = (cv) => {
        return cv.id === curriculumsWithPrincipal.principal.id;
    }

    const getStyle = (cv) => {
        if (isPrincipal(cv)){
            return "btn-secondary"
        }else if (cv.isValid){
            return "btn-success"
        }else {
            return "btn-danger"
        }
    }

    const getText = (cv) => {
        if (isPrincipal(cv)){
            return "C.V. par defaut";
        }else if (cv.isValid){
            return "Définir comme principal";
        }else {
            return "C.V. est Invalide!";
        }
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
                                <button className={"btn " + (getStyle(cv))}
                                        disabled={!cv.isValid || isPrincipal(cv)}
                                        onClick={setPrincipal(cv)}>{getText(cv)}
                                </button>
                            </div>
                        </td>
                    </TableRow>)}
            </Table>
        </div>
    )
}