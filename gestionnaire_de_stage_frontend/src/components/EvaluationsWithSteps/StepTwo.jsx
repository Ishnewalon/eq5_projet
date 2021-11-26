import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../SharedComponents/FormField/FormField";
import {Select} from "../SharedComponents/Select";

export default function StepTwo({errors, register, choices}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Productivité</h2>
        <h4 className='mt-4 mb-0'>Capacité d’optimiser son rendement au travail</h4>
        <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
        <FormGroup>
            <Select
                label='Planifier et organiser son travail de façon efficace'
                register={register}
                validation={{required: 'Ce champ est requis'}}
                name='questionUn'
                options={choices}
                error={errors.questionUn}
            />
            <Select
                label="Comprendre rapidement les directives relatives à son travail"
                validation={{required: 'Ce champ est requis'}}
                register={register}
                name='questionDeux'
                options={choices}
                error={errors.questionDeux}
            />
            <Select
                label='Maintenir un rythme de travail soutenu'
                validation={{required: 'Ce champ est requis'}}
                name='questionTrois'
                register={register}
                options={choices}
                error={errors.questionTrois}
            />
        </FormGroup>
        <FormGroup>
            <Select
                label='Établir ses priorités'
                validation={{required: 'Ce champ est requis'}}
                register={register}
                name='questionQuatre'
                options={choices}
                error={errors.questionQuatre}
            />
            <Select label='Respecter les échéanciers'
                    validation={{required: 'Ce champ est requis'}}
                    name='questionCinq'
                    register={register}
                    options={choices}
                    error={errors.questionCinq}
            />
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Commentaires</label>
                <textarea name='commentairesUn'
                          {...register("commentairesUn")}
                          placeholder='Commentaires sur la productivité du stagiaire'
                />
            </FormField>
        </FormGroup>
    </div>
}
