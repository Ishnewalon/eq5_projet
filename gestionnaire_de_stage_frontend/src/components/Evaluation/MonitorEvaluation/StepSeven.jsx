import {FormGroup} from "../../SharedComponents/Form/FormGroup";
import {FieldInput, FieldRadio, FieldTextarea} from "../../SharedComponents/Form/FormFields";
import {Column} from "../../SharedComponents/Column";

export default function StepSeven({register, errors, choices}) {
    return <div className='px-3 pb-3 pt-1 rounded'>
        <FormGroup>
            <Column>
                <FieldRadio
                    name='entrepriseApprecieEtudiant'
                    register={register}
                    list={Object.values(choices)}
                    label="L’entreprise aimerait accueillir cet élève pour son prochain stage"
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column>
                <FieldTextarea
                    label='La formation technique du stagiaire était-elle suffisante pour accomplir le mandat de
                stage?'
                    name='formationSuffisanteCommentaire'
                    register={register}
                    placeholder='Est-ce que la formation était suffisante'
                    error={errors.formationSuffisanteCommentaire}
                    validation={{required: 'Ce champ est requis'}}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{lg: 6}}>
                <FieldInput
                    label='Nom'
                    validation={{required: 'Ce champ est requis'}}
                    name='nom'
                    register={register}
                    type='text'
                    error={errors.nom}
                    placeholder={'Nom'}
                />
            </Column>
            <Column col={{lg: 6}}>
                <FieldInput
                    label='Fonction'
                    validation={{required: 'Ce champ est requis'}}
                    name='fonctionDeux'
                    register={register}
                    type='text'
                    error={errors.fonctionDeux}
                    placeholder={'Ex: Directeur de la formation'}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{lg: 6}}>
                <FieldInput
                    label='Signature'
                    validation={{required: 'Ce champ est requis'}}
                    name={'monitorSignature'}
                    register={register}
                    type='text'
                    error={errors.monitorSignature}
                    placeholder='Signature'
                />
            </Column>
            <Column col={{lg: 6}}>
                <FieldInput
                    label='Date de la signature'
                    validation={{required: 'Ce champ est requis'}}
                    name='dateSignature'
                    register={register}
                    type='date'
                    error={errors.dateSignature}
                />
            </Column>
        </FormGroup>
    </div>
}
