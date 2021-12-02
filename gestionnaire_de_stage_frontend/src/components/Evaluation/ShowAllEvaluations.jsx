import {useEffect, useState} from "react";
import {getAllStageBySupervisor} from "../../services/stage-service";
import {UserType} from "../../enums/UserTypes";
import {useAuth} from "../../hooks/use-auth";
import FormFilled from "./FormFilled";
import MessageNothingToShow from "../SharedComponents/MessageNothingToShow/MessageNothingToShow";

export default function ShowAllEvaluations({userType}) {
    const [evaluations, setEvaluations] = useState([]);
    const auth = useAuth();

    useEffect(() => {
        if (userType === UserType.SUPERVISOR[0])
            getAllStageBySupervisor(auth.user.id).then(evals => setEvaluations(evals))
    }, [auth.user.id, userType]);

    const getResponsible = evaluation => {
        if (userType === UserType.SUPERVISOR[0])
            return evaluation?.contract?.student?.supervisor;
    }

    const getForm = evaluation => {
        if (userType === UserType.SUPERVISOR[0])
            return evaluation.evalMilieuStage;
    }

    if(evaluations?.length === 0)
        return <MessageNothingToShow message="Aucun formulaire de visite à afficher..."/>

    return <>{
        evaluations.map((evaluation, index) => <div key={index}>
            <FormFilled form={getForm(evaluation)}
                        responsible={getResponsible(evaluation)}
                        student={evaluation.contract.student}
            />
        </div>)}
    </>
}
