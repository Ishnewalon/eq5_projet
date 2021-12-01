import {FormGroup} from "../../SharedComponents/Form/FormGroup";
import {FieldRadio, FieldTextarea} from "../../SharedComponents/Form/FormFields";
import {Column} from "../../SharedComponents/Column";

export default function StepThree({register, choices}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Qualité du travail</h2>
        <h4 className='mt-4 mb-0'>Capacité de s’acquitter des tâches sous sa responsabilité en s’imposant
            personnellement des normes de qualité</h4>
        <h5 className='mt-3 mb-2'>Le stagiaire est en mesure de:</h5>
        <FormGroup>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionStudentRespectTaskGiven'
                    register={register}
                    list={Object.values(choices)}
                    label='Respecter les mandats qui lui ont été confiés'
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    label='Porter attention aux détails dans la réalisation de ses
                    tâches'
                    name='questionStudentPaysAttentionToDetails'
                    register={register}
                    list={Object.values(choices)}
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    label='Vérifier son travail, s’assurer que rien n’a été oublié'
                    name='questionStudentValidateHisWork'
                    register={register}
                    list={Object.values(choices)}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{md: 4}}>
                <FieldRadio
                    label='Rechercher des occasions de se perfectionner'
                    name='questionStudentTakeInitiativeToPerfectHimself'
                    register={register}
                    list={Object.values(choices)}
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    label='Faire une bonne analyse des problèmes rencontrés'
                    name='questionStudentAnalyzeProblemsGiven'
                    register={register}
                    list={Object.values(choices)}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column>
                <FieldTextarea
                    label='Commentaires'
                    name='commentsQualityOfWorkIntern'
                    register={register}
                />
            </Column>
        </FormGroup>
    </div>
}
