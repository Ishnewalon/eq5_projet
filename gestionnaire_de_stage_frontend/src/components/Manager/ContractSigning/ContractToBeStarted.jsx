import offerAppService from "../../../services/offerAppService";
import PdfDocumentViewer from "../../PdfDocumentViewer/PdfDocumentViewer";
import {useEffect, useState} from "react";
import {toast} from "../../../utility";

export default function ContractToBeStarted(contract){



    const startContract = (contract, accepted) => {
        offerAppService.managerStartContract({contract, accepted}).then();
    }

    const [file, setFile] = useState(null);
    let isPdf = true;

    useEffect(() => {
        const decodedChars = atob(contract.curriculum.file);
        const byteNums = new Array(decodedChars.length);
        for (let i = 0; i < decodedChars.length; i++)
            byteNums[i] = decodedChars.charCodeAt(i);
        let contentType = 'application/pdf';

        if (contract.curriculum.fileName.endsWith('docx'))
            contentType = 'application/vnd.openxmlformats-officedocument.wordprocessingml.document';

        const blob = new Blob([new Uint8Array(byteNums), {type: contentType}]);
        setFile(blob);
    }, []);


    return <div className="d-flex justify-content-between align-items-center">
            <PdfDocumentViewer file={file}/>
            <button id="validateContractBtn" className="btn btn-success fw-bold text-white w-50"
                    onClick={() => startContract(contract, true)}>Valide</button>
            <button id="invalidateContractBtn" className="btn btn-danger fw-bold text-white w-50"
                    onClick={() => startContract(contract, false)}>Invalide</button>
        </div>
}
