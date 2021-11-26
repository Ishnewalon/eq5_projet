import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../SharedComponents/FormField/FormField";
import {monitorCreateForm} from "../../services/stage-service";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import StepOne from "../EvaluationsWithSteps/StepOne";
import {useForm} from "react-hook-form";
import StepTwo from "../EvaluationsWithSteps/StepTwo";
import StepThree from "../EvaluationsWithSteps/StepThree";
import StepFour from "../EvaluationsWithSteps/StepFour";

export default function EvaluationIntern() {

    const {formState: {errors}, handleSubmit, register} = useForm({
        mode: "onSubmit",
        reValidateMode: "onChange"
    });

    const today = new Date();

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

    const sendVisitForm = (e, data) => {
        e.preventDefault();
        monitorCreateForm(data).then();
    };

    return <ContainerBox className={'text-white'}>
        <form onSubmit={handleSubmit(sendVisitForm)} noValidate>
            <StepOne errors={errors} register={register}/>
            <hr/>
            <StepTwo errors={errors} register={register} choices={choixAccords}/>
            <hr/>
            <StepThree errors={errors} register={register} choices={choixAccords}/>
            <hr/>
            <StepFour errors={errors} register={register} choices={choixAccords}/>


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
                <button onClick={sendVisitForm} className='btn btn-primary w-100 mt-4 rounded fw-bold'>Créer une
                    évaluation
                    de stage
                </button>
            </div>
        </form>
    </ContainerBox>
}
