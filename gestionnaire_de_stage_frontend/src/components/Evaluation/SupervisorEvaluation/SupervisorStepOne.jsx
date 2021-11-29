import {FormInput} from "../../SharedComponents/FormInput/FormInput";
import {regexCodePostal, regexMatriculeEtudiant, regexPhone} from "../../../utility";
import {Column, FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {checkMatricule} from "../../../services/user-service";

export default function SupervisorStepOne({register, errors}) {
    return <div className='px-3 pb-3 pt-1 rounded'>
        <h4 className='mt-4 mb-0 text-decoration-underline'>Identification de l'entreprise</h4>
        <FormGroup>
            <Column>
                <FormInput
                    label='Matricule Étudiant'
                    validation={{
                        required: 'Ce champ est requis',
                        pattern: {
                            value: regexMatriculeEtudiant,
                            message: "La matricule de l'étudiant n'est pas valide!"
                        },
                        validate: async (matricule) => !await checkMatricule(matricule) || "Ce matricule étudiant n'existe pas!"
                    }}
                    name='matriculeEtudiant'
                    register={register}
                    type='number'
                    placeholder="La matricule de l'étudiant (7 chiffres)"
                    error={errors.matriculeEtudiant}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{lg: 6}}>
                <FormInput
                    label="Nom de l'entreprise"
                    validation={{required: 'Ce champ est requis'}}
                    name='entrepriseNom'
                    register={register}
                    type='text'
                    error={errors.entrepriseNom}
                    placeholder="Nom de l'entreprise"
                />
            </Column>
            <Column col={{lg: 6}}>
                <FormInput
                    label='Personne contact'
                    validation={{required: 'Ce champ est requis'}}
                    name='personneContact'
                    register={register}
                    type='text'
                    placeholder='Persone contact'
                    error={errors.personneContact}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{lg: 6}}>
                <FormInput
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
                    placeholder='Téléphone'
                    error={errors.phone}
                />
            </Column>
            <Column col={{lg: 6}}>
                <FormInput
                    label='Télécopieur'
                    validation={{required: 'Ce champ est requis'}}
                    name='telecopieur'
                    register={register}
                    type='text'
                    placeholder='Télécopieur'
                    error={errors.telecopieur}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{lg: 4}}>
                <FormInput
                    label='Adresse'
                    validation={{required: 'Ce champ est requis'}}
                    name='adresse'
                    register={register}
                    type='text'
                    placeholder='Adresse'
                    error={errors.adresse}
                />
            </Column>
            <Column col={{md: 6, lg: 4}}>
                <FormInput
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
                    placeholder='Code postal'
                    error={errors.zip}
                />
            </Column>
            <Column col={{md: 6, lg: 4}}>
                <FormInput
                    label='Ville'
                    validation={{required: 'Ce champ est} requis'}}
                    name='ville'
                    register={register}
                    type='text'
                    error={errors.ville}
                    placeholder='Ville'
                />
            </Column>
        </FormGroup>
    </div>
}
