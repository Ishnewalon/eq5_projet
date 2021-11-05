import {useEffect, useState} from "react";
import ContractSignature from "../ContractSignature/ContratSignature";
import {getAllContractsToBeSignedForMonitor, getAllContractsToBeStarted} from "../../services/contrat-service";
import {UserType} from "../../enums/UserTypes";
import {useAuth} from "../../services/use-auth";

export default function ViewContractToBeSigned({userType}) {
    const [contracts, setContracts] = useState([]);
    const auth = useAuth();

    useEffect(() => {
        if (userType === UserType.MANAGER[0])
            getAllContractsToBeStarted().then(contracts => setContracts(contracts));
        if (userType === UserType.MONITOR[0])
            getAllContractsToBeSignedForMonitor(auth.user.id).then(contracts => setContracts(contracts))
        // eslint-disable-next-line
    }, []);

    const removeContract = (contractId) => {
        const newContracts = contracts.filter(contract => contract.id !== contractId);
        setContracts(newContracts);
    };

    return <>
        {
            contracts.length > 0 ?
                contracts.map((contract, index) => <ContractSignature key={index} userId={auth.user.id} removeContract={removeContract}
                                                                      userType={userType}
                                                                      contract={contract}/>)
                :
                <div className={"d-flex justify-content-center align-items-center"}>
                    <p className={"text-center border border-white rounded p-2 mt-3"}>Aucun contrat à signer pour le
                        moment</p>
                </div>
        }
    </>
}
