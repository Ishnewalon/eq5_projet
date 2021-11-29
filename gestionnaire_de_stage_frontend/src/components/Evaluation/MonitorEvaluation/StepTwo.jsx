import {FormTextarea} from "../../SharedComponents/FormTextarea";
import {FieldRadio} from "../../SharedComponents/FormInput/FieldRadio";
import {Column, FormGroup} from "../../SharedComponents/FormGroup/FormGroup";

export default function StepTwo({register, choices}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Productivité</h2>
        <h4 className='mt-4 mb-0'>Capacité d’optimiser son rendement au travail</h4>
        <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
        <FormGroup>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionUn'
                    register={register}
                    list={Object.values(choices)}
                    label='Planifier et organiser son travail de façon efficace'
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionDeux'
                    register={register}
                    list={Object.values(choices)}
                    label='Comprendre rapidement les directives relatives à son travail'
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionTrois'
                    register={register}
                    list={Object.values(choices)}
                    label='Maintenir un rythme de travail soutenu'
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionQuatre'
                    register={register}
                    list={Object.values(choices)}
                    label='Établir ses priorités'
                />
            </Column>
            <Column col={{md: 4}}>
                <FieldRadio
                    name='questionCinq'
                    register={register}
                    list={Object.values(choices)}
                    label='Respecter les échéanciers'
                />
            </Column>
        </FormGroup>
        <FormGroup>
            <Column>
                <FormTextarea
                    label='Commentaires'
                    name='commentairesUn'
                    register={register}
                    placeholder='Commentaires sur la productivité du stagiaire'
                />
            </Column>
        </FormGroup>
    </div>
}
