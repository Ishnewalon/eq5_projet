import ContractSigned from "./ContractSigned";
import {useAuth} from "../../hooks/use-auth";
import {useEffect, useState} from "react";
import {getAllSignedContractsForManager, getAllSignedContractsForMonitor} from "../../services/contrat-service";
import {UserType} from "../../enums/UserTypes";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";

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

    return contractList.map((contract, index) =>
        <div className='d-flex align-items-center flex-column justify-content-center' key={index}>
            {
                index > 0 && <hr className='w-100'/>
            }
            <ContractSigned contract={contract}/>
        </div>
    )
}

