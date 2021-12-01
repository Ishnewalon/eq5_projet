import {FormGroup} from "../../SharedComponents/Form/FormGroup";
import {FieldInput, FieldRadio} from "../../SharedComponents/Form/FormFields";
import {Column} from "../../SharedComponents/Column";
import {regexName} from "../../../utility";

export default function SupervisorStepFour({
                                               watch,
                                               register,
                                               choixStage,
                                               choixStagiaires,
                                               yesAndNoAnswers,
                                               errors
                                           }) {
    const mondayShift = watch("mondayShiftStart", "");

    const tuesdayShift = watch("tuesdayShiftStart", "");

    const wednesdayShift = watch("wednesdayShiftStart", "");

    const thursdayShift = watch("thursdayShiftStart", "");

    const fridayShift = watch("fridayShiftStart", "");

    const saturdayShift = watch("saturdayShiftStart", "");

    const sundayShift = watch("sundayShiftStart", "");


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
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionPreferForInternship'
                    register={register}
                    list={Object.values(choixStage)}
                    label="Ce milieu est à privilégier pour le stage"
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionWelcomeMoreThanTwoIntern'
                    register={register}
                    list={Object.values(choixStagiaires)}
                    label="Ce milieu est ouvert à accueillir deux stagiaires"
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionWelcomeSameIntern'
                    register={register}
                    list={Object.values(yesAndNoAnswers)}
                    label="Ce milieu désire accueillir le même stagiaire pour un prochain stage"
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <h5>Ce milieu offre des quarts de travail variables?</h5>
            <Column>
                <FieldRadio
                    name='questionShiftsFlexible'
                    register={register}
                    list={Object.values(yesAndNoAnswers)}
                    label=''
                />
            </Column>
        </FormGroup>
        <div>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Lundi'/>
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='De'
                        validation={{}}
                        name='mondayShiftStart'
                        register={register}
                        type='time'
                        error={errors.mondayShiftStart}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, mondayShift) || 'La fin du quart doit être après le début'}}
                        name='mondayShiftEnd'
                        register={register}
                        type='time'
                        error={errors.mondayShiftEnd}
                    />
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Mardi'/>
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='De'
                        validation={{}}
                        name='tuesdayShiftStart'
                        register={register}
                        type='time'
                        error={errors.tuesdayShiftStart}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, tuesdayShift) || 'La fin du quart doit être après le début'}}
                        name='tuesdayShiftEnd'
                        register={register}
                        type='time'
                        error={errors.tuesdayShiftEnd}
                    />
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Mercredi'/>
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='De'
                        validation={{}}
                        name='wednesdayShiftStart'
                        register={register}
                        type='time'
                        error={errors.wednesdayShiftStart}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, wednesdayShift) || 'La fin du quart doit être après le début'}}
                        name='wednesdayShiftEnd'
                        register={register}
                        type='time'
                        error={errors.wednesdayShiftEnd}
                    />
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Jeudi'/>
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='De'
                        validation={{}}
                        name='thursdayShiftStart'
                        register={register}
                        type='time'
                        error={errors.thursdayShiftStart}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, thursdayShift) || 'La fin du quart doit être après le début'}}
                        name='thursdayShiftEnd'
                        register={register}
                        type='time'
                        error={errors.thursdayShiftEnd}
                    />
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Vendredi'/>
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='De'
                        validation={{}}
                        name='fridayShiftStart'
                        register={register}
                        type='time'
                        error={errors.fridayShiftStart}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, fridayShift) || 'La fin du quart doit être après le début'}}
                        name='fridayShiftEnd'
                        register={register}
                        type='time'
                        error={errors.fridayShiftEnd}
                    />
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Samedi'/>
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='De'
                        validation={{}}
                        name='saturdayShiftStart'
                        register={register}
                        type='time'
                        error={errors.saturdayShiftStart}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, saturdayShift) || 'La fin du quart doit être après le début'}}
                        name='saturdayShiftEnd'
                        register={register}
                        type='time'
                        error={errors.saturdayShiftEnd}
                    />
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Dimanche'/>
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='De'
                        validation={{}}
                        name='sundayShiftStart'
                        register={register}
                        type='time'
                        error={errors.sundayShiftStart}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FieldInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, sundayShift) || 'La fin du quart doit être après le début'}}
                        name='sundayShiftEnd'
                        register={register}
                        type='time'
                        error={errors.sundayShiftEnd}
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
                        name='supervisorSignature'
                        register={register}
                        type='text'
                        error={errors.supervisorSignature}
                    />
                </Column>
            </FormGroup>
        </div>
    </div>
}
