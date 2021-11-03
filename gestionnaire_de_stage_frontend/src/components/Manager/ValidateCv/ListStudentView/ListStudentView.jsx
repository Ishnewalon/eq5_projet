import React from 'react';
import './ListStudentView.css'
import {downloadCV} from "../../../../services/curriculum-service";
import {toast} from "../../../../utility";


export default function ListStudentView({cv}) {

    const {firstName, lastName} = cv.student;

    const downloadStudentCv = () => {
        const {id} = cv
        downloadCV(id).then(blob => {
            let myUrl = URL.createObjectURL(blob);

            let myFilename = firstName + "_" + lastName + "_" + id + ".pdf";

            const a = document.createElement('a')
            a.href = myUrl
            a.download = myFilename;
            a.click();
            URL.revokeObjectURL(myUrl)
            toast.fire({title: 'Téléchargement en cours'}).then()
        });
    }

    return <div className="shadow-lg rounded-top p-3 mt-3 border-left border-right">
        <div className={'d-flex align-items-center flex-column'}>
            <h3 className={'d-inline-block text-dark'}>
                    <span className={"badge rounded-pill"}>
                         {firstName} {lastName}
                    </span>
            </h3>
            <button className="btn btn-primary" onClick={downloadStudentCv}>Télécharger Cv</button>
        </div>
    </div>
}
