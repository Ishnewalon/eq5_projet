import ContratSignature from "./ContractSignature/ContratSignature";
import {useAuth} from "../../services/use-auth";
import {useEffect, useState} from "react";
import {getContractForStudent} from '../../services/contrat-service';
import {UserType} from "../../enums/UserTypes";

export function ContractView() {
    const auth = useAuth();
    const [contract, setContract] = useState(null);

    useEffect(() => {
        getContractForStudent(auth.user.id).then(contract => setContract(contract));
    }, [auth.user.id]);


    if (!contract)
        return <div className={'bg-secondary d-flex py-3 align-items-center justify-content-center text-white'}>
            Vous avez aucun contrat à signer en ce-moment.
        </div>

    return <>
        <ContratSignature contract={contract} userType={UserType.STUDENT[0]}/>
    </>
}
