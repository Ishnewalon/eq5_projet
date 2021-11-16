import {useState} from "react";
import {monitorCreateForm} from "../../../services/stage-service";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";

export default function EvaluationStagiaire() {

    const [monitorVisitForm, setMonitorVisitForm] = useState({
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

    const handleChange = (event) => {
        const {value, name} = event.target;

        setMonitorVisitForm(prevalue => {
            return {
                ...prevalue,
                [name]: value
            }
        })
    }

    function convertAllNumberFieldsToNumbers() {
        Object.keys(monitorVisitForm).forEach(key => {
            if (!isNaN(monitorVisitForm[key])) {
                setMonitorVisitForm(prevalue => {
                    return {
                        ...prevalue,
                        [key]: parseInt(monitorVisitForm[key])
                    }
                })
            } else if (monitorVisitForm[key] === 'true' || monitorVisitForm[key] === 'false') {
                setMonitorVisitForm(prevalue => {
                    return {
                        ...prevalue,
                        [key]: monitorVisitForm[key] === 'true'
                    }
                })
            }
        })
    }

    const sendVisitForm = (e) => {
        e.preventDefault();
        convertAllNumberFieldsToNumbers();
        monitorCreateForm(monitorVisitForm).then();
    };

    return <form onSubmit={sendVisitForm}>
        <h1 className='text-center text-decoration-underline'>Fiche d'évaluation du stagiaire</h1>
        <div className='px-3 pb-3 pt-1'>
            <FormGroup>
                <FormField>
                    <label>Nom de l'élève</label>
                    <input type="text" name='nomStagiaire' value={monitorVisitForm.nomStagiaire}
                           onChange={e => handleChange(e)} autoComplete='off'
                           placeholder="Nom de l'élève" title="Le nom de l'élève est requis" required/>
                </FormField>
                <FormField>
                    <label>Programme d'études</label>
                    <input type="text" name='programmeEtudes' value={monitorVisitForm.programmeEtudes}
                           onChange={e => handleChange(e)}
                           autoComplete='off' placeholder="Programme d'études" title="Le programme d'études est requis"
                           required/>
                </FormField>
                <FormField>
                    <label>Nom de l'entreprise</label>
                    <input type="text" name='entrepriseNom' value={monitorVisitForm.entrepriseNom}
                           onChange={e => handleChange(e)} autoComplete='off' title="Le nom de l'entreprise est requis"
                           placeholder="Nom de l'entreprise"
                           required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Fonction</label>
                    <input type="text" name='fonctionUn' value={monitorVisitForm.fonctionUn}
                           onChange={e => handleChange(e)} autoComplete='off' placeholder="Fonction"
                           title="La fonction est requise"
                           required/>
                </FormField>
                <FormField>
                    <label>Téléphone</label>
                    <input type="tel" name='phone' value={monitorVisitForm.phone} onChange={e => handleChange(e)}
                           autoComplete='off' placeholder="Téléphone" title='Le téléphone est requis' required/>
                </FormField>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h2 className='mt-4 mb-0 text-decoration-underline'>Productivité</h2>
            <h4 className='mt-4 mb-0'>Capacité d’optimiser son rendement au travail</h4>
            <blockquote className='mt-3 mb-0'>Le stagiaire est en mesure de:</blockquote>
            <FormGroup>
                <FormField>
                    <label>Planifier et organiser son travail de façon efficace</label>
                    <select required name='questionUn' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionUn}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Comprendre rapidement les directives relatives à son
                        travail</label>
                    <select required name='questionDeux' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionDeux}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Maintenir un rythme de travail soutenu</label>
                    <select required name='questionTrois' value={monitorVisitForm.questionTrois}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Établir ses priorités</label>
                    <select required name='questionQuatre' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionQuatre}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Respecter les échéanciers</label>
                    <select required name='questionCinq' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionCinq}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
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
                    <select required name='questionSix' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionSix}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Porter attention aux détails dans la réalisation de ses
                        tâches</label>
                    <select required name='questionSept' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionSept}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Vérifier son travail, s’assurer que rien n’a été oublié</label>
                    <select required name='questionHuit' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionHuit}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Rechercher des occasions de se perfectionner</label>
                    <select required name='questionNeuf' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionNeuf}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Faire une bonne analyse des problèmes rencontrés</label>
                    <select required name='questionDix' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionDix}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
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
                    <select required name='questionOnze' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionOnze}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Contribuer activement au travail d’équipe</label>
                    <select required name='questionDouze' value={monitorVisitForm.questionDouze}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>S’adapter facilement à la culture de l’entreprise</label>
                    <select required name='questionTreize' value={monitorVisitForm.questionTreize}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Accepter les critiques constructives</label>
                    <select required name='questionQuatorze' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionQuatorze}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Être respectueux envers les gens</label>
                    <select required name='questionQuinze' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionQuinze}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Faire preuve d’écoute active en essayant de
                        comprendre le point de vue de l’autre</label>
                    <select required name='questionSeize' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionSeize}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
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
                    <select required name='questionDixSept' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionDixSept}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Exprimer clairement ses idées</label>
                    <select required name='questionDixHuit' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionDixHuit}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Faire preuve d’initiative</label>
                    <select required name='questionDixNeuf' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionDixNeuf}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Travailler de façon sécuritaire</label>
                    <select required name='questionVingt' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionVingt}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Démontrer un bon sens des responsabilités ne
                        requérant qu’un minimum de supervision</label>
                    <select required name='questionVingtUn' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionVingtUn}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Être ponctuel et assidu à son travail</label>
                    <select required name='questionVingtDeux' title="Le champ doit être rempli"
                            value={monitorVisitForm.questionVingtDeux}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_EN_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
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
                    <select required title={'Le champ doit être rempli'} name='appreciationGlobale'
                            value={monitorVisitForm.appreciationGlobale}
                            onChange={e => handleChange(e)}>
                        <option value="DEPASSE_BEAUCOUP">dépassent de beaucoup les attentes</option>
                        <option value="DEPASSE">dépassent les attentes</option>
                        <option value='REPOND_PLEINEMENT'>répondent pleinement aux attentes</option>
                        <option value='REPOND_PARTIELLEMENT'>répondent partiellement aux attentes</option>
                        <option value='REPOND_PAS'>répondent pas aux attentes</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Cette évaluation a été discutée avec le stagiaire</label>
                    <select required title='Le champ doit être rempli' name='evaluationDiscuteAvecEtudiant'
                            value={monitorVisitForm.evaluationDiscuteAvecEtudiant} onChange={e => handleChange(e)}>
                        <option value={true}>Oui</option>
                        <option value={false}>Non</option>
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Précisez votre appréciation</label>
                    <textarea required name='commentairesCinq' title='Le champ doit être rempli'
                              value={monitorVisitForm.commentairesCinq}
                              onChange={e => handleChange(e)}
                              placeholder='Commentaires sur la productivité du stagiaire'/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Veuillez indiquer le nombre d’heures réel par semaine d’encadrement accordé au
                        stagiaire</label>
                    <input required type="number" placeholder='Nombre heures réel par semaine' min='0'
                           name='nbHeuresReelTravailEtudiant'
                           value={monitorVisitForm.nbHeuresReelTravailEtudiant}/>
                </FormField>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <FormGroup>
                <FormField>
                    <label>L’entreprise aimerait accueillir cet élève pour son prochain stage</label>
                    <select required title="Le champ doit être rempli" name='entrepriseApprecieEtudiant'
                            value={monitorVisitForm.entrepriseApprecieEtudiant} onChange={e => handleChange(e)}>
                        <option value={true}>Oui</option>
                        <option value={false}>Non</option>
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>La formation technique du stagiaire était-elle suffisante pour accomplir le mandat de
                        stage?</label>
                    <textarea required title="Le champ doit être rempli" name="formationSuffisanteCommentaire"
                              value={monitorVisitForm.formationSuffisanteCommentaire}
                              placeholder='Est-ce que la formation était suffisante?'
                              onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Nom</label>
                    <input required type="text" title='Le nom est requis' name='nom' placeholder='Nom' value={monitorVisitForm.nom}
                           onChange={e => handleChange(e)}/>
                </FormField>
                <FormField>
                    <label>Fonction</label>
                    <input required type="text" placeholder='Fonction' name='fonctionDeux' title='La fonction est requise'
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
                    <input type="date" name='dateSignature' title='La date doit être choisi'
                           value={monitorVisitForm.dateSignature} onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <button type='submit' className='btn btn-primary w-100 mt-4 rounded fw-bold'>Créer une évaluation de stage
            </button>
        </div>
    </form>
}
