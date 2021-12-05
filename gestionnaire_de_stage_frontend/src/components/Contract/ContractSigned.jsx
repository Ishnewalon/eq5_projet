import PdfDocumentViewer from "../SharedComponents/PdfDocumentViewer/PdfDocumentViewer";
import {useEffect, useState} from "react";
import {toPdfBlob} from "../../utility";

export default function ContractSigned({contract, showContract = false}) {
    const [pdf, setPdf] = useState(null);

    useEffect(() => setPdf(toPdfBlob(contract.contractPDF)), [contract.contractPDF]);

    const studentFullName = student => `${student.firstName} ${student.lastName}`;

    return <div className='d-flex align-items-center flex-column justify-content-center'>
        <PdfDocumentViewer message={`Contrat de ${studentFullName(contract.student)}`} showContract={showContract} file={pdf}
                           fileName={`contrat_${studentFullName(contract.student)}.pdf`}/>
    </div>
}
