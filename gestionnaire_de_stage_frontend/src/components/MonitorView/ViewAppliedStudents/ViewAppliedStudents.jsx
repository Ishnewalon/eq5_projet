import React, {useEffect, useState} from 'react';
import PreviewStudent from "./PreviewStudent/PreviewStudent";
import {getAllApplicants} from '../../../services/offerAppService';
import {useAuth} from "../../../services/use-auth";
import {ContainerBox} from "../../SharedComponents/ContainerBox/ContainerBox";

export default function ViewAppliedStudents() {//TODO: list of curriculum with a list of applicants inside
    let auth = useAuth();
    const [students, setStudents] = useState([]);

    useEffect(() => {
        getAllApplicants(auth.user.email).then(v => {
            setStudents(v)
        })
    }, [auth.user.email]);

    if (students.length === 0) {
        return <div className={'bg-secondary d-flex py-3 align-items-center justify-content-center text-white'}>
            Au moment, aucun étudiant n'a postulé à cette offre
        </div>
    }


    return <>
        <ContainerBox>
            {students.map((student, index) =>
                <div key={index}>
                    <PreviewStudent dto={student}/>
                </div>)}
        </ContainerBox>
    </>;
}
