import React, {useEffect, useState} from 'react';
import {getAllApplicants} from '../../services/offerAppService';
import {useAuth} from "../../services/use-auth";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {toast} from "../../utility";
import {AiOutlineFile} from "react-icons/all";
import OfferView from "../Offers/OfferView";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";

export default function OfferApplicationsMonitorListOfApplications() {//TODO: list of curriculum with a list of applicants inside
    let auth = useAuth();
    const [students, setStudents] = useState([]);

    useEffect(() => {
        getAllApplicants(auth.user.email).then(v => {
            setStudents(v)
        })
    }, [auth.user.email]);

    if (students.length === 0) {
        return <MessageNothingToShow message="Au moment, aucun étudiant n'a postulé à cette offre"/>
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

function PreviewStudent({dto}) {
    // TODO revoir le frontend
    const openFile = () => {
        const decodedChars = atob(dto.file);
        const byteNums = new Array(decodedChars.length);
        for (let i = 0; i < decodedChars.length; i++)
            byteNums[i] = decodedChars.charCodeAt(i);

        // noinspection JSCheckFunctionSignatures
        const blob = new Blob([new Uint8Array(byteNums), {type: 'application/pdf'}]);

        let url = window.URL.createObjectURL(blob);

        const a = document.createElement('a')
        a.href = url
        a.download = dto.fileName;
        a.click();
        URL.revokeObjectURL(url)
        toast.fire({title: 'Téléchargé'}).then()
    }

    return <div className=" p-3 mt-5 border-left border-right border-light">
        <div className={'row'}>
            <div className="col-12 col-sm-6">
                <div className={'d-flex justify-content-center align-items-center flex-column p-3 shadow h-100'}>
                    <h4 className={'p-2 rounded fw-bold'}>{dto.firstName + ', ' + dto.lastName} </h4>
                    <div className={'d-flex align-items-center'}>

                        <button onClick={openFile} className="ms-2 btn btn-primary"><AiOutlineFile/> {dto.fileName}
                        </button>
                    </div>
                </div>
            </div>
            <div className="col-12 col-sm-6">
                <OfferView offer={dto.offerDTO}/>
            </div>
        </div>
    </div>
}
