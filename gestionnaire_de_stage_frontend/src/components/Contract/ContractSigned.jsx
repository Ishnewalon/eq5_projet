import PdfDocumentViewer from "../SharedComponents/PdfDocumentViewer/PdfDocumentViewer";
import {useEffect, useState} from "react";
import {toPdfBlob} from "../../utility";

export default function ContractSigned({contract}) {
    const [pdf, setPdf] = useState(null);

    useEffect(() => setPdf(toPdfBlob(contract.contractPDF)), [contract.contractPDF]);

    const studentFullName = student => `${student.firstName} ${student.lastName}`;

    return <div className='d-flex align-items-center text-white justify-content-center flex-column'>
        <h2>Contrat de {studentFullName(contract.student)}</h2>

        <PdfDocumentViewer showContract={true} file={pdf}
                           fileName={`contrat_${studentFullName(contract.student)}.pdf`}/>
    </div>
}
