import ContractSigned from "./ContractSigned";
import {useEffect, useState} from "react";
import {getSignedContractForStudent} from "../../services/contrat-service";
import {useAuth} from "../../hooks/use-auth";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";

export default function ContractSignedStudent() {
    const auth = useAuth();
    const [contract, setContract] = useState(null);

    useEffect(() => {
        getSignedContractForStudent(auth.user.id).then(contract => setContract(contract))
    }, [auth]);

    if (!contract) {
        return <MessageNothingToShow message="Aucun contrat n'a été signé pour l'instant..."/>
    }
    return <ContractSigned contract={contract}/>
}
