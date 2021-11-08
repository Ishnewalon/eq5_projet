import ViewSignedContract from "./ViewSignedContract/ViewSignedContract";
import {useAuth} from "../../services/use-auth";
import {useEffect, useState} from "react";
import {getAllSignedContractsForManager} from "../../services/contrat-service";
import {UserType} from "../../enums/UserTypes";

export default function ViewSignedContracts({userType}) {
    const auth = useAuth();
    const [contractList, setContractList] = useState([]);

    useEffect(() => {
        if (userType === UserType.MANAGER[0])
            getAllSignedContractsForManager(auth.user.id).then(contractList => setContractList(contractList));
    }, [auth.user.id, userType]);

    return <>{
        contractList.length > 0 ?
            contractList.map((contract, index) => <div key={index}><ViewSignedContract contract={contract}/></div>) :
            <div className={'bg-secondary d-flex align-items-center justify-content-center text-white'}>
                <p className='mt-3'>Aucun contrat n'a été signé par vous...</p>
            </div>
    }</>
}

