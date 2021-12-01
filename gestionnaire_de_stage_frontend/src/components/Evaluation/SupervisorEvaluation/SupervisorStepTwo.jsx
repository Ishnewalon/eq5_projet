import {FormGroup} from "../../SharedComponents/Form/FormGroup";
import {FieldInput, FieldRadio} from "../../SharedComponents/Form/FormFields";
import {Column} from "../../SharedComponents/Column";

export default function SupervisorStepTwo({register, errors, oneOrTwo}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h4 className='mt-4 mb-0 text-decoration-underline'>Identification du stagiaire</h4>
        <FormGroup>
            <Column col={{md: 6, lg: 4}}>
                <FieldInput
                    label='Nom du stagiaire'
                    validation={{required: 'Ce champ est requis'}}
                    name='internName'
                    register={register}
                    type='text'
                    error={errors.internName}
                />
            </Column>
            <Column col={{md: 6, lg: 4}}>
                <FieldInput
                    label='Date du stage'
                    validation={{required: 'Ce champ est requis'}}
                    name='dateStage'
                    register={register}
                    type='date'
                    error={errors.dateStage}
                />
            </Column>
            <Column col={{md: 6, lg: 4}}>
                <FieldRadio
                    name='currentInternship'
                    register={register}
                    list={Object.values(oneOrTwo)}
                    label='Stage'
                />
            </Column>
        </FormGroup>
    </div>
}
