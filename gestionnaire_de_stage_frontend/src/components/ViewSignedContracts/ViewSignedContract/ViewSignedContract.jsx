import PdfDocumentViewer from "../../PdfDocumentViewer/PdfDocumentViewer";
import {toPdfBlob} from "../../ContractSignature/ContratSignature";
import {useEffect, useState} from "react";
import {toast} from "../../../utility";
import {FiDownload} from "react-icons/all";

export default function ViewSignedContract({contract}) {
    const [pdf, setPdf] = useState(null);

    useEffect(() => setPdf(toPdfBlob(contract.contractPDF)), [contract.contractPDF]);

    const _studentFullName = (student) => `${student.firstName} ${student.lastName}`;

    const downloadPdf = () => {
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(pdf);
        link.download = `contrat_${_studentFullName(contract.student)}.pdf`;
        link.click();
        window.URL.revokeObjectURL(link.href);
        toast.fire({text: 'Contrat en cours de téléchargement'});
    };

    return <div className='d-flex align-items-center justify-content-center flex-column'>
        <h5>Contrat de {_studentFullName(contract.student)}</h5>
        <button className='btn-primary mt-2' onClick={downloadPdf}>Télécharger Contrat <FiDownload/></button>
        <PdfDocumentViewer showContract={true} file={pdf}/>
    </div>
}
