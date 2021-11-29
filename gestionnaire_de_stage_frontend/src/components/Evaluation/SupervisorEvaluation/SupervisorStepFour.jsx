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
    const mondayShift = watch("questionQuatorzeHeuresUnA", "");

    const tuesdayShift = watch("questionQuatorzeHeuresUnC", "");

    const wednesdayShift = watch("questionQuatorzeHeuresUnE", "");

    const thursdayShift = watch("questionQuatorzeHeuresUnG", "");

    const fridayShift = watch("questionQuatorzeHeuresUnI", "");

    const saturdayShift = watch("questionQuatorzeHeuresUnK", "");

    const sundayShift = watch("questionQuatorzeHeuresUnM", "");


    const checkIfLowerThan = (val, previous) => {
        if(!val || !previous)
            return true;

        let nextTime = val.split(":").map(n => parseInt(n));
        let previousTime = previous.split(":").map(n => parseInt(n));

        if (nextTime[0] > previousTime[0])
            return true;
        else if(nextTime[0] === previousTime[0])
            if(nextTime[1] > previousTime[1])
                return true;
    }

    return <div className='px-3 pb-3 pt-1 rounded'>
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
            <div>
                <FormGroup repartition={[2, 5, 5]}>
                    <FormField>
                        <label/>
                        <input type="text" disabled value='Lundi'/>
                    </FormField>
                    <FormInput
                        label='De'
                        validation={{}}
                        name='questionQuatorzeHeuresUnA'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnA}
                    />
                    <FormInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, mondayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnB'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnB}
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
                        name='questionQuatorzeHeuresUnC'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnC}
                    />
                    <FormInput
                        label='À'
                        validation={{ validate: val => checkIfLowerThan(val, tuesdayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnD'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnD}
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
                        name='questionQuatorzeHeuresUnE'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnE}
                    />
                    <FormInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, wednesdayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnF'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnF}
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
                        name='questionQuatorzeHeuresUnG'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnG}
                    />
                    <FormInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, thursdayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnH'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnH}
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
                        name='questionQuatorzeHeuresUnI'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnI}
                    />
                    <FormInput
                        label='À'
                        validation={{ validate: val => checkIfLowerThan(val, fridayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnJ'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnJ}
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
                        name='questionQuatorzeHeuresUnK'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnK}
                    />
                    <FormInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, saturdayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnL'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnL}
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
                        name='questionQuatorzeHeuresUnM'
                        register={register}
                        type='time'
                        placeholder='Début du quart'
                        error={errors.questionQuatorzeHeuresUnM}
                    />
                    <FormInput
                        label='À'
                        validation={{validate: val => checkIfLowerThan(val, sundayShift) || 'La fin du quart doit être après le début'}}
                        name='questionQuatorzeHeuresUnN'
                        register={register}
                        type='time'
                        placeholder='Fin du quart'
                        error={errors.questionQuatorzeHeuresUnN}
                    />
                </FormGroup>
            </div>
    </div>
}
