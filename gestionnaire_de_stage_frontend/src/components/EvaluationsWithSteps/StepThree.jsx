import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {Select} from "../SharedComponents/Select";
import {FormField} from "../SharedComponents/FormField/FormField";

export default function StepThree(props) {

    const {errors, register, choices} = props;

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Qualité du travail</h2>
        <h4 className='mt-4 mb-0'>Capacité de s’acquitter des tâches sous sa responsabilité en s’imposant
            personnellement des normes de qualité</h4>
        <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
        <FormGroup>
            <Select
                label='Respecter les mandats qui lui ont été confiés'
                validation={{required: 'Ce champ est requis'}}
                name='questionSix'
                register={register}
                options={choices}
                error={errors.questionSix}
            />
            <Select
                label='Porter attention aux détails dans la réalisation de ses
                    tâches'
                validation={{required: 'Ce champ est requis'}}
                name='questionSept'
                register={register}
                options={choices}
                error={errors.questionSept}
            />
            <Select
                label='Vérifier son travail, s’assurer que rien n’a été oublié'
                validation={{required: 'Ce champ est requis'}}
                name='questionHuit'
                register={register}
                options={choices}
                error={errors.questionHuit}
            />
        </FormGroup>
        <FormGroup>
            <Select
                label='Rechercher des occasions de se perfectionner'
                validation={{required: 'Ce champ est requis'}}
                name='questionNeuf'
                register={register}
                options={choices}
                error={errors.questionNeuf}
            />
            <Select
                label='Faire une bonne analyse des problèmes rencontrés'
                validation={{required: 'Ce champ est requis'}}
                name='questionDix'
                register={register}
                options={choices}
                error={errors.questionDix}
            />
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Commentaires</label>
                <textarea {...register("commentairesDeux")}
                          placeholder='Commentaires sur la qualité du travail du stagiaire'/>
            </FormField>
        </FormGroup>
    </div>
}
