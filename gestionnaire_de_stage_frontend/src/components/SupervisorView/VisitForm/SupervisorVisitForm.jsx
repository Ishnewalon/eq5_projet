import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {useState} from "react";
import {supervisorCreateForm} from "../../../services/stage-service";
import Swal from "sweetalert2";


export default function SupervisorVisitForm() {

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
        questionTreize: 'Oui',
        questionQuatorzeHeuresUnA: '',
        questionQuatorzeHeuresUnB: '',
        questionQuatorzeHeuresUnC: '',
        questionQuatorzeHeuresUnD: '',
        questionQuatorzeHeuresUnE: '',
        questionQuatorzeHeuresUnF: '',
        questionQuinze: ''
    });

    const handleChange = (e) => {
        const {value, name} = e.target;

        setVisitForm(prevalue => {
            return {
                ...prevalue,
                [name]: value
            }
        })
    }

    const convertAllNumberFieldsToNumbers = () => {
        Object.keys(visitForm).forEach(key => {
            if (!isNaN(visitForm[key])) {
                setVisitForm((prevalue) => {
                    return {
                        ...prevalue,
                        [key]: parseInt(visitForm[key])
                    }
                })
            }
        })
    }

    const getAllFormErrors = () => {
        const formErrors = {};
        if (visitForm.matriculeEtudiant.trim().length === 0) {
            formErrors.matriculeEtudiant ='La matricule de l\'étudiant est requis';
        }

        if (visitForm.entrepriseNom.trim().length === 0) {
            formErrors.entrepriseNom= 'Le nom de l\'entreprise est requis';
        }

        if (visitForm.personneContact.trim().length === 0) {
            formErrors.personneContact = 'La personne contact est requis';
        }

        if (visitForm.phone.trim().length === 0) {
            formErrors.phone ='Le numéro de téléphone est requis';
        }

        if (visitForm.telecopieur.trim().length === 0) {
            formErrors.telecopieur = 'Le numéro du télécopieur est requis';
        }

        if (visitForm.adresse.trim().length === 0) {
            formErrors.adresse = 'L\'adresse est requis';
        }

        if (visitForm.zip.trim().length === 0) {
            formErrors.zip = 'Le code postal est requis';
        }

        if (visitForm.ville.trim().length === 0) {
            formErrors.ville = 'La ville est requis';
        }

        if (visitForm.nomStagiaire.trim().length === 0) {
            formErrors.nomStagiaire ='Le nom du stagiaire est requis';
        }

        if (visitForm.dateStage.trim().length === 0) {
            formErrors.dateStage = 'La date de stage est requis';
        }

        if (visitForm.nbHeuresMoisUn === 0) {
            formErrors.nbHeuresMoisUn ='Le nombre d\'heures du premier mois est requis';
        }

        if (visitForm.nbHeuresMoisDeux === 0) {
            formErrors.nbHeuresMoisDeux ='Le nombre d\'heures du deuxième mois est requis';
        }

        if (visitForm.nbHeuresMoisTrois === 0) {
            formErrors.nbHeuresMoisTrois = 'Le nombre d\'heures du troisième mois est requis';
        }

        if (visitForm.salaireStagiaire === 0) {
            formErrors.salaireStagiaire = 'Le salaire du stagiaire est requis';
        }

        if (visitForm.questionQuatorzeHeuresUnA.trim().length === 0) {
            formErrors.questionQuatorzeHeuresUnA = '1. L\'heure de départ est requis';
        }

        if (visitForm.questionQuatorzeHeuresUnB.trim().length === 0) {
            formErrors.questionQuatorzeHeuresUnB ='1. L\'heure de fin est requis';
        }

        if (visitForm.questionQuatorzeHeuresUnC.trim().length === 0) {
            formErrors.questionQuatorzeHeuresUnC ='2. L\'heure de départ est requis';
        }

        if (visitForm.questionQuatorzeHeuresUnD.trim().length === 0) {
            formErrors.questionQuatorzeHeuresUnD = '2. L\'heure de fin est requis';
        }

        if (visitForm.questionQuatorzeHeuresUnE.trim().length === 0) {
            formErrors.questionQuatorzeHeuresUnE = '3. L\'heure de départ est requis';
        }

        if (visitForm.questionQuatorzeHeuresUnF.trim().length === 0) {
            formErrors.questionQuatorzeHeuresUnF = '3. L\'heure de fin est requis';
        }

        if (visitForm.signatureSuperviseur.trim().length === 0) {
            formErrors.signatureSuperviseur ='La signature du superviseur est requis';
        }

        return [Object.keys(formErrors), Object.values(formErrors)];
    }

    const sendVisitForm = (e) => {
        e.preventDefault();
        const [, values] = getAllFormErrors();

        if (values.length === 0) {
            convertAllNumberFieldsToNumbers();
            supervisorCreateForm(visitForm).then(() => document.forms[0].reset());
        }else{
            Swal.fire({title:'Champ requis', text:`${values[0]} et ${values.length} autres...`, icon: 'error'} ).then();
        }
    };

    return <form onSubmit={sendVisitForm}>
        <h1 className='text-center text-decoration-underline'>Évaluation de stage</h1>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='mt-4 mb-0 text-decoration-underline'>Identification de l'entreprise</h4>
            <FormGroup>
                <FormField>
                    <label>Matricule Étudiant</label>
                    <input type='text' name='matriculeEtudiant' value={visitForm.matriculeEtudiant}
                           onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Nom de l'entreprise</label>
                    <input type="text" name='entrepriseNom' value={visitForm.entrepriseNom}
                           onChange={e => handleChange(e)} autoComplete='off' placeholder="Nom de l'entreprise" />
                </FormField>
                <FormField>
                    <label>Personne contact</label>
                    <input type="text" name='personneContact' value={visitForm.personneContact}
                           onChange={e => handleChange(e)} placeholder="Personne contact"/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Téléphone</label>
                    <input type="text" name='phone' value={visitForm.phone} onChange={e => handleChange(e)}
                           autoComplete='off' placeholder="Téléphone"/>
                </FormField>
                <FormField>
                    <label>Télécopieur</label>
                    <input type="text" name='telecopieur' value={visitForm.telecopieur} onChange={e => handleChange(e)}
                           autoComplete='off' placeholder="Télécopieur"/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Adresse</label>
                    <input type="text" name='adresse' value={visitForm.adresse} onChange={e => handleChange(e)}
                           placeholder="Adresse"/>
                </FormField>
                <FormField>
                    <label>Code postal</label>
                    <input type="text" name='zip' value={visitForm.zip} onChange={e => handleChange(e)}
                           placeholder="Code postal"/>
                </FormField>
                <FormField>
                    <label>Ville</label>
                    <input type="text" name='ville' value={visitForm.ville} onChange={e => handleChange(e)}
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
                           onChange={e => handleChange(e)}
                           placeholder="Entrez le nom du stagiare"/>
                </FormField>
                <FormField>
                    <label>Date du stage</label>
                    <input type="date" name='dateStage' value={visitForm.dateStage} onChange={e => handleChange(e)}
                           placeholder="Date du stage"/>
                </FormField>
                <>
                    <label className='d-flex justify-content-center'>Stage</label>
                    <div className='d-flex justify-content-around align-items-center flex-row'>
                        <input type="radio" onChange={e => handleChange(e)} name='stage' value='1'/>
                        <label>1</label>
                        <input type="radio" onChange={e => handleChange(e)} name='stage' value='2'/>
                        <label>2</label>
                    </div>
                </>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='fw-bold p-2 rounded mt-4 mb-0 text-decoration-underline'>Évaluation</h4>
            <FormGroup>
                <FormField>
                    <label>Les tâches confiées au stagiaire sont conformes aux tâches annoncées dans l’entente de
                        stage?</label>
                    <select name='questionUn' value={visitForm.questionUn} onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Des mesures d’accueil facilitent l’intégration du nouveau stagiaire?</label>
                    <select name='questionDeux' value={visitForm.questionDeux}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Le temps réel consacré à l’encadrement du stagiaire est suffisant?</label>
                    <select name='questionTrois' value={visitForm.questionTrois}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Nombre d'heures pour le 1er mois?</label>
                    <input type="number" name="nbHeuresMoisUn" value={visitForm.nbHeuresMoisUn}
                           onChange={e => handleChange(e)} placeholder="Nombre d'heures"/>
                </FormField>
                <FormField>
                    <label>Nombre d'heures pour le 2ième mois?</label>
                    <input type="number" name='nbHeuresMoisDeux'
                           value={visitForm.nbHeuresMoisDeux} onChange={e => handleChange(e)}
                           placeholder="Nombre d'heures"/>
                </FormField>
                <FormField>
                    <label>Nombre d'heures pour le 3ième mois?</label>
                    <input type="number" name='nbHeuresMoisTrois'
                           value={visitForm.nbHeuresMoisTrois} onChange={e => handleChange(e)}
                           placeholder="Nombre d'heures"/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>L’environnement de travail respecte les normes d’hygiène et de sécurité au travail?</label>
                    <select name='questionQuatre' value={visitForm.questionQuatre}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Le climat de travail est agréable?</label>
                    <select name='questionCinq' value={visitForm.questionCinq}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Le milieu de stage est accessible par transport en commun?</label>
                    <select name='questionSix' value={visitForm.questionSix} onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Le salaire offert est intéressant pour le stagiaire?</label>
                    <select name='questionSept' value={visitForm.questionSept}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Préciser le salaire par heure</label>
                    <input type="number" name='salaireStagiaire' value={visitForm.salaireStagiaire}
                           onChange={(e) => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>La communication avec le superviseur de stage facilite le déroulement du stage?</label>
                    <select name="questionHuit" value={visitForm.questionHuit}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>L’équipement fourni est adéquat pour réaliser les tâches confiées?</label>
                    <select name='questionNeuf' value={visitForm.questionNeuf}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Le volume de travail est acceptable?</label>
                    <select name='questionDix' value={visitForm.questionDix} onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
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
                        <option value="Choisissez une option" selected disabled>Choisissez une option</option>
                        <option value='Premier stage'>Premier stage</option>
                        <option value='Deuxième stage'>Deuxième stage</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Ce milieu est ouvert à accueillir deux stagiaires
                    </label>
                    <select name='questionDouze' value={visitForm.questionDouze}
                            onChange={e => handleChange(e)}>
                        <option value="Choisissez une option" selected disabled>Choisissez une option</option>
                        <option value='Un stagiaire'>Un stagiaire</option>
                        <option value='Deux stagiaires'>Deux stagiaires</option>
                        <option value='Trois stagiaires'>Trois stagiaires</option>
                        <option value='Plus de trois stagiaires'>Plus de trois stagiaires</option>
                    </select>
                </FormField>
                <>
                    <label className='d-flex justify-content-center'>Ce milieu désire accueillir le même stagiaire pour
                        un prochain stage</label>
                    <div className='d-flex justify-content-around align-items-center flex-row'>
                        <input type="radio" onChange={e => handleChange(e)} name='questionTreize' value='Oui'
                        />
                        <label>Oui</label>
                        <input type="radio" onChange={e => handleChange(e)} name='questionTreize' value='Non'
                        />
                        <label>Non</label>
                    </div>
                </>
            </FormGroup>
            <FormGroup repartition={[12, 6, 6]}>
                <h5>Ce milieu offre des quarts de travail variables?</h5>
                <FormField>
                    <label>De</label>
                    <input type="number" name="questionQuatorzeHeuresUnA"
                           value={visitForm.questionQuatorzeHeuresUnA} onChange={e => handleChange(e)}/>
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="questionQuatorzeHeuresUnB"
                           value={visitForm.questionQuatorzeHeuresUnB} onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>De</label>
                    <input type="number" name="questionQuatorzeHeuresUnC"
                           value={visitForm.questionQuatorzeHeuresUnC} onChange={e => handleChange(e)}/>
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="questionQuatorzeHeuresUnD"
                           value={visitForm.questionQuatorzeHeuresUnD} onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>De</label>
                    <input type="number" name="questionQuatorzeHeuresUnE"
                           value={visitForm.questionQuatorzeHeuresUnE} onChange={e => handleChange(e)}/>
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="questionQuatorzeHeuresUnF"
                           value={visitForm.questionQuatorzeHeuresUnF} onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <>
                    <label/>
                    <div className='d-flex justify-content-around align-items-center flex-row'>
                        <input type="radio" onChange={e => handleChange(e)} name='questionQuinze' value='Oui'
                        />
                        <label>Oui</label>
                        <input type="radio" onChange={e => handleChange(e)} name='questionQuinze' value='Non'
                        />
                        <label>Non</label>
                    </div>
                </>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Signature de l'enseignant responsable du stagiaire</label>
                    <input type="text" name="signatureSuperviseur" value={visitForm.signatureSuperviseur}
                           onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
        </div>
        <button type='submit' className='btn btn-primary w-100 mt-4'>Créer un formulaire de visite</button>
    </form>
}
