import ViewSignedContract from "./ViewSignedContract/ViewSignedContract";
import {useAuth} from "../../services/use-auth";
import {useEffect, useState} from "react";
import {getAllSignedContractsForManager, getAllSignedContractsForMonitor} from "../../services/contrat-service";
import {UserType} from "../../enums/UserTypes";

export default function ViewSignedContracts({userType}) {
    const auth = useAuth();
    const [contractList, setContractList] = useState([]);

    useEffect(() => {
        if (userType === UserType.MANAGER[0])
            getAllSignedContractsForManager(auth.user.id).then(contractList => setContractList(contractList));
        else if (userType === UserType.MONITOR[0])
            getAllSignedContractsForMonitor(auth.user.id).then(contractList => setContractList(contractList));
    }, [auth.user.id, userType]);

    if (contractList.length === 0)
        return <div className={'bg-secondary d-flex py-3 align-items-center justify-content-center text-white'}>
            Aucun contrat à afficher pour le moment...
        </div>;

    return <>{
        contractList.map((contract, index) => <div key={index}><ViewSignedContract contract={contract}/></div>)
    }</>
}

