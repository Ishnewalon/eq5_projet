import {FormGroup} from "../../SharedComponents/Form/FormGroup";
import {FieldInput, FieldRadio, FieldTextarea} from "../../SharedComponents/Form/FormFields";
import {Column} from "../../SharedComponents/Column";

export default function StepSix({errors, register, choixAppreciation, yesAndNoAnswers}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Appréciation globale du stagiaire</h2>
        <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
        <FormGroup>
            <Column col={{sm: 6}}>
                <FieldRadio
                    name="appreciationGlobale"
                    validation={{required: 'Ce champ est requis'}}
                    register={register}
                    list={Object.values(choixAppreciation)}
                    label='Les habiletés démontrées'
                />
            </Column>
            <Column col={{sm: 6}}>
                <FieldRadio
                    name="evaluationDiscuteAvecEtudiant"
                    register={register}
                    list={Object.values(yesAndNoAnswers)}
                    label='Cette évaluation a été discutée avec le stagiaire'
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column>
                <FieldInput
                    label='Veuillez indiquer le nombre d’heures réel par semaine d’encadrement accordé au
                stagiaire'
                    validation={{
                        required: 'Ce champ est requis',
                        min: {
                            value: 0,
                            message: 'Les heures doivent être supérieur ou égale à 0'
                        }
                    }}
                    name='nbHeuresReelTravailEtudiant'
                    register={register}
                    type='number'
                    error={errors.nbHeuresReelTravailEtudiant}
                />
                <FieldTextarea
                    label='Précisez votre appréciation'
                    name='commentsInternProductivity'
                    register={register}
                    validation={{required: 'Ce champ est requis'}}
                    error={errors.commentsInternProductivity}
                />
            </Column>
        </FormGroup>
    </div>
}
