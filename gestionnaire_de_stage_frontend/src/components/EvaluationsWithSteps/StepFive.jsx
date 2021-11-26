import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../SharedComponents/FormField/FormField";
import {FormSelect} from "../SharedComponents/FormInput/FormSelect";
import {FieldRadio} from "../SharedComponents/FormInput/FieldRadio";
import {FormTextarea} from "../SharedComponents/FormTextarea";

export default function StepFive({register, errors, choices}){

    return <div className='px-3 pb-3 pt-1 rounded'>
            <h2 className='mt-4 mb-0 text-decoration-underline'>Habiletés personnelles</h2>
            <h4 className='mt-4 mb-0'>Capacité de faire preuve d’attitudes ou de comportements matures et
                responsables</h4>
            <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
            <FormGroup>
                <FormSelect
                    label="Démontrer de l’intérêt et de la motivation au travail"
                    validation={{required: 'Ce champ est requis'}}
                    name='questionDixSept'
                    register={register}
                    displayed={[1]}
                    options={Object.values(choices)}
                    fieldValue={0}
                    error={errors.questionDixSept}
                    defaultMessage={'Choisiser une évaluation'}
                />
                <FormSelect
                    label='Exprimer clairement ses idées'
                    validation={{required: 'Ce champ est requis'}}
                    name='questionDixHuit'
                    register={register}
                    displayed={[1]}
                    options={Object.values(choices)}
                    fieldValue={0}
                    error={errors.questionDixHuit}
                    defaultMessage={'Choisiser une évaluation'}
                    />
                <FormSelect
                    label='Faire preuve d’initiative'
                    validation={{required: 'Ce champ est} requis'}}
                    name='questionDixNeuf'
                    register={register}
                    displayed={[1]}
                    options={Object.values(choices)}
                    defaultMessage='Choisiser une évaluation'
                    error={errors.questionDixNeuf}
                    fieldValue={0}
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
