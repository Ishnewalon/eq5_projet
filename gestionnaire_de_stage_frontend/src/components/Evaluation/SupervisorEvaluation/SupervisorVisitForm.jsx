import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {supervisorCreateForm} from "../../../services/stage-service";
import {useForm} from "react-hook-form";
import {FormInput} from "../../SharedComponents/FormInput/FormInput";
import {regexCodePostal, regexMatriculeEtudiant, regexPhone} from "../../../utility";
import {FieldRadio} from "../../SharedComponents/FormInput/FieldRadio";
import {FormTextarea} from "../../SharedComponents/FormTextarea";
import {useRef} from "react";


export default function SupervisorVisitForm() {

    const {formState: {errors}, handleSubmit, register, watch} = useForm({
        mode: "onSubmit",
        reValidateMode: "onChange"
    });

    const choixAccords = {
        TOTALEMENT_EN_ACCORD: ["TOTALEMENT_EN_ACCORD", "Totalement en accord"],
        PLUTOT_EN_ACCORD: ['PLUTOT_EN_ACCORD', 'Plûtot en accord'],
        PLUTOT_EN_DESACCORD: ['PLUTOT_EN_DESACCORD', 'Plûtot en désaccord'],
        TOTALEMENT_EN_DESACCORD: ['TOTALEMENT_EN_DESACCORD', 'Totalement en désaccord'],
        IMPOSSIBLE_DE_SE_PRONONCER: ['IMPOSSIBLE_DE_SE_PRONONCER', 'Impossible de se prononcer']
    }

    const choixStagiaires = {
        UN_STAGIAIRE: ['Un stagiaire', 'Un stagiaire'],
        DEUX_STAGIAIRES: ['Deux stagiaires', 'Deux stagiaires'],
        TROIS_STAGIAIRES: ['Trois stagiaires', 'Trois stagiaires'],
        PLUS_DE_TROIS_STAGIAIRES: ['Plus de trois stagiaires', 'Plus de trois stagiaires'],
    }

    const choixStage = {
        UN_STAGIAIRE: ['Premier stage', 'Premier stage'],
        DEUX_STAGIAIRES: ['Deuxième stage', 'Deuxième stage']
    }

    const yesAndNoAnswers = {
        OUI: ['true', 'Oui'],
        NON: ['false', 'Non']
    }

    const oneOrTwo = {
        ONE: ['1', 1],
        TWO: ['2', 2]
    }

    const daysOfWeek = {
        LUNDI: ['Lundi', "Lundi"],
        MARDI: ['Mardi', "Mardi"],
        MERCREDI: ['Mercredi', "Mercredi"],
        JEUDI: ['Jeudi', "Jeudi"],
        VENDREDI: ['Vendredi', "Vendredi"],
        SAMEDI: ['Samedi', "Samedi"],
        DIMANCHE: ['Dimanche', "Dimanche"],
    };


    const sendEvaluation = (data, e) => {
        e.preventDefault();
        supervisorCreateForm(data).then();
    };

    const startTimeThree = useRef({});
    startTimeThree.current = watch("questionQuatorzeHeuresUnC");

    function checkIfLowerThan(value, startTimeRef) {
        let date = new Date(value);
        let endDate = new Date(startTimeRef);

        return date.getTime() < endDate.getTime();
    }


    return <div>
        <form action="" onSubmit={handleSubmit(sendEvaluation)}>
            <h1 className='text-center text-decoration-underline'>Évaluation de stage</h1>
            <div className='px-3 pb-3 pt-1 rounded'>
                <h4 className='mt-4 mb-0 text-decoration-underline'>Identification de l'entreprise</h4>
                <FormGroup>
                    <FormInput
                        label='Matricule Étudiant'
                        validation={{
                            required: 'Ce champ est requis',
                            pattern: {
                                value: regexMatriculeEtudiant,
                                message: "La matricule de l'étudiant n'est pas valide!"
                            }
                        }}
                        name='matriculeEtudiant'
                        register={register}
                        type='number'
                        placeholder="La matricule de l'étudiant (7 chiffres)"
                        error={errors.matriculeEtudiant}
                    />
                </FormGroup>
                <FormGroup>
                    <FormInput
                        label="Nom de l'entreprise"
                        validation={{required: 'Ce champ est requis'}}
                        name='entrepriseNom'
                        register={register}
                        type='text'
                        error={errors.entrepriseNom}
                        placeholder="Nom de l'entreprise"
                    />
                    <FormInput
                        label='Personne contact'
                        validation={{required: 'Ce champ est requis'}}
                        name='personneContact'
                        register={register}
                        type='text'
                        placeholder='Persone contact'
                        error={errors.personneContact}
                    />
                </FormGroup>
                <FormGroup>
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
                    <FormInput
                        label='Télécopieur'
                        validation={{required: 'Ce champ est requis'}}
                        name='telecopieur'
                        register={register}
                        type='text'
                        placeholder='Télécopieur'
                        error={errors.telecopieur}
                    />
                </FormGroup>
                <FormGroup>
                    <FormInput
                        label='Adresse'
                        validation={{required: 'Ce champ est requis'}}
                        name='adresse'
                        register={register}
                        type='text'
                        placeholder='Adresse'
                        error={errors.adresse}
                    />
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
                    <FormInput
                        label='Ville'
                        validation={{required: 'Ce champ est} requis'}}
                        name='ville'
                        register={register}
                        type='text'
                        error={errors.ville}
                        placeholder='Ville'
                    />
                </FormGroup>
            </div>
            <hr/>
            <div className='px-3 pb-3 pt-1 rounded'>
                <h4 className='mt-4 mb-0 text-decoration-underline'>Identification du stagiaire</h4>
                <FormGroup>
                    <FormInput
                        label='Nom du stagiaire'
                        validation={{required: 'Ce champ est requis'}}
                        name='nomStagiaire'
                        register={register}
                        type='text'
                        placeholder="Entrez le nom du stagiare"
                        error={errors.nomStagiaire}
                    />
                    <FormInput
                        label='Date du stage'
                        validation={{required: 'Ce champ est requis'}}
                        name='dateStage'
                        register={register}
                        type='date'
                        error={errors.dateStage}
                        placeholder='Date du stage'
                    />
                    <FieldRadio
                        name='stageCourant'
                        register={register}
                        list={Object.values(oneOrTwo)}
                        label='Stage'
                    />
                </FormGroup>
            </div>
            <hr/>
            <div className='px-3 pb-3 pt-1 rounded'>
                <h4 className='fw-bold p-2 rounded mt-4 mb-0 text-decoration-underline'>Évaluation</h4>
                <FormGroup>
                    <FieldRadio
                        name='questionUn'
                        register={register}
                        list={Object.values(choixAccords)}
                        label='Les tâches confiées au stagiaire sont conformes aux tâches annoncées dans l’entente de
                            stage?'
                    />
                    <FieldRadio
                        name='questionDeux'
                        register={register}
                        list={Object.values(choixAccords)}
                        label='Des mesures d’accueil facilitent l’intégration du nouveau stagiaire?'
                    />
                    <FieldRadio
                        name='questionTrois'
                        register={register}
                        list={Object.values(choixAccords)}
                        label='Le temps réel consacré à l’encadrement du stagiaire est suffisant?'
                    />
                </FormGroup>
                <FormGroup>
                    <FormInput
                        label="Nombre d'heures pour le 1er mois?"
                        validation={{
                            required: 'Ce champ est requis', min: {
                                value: 0,
                                message: 'Le nombre d\'heures doit être supérieur ou égale à 0'
                            }
                        }}
                        name='nbHeuresMoisUn'
                        register={register}
                        type='number'
                        error={errors.nbHeuresMoisUn}
                        placeholder="1er mois"
                    />
                    <FormInput
                        label="Nombre d'heures pour le 2ième mois?"
                        validation={{
                            required: 'Ce champ est requis', min: {
                                value: 0,
                                message: 'Le nombre d\'heures doit être supérieur ou égale à 0'
                            }
                        }}
                        name='nbHeuresMoisDeux'
                        register={register}
                        type='number'
                        placeholder='2ième mois'
                        error={errors.nbHeuresMoisDeux}
                    />
                    <FormInput
                        label="Nombre d'heures pour le 3ième mois?"
                        validation={{
                            required: 'Ce champ est requis', min: {
                                value: 0,
                                message: 'Le nombre d\'heures doit être supérieur ou égale à 0'
                            }
                        }}
                        name='nbHeuresMoisTrois'
                        register={register}
                        type='number'
                        error={errors.nbHeuresMoisTrois}
                        placeholder="3ième mois"
                    />
                </FormGroup>
                <FormGroup>
                    <FieldRadio
                        name='questionQuatre'
                        register={register}
                        list={Object.values(choixAccords)}
                        label="L'environnement de travail respecte les normes d’hygiène et de sécurité au
                            travail?"
                    />
                    <FieldRadio
                        name='questionCinq'
                        register={register}
                        list={Object.values(choixAccords)}
                        label="Le climat de travail est agréable?"
                    />
                    <FieldRadio
                        name='questionSix'
                        register={register}
                        list={Object.values(choixAccords)}
                        label="Le milieu de stage est accessible par transport en commun?"
                    />
                </FormGroup>
                <FormGroup>
                    <FieldRadio
                        name='questionSept'
                        register={register}
                        list={Object.values(choixAccords)}
                        label="Le salaire offert est intéressant pour le stagiaire?"
                    />
                    <FormInput
                        validation={{
                            required: 'Ce champ est requis', min: {
                                value: 0,
                                message: 'Le salaire doit être supérieur ou égale à 0'
                            }
                        }}
                        register={register}
                        type='number'
                        name='salaireStagiaire'
                        error={errors.salaireStagiaire}
                        placeholder='Salaire par heure'
                        label='Préciser le salaire par heure'
                    />
                </FormGroup>
                <FormGroup>
                    <FieldRadio
                        name='questionHuit'
                        register={register}
                        list={Object.values(choixAccords)}
                        label="La communication avec le superviseur de stage facilite le déroulement du stage?"
                    />
                    <FieldRadio
                        name='questionNeuf'
                        register={register}
                        list={Object.values(choixAccords)}
                        label="L’équipement fourni est adéquat pour réaliser les tâches confiées?"
                    />
                    <FieldRadio
                        name='questionDix'
                        register={register}
                        list={Object.values(choixAccords)}
                        label="Le volume de travail est acceptable?"
                    />
                </FormGroup>
                <FormGroup>
                    <FormTextarea
                        label='Commentaires'
                        name='commentaires'
                        register={register}
                    />
                </FormGroup>
            </div>
            <hr/>
            <div className='px-3 pb-3 pt-1 rounded'>
                <h4 className='fw-bold p-2 rounded mt-4 mb-0 text-decoration-underline'>Observations générales</h4>
                <FormGroup>
                    <FieldRadio
                        name='questionOnze'
                        register={register}
                        list={Object.values(choixStage)}
                        label="Ce milieu est à privilégier pour le stage"
                    />
                    <FieldRadio
                        name='questionDouze'
                        register={register}
                        list={Object.values(choixStagiaires)}
                        label="Ce milieu est ouvert à accueillir deux stagiaires"
                    />
                    <FieldRadio
                        name='questionTreize'
                        register={register}
                        list={Object.values(yesAndNoAnswers)}
                        label="Ce milieu désire accueillir le même stagiaire pour un prochain stage"
                    />
                </FormGroup>
                <FormGroup repartition={[12, 12]}>
                    <h5>Ce milieu offre des quarts de travail variables?</h5>
                    <FieldRadio
                        name='questionQuatorze'
                        register={register}
                        list={Object.values(yesAndNoAnswers)}
                        label=''
                    />
                </FormGroup>
                <FormGroup>
                    <FormInput
                        label='De'
                        validation={{required: 'Ce champ est requis'}}
                        name='questionQuatorzeHeuresUnA'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnA}
                    />
                    <FormInput
                        label='À'
                        validation={{required: 'Ce champ est requis'}}
                        name='questionQuatorzeHeuresUnB'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnB}
                    />
                </FormGroup>
                <FormGroup>

                    <FormInput
                        label='De'
                        validation={{required: 'Ce champ est requis'}}
                        name='questionQuatorzeHeuresUnC'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnC}
                    />
                    <FormInput
                        label='À'
                        validation={{
                            required: 'Ce champ est requis',
                            validate: (value) => checkIfLowerThan(value, startTimeThree.current)
                        }}
                        name='questionQuatorzeHeuresUnD'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnD}
                    />
                </FormGroup>
                <FormGroup>
                    <FormInput
                        label='De'
                        validation={{required: 'Ce champ est requis'}}
                        name='questionQuatorzeHeuresUnE'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnE}
                    />
                    <FormInput
                        label='À'
                        validation={{required: 'Ce champ est requis', validate: value => checkIfLowerThan(value, startTimeThree.start) || 'Le temps doit être supérieur' }}
                        name='questionQuatorzeHeuresUnF'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnF}
                    />
                </FormGroup>
                <FormGroup>
                    <FormInput
                        label="Signature de l'enseignant responsable du stagiaire"
                        validation={{required: 'Ce champ est requis'}}
                        name='signatureSuperviseur'
                        register={register}
                        type='text'
                        placeholder='Signature du superviseur'
                        error={errors.signatureSuperviseur}
                    />
                </FormGroup>
            </div>
            <button type='submit' className='btn btn-primary w-100 mt-4'>Créer un formulaire de visite</button>
        </form>
    </div>
}
