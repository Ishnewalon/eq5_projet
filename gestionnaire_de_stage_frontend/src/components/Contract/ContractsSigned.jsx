import ContractSigned from "./ContractSigned";
import {useAuth} from "../../services/use-auth";
import {useEffect, useState} from "react";
import {getAllSignedContractsForManager, getAllSignedContractsForMonitor} from "../../services/contrat-service";
import {UserType} from "../../enums/UserTypes";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";

export default function ContractsSigned({userType}) {
    const auth = useAuth();
    const [contractList, setContractList] = useState([]);

    useEffect(() => {
        if (userType === UserType.MANAGER[0])
            getAllSignedContractsForManager(auth.user.id).then(contractList => setContractList(contractList));
        else if (userType === UserType.MONITOR[0])
            getAllSignedContractsForMonitor(auth.user.id).then(contractList => setContractList(contractList));
    }, [auth.user.id, userType]);

    if (contractList.length === 0)
        return <MessageNothingToShow message={"Aucun contrat à afficher pour le moment..."}/>;

    return <ContainerBox>{contractList.map((contract, index) =>
        <ContractSigned key={index} contract={contract}/>)}
    </ContainerBox>
}

