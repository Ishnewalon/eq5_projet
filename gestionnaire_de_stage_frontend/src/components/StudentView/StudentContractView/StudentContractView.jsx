import ViewSignedContract from "../../ViewSignedContracts/ViewSignedContract/ViewSignedContract";
import {useEffect, useState} from "react";
import {getSignedContractForStudent} from "../../../services/contrat-service";
import {useAuth} from "../../../services/use-auth";

export default function StudentContractView(){
    const auth = useAuth();
    const [contract, setContract] = useState(null);

    useEffect(() => {
        getSignedContractForStudent(auth.user.id).then(contract => setContract(contract))
    }, [contract, auth.user.id]);

    return (
        <div>
            {contract ? <ViewSignedContract contract={contract}/> :
            <div className={'bg-secondary d-flex align-items-center justify-content-center text-white'}>
                <p className='mt-3'>Aucun contrat a été signé par vous...</p>
            </div>}
        </div>
    )
}
