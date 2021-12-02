import {regexEmail, regexName, regexPhone} from "../../../utility";
import {FormGroup} from "../../SharedComponents/Form/FormGroup";
import {FieldInput} from "../../SharedComponents/Form/FormFields";
import {Column} from "../../SharedComponents/Column";
import {checkEmail} from "../../../services/user-service";

export default function StepOne({errors, register}) {

    return <div className='px-3 pb-3 pt-1'>
        <h3>Informations générales</h3>
        <FormGroup>
            <Column col={{lg: 6}}>
                <FieldInput
                    label="Email de l'élève"
                    validation={
                        {
                            required: 'Ce champ est requis',
                            pattern: {
                                value: regexEmail,
                                message: 'Veuillez entrer une adresse email valide'
                            },
                            validate: async (emailStudent) => !await checkEmail(emailStudent) || 'Aucun étudiant avec cette adresse email existe!'
                        }
                    }
                    name='studentEmail'
                    register={register}
                    type='email'
                    error={errors.studentEmail}
                />
            </Column>
            <Column col={{lg: 6}}>
                <FieldInput
                    label="Nom de l'élève"
                    validation={
                        {
                            required: 'Ce champ est requis',
                            pattern: {
                                value: regexName,
                                message: 'Veuillez entrer un nom avec seulement des lettre et des -'
                            }
                        }
                    }
                    name='internName'
                    register={register}
                    type='text'
                    error={errors.internName}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{md: 6, lg: 3}}>
                <FieldInput
                    label="Programme d'études"
                    validation={{required: 'Ce champ est requis'}}
                    name='programStudyField'
                    register={register}
                    type='text'
                    error={errors.programStudyField}
                />
            </Column>
            <Column col={{md: 6, lg: 3}}>
                <FieldInput
                    label="Nom de l'entreprise"
                    validation={{required: 'Ce champ est requis'}}
                    name='companyName'
                    register={register}
                    type='text'
                    error={errors.companyName}
                />
            </Column>
            <Column col={{md: 6, lg: 3}}>
                <FieldInput
                    label="Fonction"
                    validation={{required: 'Ce champ est requis'}}
                    name='firstFonction'
                    register={register}
                    type='text'
                    error={errors.firstFonction}
                />
            </Column>
            <Column col={{md: 6, lg: 3}}>
                <FieldInput
                    label="Téléphone"
                    validation={
                        {
                            required: 'Ce champ est requis',
                            pattern: {
                                value: regexPhone,
                                message: 'Le téléphone doit avoir le format suivant : 555-444-3333'
                            }
                        }
                    }
                    name='phone'
                    register={register}
                    type='tel'
                    error={errors.phone}
                />
            </Column>
        </FormGroup>
    </div>

}
