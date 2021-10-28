import './ValiderCv.css'
import React, {useEffect, useState} from "react";
import {getCurriculumWithInvalidCV, validateCV} from "../../../services/curriculum-service";
import ListStudentView from "./ListStudentView/ListStudentView";
import {swalErr} from "../../../utility";
import Swal from "sweetalert2";


export default function ValiderCv() {

    const [curriculumList, setCurriculumList] = useState([]);
    const [valid, setValid] = useState(false);
    useEffect(() => {
        getCurriculumWithInvalidCV()
            .then(curriculumList => {
                console.log(curriculumList);
                setCurriculumList(curriculumList)
            })
            .catch(e => {
                setCurriculumList([])
                console.error(e);
            });
    },[])

    const valideCV = (id, valid) => {
        validateCV(id, valid)
            .then(responseMessage => {
                setValid(valid)
                Swal.fire({title: responseMessage.message, icon: valid ? 'success' : 'error'})
                    .then();
            })
            .catch(e => {
                console.trace(e)
                swalErr(e).fire({}).then();
            });

    }

    return (
        <div className='container'>
            <h2 className="text-center">Liste des étudiants</h2>
            {curriculumList.map((cv, index) =>
                <div key={index}>
                    <ul>
                        <li><ListStudentView cv={cv}/></li>
                    </ul>
                    <div className={`${valid ? 'border-left border-success' : 'border-left border-danger'}`}>
                        <div className="d-flex justify-content-between align-items-center">
                            <button id="validateBtn" className="btn btn-success fw-bold text-white w-50"
                                    onClick={() => valideCV(cv.id, true)}>Valide
                            </button>
                            <button id="invalidateBtn" className="btn btn-danger fw-bold text-white w-50"
                                    onClick={() => valideCV(cv.id, false)}>Invalide
                            </button>
                        </div>
                    </div>
                </div>)}
        </div>
    )
}
