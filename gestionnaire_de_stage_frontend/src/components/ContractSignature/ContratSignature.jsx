import PdfDocumentViewer from "../PdfDocumentViewer/PdfDocumentViewer";
import {useEffect, useState} from "react";
import {BsPenFill} from "react-icons/all";
import Swal from "sweetalert2";
import {managerSignContract, monitorSignContract} from "../../services/contrat-service";
import {UserType} from "../../enums/UserTypes";
import {useAuth} from "../../services/use-auth";

export default function ContratSignature({userType, contract, removeContract}) {

    const auth = useAuth();

    const [signature, setSignature] = useState(undefined);

    const toPdfBlob = (pdfFile) => {
        if (!pdfFile)
            return null;

        const decodedChars = atob(pdfFile);
        const numBytes = new Array(decodedChars.length);
        for (let i = 0; i < numBytes.length; i++)
            numBytes[i] = decodedChars.charCodeAt(i);

        return new Blob([new Uint8Array(numBytes), {type: 'application/pdf'}]);
    }

    const [pdf, setPdf] = useState(null);

    useEffect(() => {
        setPdf(toPdfBlob(contract.contractPDF));
    }, []);

    const startContract = (e) => {
        e.preventDefault();
        if (!signature && signature === '') {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Veuillez signer le contrat avant de continuer.',
            });
            return;
        }
        if (userType === UserType.MANAGER[0])
            managerSignContract(signature, auth.user.id, contract.id).then(isSigned => {
                if (isSigned)
                    removeContract(contract.id);
            });
        else if(userType === UserType.MONITOR[0])
            monitorSignContract(signature, auth.user.id, contract.id).then(isSigned => {
                if (isSigned)
                    removeContract(contract.id);
            });
    }

    return <div className={"container bg-secondary my-2"}>
        <div className="d-flex justify-content-between flex-column">
            <PdfDocumentViewer file={pdf}/>
            <form onSubmit={startContract}>
                <div className={'input-group'}>
                    <label>Signature</label>
                    <input type="text" placeholder="Entrez votre signature" className="w-100 form-control"
                           onChange={e => setSignature(e.target.value)}/>
                </div>
                <h6 className="text-white text-center mt-3">En appuyant sur envoyer, vous confirmez avoir lu le contrat
                    et que la signature entré correspond à la votre.</h6>
                <button id="invalidateContractBtn" className="btn btn-primary fw-bold w-100 mb-4 mt-0"
                        type="submit">Signer le contrat <BsPenFill/></button>
            </form>
        </div>
    </div>
}
