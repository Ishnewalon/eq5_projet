import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FieldRadio} from "../../SharedComponents/FormInput/FieldRadio";
import {FormInput} from "../../SharedComponents/FormInput/FormInput";
import {FormField} from "../../SharedComponents/FormField/FormField";

export default function SupervisorStepFour({
                                               watch,
                                               register,
                                               choixStage,
                                               choixStagiaires,
                                               yesAndNoAnswers,
                                               errors
                                           }) {
    const isShiftFlexible = watch('questionShiftsFlexible', "");

    const mondayShift = watch("mondayShiftStart", "");

    const tuesdayShift = watch("tuesdayShiftStart", "");

    const wednesdayShift = watch("wednesdayStart", "");

    const thursdayShift = watch("thursdayStart", "");

    const fridayShift = watch("fridayStart", "");

    const saturdayShift = watch("saturdayStart", "");

    const sundayShift = watch("sundayStart", "");


    const checkIfLowerThan = (val, previous) => {
        if (!val || !previous)
            return true;

        let nextTime = val.split(":").map(n => parseInt(n));
        let previousTime = previous.split(":").map(n => parseInt(n));

        if (nextTime[0] > previousTime[0])
            return true;
        else if (nextTime[0] === previousTime[0])
            if (nextTime[1] > previousTime[1])
                return true;
    }

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h4 className='fw-bold p-2 rounded mt-4 mb-0 text-decoration-underline'>Observations générales</h4>
        <FormGroup>
            <FieldRadio
                name='questionPreferForInternship'
                register={register}
                list={Object.values(choixStage)}
                label="Ce milieu est à privilégier pour le stage"
            />
            <FieldRadio
                name='questionWelcomeMoreThanTwoIntern'
                register={register}
                list={Object.values(choixStagiaires)}
                label="Ce milieu est ouvert à accueillir deux stagiaires"
            />
            <FieldRadio
                name='questionWelcomeSameIntern'
                register={register}
                list={Object.values(yesAndNoAnswers)}
                label="Ce milieu désire accueillir le même stagiaire pour un prochain stage"
            />
        </FormGroup>
        <FormGroup repartition={[12, 12]}>
            <h5>Ce milieu offre des quarts de travail variables?</h5>
            <FieldRadio
                name='questionShiftsFlexible'
                register={register}
                list={Object.values(yesAndNoAnswers)}
                label=''
            />
        </FormGroup>
        <div>
            <FormGroup repartition={[2, 5, 5]}>
                <FormField>
                    <label/>
                    <input type="text" disabled value='Lundi'/>
                </FormField>
                <FormInput
                    label='De'
                    validation={{}}
                    name='mondayShiftStart'
                    register={register}
                    type='time'
                    placeholder='Début du quart'
                    error={errors.mondayShiftStart}
                />
                <FormInput
                    label='À'
                    validation={{validate: val => checkIfLowerThan(val, mondayShift) || 'La fin du quart doit être après le début'}}
                    name='mondayShiftEnd'
                    register={register}
                    type='time'
                    placeholder='Fin du quart'
                    error={errors.mondayShiftEnd}
                />
            </FormGroup>
            <FormGroup repartition={[2, 5, 5]}>
                <FormField>
                    <label/>
                    <input type="text" disabled value='Mardi'/>
                </FormField>
                <FormInput
                    label='De'
                    validation={{}}
                    name='tuesdayShiftStart'
                    register={register}
                    type='time'
                    placeholder='Début du quart'
                    error={errors.tuesdayShiftStart}
                />
                <FormInput
                    label='À'
                    validation={{validate: val => checkIfLowerThan(val, tuesdayShift) || 'La fin du quart doit être après le début'}}
                    name='tuesdayShiftEnd'
                    register={register}
                    type='time'
                    placeholder='Fin du quart'
                    error={errors.tuesdayShiftEnd}
                />
            </FormGroup>
            <FormGroup repartition={[2, 5, 5]}>
                <FormField>
                    <label/>
                    <input type="text" disabled value='Mercredi'/>
                </FormField>
                <FormInput
                    label='De'
                    validation={{}}
                    name='wednesdayShiftStart'
                    register={register}
                    type='time'
                    placeholder='Début du quart'
                    error={errors.wednesdayShiftStart}
                />
                <FormInput
                    label='À'
                    validation={{validate: val => checkIfLowerThan(val, wednesdayShift) || 'La fin du quart doit être après le début'}}
                    name='wednesdayShiftEnd'
                    register={register}
                    type='time'
                    placeholder='Fin du quart'
                    error={errors.wednesdayShiftEnd}
                />
            </FormGroup>
            <FormGroup repartition={[2, 5, 5]}>
                <FormField>
                    <label/>
                    <input type="text" disabled value='Jeudi'/>
                </FormField>
                <FormInput
                    label='De'
                    validation={{}}
                    name='thursdayShiftStart'
                    register={register}
                    type='time'
                    placeholder='Début du quart'
                    error={errors.thursdayShiftStart}
                />
                <FormInput
                    label='À'
                    validation={{validate: val => checkIfLowerThan(val, thursdayShift) || 'La fin du quart doit être après le début'}}
                    name='thursdayShiftEnd'
                    register={register}
                    type='time'
                    placeholder='Fin du quart'
                    error={errors.thursdayShiftEnd}
                />
            </FormGroup>
            <FormGroup repartition={[2, 5, 5]}>
                <FormField>
                    <label/>
                    <input type="text" disabled value='Vendredi'/>
                </FormField>
                <FormInput
                    label='De'
                    validation={{}}
                    name='fridayShiftStart'
                    register={register}
                    type='time'
                    placeholder='Début du quart'
                    error={errors.fridayShiftStart}
                />
                <FormInput
                    label='À'
                    validation={{validate: val => checkIfLowerThan(val, fridayShift) || 'La fin du quart doit être après le début'}}
                    name='fridayShiftEnd'
                    register={register}
                    type='time'
                    placeholder='Fin du quart'
                    error={errors.fridayShiftEnd}
                />
            </FormGroup>
            <FormGroup repartition={[2, 5, 5]}>
                <FormField>
                    <label/>
                    <input type="text" disabled value='Samedi'/>
                </FormField>
                <FormInput
                    label='De'
                    validation={{}}
                    name='saturdayShiftStart'
                    register={register}
                    type='time'
                    placeholder='Début du quart'
                    error={errors.saturdayShiftStart}
                />
                <FormInput
                    label='À'
                    validation={{validate: val => checkIfLowerThan(val, saturdayShift) || 'La fin du quart doit être après le début'}}
                    name='saturdayShiftEnd'
                    register={register}
                    type='time'
                    placeholder='Fin du quart'
                    error={errors.saturdayShiftEnd}
                />
            </FormGroup>
            <FormGroup repartition={[2, 5, 5]}>
                <FormField>
                    <label/>
                    <input type="text" disabled value='Dimanche'/>
                </FormField>
                <FormInput
                    label='De'
                    validation={{}}
                    name='sundayShiftStart'
                    register={register}
                    type='time'
                    placeholder='Début du quart'
                    error={errors.sundayShiftStart}
                />
                <FormInput
                    label='À'
                    validation={{validate: val => checkIfLowerThan(val, sundayShift) || 'La fin du quart doit être après le début'}}
                    name='sundayShiftEnd'
                    register={register}
                    type='time'
                    placeholder='Fin du quart'
                    error={errors.sundayShiftEnd}
                />
            </FormGroup>
        </div>
    </div>
}
