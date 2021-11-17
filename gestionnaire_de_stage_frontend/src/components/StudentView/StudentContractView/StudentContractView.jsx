import ViewSignedContract from "../../Contract/ViewSignedContracts/ViewSignedContract/ViewSignedContract";
import {useEffect, useState} from "react";
import {getSignedContractForStudent} from "../../../services/contrat-service";
import {useAuth} from "../../../services/use-auth";
import MessageNothingToShow from "../../SharedComponents/MessageNothingToShow/MessageNothingToShow";

export default function StudentContractView() {
    const auth = useAuth();
    const [contract, setContract] = useState(null);

    useEffect(() => {
        getSignedContractForStudent(auth.user.id).then(contract => setContract(contract))
    }, [auth]);

    if (!contract) {
        return <MessageNothingToShow message="Aucun contrat n'a été signé pour l'instant..."/>
    }
    return <ViewSignedContract contract={contract}/>
}
