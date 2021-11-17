import ContratSignature from "./ContractSignature/ContratSignature";
import {useAuth} from "../../services/use-auth";
import {useEffect, useState} from "react";
import {getContractForStudent} from '../../services/contrat-service';
import {UserType} from "../../enums/UserTypes";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";

export function ContractView() {
    const auth = useAuth();
    const [contract, setContract] = useState(null);

    useEffect(() => {
        getContractForStudent(auth.user.id).then(contract => setContract(contract));
    }, [auth.user.id]);


    if (!contract)
        return <MessageNothingToShow message="Vous avez aucun contrat à signer en ce moment."/>

    return <ContratSignature contract={contract} userType={UserType.STUDENT[0]}/>

}
