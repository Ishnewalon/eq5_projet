import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../SharedComponents/FormField/FormField";
import {Select} from "../SharedComponents/Select";

export default function StepTwo(props){

    const {errors, register, choices} = props;

   

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
            <Select label={} validation={} name={} register={}
            
            <FormField>
                <label>Respecter les échéanciers</label>
                <select name='questionCinq' title="Le champ 'Respecter les échéanciers' doit être rempli"
                        value={monitorVisitForm.questionCinq}
                        onChange={e => handleChange(e)}>
                    <option disabled value="">Choisiser une évaluation</option>
                    {Object.values(choices).map((choix, index) => <option key={index}
                                                                               value={choix[0]}>{choix[1]}</option>)}
                </select>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Commentaires</label>
                <textarea name='commentairesUn' value={monitorVisitForm.commentairesUn}
                          onChange={e => handleChange(e)}
                          placeholder='Commentaires sur la productivité du stagiaire'/>
            </FormField>
        </FormGroup>
        <hr/>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Qualité du travail</h2>
        <h4 className='mt-4 mb-0'>Capacité de s’acquitter des tâches sous sa responsabilité en s’imposant
            personnellement des normes de qualité</h4>
        <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
        <FormGroup>
            <FormField>
                <label>Respecter les mandats qui lui ont été confiés</label>
                <select name='questionSix'
                        title="Le champ 'Respecter les mandats qui lui ont été confiés' doit être rempli"
                        value={monitorVisitForm.questionSix}
                        onChange={e => handleChange(e)}>
                    <option disabled value="">Choisiser une évaluation</option>
                    {Object.values(choices).map((choix, index) => <option key={index}
                                                                               value={choix[0]}>{choix[1]}</option>)}
                </select>
            </FormField>
            <FormField>
                <label>Porter attention aux détails dans la réalisation de ses
                    tâches</label>
                <select name='questionSept' title="Le champ 'Porter attention aux détails dans la réalisation de ses
                        tâches' doit être rempli"
                        value={monitorVisitForm.questionSept}
                        onChange={e => handleChange(e)}>
                    <option disabled value="">Choisiser une évaluation</option>
                    {Object.values(choices).map((choix, index) => <option key={index}
                                                                               value={choix[0]}>{choix[1]}</option>)}
                </select>
            </FormField>
            <FormField>
                <label>Vérifier son travail, s’assurer que rien n’a été oublié</label>
                <select name='questionHuit'
                        title="Le champ 'Vérifier son travail, s’assurer que rien n’a été oublié' doit être rempli"
                        value={monitorVisitForm.questionHuit}
                        onChange={e => handleChange(e)}>
                    <option disabled value="">Choisiser une évaluation</option>
                    {Object.values(choices).map((choix, index) => <option key={index}
                                                                               value={choix[0]}>{choix[1]}</option>)}
                </select>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Rechercher des occasions de se perfectionner</label>
                <select name='questionNeuf'
                        title="Le champ 'Rechercher des occasions de se perfectionner' doit être rempli"
                        value={monitorVisitForm.questionNeuf}
                        onChange={e => handleChange(e)}>
                    <option disabled value="">Choisiser une évaluation</option>
                    {Object.values(choices).map((choix, index) => <option key={index}
                                                                               value={choix[0]}>{choix[1]}</option>)}
                </select>
            </FormField>
            <FormField>
                <label>Faire une bonne analyse des problèmes rencontrés</label>
                <select name='questionDix'
                        title="Le champ 'Faire une bonne analyse des problèmes rencontrés' doit être rempli"
                        value={monitorVisitForm.questionDix}
                        onChange={e => handleChange(e)}>
                    <option disabled value="">Choisiser une évaluation</option>
                    {Object.values(choices).map((choix, index) => <option key={index}
                                                                               value={choix[0]}>{choix[1]}</option>)}
                </select>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Commentaires</label>
                <textarea name='commentairesDeux' value={monitorVisitForm.commentairesDeux}
                          placeholder='Commentaires sur la qualité du travail du stagiaire'
                          onChange={e => handleChange(e)}/>
            </FormField>
        </FormGroup>
    </div>
}
