import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FieldRadio} from "../SharedComponents/FormInput/FieldRadio";
import {FormTextarea} from "../SharedComponents/FormTextarea";
import {FormInput} from "../SharedComponents/FormInput/FormInput";

export default function StepSeven({register, errors, yesAndNoAnswers}) {
    return <div className='px-3 pb-3 pt-1 rounded'>
        <FormGroup>
            <FieldRadio
                name='entrepriseApprecieEtudiant'
                register={register}
                list={Object.values(yesAndNoAnswers)}
                label="L’entreprise aimerait accueillir cet élève pour son prochain stage"
            />
        </FormGroup>
        <FormGroup>
            <FormTextarea
                label='La formation technique du stagiaire était-elle suffisante pour accomplir le mandat de
                stage?'
                name='formationSuffisanteCommentaire'
                register={register}
                placeholder='Est-ce que la formation était suffisante'
                error={errors.formationSuffisanteCommentaire}
                validation={{required: 'Ce champ est requis'}}
            />
        </FormGroup>
        <FormGroup>
            <FormInput
                label='Nom'
                validation={{required: 'Ce champ est requis'}}
                name='nom'
                register={register}
                type='text'
                error={errors.nom}
            />
            <FormInput
                label='Fonction'
                validation={{required: 'Ce champ est requis'}}
                name='fonctionDeux'
                register={register}
                type='text'
                error={errors.fonctionDeux}
            />
        </FormGroup>
        <FormGroup>
            <FormInput
                label='Signature'
                validation={{required: 'Ce champ est requis'}}
                name={'monitorSignature'}
                register={register}
                type='text'
                error={errors.monitorSignature}
                placeholder='Signature'
            />
            <FormInput
                label='Date de la signature'
                validation={{required: 'Ce champ est requis'}}
                name='dateSignature'
                register={register}
                type='date'
                error={errors.dateSignature}
            />
        </FormGroup>
    </div>
}
