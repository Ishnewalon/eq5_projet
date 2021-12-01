import {supervisorCreateForm} from "../../../services/stage-service";
import {useForm} from "react-hook-form";
import SupervisorStepOne from "./SupervisorStepOne";
import SupervisorStepTwo from "./SupervisorStepTwo";
import SupervisorStepThree from "./SupervisorStepThree";
import SupervisorStepFour from "./SupervisorStepFour";
import Swal from "sweetalert2";
import {swalErr} from "../../../utility";


export default function SupervisorVisitForm() {

    const {formState: {errors}, handleSubmit, register, watch} = useForm({
        mode: "onSubmit",
        reValidateMode: "onChange"
    });

    const choixAccords = {
        TOTALEMENT_EN_ACCORD: ["TOTALEMENT_EN_ACCORD", "Totalement en accord"],
        PLUTOT_EN_ACCORD: ['PLUTOT_EN_ACCORD', 'Plûtot en accord'],
        PLUTOT_EN_DESACCORD: ['PLUTOT_EN_DESACCORD', 'Plûtot en désaccord'],
        TOTALEMENT_EN_DESACCORD: ['TOTALEMENT_EN_DESACCORD', 'Totalement en désaccord'],
        IMPOSSIBLE_DE_SE_PRONONCER: ['IMPOSSIBLE_DE_SE_PRONONCER', 'Impossible de se prononcer']
    }

    const choixStagiaires = {
        UN_STAGIAIRE: ['Un stagiaire', 'Un stagiaire'],
        DEUX_STAGIAIRES: ['Deux stagiaires', 'Deux stagiaires'],
        TROIS_STAGIAIRES: ['Trois stagiaires', 'Trois stagiaires'],
        PLUS_DE_TROIS_STAGIAIRES: ['Plus de trois stagiaires', 'Plus de trois stagiaires'],
    }

    const choixStage = {
        UN_STAGIAIRE: ['Premier stage', 'Premier stage'],
        DEUX_STAGIAIRES: ['Deuxième stage', 'Deuxième stage']
    }

    const yesAndNoAnswers = {
        OUI: ['true', 'Oui'],
        NON: ['false', 'Non']
    }

    const oneOrTwo = {
        ONE: ['1', '1'],
        TWO: ['2', '2']
    }

    const sendEvaluation = (data, e) => {
        e.preventDefault();
        let status, body;
        Swal.fire({
            title: `Création du formulaire de visite...`,
            timer: 120000,
            didOpen: () => {
                Swal.showLoading()
                supervisorCreateForm(data).then(
                    response =>
                        response.json().then(b => {
                            status = response.status
                            body = b
                            Swal.close()
                        })
                );
            }
        }).then(() => {
            if (status === 200)
                Swal.fire({icon: 'success',title: body.message});
            if (status === 400)
                swalErr.fire({title: body.message});
        })
    };


    return <div>
        <form action="" onSubmit={handleSubmit(sendEvaluation)}>
            <h1 className='text-center text-decoration-underline'>Évaluation de stage</h1>
            <SupervisorStepOne register={register} errors={errors}/>
            <hr/>
            <SupervisorStepTwo
                errors={errors}
                register={register}
                oneOrTwo={oneOrTwo}
            />
            <hr/>
            <SupervisorStepThree
                errors={errors}
                register={register}
                choixAccords={choixAccords}
            />
            <hr/>
            <SupervisorStepFour
                watch={watch}
                register={register}
                errors={errors}
                yesAndNoAnswers={yesAndNoAnswers}
                choixStage={choixStage}
                choixStagiaires={choixStagiaires}
            />
            <button type='submit' className='btn btn-primary w-100 mt-4'>Créer un formulaire de visite</button>
        </form>
    </div>
}
