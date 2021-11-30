import React, {useEffect, useState} from "react";
import {
    deleteCurriculumById,
    getAllCurriculumsByStudentWithPrincipal,
    setPrincipalCurriculum
} from "../../../../vue_frontend/src/services/curriculum-service";
import {useAuth} from "../../hooks/use-auth";
import {Table, TableHeader, TableRow} from "../SharedComponents/Table/Table";
import {AiOutlineCloseCircle, BsTrash, GoStar, MdOutlinePendingActions} from "react-icons/all";
import {downloadFile, toPdfBlob} from "../../utility";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import Swal from "sweetalert2";

export default function CurriculumsStudent() {
    let auth = useAuth();
    const [curriculumsWithPrincipal, setCurriculumsWithPrincipal] = useState({});

    useEffect(() => {
        getAllCurriculumsByStudentWithPrincipal(auth.user.id)
            .then(curriculums => {
                curriculums.curriculumList.sort((a, b) => {
                    if (b.isValid === null || (a.isValid === false && b.isValid === true))
                        return 1;
                    if (a.isValid === null || (a.isValid === true && b.isValid === false))
                        return -1;
                    return 0
                });
                setCurriculumsWithPrincipal(curriculums);
            })
            .catch(e => {
                setCurriculumsWithPrincipal({})
                console.error(e);
            });
    }, [auth.user.id]);


    const setPrincipal = cv => e => {
        e.preventDefault();

        setPrincipalCurriculum(auth.user.id, cv.id).then(
            (success) => {
                if (success)
                    setCurriculumsWithPrincipal(prev => ({...prev, "principal": cv}))
            }
        );
    }

    const isPrincipal = (cv) => {
        return curriculumsWithPrincipal.principal &&
            cv.id === curriculumsWithPrincipal.principal.id;
    }


    const getIcon = cv => {
        if (isPrincipal(cv)) {
            return <GoStar color="orange" title="C.V. par défaut" size="20"/>
        } else {
            if (cv.isValid)
                return <button className="link-button" onClick={setPrincipal(cv)}>
                    set principal
                </button>
            else if (cv.isValid === null)
                return "En attente de validation"
            else
                return <span>Invalide</span>
        }
    };

    if (!curriculumsWithPrincipal.curriculumList || curriculumsWithPrincipal.curriculumList.length === 0)
        return <MessageNothingToShow message="Aucun C.V. à afficher, pensez à téléversez le votre"/>

    const deleteCurriculum = cv => e => {
        e.preventDefault();
        Swal.fire({
            title: 'Êtes-vous sûr?',
            text: "Vous ne pourrez pas revenir en arrière!",
            icon: 'warning',
            iconColor: '#ffc107',
            showDenyButton: true,
            confirmButtonColor: '#4f657d',
            confirmButtonText: 'Oui, supprimer!',
            denyButtonText: 'Annuler'
        }).then((result) => {
            if (result.value) {
                deleteCurriculumById(cv.id)
                    .then((succes) => {
                        if (succes)
                            setCurriculumsWithPrincipal(prev => ({
                                ...prev,
                                "curriculumList": prev.curriculumList.filter(c => c.id !== cv.id)
                            }))
                    })
            }
        })
    };

    return (
        <>
            <Table>
                <TableHeader>
                    <th>Principal</th>
                    <th>Nom</th>
                    <th>Supprimer</th>
                </TableHeader>

                {curriculumsWithPrincipal.curriculumList.map((cv, index) =>
                    <TableRow key={index}>
                        <td>{getIcon(cv)}</td>
                        <td className={cv.isValid === false ? "text-danger" : ""}>
                            <button className="link-button" onClick={() => downloadFile(toPdfBlob(cv.data), cv.name)}>
                                {cv.name}
                            </button>
                        </td>
                        <td>
                            <button className="link-button" onClick={deleteCurriculum(cv)}>
                                <BsTrash color="red" title="Supprimer ce curriculum" size="20"/>
                            </button>
                        </td>
                    </TableRow>)}
            </Table>
        </>
    )
}
