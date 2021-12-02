import {toPdfBlob} from "../../utility";
import PdfDocumentViewer from "../SharedComponents/PdfDocumentViewer/PdfDocumentViewer";
import {useEffect, useState} from "react";


export default function FormFilled({form, student, responsible}){
    const [pdf, setPdf] = useState(null);

    useEffect(() => setPdf(toPdfBlob(form)), [form]);

    const userFullName = user => `${user.firstName} ${user.lastName}`;

    return <div className='d-flex align-items-center justify-content-center flex-column'>
        <h2>Visite pour {userFullName(student)}</h2>
        <PdfDocumentViewer file={pdf} fileName={`evaluation_${userFullName(responsible)}.pdf`}/>
    </div>
}
