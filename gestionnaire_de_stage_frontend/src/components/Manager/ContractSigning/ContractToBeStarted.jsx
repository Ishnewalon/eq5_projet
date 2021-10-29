import offerAppService from "../../../services/offerAppService";

export default function ContractToBeStarted(contract){

    const startContract = (contract, accepted) => {
        offerAppService.managerStartContract({contract, accepted}).then();
    }

    return <div className="d-flex justify-content-between align-items-center">
            <button id="validateContractBtn" className="btn btn-success fw-bold text-white w-50"
                    onClick={() => startContract(contract, true)}>Valide</button>
            <button id="invalidateContractBtn" className="btn btn-danger fw-bold text-white w-50"
                    onClick={() => startContract(contract, false)}>Invalide</button>
        </div>
}
