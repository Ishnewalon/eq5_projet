import {FormInput} from "../../SharedComponents/FormInput/FormInput";
import {FieldRadio} from "../../SharedComponents/FormInput/FieldRadio";
import {Column, FormGroup} from "../../SharedComponents/FormGroup/FormGroup";

export default function SupervisorStepTwo({register, errors, oneOrTwo}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h4 className='mt-4 mb-0 text-decoration-underline'>Identification du stagiaire</h4>
        <FormGroup>
            <Column col={{md: 6, lg: 4}}>
                <FormInput
                    label='Nom du stagiaire'
                    validation={{required: 'Ce champ est requis'}}
                    name='nomStagiaire'
                    register={register}
                    type='text'
                    placeholder="Entrez le nom du stagiare"
                    error={errors.nomStagiaire}
                />
            </Column>
            <Column col={{md: 6, lg: 4}}>
                <FormInput
                    label='Date du stage'
                    validation={{required: 'Ce champ est requis'}}
                    name='dateStage'
                    register={register}
                    type='date'
                    error={errors.dateStage}
                    placeholder='Date du stage'
                />
            </Column>
            <Column col={{md: 6, lg: 4}}>
                <FieldRadio
                    name='stageCourant'
                    register={register}
                    list={Object.values(oneOrTwo)}
                    label='Stage'
                />
            </Column>
        </FormGroup>
    </div>
}
