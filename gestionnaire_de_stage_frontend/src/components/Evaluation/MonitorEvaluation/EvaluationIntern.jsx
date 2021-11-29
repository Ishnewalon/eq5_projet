import {monitorCreateForm} from "../../../services/stage-service";
import {ContainerBox} from "../../SharedComponents/ContainerBox";
import StepOne from "./StepOne";
import {useForm} from "react-hook-form";
import StepTwo from "./StepTwo";
import StepThree from "./StepThree";
import StepFour from "./StepFour";
import StepFive from "./StepFive";
import StepSix from "./StepSix";
import StepSeven from "./StepSeven";
import Swal from "sweetalert2";

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
        OUI: ["true", 'Oui'],
        NON: ["false", 'Non']
    }

    const sendVisitForm = (data, e) => {
        e.preventDefault();
        let status, body;
        Swal.fire({
            title: `Création de l'évaluation...`,
            timer: 120000,
            didOpen: () => {
                Swal.showLoading()
                monitorCreateForm(data).then(
                    response => {
                        response.json().then(b => {
                            status = response.status
                            body = b
                            Swal.close()
                        })
                    }
                );
            }
        }).then(() => {
            if (status === 200)
                Swal.fire({title: body.message});
            if (status === 400)
                Swal.fire({title: body.message});
        })
    };

    return <ContainerBox>
        <form onSubmit={handleSubmit(sendVisitForm)} noValidate>
            <StepOne
                errors={errors}
                register={register}
            />
            <hr/>
            <StepTwo
                register={register}
                choices={choixAccords}
            />
            <hr/>
            <StepThree
                register={register}
                choices={choixAccords}
            />
            <hr/>
            <StepFour
                register={register}
                choices={choixAccords}
            />
            <hr/>
            <StepFive
                register={register}
                choices={choixAccords}
            />
            <hr/>
            <StepSix
                register={register}
                errors={errors}
                choixAppreciation={choixAppreciation}
                yesAndNoAnswers={yesAndNoAnswers}
            />
            <hr/>
            <StepSeven
                errors={errors}
                register={register}
                choices={yesAndNoAnswers}
            />
            <button type='submit' className='btn btn-primary w-100 mt-4 rounded fw-bold'>
                Créer une évaluation de stage
            </button>
        </form>
    </ContainerBox>
}
