import {FormGroup} from "../../SharedComponents/Form/FormGroup";
import {FieldInput, FieldRadio, FieldTextarea} from "../../SharedComponents/Form/FormFields";
import {Column} from "../../SharedComponents/Column";

export default function SupervisorStepThree({register, choixAccords, errors}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h4 className='fw-bold p-2 rounded mt-4 mb-0 text-decoration-underline'>Évaluation</h4>
        <FormGroup>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionTaskGivenWereInContract'
                    register={register}
                    list={Object.values(choixAccords)}
                    label='Les tâches confiées au stagiaire sont conformes aux tâches annoncées dans l’entente de
                            stage?'
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionInternshipEasyToIntegrateInterns'
                    register={register}
                    list={Object.values(choixAccords)}
                    label='Des mesures d’accueil facilitent l’intégration du nouveau stagiaire?'
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionWasEnoughTimeToTrainIntern'
                    register={register}
                    list={Object.values(choixAccords)}
                    label='Le temps réel consacré à l’encadrement du stagiaire est suffisant?'
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{lg: 4}}>
                <FieldInput
                    label="Nombre d'heures pour le 1er mois?"
                    validation={{
                        required: 'Ce champ est requis', min: {
                            value: 0,
                            message: 'Le nombre d\'heures doit être supérieur ou égale à 0'
                        }
                    }}
                    name='nbHoursMonthOne'
                    register={register}
                    type='number'
                    error={errors.nbHoursMonthOne}
                    placeholder="1er mois"
                />
            </Column>
            <Column col={{lg: 4}}>
                <FieldInput
                    label="Nombre d'heures pour le 2ième mois?"
                    validation={{
                        required: 'Ce champ est requis', min: {
                            value: 0,
                            message: 'Le nombre d\'heures doit être supérieur ou égale à 0'
                        }
                    }}
                    name='nbHoursMonthTwo'
                    register={register}
                    type='number'
                    placeholder='2ième mois'
                    error={errors.nbHoursMonthTwo}
                />
            </Column>
            <Column col={{lg: 4}}>
                <FieldInput
                    label="Nombre d'heures pour le 3ième mois?"
                    validation={{
                        required: 'Ce champ est requis', min: {
                            value: 0,
                            message: 'Le nombre d\'heures doit être supérieur ou égale à 0'
                        }
                    }}
                    name='nbHoursMonthThree'
                    register={register}
                    type='number'
                    error={errors.nbHoursMonthThree}
                    placeholder="3ième mois"
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionHygiene'
                    register={register}
                    list={Object.values(choixAccords)}
                    label="L'environnement de travail respecte les normes d’hygiène et de sécurité au
                            travail?"
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionWorkEnvironmentNice'
                    register={register}
                    list={Object.values(choixAccords)}
                    label="Le climat de travail est agréable?"
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionWorkAccesibleByMetro'
                    register={register}
                    list={Object.values(choixAccords)}
                    label="Le milieu de stage est accessible par transport en commun?"
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionSalaryInterestingForIntern'
                    register={register}
                    list={Object.values(choixAccords)}
                    label="Le salaire offert est intéressant pour le stagiaire?"
                />
            </Column>
            <Column col={{md: 8}}>
                <FieldInput
                    validation={{
                        required: 'Ce champ est requis', min: {
                            value: 0,
                            message: 'Le salaire doit être supérieur ou égale à 0'
                        }
                    }}
                    register={register}
                    type='number'
                    name='salaryIntern'
                    error={errors.salaryIntern}
                    placeholder='Salaire par heure'
                    label='Préciser le salaire par heure'
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionSupervisorFacilitateInternship'
                    register={register}
                    list={Object.values(choixAccords)}
                    label="La communication avec le superviseur de stage facilite le déroulement du stage?"
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionTaskGivenWereAdequate'
                    register={register}
                    list={Object.values(choixAccords)}
                    label="L’équipement fourni est adéquat pour réaliser les tâches confiées?"
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionWorkAcceptable'
                    register={register}
                    list={Object.values(choixAccords)}
                    label="Le volume de travail est acceptable?"
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column>
                <FieldTextarea
                    label='Commentaires'
                    name='comments'
                    register={register}
                />
            </Column>
        </FormGroup>
    </div>
}
