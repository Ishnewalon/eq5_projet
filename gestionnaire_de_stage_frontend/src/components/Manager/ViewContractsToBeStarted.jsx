import {useEffect, useState} from "react";
import ContractSignature from "../ContractSigning/ContratSignature";
import {getAllContractsToBeStarted} from "../../services/contrat-service";
import {UserType} from "../../enums/UserTypes";

export default function ViewContractsToBeStarted() {
    const [contracts, setContracts] = useState([]);

    useEffect(() => getAllContractsToBeStarted().then(contracts => setContracts(contracts)), []);

    const removeContract = (contractId) => {
        const newContracts = contracts.filter(contract => contract.id !== contractId);
        setContracts(newContracts);
    };

    return <>
        <h2 className="text-center">Signature des contrats</h2>
        {
            contracts.length > 0 ?
                contracts.map((contract, index) => <ContractSignature key={index} removeContract={removeContract}
                                                                      userType={UserType.MANAGER[0]}
                                                                      contract={contract}/>)
                :
                <div className={"d-flex justify-content-center align-items-center"}>
                    <p className={"text-center border border-white rounded p-2 mt-3"}>Aucun contrat à signer pour le
                        moment</p>
                </div>
        }
    </>
}
