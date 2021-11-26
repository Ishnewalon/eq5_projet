import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FieldRadio} from "../SharedComponents/FormInput/FieldRadio";
import {FormTextarea} from "../SharedComponents/FormTextarea";
import {FormInput} from "../SharedComponents/FormInput/FormInput";

export default function StepSix({errors, register, choixAppreciation, yesAndNoAnswers}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Appréciation globale du stagiaire</h2>
        <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
        <FormGroup>
            <FieldRadio
                name="appreciationGlobale"
                register={register}
                list={Object.values(choixAppreciation)}
                label='Les habiletés démontrées'
            />
            <FieldRadio
                name="evaluationDiscuteAvecEtudiant"
                register={register}
                list={Object.values(yesAndNoAnswers)}
                label='Cette évaluation a été discutée avec le stagiaire'
            />
        </FormGroup>
        <FormGroup>
            <FormTextarea
                label='Précisez votre appréciation'
                name='commentairesCinq'
                register={register}
                placeholder='Commentaires sur la productivité du stagiaire'
            />
        </FormGroup>
        <FormGroup>
            <FormInput
                label='Veuillez indiquer le nombre d’heures réel par semaine d’encadrement accordé au
                stagiaire'
                validation={{required: 'Ce champ est requis'}}
                name='nbHeuresReelTravailEtudiant'
                register={register}
                type='number'
                error={errors.nbHeuresReelTravailEtudiant}
                placeholder='Nombre heures réel par semaine'
            />
        </FormGroup>
    </div>
}
