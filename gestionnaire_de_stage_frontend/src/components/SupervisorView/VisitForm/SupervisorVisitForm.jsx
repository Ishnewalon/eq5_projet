import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {useState} from "react";
import {supervisorCreateForm} from "../../../services/stage-service";
import {regexMatriculeEtudiant, swalErr} from "../../../utility";


export default function SupervisorVisitForm() {
    const [errors, setErrors] = useState({});
    const [visitForm, setVisitForm] = useState({
        entrepriseNom: '',
        matriculeEtudiant: '',
        signatureSuperviseur: '',
        personneContact: '',
        phone: '',
        telecopieur: '',
        adresse: '',
        zip: '',
        ville: '',
        nomStagiaire: '',
        dateStage: '',
        stageCourant: 1,
        questionUn: 'TOTALEMENT_EN_ACCORD',
        questionDeux: 'TOTALEMENT_EN_ACCORD',
        questionTrois: 'TOTALEMENT_EN_ACCORD',
        nbHeuresMoisUn: 0,
        nbHeuresMoisDeux: 0,
        nbHeuresMoisTrois: 0,
        questionQuatre: 'TOTALEMENT_EN_ACCORD',
        questionCinq: 'TOTALEMENT_EN_ACCORD',
        questionSix: 'TOTALEMENT_EN_ACCORD',
        questionSept: 'TOTALEMENT_EN_ACCORD',
        salaireStagiaire: 0,
        questionHuit: 'TOTALEMENT_EN_ACCORD',
        questionNeuf: 'TOTALEMENT_EN_ACCORD',
        questionDix: 'TOTALEMENT_EN_ACCORD',
        commentaires: '',
        questionOnze: 'Premier stage',
        questionDouze: 'Un stagiaire',
        questionTreize: 'true',
        questionQuinze: 'true',
        questionQuatorzeHeuresUnA: 0,
        questionQuatorzeHeuresUnB: 0,
        questionQuatorzeHeuresUnC: 0,
        questionQuatorzeHeuresUnD: 0,
        questionQuatorzeHeuresUnE: 0,
        questionQuatorzeHeuresUnF: 0
    });

    const choixAccords = {
        TOTALEMENT_EN_ACCORD: ["TOTALEMENT_EN_ACCORD", "Totalement en accord"],
        PLUTOT_EN_ACCORD: ['PLUTOT_EN_ACCORD', 'Plûtot en accord'],
        PLUTOT_EN_DESACCORD: ['PLUTOT_EN_DESACCORD', 'Plûtot en désaccord'],
        TOTALEMENT_EN_DESACCORD: ['TOTALEMENT_EN_DESACCORD', 'Totalement en désaccord'],
        IMPOSSIBLE_DE_SE_PRONONCER: ['IMPOSSIBLE_DE_SE_PRONONCER', 'Impossible de se prononcer']
    }

    const choixStagiaires = {
        UN_STAGIAIRE: ['Un stagiaire'],
        DEUX_STAGIAIRES: ['Deux stagiaires'],
        TROIS_STAGIAIRES: ['Trois stagiaires'],
        PLUS_DE_TROIS_STAGIAIRES: ['Plus de trois stagiaires'],
    }

    const choixStage = {
        UN_STAGIAIRE: ['Premier stage'],
        DEUX_STAGIAIRES: ['Deuxième stage']
    }

    const yesAndNoAnswers = {
        OUI: ['true', 'Oui'],
        NON: ['false', 'Non']
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

    const validateByNameSelection = (tagName, name, value, element, title) => {
        let isValid = true;
        tagName = tagName.toLowerCase();

        if (tagName === 'select') {
            if (name.indexOf('question') >= 0) {
                if (name.indexOf("Douze") >= 0) {
                    isValid = applyValidationStyleBasedOnChoices(value, name, choixStagiaires);
                } else if (name.indexOf("Quinze") >= 0 || name.indexOf("Treize") >= 0) {
                    isValid = applyValidationStyleBasedOnChoices(value, name, yesAndNoAnswers);
                } else if (name.indexOf("Onze") >= 0) {
                    isValid = applyValidationStyleBasedOnChoices(value, name, choixStage);
                } else {
                    isValid = applyValidationStyleBasedOnChoices(value, name, choixAccords);
                }
            }
        } else if (tagName === 'input') {
            const {type} = element;

            if (type === 'number') {
                if (parseInt(value) === 0) {
                    isValid = false;
                    elementIsWrong(name);
                } else
                    elementIsRight(name);
            } else if (type === 'text' || type === 'date') {
                if (name.indexOf("matricule") >= 0) {
                    if (regexMatriculeEtudiant.test(value))
                        elementIsRight(name);
                    else {
                        elementIsWrong(name);
                        isValid = false;
                    }
                } else if (value === '') {
                    elementIsWrong(name);
                    isValid = false;
                } else {
                    elementIsRight(name);
                }
            }
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

        setVisitForm(prevalue => {
            return {
                ...prevalue,
                [name]: value
            }
        })
    }

    const sendEvaluation = (e) => {
        e.preventDefault();
        setErrors({});
        let isValid = true;
        for (const key in visitForm) {
            const element = document.getElementsByName(key)[0];
            const {tagName, title} = element;

            if (!validateByNameSelection(tagName, key, visitForm[key], element, title)) {
                isValid = false;
            }
        }
        if (!isValid) {
            swalErr.fire({text: "Veuillez remplir tous les champs requis!"}).then();
        } else {
            supervisorCreateForm(visitForm).then();
        }
    };

    return <div>
        <h1 className='text-center text-decoration-underline'>Évaluation de stage</h1>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='mt-4 mb-0 text-decoration-underline'>Identification de l'entreprise</h4>
            <FormGroup>
                <FormField>
                    <label>Matricule Étudiant</label>
                    <input type='text' placeholder="La matricule de l'étudiant (7 chiffres)"
                           title="La matricule de l'étudiant est requis" name='matriculeEtudiant'
                           value={visitForm.matriculeEtudiant}
                           onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Nom de l'entreprise</label>
                    <input type="text" title="Le nom de l'entreprise est requis" name='entrepriseNom'
                           value={visitForm.entrepriseNom}
                           onChange={e => handleChange(e)} autoComplete='off' placeholder="Nom de l'entreprise"/>
                </FormField>
                <FormField>
                    <label>Personne contact</label>
                    <input type="text" title="La personne contact est requis" name='personneContact'
                           value={visitForm.personneContact}
                           onChange={e => handleChange(e)} placeholder="Personne contact"/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Téléphone</label>
                    <input type="text" name='phone' title="Le numéro de téléphone est requis" value={visitForm.phone}
                           onChange={e => handleChange(e)}
                           autoComplete='off' placeholder="Téléphone"/>
                </FormField>
                <FormField>
                    <label>Télécopieur</label>
                    <input type="text" name='telecopieur' title="Le télécopieur est requis"
                           value={visitForm.telecopieur} onChange={e => handleChange(e)}
                           autoComplete='off' placeholder="Télécopieur"/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Adresse</label>
                    <input type="text" name='adresse' title="L'adresse est requis" value={visitForm.adresse}
                           onChange={e => handleChange(e)}
                           placeholder="Adresse"/>
                </FormField>
                <FormField>
                    <label>Code postal</label>
                    <input type="text" name='zip' title="Le code postal est requis" value={visitForm.zip}
                           onChange={e => handleChange(e)}
                           placeholder="Code postal"/>
                </FormField>
                <FormField>
                    <label>Ville</label>
                    <input type="text" name='ville' title="La ville est requis" value={visitForm.ville}
                           onChange={e => handleChange(e)}
                           placeholder="Ville"/>
                </FormField>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='mt-4 mb-0 text-decoration-underline'>Identification du stagiaire</h4>
            <FormGroup>
                <FormField>
                    <label>Nom du stagiaire</label>
                    <input type="text" name='nomStagiaire' value={visitForm.nomStagiaire}
                           title="Le nom du stagiaire est requis"
                           onChange={e => handleChange(e)}
                           placeholder="Entrez le nom du stagiare"/>
                </FormField>
                <FormField>
                    <label>Date du stage</label>
                    <input type="date" name='dateStage' title="La date du stage est requis" value={visitForm.dateStage}
                           onChange={e => handleChange(e)}
                           placeholder="Date du stage"/>
                </FormField>
                <FormField>
                    <label className='d-flex justify-content-center'>Stage</label>
                    <input name='stageCourant' type='number' min='1' max='2' title="Le numéro du stage est requis"
                           value={visitForm.stageCourant}
                           onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='fw-bold p-2 rounded mt-4 mb-0 text-decoration-underline'>Évaluation</h4>
            <FormGroup>
                <FormField>
                    <label>Les tâches confiées au stagiaire sont conformes aux tâches annoncées dans l’entente de
                        stage?</label>
                    <select name='questionUn' title="Le champ 'Les tâches confiées au stagiaire sont conformes aux tâches annoncées dans l’entente de
                        stage' doit être rempli" value={visitForm.questionUn} onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Des mesures d’accueil facilitent l’intégration du nouveau stagiaire?</label>
                    <select name='questionDeux' value={visitForm.questionDeux}
                            title="Le champ 'Des mesures d’accueil facilitent l’intégration du nouveau stagiaire' doit être rempli"
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) =>
                            <option key={index} value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Le temps réel consacré à l’encadrement du stagiaire est suffisant?</label>
                    <select name='questionTrois'
                            title="Le champ 'Le temps réel consacré à l’encadrement du stagiaire est suffisant' doit être rempli"
                            value={visitForm.questionTrois}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Nombre d'heures pour le 1er mois?</label>
                    <input type="number" name="nbHeuresMoisUn" min={0} value={visitForm.nbHeuresMoisUn}
                           title="Le champ 'Nombre d'heures pour le 1ier mois' doit être rempli"
                           onChange={e => handleChange(e)} placeholder="Nombre d'heures"/>
                </FormField>
                <FormField>
                    <label>Nombre d'heures pour le 2ième mois?</label>
                    <input type="number" min={0} name='nbHeuresMoisDeux'
                           title="Le champ 'Nombre d'heures pour le 2ième mois' doit être rempli"
                           value={visitForm.nbHeuresMoisDeux} onChange={e => handleChange(e)}
                           placeholder="Nombre d'heures"/>
                </FormField>
                <FormField>
                    <label>Nombre d'heures pour le 3ième mois?</label>
                    <input type="number" min={0} name='nbHeuresMoisTrois'
                           title="Le champ 'Nombre d'heures pour le 3ième mois' doit être rempli"
                           value={visitForm.nbHeuresMoisTrois} onChange={e => handleChange(e)}
                           placeholder="Nombre d'heures"/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>L’environnement de travail respecte les normes d’hygiène et de sécurité au travail?</label>
                    <select name='questionQuatre'
                            title="Le champ 'L’environnement de travail respecte les normes d’hygiène et de sécurité au travail?' doit être rempli"
                            value={visitForm.questionQuatre}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Le climat de travail est agréable?</label>
                    <select name='questionCinq' title="Le champ 'Le climat de travail est agréable?' doit être rempli"
                            value={visitForm.questionCinq}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Le milieu de stage est accessible par transport en commun?</label>
                    <select name='questionSix'
                            title="Le champ 'Le milieu de stage est accessible par transport en commun' doit être rempli"
                            value={visitForm.questionSix} onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Le salaire offert est intéressant pour le stagiaire?</label>
                    <select name='questionSept'
                            title="Le champ 'Le salaire offert est intéressant pour le stagiaire' doit être rempli"
                            value={visitForm.questionSept}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une réponse</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Préciser le salaire par heure</label>
                    <input type="number" min={0} name='salaireStagiaire' title="Le salaire du stagiaire est requis"
                           value={visitForm.salaireStagiaire}
                           onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>La communication avec le superviseur de stage facilite le déroulement du stage?</label>
                    <select name="questionHuit"
                            title="Le champ 'La communication avec le superviseur de stage facilite le déroulement du stage' doit être rempli"
                            value={visitForm.questionHuit}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>

                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>L’équipement fourni est adéquat pour réaliser les tâches confiées?</label>
                    <select name='questionNeuf' value={visitForm.questionNeuf}
                            onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Le volume de travail est acceptable?</label>
                    <select name='questionDix' value={visitForm.questionDix} onChange={e => handleChange(e)}>
                        <option disabled value="">Choisiser une évaluation</option>
                        {Object.values(choixAccords).map((choix, index) => <option key={index}
                                                                                   value={choix[0]}>{choix[1]}</option>)}
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Commentaires</label>
                    <textarea name='commentaires' value={visitForm.commentaires} onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='fw-bold p-2 rounded mt-4 mb-0 text-decoration-underline'>Observations générales</h4>
            <FormGroup>
                <FormField>
                    <label>Ce milieu est à privilégier pour le stage</label>
                    <select name='questionOnze' value={visitForm.questionOnze}
                            onChange={e => handleChange(e)}>
                        <option value="" disabled>Choisissez une option</option>
                        {Object.values(choixStage).map((choix, index) => <option key={index}
                                                                                 value={choix[0]}>{choix[0]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label>Ce milieu est ouvert à accueillir deux stagiaires
                    </label>
                    <select name='questionDouze' value={visitForm.questionDouze}
                            onChange={e => handleChange(e)}>
                        <option value="" disabled>Choisissez une option</option>
                        {Object.values(choixStagiaires).map((choix, index) => <option key={index}
                                                                                      value={choix[0]}>{choix[0]}</option>)}
                    </select>
                </FormField>
                <FormField>
                    <label className='d-flex justify-content-center'>Ce milieu désire accueillir le même stagiaire pour
                        un prochain stage</label>
                    <select name='questionTreize' value={visitForm.questionTreize}
                            title="Le choix oui ou non pour le désir d'accueillir le même stagiaire doit être rempli"
                            onChange={e => handleChange(e)}>
                        <option value="" disabled>Choisissez une option</option>
                        {Object.values(yesAndNoAnswers).map((choix, index) =>
                            <option key={index} value={choix[0]}>{choix[1]}</option>
                        )}
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup repartition={[12, 12]}>
                <h5>Ce milieu offre des quarts de travail variables?</h5>
                <FormField>
                    <label/>
                    <select name='questionQuinze' value={visitForm.questionQuinze}
                            title={'Le choix oui ou non pour les quarts variables doit être rempli'}
                            onChange={e => handleChange(e)}>
                        <option value="" disabled>Choisissez une option</option>
                        {Object.values(yesAndNoAnswers).map((choix, index) =>
                            <option key={index} value={choix[0]}>{choix[1]}</option>
                        )}
                    </select>
                </FormField>
            </FormGroup>

            <FormGroup>
                <FormField>
                    <label>De</label>
                    <input type="number" name="questionQuatorzeHeuresUnA" min={1} max={24}
                           value={visitForm.questionQuatorzeHeuresUnA} onChange={e => handleChange(e)}/>
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="questionQuatorzeHeuresUnB" min={1} max={24}
                           value={visitForm.questionQuatorzeHeuresUnB} onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>De</label>
                    <input type="number" name="questionQuatorzeHeuresUnC" min={1} max={24}
                           value={visitForm.questionQuatorzeHeuresUnC} onChange={e => handleChange(e)}/>
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="questionQuatorzeHeuresUnD" min={1} max={24}
                           value={visitForm.questionQuatorzeHeuresUnD} onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>De</label>
                    <input type="number" name="questionQuatorzeHeuresUnE" min={1} max={24}
                           value={visitForm.questionQuatorzeHeuresUnE} onChange={e => handleChange(e)}/>
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="questionQuatorzeHeuresUnF" min={1} max={24}
                           value={visitForm.questionQuatorzeHeuresUnF} onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Signature de l'enseignant responsable du stagiaire</label>
                    <input type="text" placeholder='Signature du superviseur'
                           title="La signature du superviseur est requise" name="signatureSuperviseur"
                           value={visitForm.signatureSuperviseur}
                           onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            {Object.keys(errors).length > 0 ?
                <>
                    <h4 className={'my-3'}>Voici la liste des champs à remplir</h4>
                    <ul>
                        {Object.keys(errors).map((key, index) => <li
                            key={index}>{errors[key]}</li>)}
                    </ul>
                </>
                : <div></div>
            }
        </div>
        <button onClick={sendEvaluation} className='btn btn-primary w-100 mt-4'>Créer un formulaire de visite</button>
    </div>
}
