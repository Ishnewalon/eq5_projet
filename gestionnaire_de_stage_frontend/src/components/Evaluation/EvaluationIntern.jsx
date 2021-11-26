import {monitorCreateForm} from "../../services/stage-service";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import StepOne from "../EvaluationsWithSteps/StepOne";
import {useForm} from "react-hook-form";
import StepTwo from "../EvaluationsWithSteps/StepTwo";
import StepThree from "../EvaluationsWithSteps/StepThree";
import StepFour from "../EvaluationsWithSteps/StepFour";
import StepFive from "../EvaluationsWithSteps/StepFive";
import StepSix from "../EvaluationsWithSteps/StepSix";
import StepSeven from "../EvaluationsWithSteps/StepSeven";

export default function EvaluationIntern() {

    const {formState: {errors}, handleSubmit, register} = useForm({
        mode: "onSubmit",
        reValidateMode: "onChange"
    });

    const choixAccords = {
        TOTALEMENT_EN_ACCORD: ["TOTALEMENT_EN_ACCORD", "Totalement en accord"],
        PLUTOT_EN_ACCORD: ['PLUTOT_EN_ACCORD', 'Plûtot en accord'],
        PLUTOT_EN_DESACCORD: ['PLUTOT_EN_DESACCORD', 'Plûtot en désaccord'],
        TOTALEMENT_EN_DESACCORD: ['TOTALEMENT_EN_DESACCORD', 'Totalement en désaccord'],
        NON_APPLICABLE: ['NON_APPLICABLE', 'N/A*']
    }

    const choixAppreciation = {
        DEPASSE_BEAUCOUP: ['DEPASSE_BEAUCOUP', 'Dépassent de beaucoup les attentes'],
        DEPASSE: ['DEPASSE', 'Dépassent les attentes'],
        REPOND_PLEINEMENT: ['REPOND_PLEINEMENT', 'Répondent pleinement aux attentes'],
        REPOND_PARTIELLEMENT: ['REPOND_PARTIELLEMENT', 'Répondent partiellement aux attentes'],
        REPOND_PAS: ['REPOND_PAS', 'Répondent pas aux attentes']
    }

    const yesAndNoAnswers = {
        OUI: [true, 'Oui'],
        NON: [false, 'Non']
    }

    const sendVisitForm = (e, data) => {
        e.preventDefault();
        monitorCreateForm(data).then();
    };

    return <ContainerBox>
        <form onSubmit={handleSubmit(sendVisitForm)} noValidate>
            <StepOne errors={errors} register={register}/>
            <hr/>
            <StepTwo errors={errors} register={register} choices={choixAccords}/>
            <hr/>
            <StepThree errors={errors} register={register} choices={choixAccords}/>
            <hr/>
            <StepFour errors={errors} register={register} choices={choixAccords}/>
            <hr/>
            <StepFive errors={errors} register={register} choices={choixAccords}/>
            <hr/>
            <StepSix register={register} errors={errors} choixAppreciation={choixAppreciation}
                     yesAndNoAnswers={yesAndNoAnswers}/>
            <hr/>
            <StepSeven errors={errors} register={register} yesAndNoAnswers={yesAndNoAnswers}/>
            <button onClick={sendVisitForm} className='btn btn-primary w-100 mt-4 rounded fw-bold'>Créer une
                évaluation
                de stage
            </button>
        </form>
    </ContainerBox>
}
