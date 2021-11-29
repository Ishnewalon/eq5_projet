import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FieldRadio} from "../../SharedComponents/FormInput/FieldRadio";
import {FormInput} from "../../SharedComponents/FormInput/FormInput";
import {FormTextarea} from "../../SharedComponents/FormTextarea";

export default function SupervisorStepThree({register, choixAccords, errors}){

    return <div className='px-3 pb-3 pt-1 rounded'>
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
}
