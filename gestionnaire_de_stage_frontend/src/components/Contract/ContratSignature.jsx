import PdfDocumentViewer from "../SharedComponents/PdfDocumentViewer/PdfDocumentViewer";
import {useEffect, useState} from "react";
import {BsPenFill} from "react-icons/all";
import {managerSignContract, monitorSignContract, studentSignContract} from "../../services/contrat-service";
import {UserType} from "../../enums/UserTypes";
import {swalErr, toPdfBlob} from "../../utility";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {useForm} from "react-hook-form";


export default function ContratSignature({userType, contract, removeContract}) {
    const {register, handleSubmit, formState: {errors}} = useForm({
        mode: "onSubmit",
    });
    const [signed, setSigned] = useState(false);

    const [pdf, setPdf] = useState(null);

    useEffect(() => {
        setPdf(toPdfBlob(contract.contractPDF));
    }, [contract.contractPDF]);

    const studentFullName = student => `${student.firstName} ${student.lastName}`;
    const startContract = (data, e) => {
        e.preventDefault();
        const {signature} = data;
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
            studentSignContract(signature, contract.id).then(isSigned => setSigned(isSigned));
    }
    if (signed) {
        return <MessageNothingToShow message="Vous avez signé le contrat."/>
    }
    return <div>

        <div className="d-flex justify-content-between flex-column">
            <PdfDocumentViewer message={`Contrat de ${studentFullName(contract.student)}`} file={pdf} fileName={`contrat_${studentFullName(contract.student)}.pdf`}/>
            <form onSubmit={handleSubmit(startContract)}>
                <label className="label h5" htmlFor="signature">
                    Signature <span className="text-muted small">En appuyant sur envoyer, vous confirmez avoir lu le
                    contrat et que la signature entrée correspond à la votre.</span>
                </label>
                <div className="input-group mb-3">
                    <input id="signature"
                           className="form-control"
                           type="text"
                           placeholder="Entrez votre signature"
                           {...register("signature", {
                               required: "Vous devez entrer votre signature!",
                           })}/>
                    <button id="invalidateContractBtn" className="btn btn-primary fw-bold" type="submit">
                        Signez le contrat <BsPenFill/>
                    </button>
                </div>
                {errors.signature && <span className="text-danger">{errors.signature.message}</span>}
            </form>
        </div>
    </div>
}
