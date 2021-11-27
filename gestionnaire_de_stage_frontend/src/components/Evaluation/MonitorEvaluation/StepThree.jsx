import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormTextarea} from "../../SharedComponents/FormTextarea";
import {FieldRadio} from "../../SharedComponents/FormInput/FieldRadio";

export default function StepThree({register, choices}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Qualité du travail</h2>
        <h4 className='mt-4 mb-0'>Capacité de s’acquitter des tâches sous sa responsabilité en s’imposant
            personnellement des normes de qualité</h4>
        <h5 className='mt-3 mb-2'>Le stagiaire est en mesure de:</h5>
        <FormGroup>
            <FieldRadio
                name='questionSix'
                register={register}
                list={Object.values(choices)}
                label='Respecter les mandats qui lui ont été confiés'
            />

            <FieldRadio
                label='Porter attention aux détails dans la réalisation de ses
                    tâches'
                name='questionSept'
                register={register}
                list={Object.values(choices)}
            />
            <FieldRadio
                label='Vérifier son travail, s’assurer que rien n’a été oublié'
                name='questionHuit'
                register={register}
                list={Object.values(choices)}
            />
        </FormGroup>
        <FormGroup repartition={[4, 4]}>
            <FieldRadio
                label='Rechercher des occasions de se perfectionner'
                name='questionNeuf'
                register={register}
                list={Object.values(choices)}
            />
            <FieldRadio
                label='Faire une bonne analyse des problèmes rencontrés'
                name='questionDix'
                register={register}
                list={Object.values(choices)}
            />
        </FormGroup>
        <FormGroup>
            <FormTextarea
                label='Commentaires'
                name='commentairesDeux'
                register={register}
                placeholder='Commentaires sur la qualité du travail du stagiaire'
            />
        </FormGroup>
    </div>
}
