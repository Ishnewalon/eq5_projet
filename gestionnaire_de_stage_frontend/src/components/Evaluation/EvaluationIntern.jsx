import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../SharedComponents/FormField/FormField";
import {monitorCreateForm} from "../../services/stage-service";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import StepOne from "../EvaluationsWithSteps/StepOne";
import {useForm} from "react-hook-form";
import StepTwo from "../EvaluationsWithSteps/StepTwo";
import StepThree from "../EvaluationsWithSteps/StepThree";
import StepFour from "../EvaluationsWithSteps/StepFour";
import StepFive from "../EvaluationsWithSteps/StepFive";
import StepSix from "../EvaluationsWithSteps/StepSix";

export default function EvaluationIntern() {

    const {formState: {errors}, handleSubmit, register} = useForm({
        mode: "onSubmit",
        reValidateMode: "onChange"
    });

    const today = new Date();

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

    return <ContainerBox className={'text-white'}>
        <form onSubmit={handleSubmit(sendVisitForm)} noValidate>
            <StepOne errors={errors} register={register}/>
            <hr/>
            <StepTwo errors={errors} register={register} choices={choixAccords}/>
            <hr/>
            <StepThree errors={errors} register={register} choices={choixAccords}/>
            <hr/>
            <StepFour errors={errors} register={register} choices={choixAccords}/>
            <hr/>
            <StepFive errors={errors} register={register} choices={choixAccords} />
            <hr/>
            <StepSix register={register} errors={errors} choixAppreciation={choixAppreciation} yesAndNoAnswers={yesAndNoAnswers}/>
            <hr/>
            <div className='px-3 pb-3 pt-1 rounded'>
                <FormGroup>
                    <FormField>
                        <label>L’entreprise aimerait accueillir cet élève pour son prochain stage</label>
                        <select
                            title="Le champ 'L’entreprise aimerait accueillir cet élève pour son prochain stage' doit être rempli"
                            name='entrepriseApprecieEtudiant'
                            value={monitorVisitForm.entrepriseApprecieEtudiant} onChange={e => handleChange(e)}>
                            <option disabled value="">Choisiser une réponse</option>
                            {Object.values(yesAndNoAnswers).map((c, index) => <option key={index}
                                                                                      value={c[0]}>{c[1]}</option>)}
                        </select>
                    </FormField>
                </FormGroup>
                <FormGroup>
                    <FormField>
                        <label>La formation technique du stagiaire était-elle suffisante pour accomplir le mandat de
                            stage?</label>
                        <textarea title="Le champ 'La formation technique du stagiaire était-elle suffisante pour accomplir le mandat de
                        stage' doit être rempli" name="formationSuffisanteCommentaire"
                                  value={monitorVisitForm.formationSuffisanteCommentaire}
                                  placeholder='Est-ce que la formation était suffisante?'
                                  onChange={e => handleChange(e)}/>
                    </FormField>
                </FormGroup>
                <FormGroup>
                    <FormField>
                        <label>Nom</label>
                        <input type="text" title='Le nom est requis' name='nom' placeholder='Nom'
                               value={monitorVisitForm.nom}
                               onChange={e => handleChange(e)}/>
                    </FormField>
                    <FormField>
                        <label>Fonction</label>
                        <input type="text" placeholder='Fonction' name='fonctionDeux'
                               title='La 2ième fonction est requise'
                               value={monitorVisitForm.fonctionDeux} onChange={e => handleChange(e)}/>
                    </FormField>
                </FormGroup>
                <FormGroup>
                    <FormField>
                        <label>Signature</label>
                        <input type="text" name='monitorSignature' placeholder='Signature'
                               title="La signature doit être rempli" value={monitorVisitForm.monitorSignature}
                               onChange={e => handleChange(e)}/>
                    </FormField>
                    <FormField>
                        <label>Date</label>
                        <input type="date" name='dateSignature' max={today.toString()}
                               title='La date doit être choisi'
                               value={monitorVisitForm.dateSignature} onChange={e => handleChange(e)}/>
                    </FormField>
                </FormGroup>
                <button onClick={sendVisitForm} className='btn btn-primary w-100 mt-4 rounded fw-bold'>Créer une
                    évaluation
                    de stage
                </button>
            </div>
        </form>
    </ContainerBox>
}
