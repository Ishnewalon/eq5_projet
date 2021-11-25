import {useState} from "react";
import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../SharedComponents/FormField/FormField";
import {monitorCreateForm} from "../../services/stage-service";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import StepOne from "../EvaluationsWithSteps/StepOne";
import {useForm} from "react-hook-form";
import StepTwo from "../EvaluationsWithSteps/StepTwo";

export default function EvaluationIntern() {

    const {formState:{errors}, handleSubmit, register} = useForm();

    const today = new Date();
    const [monitorVisitForm, setMonitorVisitForm] = useState({
        emailEtudiant: '',
        entrepriseNom: '',
        phone: '',
        nomStagiaire: '',
        questionUn: 'TOTALEMENT_EN_ACCORD',
        questionDeux: 'TOTALEMENT_EN_ACCORD',
        questionTrois: 'TOTALEMENT_EN_ACCORD',
        questionQuatre: 'TOTALEMENT_EN_ACCORD',
        questionCinq: 'TOTALEMENT_EN_ACCORD',
        questionSix: 'TOTALEMENT_EN_ACCORD',
        questionSept: 'TOTALEMENT_EN_ACCORD',
        questionHuit: 'TOTALEMENT_EN_ACCORD',
        questionNeuf: 'TOTALEMENT_EN_ACCORD',
        questionDix: 'TOTALEMENT_EN_ACCORD',
        questionOnze: 'TOTALEMENT_EN_ACCORD',
        questionDouze: 'TOTALEMENT_EN_ACCORD',
        questionTreize: 'TOTALEMENT_EN_ACCORD',
        questionQuatorze: 'TOTALEMENT_EN_ACCORD',
        questionQuinze: 'TOTALEMENT_EN_ACCORD',
        questionSeize: 'TOTALEMENT_EN_ACCORD',
        questionDixSept: 'TOTALEMENT_EN_ACCORD',
        questionDixHuit: 'TOTALEMENT_EN_ACCORD',
        questionDixNeuf: 'TOTALEMENT_EN_ACCORD',
        questionVingt: 'TOTALEMENT_EN_ACCORD',
        questionVingtUn: 'TOTALEMENT_EN_ACCORD',
        questionVingtDeux: 'TOTALEMENT_EN_ACCORD',
        programmeEtudes: '',
        appreciationGlobale: 'DEPASSE_BEAUCOUP',
        evaluationDiscuteAvecEtudiant: true,
        commentairesUn: '',
        commentairesDeux: '',
        commentairesTrois: '',
        commentairesQuatre: '',
        commentairesCinq: '',
        entrepriseApprecieEtudiant: true,
        nbHeuresReelTravailEtudiant: 0,
        fonctionUn: '',
        fonctionDeux: '',
        formationSuffisanteCommentaire: '',
        nom: '',
        dateSignature: '',
        monitorSignature: ''
    });

    const choixAccords = {
        TOTALEMENT_EN_ACCORD: ["TOTALEMENT_EN_ACCORD", "Totalement en accord"],
        PLUTOT_EN_ACCORD: ['PLUTOT_EN_ACCORD', 'Plûtot en accord'],
        PLUTOT_EN_DESACCORD: ['PLUTOT_EN_DESACCORD', 'Plûtot en désaccord'],
        TOTALEMENT_EN_DESACCORD: ['TOTALEMENT_EN_DESACCORD', 'Totalement en désaccord'],
        NON_APPLICABLE: ['NON_APPLICABLE', 'N/A*']
    }

    const choixAppreciation = {
        DEPASSE_BEAUCOUP: ['DEPASSE_BEAUCOUP', 'Dépassent de beaucoup les attentes'],
        DEPASSE: ['DEPASSE', 'Dépassent les attentes'],
        REPOND_PLEINEMENT: ['REPOND_PLEINEMENT', 'Répondent pleinement aux attentes'],
        REPOND_PARTIELLEMENT: ['REPOND_PARTIELLEMENT', 'Répondent partiellement aux attentes'],
        REPOND_PAS: ['REPOND_PAS', 'Répondent pas aux attentes']
    }

    const yesAndNoAnswers = {
        OUI: [true, 'Oui'],
        NON: [false, 'Non']
    }

    const sendVisitForm = (e) => {
        e.preventDefault();
        monitorCreateForm(monitorVisitForm).then();
    };

    return <ContainerBox className={'text-white'}>
        <StepOne errors={errors} register={register} />
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>

            <FormGroup>
                <FormField>
                    <label>Planifier et organiser son travail de façon efficace</label>
                    <select name='questionUn'
                            title="Le champ 'Planifier et organiser son travail de façon efficace' doit être rempli"
                            value={monitorVisitForm.questionUn}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Comprendre rapidement les directives relatives à son
                        travail</label>
                    <select name='questionDeux' title="Le champ 'Comprendre rapidement les directives relatives à son
                        travail' doit être rempli"
                            value={monitorVisitForm.questionDeux}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Maintenir un rythme de travail soutenu</label>
                    <select name='questionTrois'
                            title="Le champ 'Maintenir un rythme de travail soutenu' doit êtr rempli"
                            value={monitorVisitForm.questionTrois}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Établir ses priorités</label>
                    <select name='questionQuatre' title="Le champ 'Établir ses priorités' doit être rempli"
                            value={monitorVisitForm.questionQuatre}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Respecter les échéanciers</label>
                    <select name='questionCinq' title="Le champ 'Respecter les échéanciers' doit être rempli"
                            value={monitorVisitForm.questionCinq}
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
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
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
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
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
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
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
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
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
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
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
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h2 className='mt-4 mb-0 text-decoration-underline'>Qualités des relations interpersonnelles</h2>
            <h4 className='mt-4 mb-0'>Capacité d’établir des interrelations harmonieuses dans son milieu de travail</h4>
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
                    <select name='questionQuinze' title="Le champ 'Être respectueux envers les gens' doit être rempli"
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
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h2 className='mt-4 mb-0 text-decoration-underline'>Habiletés personnelles</h2>
            <h4 className='mt-4 mb-0'>Capacité de faire preuve d’attitudes ou de comportements matures et
                responsables</h4>
            <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
            <FormGroup>
                <FormField>
                    <label>Démontrer de l’intérêt et de la motivation au travail</label>
                    <select name='questionDixSept'
                            title="Le champ 'Démontrer de l’intérêt et de la motivation au travail' doit être rempli"
                            value={monitorVisitForm.questionDixSept}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Exprimer clairement ses idées</label>
                    <select name='questionDixHuit' title="Le champ 'Exprimer clairement ses idées' doit être rempli"
                            value={monitorVisitForm.questionDixHuit}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Faire preuve d’initiative</label>
                    <select name='questionDixNeuf' title="Le champ 'Faire preuve d’initiative' doit être rempli"
                            value={monitorVisitForm.questionDixNeuf}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Travailler de façon sécuritaire</label>
                    <select name='questionVingt' title="Le champ 'Travailler de façon sécuritaire' doit être rempli"
                            value={monitorVisitForm.questionVingt}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Démontrer un bon sens des responsabilités ne
                        requérant qu’un minimum de supervision</label>
                    <select name='questionVingtUn' title="Le champ 'Démontrer un bon sens des responsabilités ne
                        requérant qu’un minimum de supervision' doit être rempli"
                            value={monitorVisitForm.questionVingtUn}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Être ponctuel et assidu à son travail</label>
                    <select name='questionVingtDeux'
                            title="Le champ 'Être ponctuel et assidu à son travail' doit être rempli"
                            value={monitorVisitForm.questionVingtDeux}
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
                    <textarea name='commentairesQuatre' value={monitorVisitForm.commentairesQuatre}
                              placeholder='Commentaires sur les habiletés personnelles du stagiaire'
                              onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h2 className='mt-4 mb-0 text-decoration-underline'>Appréciation globale du stagiaire</h2>
            <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
            <FormGroup>
                <FormField>
                    <label>Les habiletés démontrées</label>
                    <select title={'Les habiletés démontrées doit être rempli'} name='appreciationGlobale'
                            value={monitorVisitForm.appreciationGlobale}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une appréciation</option>
                        {Object.values(choixAppreciation).map((c, index) => <option key={index}
                                                                                    value={c[0]}>{c[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Cette évaluation a été discutée avec le stagiaire</label>
                    <select title="Il faut mentionner si l'évaluation a été discuté avec le stagiaire"
                            name='evaluationDiscuteAvecEtudiant'
                            value={monitorVisitForm.evaluationDiscuteAvecEtudiant} onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une réponse</option>
                        {Object.values(yesAndNoAnswers).map((c, index) => <option key={index}
                                                                                  value={c[0]}>{c[1]}</option>)}
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Précisez votre appréciation</label>
                    <textarea name='commentairesCinq' title="L'appréciation pour le stagiaire est requis"
                              value={monitorVisitForm.commentairesCinq}
                              onChange={e => handleChange(e)}
                              placeholder='Commentaires sur la productivité du stagiaire'/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Veuillez indiquer le nombre d’heures réel par semaine d’encadrement accordé au
                        stagiaire</label>
                    <input type="number" placeholder='Nombre heures réel par semaine' min='0'
                           name='nbHeuresReelTravailEtudiant'
                           title="Il faut écrire le nombre d'heures réel par semaine d'encadrement accordé au stagiaire"
                           value={monitorVisitForm.nbHeuresReelTravailEtudiant} onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <FormGroup>
                <FormField>
                    <label>L’entreprise aimerait accueillir cet élève pour son prochain stage</label>
                    <select
                        title="Le champ 'L’entreprise aimerait accueillir cet élève pour son prochain stage' doit être rempli"
                        name='entrepriseApprecieEtudiant'
                        value={monitorVisitForm.entrepriseApprecieEtudiant} onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une réponse</option>
                        {Object.values(yesAndNoAnswers).map((c, index) => <option key={index}
                                                                                  value={c[0]}>{c[1]}</option>)}
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>La formation technique du stagiaire était-elle suffisante pour accomplir le mandat de
                        stage?</label>
                    <textarea title="Le champ 'La formation technique du stagiaire était-elle suffisante pour accomplir le mandat de
                        stage' doit être rempli" name="formationSuffisanteCommentaire"
                              value={monitorVisitForm.formationSuffisanteCommentaire}
                              placeholder='Est-ce que la formation était suffisante?'
                              onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Nom</label>
                    <input type="text" title='Le nom est requis' name='nom' placeholder='Nom'
                           value={monitorVisitForm.nom}
                           onChange={e => handleChange(e)}/>
                </FormField>
                <FormField>
                    <label>Fonction</label>
                    <input type="text" placeholder='Fonction' name='fonctionDeux'
                           title='La 2ième fonction est requise'
                           value={monitorVisitForm.fonctionDeux} onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Signature</label>
                    <input type="text" name='monitorSignature' placeholder='Signature'
                           title="La signature doit être rempli" value={monitorVisitForm.monitorSignature}
                           onChange={e => handleChange(e)}/>
                </FormField>
                <FormField>
                    <label>Date</label>
                    <input type="date" name='dateSignature' max={today.toString()}
                           title='La date doit être choisi'
                           value={monitorVisitForm.dateSignature} onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            {Object.keys(errors).length > 0 ?
                <>
                    <p className={'my-3'}>Voici la liste des champs à remplir</p>
                    <ul>
                        {Object.keys(errors).map((key, index) => <li
                            key={index}>{errors[key]}</li>)}
                    </ul>
                </>
                : <></>
            }
            <button onClick={sendVisitForm} className='btn btn-primary w-100 mt-4 rounded fw-bold'>Créer une évaluation
                de stage
            </button>
        </div>
    </ContainerBox>
}
