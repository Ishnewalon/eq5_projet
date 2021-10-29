import './ManagerStartContract.css';
import {useEffect, useState} from "react";
import offerAppService from "../../../services/offerAppService";
import ContractToBeStarted from "../ContractToBeStarted/ContractToBeStarted";

export default function ManagerStartContract(){
    const [contracts, setContracts] = useState([]);

    useEffect(() => {
        offerAppService.getAllApplicantsReadyToSign().then(contracts => setContracts(contracts)).then();
    }, []);

    return <>{contracts.map((contract, index) => <ContractToBeStarted key={index} contract={contract} />)}</>
}
