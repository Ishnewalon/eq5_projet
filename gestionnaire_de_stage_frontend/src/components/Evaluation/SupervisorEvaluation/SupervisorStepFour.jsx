import {FieldRadio} from "../../SharedComponents/FormInput/FieldRadio";
import {FormInput} from "../../SharedComponents/FormInput/FormInput";
import {Column, FormGroup} from "../../SharedComponents/FormGroup/FormGroup";

export default function SupervisorStepFour({
                                               watch,
                                               register,
                                               choixStage,
                                               choixStagiaires,
                                               yesAndNoAnswers,
                                               errors
                                           }) {
    const mondayShift = watch("questionQuatorzeHeuresUnA", "");

    const tuesdayShift = watch("questionQuatorzeHeuresUnC", "");

    const wednesdayShift = watch("questionQuatorzeHeuresUnE", "");

    const thursdayShift = watch("questionQuatorzeHeuresUnG", "");

    const fridayShift = watch("questionQuatorzeHeuresUnI", "");

    const saturdayShift = watch("questionQuatorzeHeuresUnK", "");

    const sundayShift = watch("questionQuatorzeHeuresUnM", "");


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
                    name='questionOnze'
                    register={register}
                    list={Object.values(choixStage)}
                    label="Ce milieu est à privilégier pour le stage"
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionDouze'
                    register={register}
                    list={Object.values(choixStagiaires)}
                    label="Ce milieu est ouvert à accueillir deux stagiaires"
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionTreize'
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
                    name='questionQuatorze'
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
                    <FormInput
                        label='De'
                        validation={{}}
                        name='questionQuatorzeHeuresUnA'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnA}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, mondayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnB'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnB}
                    />
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Mardi'/>
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='De'
                        validation={{}}
                        name='questionQuatorzeHeuresUnC'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnC}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, tuesdayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnD'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnD}
                    />
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Mercredi'/>
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='De'
                        validation={{}}
                        name='questionQuatorzeHeuresUnE'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnE}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, wednesdayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnF'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnF}
                    />
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Jeudi'/>
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='De'
                        validation={{}}
                        name='questionQuatorzeHeuresUnG'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnG}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, thursdayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnH'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnH}
                    />
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Vendredi'/>
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='De'
                        validation={{}}
                        name='questionQuatorzeHeuresUnI'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnI}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, fridayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnJ'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnJ}
                    />
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Samedi'/>
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='De'
                        validation={{}}
                        name='questionQuatorzeHeuresUnK'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnK}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, saturdayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnL'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnL}
                    />
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 2}} className="form-floating">
                    <label/>
                    <input className="form-control" type="text" disabled value='Dimanche'/>
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='De'
                        validation={{}}
                        name='questionQuatorzeHeuresUnM'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnM}
                    />
                </Column>
                <Column col={{md: 5}}>
                    <FormInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, sundayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnN'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnN}
                    />
                </Column>
            </FormGroup>
        </div>
    </div>
}
