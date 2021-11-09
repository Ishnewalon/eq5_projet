import ContratSignature from "./ContractSignature/ContratSignature";
import {useAuth} from "../../services/use-auth";
import {useEffect, useState} from "react";
import {getContractForStudent} from '../../services/contrat-service';
import {UserType} from "../../enums/UserTypes";

export function ContractView(){
    const auth = useAuth();
    const [contract, setContract] = useState(null);

    useEffect(() => {
        getContractForStudent(auth.user.id).then(contract => setContract(contract));
    }, [auth.user.id]);


    if (!contract)
        return <h6 className="text-white text-center p-3 bg-secondary">Vous avez aucun contrat à signer en ce-moment.</h6>

    return <>
        <ContratSignature contract={contract} userType={UserType.STUDENT[0]}/>
    </>
}
