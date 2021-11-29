import {FormGroup} from "../../SharedComponents/Form/FormGroup";
import {FieldRadio, FieldTextarea} from "../../SharedComponents/Form/FormFields";
import {Column} from "../../SharedComponents/Column";

export default function StepFive({register, choices}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Habiletés personnelles</h2>
        <h4 className='mt-4 mb-0'>Capacité de faire preuve d’attitudes ou de comportements matures et
            responsables</h4>
        <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
        <FormGroup>
            <Column col={{md: 4}}>
                <FieldRadio
                    label="Démontrer de l’intérêt et de la motivation au travail"
                    name='questionDixSept'
                    register={register}
                    list={Object.values(choices)}
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    label='Exprimer clairement ses idées'
                    name='questionDixHuit'
                    register={register}
                    list={Object.values(choices)}
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    label='Faire preuve d’initiative'
                    name='questionDixNeuf'
                    register={register}
                    list={Object.values(choices)}
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionVingt'
                    register={register}
                    list={Object.values(choices)}
                    label='Travailler de façon sécuritaire'
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name={'questionVingUn'}
                    register={register}
                    list={Object.values(choices)}
                    label='Démontrer un bon sens des responsabilités ne
                        requérant qu’un minimum de supervision'
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionVingDeux'
                    register={register}
                    list={Object.values(choices)}
                    label='Être ponctuel et assidu à son travail'
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column>
                <FieldTextarea
                    label='Commentaires'
                    name='commentairesQuatre'
                    register={register}
                    placeholder='Commentaires sur les habiletés personnelles du stagiaire'
                />
            </Column>
        </FormGroup>
    </div>
}
