import PdfDocumentViewer from "../../SharedComponents/PdfDocumentViewer/PdfDocumentViewer";
import {useEffect, useState} from "react";
import {BsPenFill} from "react-icons/all";
import {managerSignContract, monitorSignContract, studentSignContract} from "../../../services/contrat-service";
import {UserType} from "../../../enums/UserTypes";
import {swalErr} from "../../../utility";
import {FormField} from "../../SharedComponents/FormField/FormField";
import MessageNothingToShow from "../../SharedComponents/MessageNothingToShow/MessageNothingToShow";

export const toPdfBlob = (pdfFile) => {
    if (!pdfFile)
        return null;

    const decodedChars = atob(pdfFile);
    const numBytes = new Array(decodedChars.length);
    for (let i = 0; i < numBytes.length; i++)
        numBytes[i] = decodedChars.charCodeAt(i);

    return new Blob([new Uint8Array(numBytes), {type: 'application/pdf'}]);
}

export default function ContratSignature({userType, contract, removeContract}) {

    const [signature, setSignature] = useState('');
    const [signed, setSigned] = useState(false);

    const [pdf, setPdf] = useState(null);

    useEffect(() => {
        setPdf(toPdfBlob(contract.contractPDF));
    }, [contract.contractPDF]);

    const startContract = (e) => {
        e.preventDefault();
        if (!signature || signature === '') {
            swalErr.fire({
                text: 'Veuillez signer le contrat avant de continuer.',
            }).then();
            return;
        }
        if (userType === UserType.MANAGER[0])
            managerSignContract(signature, contract.id).then(isSigned => {
                if (isSigned)
                    removeContract(contract.id);
                setSigned(isSigned)
            });
        else if (userType === UserType.MONITOR[0])
            monitorSignContract(signature, contract.id).then(isSigned => {
                if (isSigned)
                    removeContract(contract.id);
                setSigned(isSigned)
            });
        else if (userType === UserType.STUDENT[0])
            studentSignContract(signature, contract.id).then(isSigned => {
                setSigned(isSigned)
            });
    }
    if (!signed) {
        return <MessageNothingToShow message="Vous avez signé le contrat."/>
    }
    return <div className={"container bg-secondary my-2"}>

        <div className="d-flex justify-content-between flex-column">
            <PdfDocumentViewer file={pdf}/>
            <form onSubmit={startContract}>
                <FormField>
                    <label>Signature</label>
                    <input type="text" placeholder="Entrez votre signature" className="w-100"
                           onChange={e => setSignature(e.target.value)}/>
                </FormField>
                <h6 className="text-white text-center mt-3">En appuyant sur envoyer, vous confirmez avoir lu le
                    contrat
                    et que la signature entré correspond à la votre.</h6>
                <button id="invalidateContractBtn" className="btn btn-primary fw-bold w-100 mb-4 mt-0"
                        type="submit">Signer le contrat <BsPenFill/></button>
            </form>
        </div>

    </div>
}
