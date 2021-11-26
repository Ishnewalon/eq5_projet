import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FieldRadio} from "../SharedComponents/FormInput/FieldRadio";
import {FormTextarea} from "../SharedComponents/FormTextarea";

export default function StepFive({register, choices}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Habiletés personnelles</h2>
        <h4 className='mt-4 mb-0'>Capacité de faire preuve d’attitudes ou de comportements matures et
            responsables</h4>
        <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
        <FormGroup>
            <FieldRadio
                label="Démontrer de l’intérêt et de la motivation au travail"
                name='questionDixSept'
                register={register}
                list={Object.values(choices)}
            />
            <FieldRadio
                label='Exprimer clairement ses idées'
                name='questionDixHuit'
                register={register}
                list={Object.values(choices)}
            />
            <FieldRadio
                label='Faire preuve d’initiative'
                name='questionDixNeuf'
                register={register}
                list={Object.values(choices)}
            />
        </FormGroup>
        <FormGroup>
            <FieldRadio
                name='questionVingt'
                register={register}
                list={Object.values(choices)}
                label='Travailler de façon sécuritaire'
            />
            <FieldRadio
                name={'questionVingUn'}
                register={register}
                list={Object.values(choices)}
                label='Démontrer un bon sens des responsabilités ne
                        requérant qu’un minimum de supervision'
            />
            <FieldRadio
                name='questionVingDeux'
                register={register}
                list={Object.values(choices)}
                label='Être ponctuel et assidu à son travail'
            />
        </FormGroup>
        <FormGroup>
            <FormTextarea
                label='Commentaires'
                name='commentairesQuatre'
                register={register}
                placeholder='Commentaires sur les habiletés personnelles du stagiaire'
            />
        </FormGroup>
    </div>
}
