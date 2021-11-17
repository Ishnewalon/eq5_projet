import ViewSignedContract from "../../Contract/ViewSignedContracts/ViewSignedContract/ViewSignedContract";
import {useEffect, useState} from "react";
import {getSignedContractForStudent} from "../../../services/contrat-service";
import {useAuth} from "../../../services/use-auth";

export default function StudentContractView() {
    const auth = useAuth();
    const [contract, setContract] = useState(null);

    useEffect(() => {
        getSignedContractForStudent(auth.user.id).then(contract => setContract(contract))
    }, [auth]);

    if (!contract) {
        return <div className={'bg-secondary d-flex py-3 align-items-center justify-content-center text-white'}>
            Aucun contrat a été signé par vous...
        </div>
    }
    return <ViewSignedContract contract={contract}/>
}
