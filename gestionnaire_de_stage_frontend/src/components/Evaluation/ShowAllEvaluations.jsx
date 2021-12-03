import {useEffect, useState} from "react";
import {getAllStageByMonitor, getAllStageBySupervisor} from "../../services/stage-service";
import {UserType} from "../../enums/UserTypes";
import {useAuth} from "../../hooks/use-auth";
import FormFilled from "./FormFilled";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";
import {ContainerBox} from "../SharedComponents/ContainerBox";

export default function ShowAllEvaluations({userType}) {
    const [evaluations, setEvaluations] = useState([]);
    const auth = useAuth();

    useEffect(() => {
        if (userType === UserType.SUPERVISOR[0])
            getAllStageBySupervisor(auth.user.id).then(evals => setEvaluations(evals))
        else if (userType === UserType.MONITOR[0])
            getAllStageByMonitor(auth.user.id).then(evals => setEvaluations(evals))
    }, [auth.user.id, userType]);

    const getResponsible = evaluation => {
        if (userType === UserType.SUPERVISOR[0])
            return evaluation?.contract?.student?.supervisor;
        else if (userType === UserType.MONITOR[0])
            return evaluation?.contract.monitor;
    }

    const getForm = evaluation => {
        if (userType === UserType.SUPERVISOR[0])
            return evaluation.evalMilieuStage;
        else if (userType === UserType.MONITOR[0])
            return evaluation.evalStagiaire;
    }

    const getMessage = (userType) => {
        if (userType === UserType.SUPERVISOR[0])
            return "Aucun formulaire de visite à afficher...";
        else if (userType === UserType.MONITOR[0])
            return "Aucune évaluation de stage à afficher...";
    }

    const getMessageForPdf = (userType) => {
        if (userType === UserType.SUPERVISOR[0])
            return "Formulaire de visite de";
        else if (userType === UserType.MONITOR[0])
            return "Evaluation de stage de";
    }

    if (evaluations?.length === 0)
        return <MessageNothingToShow message={getMessage(userType)}/>

    return <ContainerBox>
        <div className='d-flex align-items-center flex-column justify-content-center'>
            {evaluations.map((evaluation, index) => <div key={index}>
                <FormFilled message={getMessageForPdf(userType)} form={getForm(evaluation)}
                            responsible={getResponsible(evaluation)}
                            student={evaluation.contract.student}
                />
                {evaluations.length > 1 && <hr className='my-3'/>}
            </div>)}
        </div>
    </ContainerBox>
}
