import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FormTextarea} from "../SharedComponents/FormTextarea";
import {FormSelect} from "../SharedComponents/FormInput/FormSelect";

export default function StepTwo({errors, register, choices}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Productivité</h2>
        <h4 className='mt-4 mb-0'>Capacité d’optimiser son rendement au travail</h4>
        <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
        <FormGroup>
            <FormSelect
                label='Planifier et organiser son travail de façon efficace'
                validation={{required: 'Ce champ est requis'}}
                name='questionUn'
                register={register}
                displayed={[1]}
                options={Object.values(choices)}
                fieldValue={0}
                error={errors.questionUn}
                defaultMessage={'Choisiser une évaluation'}
            />
            <FormSelect
                label='Comprendre rapidement les directives relatives à son travail'
                validation={{required: 'Ce champ est requis'}}
                name='questionDeux'
                register={register}
                displayed={[1]}
                options={Object.values(choices)}
                fieldValue={0}
                error={errors.questionDeux}
                defaultMessage={'Choisiser une évaluation'}
            />
            <FormSelect
                label='Maintenir un rythme de travail soutenu'
                validation={{required: 'Ce champ est requis'}}
                name='questionTrois'
                register={register}
                displayed={[1]}
                options={Object.values(choices)}
                fieldValue={0}
                error={errors.questionTrois}
                defaultMessage={'Choisiser une évaluation'}
            />
        </FormGroup>
        <FormGroup>
            <FormSelect
                label='Établir ses priorités'
                validation={{required: 'Ce champ est requis'}}
                register={register}
                name='questionQuatre'
                defaultMessage={'Choisiser une évaluation'}
                options={Object.values(choices)}
                fieldValue={0}
                error={errors.questionQuatre}
                displayed={[1]}
            />
            <FormSelect
                label='Respecter les échéanciers'
                validation={{required: 'Ce champ est requis'}}
                name='questionCinq'
                register={register}
                displayed={[1]}
                options={Object.values(choices)}
                defaultMessage={'Choisiser une évaluation'}
                error={errors.questionCinq}
                fieldValue={0}
            />
        </FormGroup>
        <FormGroup>
            <FormTextarea
                label='Commentaires'
                name='commentairesUn'
                register={register}
                placeholder='Commentaires sur la productivité du stagiaire'
            />
        </FormGroup>
    </div>
}
