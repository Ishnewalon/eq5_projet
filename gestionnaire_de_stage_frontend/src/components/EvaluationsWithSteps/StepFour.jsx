import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FieldRadio} from "../SharedComponents/FormInput/FieldRadio";
import {FormTextarea} from "../SharedComponents/FormTextarea";

export default function StepFour({register, choices}) {

    return <div className='px-3 pb-3 pt-1 rounded'>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Qualités des relations interpersonnelles</h2>
        <h4 className='mt-4 mb-0'>Capacité d’établir des interrelations harmonieuses dans son milieu de
            travail</h4>
        <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
        <FormGroup>
            <FieldRadio
                name={'questionOnze'}
                register={register}
                list={Object.values(choices)}
                label='Établir facilement des contacts avec les gens'

            />
            <FieldRadio
                name='questionDouze'
                register={register}
                list={Object.values(choices)}
                label='Contribuer activement au travail d’équipe'
            />
        </FormGroup>
        <FormGroup>
            <FieldRadio
                name='questionTreize'
                register={register}
                list={Object.values(choices)}
                label="S’adapter facilement à la culture de l’entreprise"
            />
            <FieldRadio
                name='questionQuatorze'
                register={register}
                list={Object.values(choices)}
                label='Accepter les critiques constructives'
            />
        </FormGroup>
        <FormGroup>
            <FieldRadio
                name='questionQuinze'
                register={register}
                list={Object.values(choices)}
                label='Être respectueux envers les gens'
            />
            <FieldRadio
                name='questionSeize'
                register={register}
                list={Object.values(choices)}
                label='Faire preuve d’écoute active en essayant de
                    comprendre le point de vue de l’autre'
            />
        </FormGroup>
        <FormGroup>
            <FormTextarea
                label='Commentaires'
                name='commentairesTrois'
                register={register}
                placeholder='Commentaires sur les qualités des relations interpersonnelles du stagiaire'/>
        </FormGroup>
    </div>
}
