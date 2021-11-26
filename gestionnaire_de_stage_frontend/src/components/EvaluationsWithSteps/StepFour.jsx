import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../SharedComponents/FormField/FormField";

export default function StepFour(props){

    const {errors, register, choices } = props;

    return  <div className='px-3 pb-3 pt-1 rounded'>
        <h2 className='mt-4 mb-0 text-decoration-underline'>Qualités des relations interpersonnelles</h2>
        <h4 className='mt-4 mb-0'>Capacité d’établir des interrelations harmonieuses dans son milieu de
            travail</h4>
        <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
        <FormGroup>
            <FormField>
                <label>Établir facilement des contacts avec les gens</label>
                <select name='questionOnze'
                        title="Le champ 'Établir facilement des contacts avec les gens' doit être rempli"
                        value={monitorVisitForm.questionOnze}
                        onChange={e => handleChange(e)}>
                    <option disabled value="">Choisiser une évaluation</option>
                    {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                               value={choix[0]}>{choix[1]}</option>)}
                </select>
            </FormField>
            <FormField>
                <label>Contribuer activement au travail d’équipe</label>
                <select name='questionDouze'
                        title="Le champ 'Contribuer activement au travail d’équipe' doit être rempli"
                        value={monitorVisitForm.questionDouze}
                        onChange={e => handleChange(e)}>
                    <option disabled value="">Choisiser une évaluation</option>
                    {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                               value={choix[0]}>{choix[1]}</option>)}
                </select>
            </FormField>
            <FormField>
                <label>S’adapter facilement à la culture de l’entreprise</label>
                <select name='questionTreize'
                        title="Le champ 'Accepter les critiques constructives' doit être rempli"
                        value={monitorVisitForm.questionTreize}
                        onChange={e => handleChange(e)}>
                    <option disabled value="">Choisiser une évaluation</option>
                    {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                               value={choix[0]}>{choix[1]}</option>)}
                </select>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Accepter les critiques constructives</label>
                <select name='questionQuatorze'
                        title="Le champ 'Accepter les critiques constructives' doit être rempli"
                        value={monitorVisitForm.questionQuatorze}
                        onChange={e => handleChange(e)}>
                    <option disabled value="">Choisiser une évaluation</option>
                    {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                               value={choix[0]}>{choix[1]}</option>)}
                </select>
            </FormField>
            <FormField>
                <label>Être respectueux envers les gens</label>
                <select name='questionQuinze'
                        title="Le champ 'Être respectueux envers les gens' doit être rempli"
                        value={monitorVisitForm.questionQuinze}
                        onChange={e => handleChange(e)}>
                    <option disabled value="">Choisiser une évaluation</option>
                    {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                               value={choix[0]}>{choix[1]}</option>)}
                </select>
            </FormField>
            <FormField>
                <label>Faire preuve d’écoute active en essayant de
                    comprendre le point de vue de l’autre</label>
                <select name='questionSeize' title="Le champ 'Faire preuve d’écoute active en essayant de
                        comprendre le point de vue de l’autre' doit être rempli"
                        value={monitorVisitForm.questionSeize}
                        onChange={e => handleChange(e)}>
                    <option disabled value="">Choisiser une évaluation</option>
                    {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                               value={choix[0]}>{choix[1]}</option>)}
                </select>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Commentaires</label>
                <textarea name='commentairesTrois' value={monitorVisitForm.commentairesTrois}
                          placeholder='Commentaires sur les qualités des relations interpersonnelles du stagiaire'
                          onChange={e => handleChange(e)}/>
            </FormField>
        </FormGroup>
    </div>
}
