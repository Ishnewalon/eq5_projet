import {regexCodePostal, regexMatriculeStudent, regexPhone} from "../../../utility";
import {FormGroup} from "../../SharedComponents/Form/FormGroup";
import {checkMatricule} from "../../../services/user-service";
import {FieldInput} from "../../SharedComponents/Form/FormFields";
import {Column} from "../../SharedComponents/Column";

export default function SupervisorStepOne({register, errors}) {
    return <div className='px-3 pb-3 pt-1 rounded'>
        <h4 className='mt-4 mb-0 text-decoration-underline'>Identification de l'entreprise</h4>
        <FormGroup>
            <Column>
                <FieldInput
                    label='Matricule Étudiant'
                    validation={{
                        required: 'Ce champ est requis',
                        pattern: {
                            value: regexMatriculeStudent,
                            message: "La matricule de l'étudiant n'est pas valide!"
                        },
                        validate: async (matricule) => !await checkMatricule(matricule) || "Ce matricule étudiant n'existe pas!"
                    }}
                    name='studentMatricule'
                    register={register}
                    type='number'
                    error={errors.studentMatricule}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{lg: 6}}>
                <FieldInput
                    label="Nom de l'entreprise"
                    validation={{required: 'Ce champ est requis'}}
                    name='companyName'
                    register={register}
                    type='text'
                    error={errors.companyName}
                />
            </Column>
            <Column col={{lg: 6}}>
                <FieldInput
                    label='Personne contact'
                    validation={{required: 'Ce champ est requis'}}
                    name='contactPerson'
                    register={register}
                    type='text'
                    error={errors.contactPerson}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{lg: 6}}>
                <FieldInput
                    label='Téléphone'
                    validation={{
                        required: 'Ce champ est requis',
                        pattern: {
                            value: regexPhone,
                            message: "Le numéro de téléphone doit avoir le format 123 456 7890"
                        }
                    }}
                    name='phone'
                    register={register}
                    type='tel'
                    error={errors.phone}
                />
            </Column>
            <Column col={{lg: 6}}>
                <FieldInput
                    label='Télécopieur'
                    validation={{required: 'Ce champ est requis'}}
                    name='fax'
                    register={register}
                    type='text'
                    error={errors.fax}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{lg: 4}}>
                <FieldInput
                    label='Adresse'
                    validation={{required: 'Ce champ est requis'}}
                    name='adresse'
                    register={register}
                    type='text'
                    error={errors.adresse}
                />
            </Column>
            <Column col={{md: 6, lg: 4}}>
                <FieldInput
                    label='Code postal'
                    validation={{
                        required: 'Ce champ est requis',
                        pattern: {
                            value: regexCodePostal,
                            message: "Le code postal doit avoir le format H0H 0H0!"
                        }
                    }}
                    name='zip'
                    register={register}
                    type='text'
                    error={errors.zip}
                />
            </Column>
            <Column col={{md: 6, lg: 4}}>
                <FieldInput
                    label='Ville'
                    validation={{required: 'Ce champ est requis'}}
                    name='city'
                    register={register}
                    type='text'
                    error={errors.city}
                />
            </Column>
        </FormGroup>
    </div>
}
