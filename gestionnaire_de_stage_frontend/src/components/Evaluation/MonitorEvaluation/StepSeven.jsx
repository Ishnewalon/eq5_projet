import {FormGroup} from "../../SharedComponents/Form/FormGroup";
import {FieldInput, FieldRadio, FieldTextarea} from "../../SharedComponents/Form/FormFields";
import {Column} from "../../SharedComponents/Column";
import {regexMatriculeEtudiant, regexName} from "../../../utility";

export default function StepSeven({register, errors, choices}) {
    return <div className='px-3 pb-3 pt-1 rounded'>
        <FormGroup>
            <Column>
                <FieldRadio
                    name='companyAppreciateStudent'
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
                    name='commentsStudentTraining'
                    register={register}
                    placeholder='Est-ce que la formation était suffisante'
                    error={errors.commentsStudentTraining}
                    validation={{required: 'Ce champ est requis'}}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{lg: 6}}>
                <FieldInput
                    label='Nom'
                    validation={{required: 'Ce champ est requis'}}
                    name='name'
                    register={register}
                    type='text'
                    error={errors.name}
                    placeholder={'Nom'}
                />
            </Column>
            <Column col={{lg: 6}}>
                <FieldInput
                    label='Fonction'
                    validation={{required: 'Ce champ est requis'}}
                    name='secondFunction'
                    register={register}
                    type='text'
                    error={errors.secondFunction}
                    placeholder={'Ex: Directeur de la formation'}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column>
                <FieldInput
                    label='Signature'
                    validation={{
                        required: 'Ce champ est requis',
                        pattern: {
                            value: regexName,
                            message: "La signature doit contenir seulement des lettres!"
                        }}
                    }
                    name='monitorSignature'
                    register={register}
                    type='text'
                    error={errors.monitorSignature}
                />
            </Column>
        </FormGroup>
    </div>
}
