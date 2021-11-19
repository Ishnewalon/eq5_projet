import {useState} from "react";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {regexEmail, swalErr} from "../../../utility";
import {monitorCreateForm} from "../../../services/stage-service";

export default function EvaluationIntern() {

    const [errors, setErrors] = useState({});
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

    const applyValidationStyleBasedOnChoices = (value, name, validationObj) => {
        for (const val of Object.values(validationObj))
            if (value === val[0]) {
                elementIsRight(name);
                return true;
            } else
                elementIsWrong(name);
        return false;
    }

    const elementIsRight = (name) => {
        document.getElementsByName(name)
            .forEach(input => {
                if (!input.classList.contains('border'))
                    input.classList.add('border');

                input.classList.remove('border-danger');
                if (!input.classList.contains('border-success'))
                    input.classList.add('border-success');
            });
    }

    const elementIsWrong = (name) => {
        document.getElementsByName(name)
            .forEach(input => {
                if (!input.classList.contains('border'))
                    input.classList.add('border');
                input.classList.remove('border-success');
                if (!input.classList.contains('border-danger'))
                    input.classList.add('border-danger');
            });
    }

    const yesAndNoAnswers = {
        OUI: [true, 'Oui'],
        NON: [false, 'Non']
    }

    const validateByNameSelection = (tagName, name, value, element, title) => {
        let isValid = true;
        tagName = tagName.toLowerCase();
        if (tagName === 'select') {
            if (name.indexOf('question') >= 0)
                isValid = applyValidationStyleBasedOnChoices(value, name, choixAccords)
            else if (name.indexOf('appreciation') >= 0)
                isValid = applyValidationStyleBasedOnChoices(value, name, choixAppreciation);
            else if (name.indexOf('evaluationDiscute') >= 0 || name.indexOf('entrepriseApprecie') >= 0)
                isValid = applyValidationStyleBasedOnChoices(value, name, yesAndNoAnswers);
        } else if (tagName === 'input') {
            const {type} = element;
            if (type === 'email') {
                if (!regexEmail.test(value)) {
                    isValid = false;
                    elementIsWrong(name);
                } else {
                    elementIsRight(name);
                }
            } else if (type === 'number') {
                if (parseInt(value) === 0) {
                    isValid = false;
                    elementIsWrong(name);
                } else
                    elementIsRight(name);
            } else if (type === 'text' || type === 'date') {
                if (value.length === 0) {
                    elementIsWrong(name);
                    isValid = false;
                } else
                    elementIsRight(name);
            }
        } else if (tagName === 'textarea' && (name.indexOf('formation') >= 0 || name.indexOf('commentairesCinq') >= 0)) {
            if (value.length === 0) {
                elementIsWrong(name);
                isValid = false;
            } else
                elementIsRight(name);
        }
        if (!isValid)
            setErrors(prevalue => {
                return {
                    ...prevalue,
                    [name]: title
                }
            })
        return isValid;
    }

    const handleChange = (event) => {
        const {value, name} = event.target;

        const element = document.getElementsByName(name)[0];
        const {tagName, title} = element;

        validateByNameSelection(tagName, name, value, element, title);

        setMonitorVisitForm(prevalue => {
            return {
                ...prevalue,
                [name]: value
            }
        })
    }

    const sendVisitForm = (e) => {
        e.preventDefault();
        setErrors({});
        let isValid = true;
        for (const key in monitorVisitForm) {
            const element = document.getElementsByName(key)[0];
            const {tagName, title} = element;

            if (!validateByNameSelection(tagName, key, monitorVisitForm[key], element, title)) {
                isValid = false;
            }
        }
        if (!isValid) {
            swalErr.fire({text: "Veuillez remplir tous les champs requis!"}).then();
        } else {
            monitorCreateForm(monitorVisitForm).then();
        }
    };

    return <div className={'text-white'}>
        <h1 className='text-center text-decoration-underline'>Fiche d'évaluation du stagiaire</h1>
        <div className='px-3 pb-3 pt-1'>
            <FormGroup>
                <FormField>
                    <label>Email de l'élève</label>
                    <input type="email" name='emailEtudiant' value={monitorVisitForm.emailEtudiant}
                           onChange={e => handleChange(e)} autoComplete='off'
                           placeholder="Email de l'élève" title="Le email de l'élève est requis"/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Nom de l'élève</label>
                    <input type="text" name='nomStagiaire' value={monitorVisitForm.nomStagiaire}
                           onChange={e => handleChange(e)} autoComplete='off'
                           placeholder="Nom de l'élève" title="Le nom de l'élève est requis"/>
                </FormField>
                <FormField>
                    <label>Programme d'études</label>
                    <input type="text" name='programmeEtudes' value={monitorVisitForm.programmeEtudes}
                           onChange={e => handleChange(e)}
                           autoComplete='off' placeholder="Programme d'études"
                           title="Le programme d'études est requis"/>
                </FormField>
                <FormField>
                    <label>Nom de l'entreprise</label>
                    <input type="text" name='entrepriseNom' value={monitorVisitForm.entrepriseNom}
                           onChange={e => handleChange(e)} autoComplete='off' title="Le nom de l'entreprise est requis"
                           placeholder="Nom de l'entreprise"/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Fonction</label>
                    <input type="text" name='fonctionUn' value={monitorVisitForm.fonctionUn}
                           onChange={e => handleChange(e)} autoComplete='off' placeholder="Fonction"
                           title="La fonction est requise"/>
                </FormField>
                <FormField>
                    <label>Téléphone</label>
                    <input type="text" name='phone' value={monitorVisitForm.phone} onInput={e => handleChange(e)}
                           autoComplete='off' placeholder="Téléphone" title='Le téléphone est requis'/>
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
    </div>
}
