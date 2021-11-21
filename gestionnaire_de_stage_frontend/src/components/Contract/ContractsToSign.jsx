import React, {useEffect, useState} from "react";
import ContractSignature from "./ContratSignature";
import {getAllContractsToBeSignedForMonitor, getAllContractsToBeStarted} from "../../services/contrat-service";
import {UserType} from "../../enums/UserTypes";
import {useAuth} from "../../services/use-auth";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";

export default function ContractsToSign({userType}) {
    const [contracts, setContracts] = useState([]);
    const auth = useAuth();

    useEffect(() => {
        if (userType === UserType.MANAGER[0])
            getAllContractsToBeStarted().then(contracts => setContracts(contracts));
        if (userType === UserType.MONITOR[0])
            getAllContractsToBeSignedForMonitor(auth.user.id).then(contracts => setContracts(contracts))
    }, [auth.user.id, userType]);

    const removeContract = (contractId) => {
        setContracts(prev => prev.filter(contract => contract.id !== contractId));
    };

    if (contracts.length === 0)
        return <MessageNothingToShow message="Aucun contrat à signer pour le moment..."/>


    return contracts.map((contract, index) =>
        <ContractSignature key={index} removeContract={removeContract} userType={userType}
                           contract={contract}/>)
}
