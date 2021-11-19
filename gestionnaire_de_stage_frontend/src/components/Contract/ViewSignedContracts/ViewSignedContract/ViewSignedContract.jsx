import PdfDocumentViewer from "../../../SharedComponents/PdfDocumentViewer/PdfDocumentViewer";
import {useEffect, useState} from "react";
import {downloadFile, toPdfBlob} from "../../../../utility";
import {FiDownload} from "react-icons/all";

export default function ViewSignedContract({contract}) {
    const [pdf, setPdf] = useState(null);

    useEffect(() => setPdf(toPdfBlob(contract.contractPDF)), [contract.contractPDF]);

    const studentFullName = student => `${student.firstName} ${student.lastName}`;

    return <div className='d-flex align-items-center justify-content-center flex-column'>
        <h5>Contrat de {studentFullName(contract.student)}</h5>
        <button className='btn-primary mt-2'
                onClick={() => downloadFile(pdf, `contrat_${studentFullName(contract.student)}.pdf`)}>Télécharger
            Contrat <FiDownload/></button>
        <PdfDocumentViewer showContract={true} file={pdf}/>
    </div>
}
