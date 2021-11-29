import {regexEmail, regexName, regexPhone} from "../../../utility";
import {FormInput} from "../../SharedComponents/FormInput/FormInput";
import {Column, FormGroup} from "../../SharedComponents/FormGroup/FormGroup";

export default function StepOne({errors, register}) {

    return <div className='px-3 pb-3 pt-1'>
        <h3>Informations générales</h3>
        <FormGroup>
            <Column col={{lg: 6}}>
                <FormInput
                    label="Email de l'élève"
                    validation={
                        {
                            required: 'Ce champ est requis',
                            pattern: {
                                value: regexEmail,
                                message: 'Veuillez entrer une adresse email valide'
                            }
                        }
                    }
                    name='emailEtudiant'
                    register={register}
                    type='email'
                    placeholder="Email de l'élève"
                    error={errors.emailEtudiant}
                />
            </Column>
            <Column col={{lg: 6}}>
                <FormInput
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
                    name='nomStagiaire'
                    register={register}
                    type='text'
                    placeholder="Entrez le nom de l'élève"
                    error={errors.nomStagiaire}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{md: 6, lg: 3}}>
                <FormInput
                    label="Programme d'études"
                    validation={{required: 'Ce champ est requis'}}
                    name='programmeEtudes'
                    register={register}
                    type='text'
                    placeholder="Entrez le programme d'études de l'élève"
                    error={errors.programmeEtudes}
                />
            </Column>
            <Column col={{md: 6, lg: 3}}>
                <FormInput
                    label="Nom de l'entreprise"
                    validation={{required: 'Ce champ est requis'}}
                    name='entrepriseNom'
                    register={register}
                    type='text'
                    placeholder="Entrez le nom de l'entreprise"
                    error={errors.entrepriseNom}
                />
            </Column>
            <Column col={{md: 6, lg: 3}}>
                <FormInput
                    label="Fonction"
                    validation={{required: 'Ce champ est requis'}}
                    name='fonctionUn'
                    register={register}
                    type='text'
                    placeholder="Entrez la fonction"
                    error={errors.fonctionUn}
                />
            </Column>
            <Column col={{md: 6, lg: 3}}>
                <FormInput
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
                    placeholder="Entrez votre numéro de téléphone"
                    error={errors.phone}
                />
            </Column>
        </FormGroup>
    </div>

}
