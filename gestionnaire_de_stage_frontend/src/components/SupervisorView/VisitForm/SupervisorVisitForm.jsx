import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {useState} from "react";
import {supervisorCreateForm} from "../../../services/stage-service";


export default function SupervisorVisitForm() {

    const [visitForm, setVisitForm] = useState({
        entrepriseNom: '',
        matriculeEtudiant: 0,
        signatureSuperviseur: '',
        personneContact: '',
        phone: '',
        telecopieur: '',
        adresse: '',
        zip: '',
        ville: '',
        nomStagiaire: '',
        dateStage: undefined,
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
        questionTreize: '',
        questionQuatorzeHeuresUnA: '',
        questionQuatorzeHeuresUnB: '',
        questionQuatorzeHeuresUnC: '',
        questionQuatorzeHeuresUnD: '',
        questionQuatorzeHeuresUnE: '',
        questionQuatorzeHeuresUnF: '',
        questionQuinze: ''
    });

    const handleChange = (event) => {
        const {value, name} = event.target;

        setVisitForm((prevalue) => {
            return {
                ...prevalue,
                [name]: value
            }
        })
    }

    function convertAllNumberFieldsToNumbers() {
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

    const sendVisitForm = (e) => {
        e.preventDefault();
        convertAllNumberFieldsToNumbers();
        supervisorCreateForm(visitForm).then();
    };

    return <form onSubmit={sendVisitForm}>
        <h1 className='text-center text-decoration-underline'>Évaluation de stage</h1>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='mt-4 mb-0 text-decoration-underline'>Identification de l'entreprise</h4>
            <FormGroup>
                <FormField>
                    <label>Nom de l'entreprise</label>
                    <input type="text" name='entrepriseNom' value={visitForm.entrepriseNom}
                           onChange={e => handleChange(e)} autoComplete='off' placeholder="Nom de l'entreprise"
                           required/>
                </FormField>
                <FormField>
                    <label>Personne contact</label>
                    <input type="text" name='personneContact' value={visitForm.personneContact}
                           onChange={e => handleChange(e)} placeholder="Personne contact" required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Téléphone</label>
                    <input type="text" name='phone' value={visitForm.phone} onChange={e => handleChange(e)}
                           autoComplete='off' placeholder="Téléphone" required/>
                </FormField>
                <FormField>
                    <label>Télécopieur</label>
                    <input type="text" name='telecopieur' value={visitForm.telecopieur} onChange={e => handleChange(e)}
                           autoComplete='off' placeholder="Télécopieur" required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Adresse</label>
                    <input type="text" name='adresse' value={visitForm.adresse} onChange={e => handleChange(e)}
                           placeholder="Adresse" required/>
                </FormField>
                <FormField>
                    <label>Code postal</label>
                    <input type="text" name='zip' value={visitForm.zip} onChange={e => handleChange(e)}
                           placeholder="Code postal" required/>
                </FormField>
                <FormField>
                    <label>Ville</label>
                    <input type="text" name='ville' value={visitForm.ville} onChange={e => handleChange(e)}
                           placeholder="Ville" required/>
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
                           onChange={e => handleChange(e)} title='Entrez votre numéro de téléphone'
                           placeholder="Entrez le nom du stagiare" required/>
                </FormField>
                <FormField>
                    <label>Date du stage</label>
                    <input type="date" name='dateStage' value={visitForm.dateStage} onChange={e => handleChange(e)}
                           placeholder="Date du stage" required/>
                </FormField>
                <>
                    <label className='d-flex justify-content-center'>Stage</label>
                    <div className='d-flex justify-content-around align-items-center flex-row'>
                        <input type="radio" onChange={e => handleChange(e)} name='stage' value='1' required/>
                        <label>1</label>
                        <input type="radio" onChange={e => handleChange(e)} name='stage' value='2' required/>
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
                    <select required name='questionUn' value={visitForm.questionUn} onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Des mesures d’accueil facilitent l’intégration du nouveau stagiaire?</label>
                    <select required name='questionDeux' value={visitForm.questionDeux}
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
                    <select required name='questionTrois' value={visitForm.questionTrois}
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
                    <input type="number" min='0' max='240' name="nbHeuresMoisUn" value={visitForm.nbHeuresMoisUn}
                           onChange={e => handleChange(e)} placeholder="Nombre d'heures" required/>
                </FormField>
                <FormField>
                    <label>Nombre d'heures pour le 2ième mois?</label>
                    <input type="number" min='0' max='240' name='nbHeuresMoisDeux'
                           value={visitForm.nbHeuresMoisDeux} onChange={e => handleChange(e)}
                           placeholder="Nombre d'heures" required/>
                </FormField>
                <FormField>
                    <label>Nombre d'heures pour le 3ième mois?</label>
                    <input type="number" max='240' name='nbHeuresMoisTrois' min='0'
                           value={visitForm.nbHeuresMoisTrois} onChange={e => handleChange(e)}
                           placeholder="Nombre d'heures" required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>L’environnement de travail respecte les normes d’hygiène et de sécurité au travail?</label>
                    <select required name='questionQuatre' value={visitForm.questionQuatre}
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
                    <select required name='questionCinq' value={visitForm.questionCinq}
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
                    <select required name='questionSix' value={visitForm.questionSix} onChange={e => handleChange(e)}>
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
                    <select required name='questionSept' value={visitForm.questionSept}
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
                    <input required type="number" min='10' name='salaireStagiaire' value={visitForm.salaireStagiaire}
                           onChange={(e) => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>La communication avec le superviseur de stage facilite le déroulement du stage?</label>
                    <select required name="questionHuit" value={visitForm.questionHuit}
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
                    <select required name='questionNeuf' value={visitForm.questionNeuf}
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
                    <select required name='questionDix' value={visitForm.questionDix} onChange={e => handleChange(e)}>
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
                    <select required name='questionOnze' value={visitForm.questionOnze}
                            onChange={e => handleChange(e)}>
                        <option value="Choisissez une option" selected disabled>Choisissez une option</option>
                        <option value='Premier stage'>Premier stage</option>
                        <option value='Deuxième stage'>Deuxième stage</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Ce milieu est ouvert à accueillir deux stagiaires
                    </label>
                    <select required name='questionDouze' value={visitForm.questionDouze}
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
                               required/>
                        <label>Oui</label>
                        <input type="radio" onChange={e => handleChange(e)} name='questionTreize' value='Non'
                               required/>
                        <label>Non</label>
                    </div>
                </>
            </FormGroup>
            <FormGroup repartition={[12, 6, 6]}>
                <h5>Ce milieu offre des quarts de travail variables?</h5>
                <FormField>
                    <label>De</label>
                    <input type="number" name="questionQuatorzeHeuresUnA"
                           value={visitForm.questionQuatorzeHeuresUnA} onChange={e => handleChange(e)} required/>
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="questionQuatorzeHeuresUnB"
                           value={visitForm.questionQuatorzeHeuresUnB} onChange={e => handleChange(e)} required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>De</label>
                    <input type="number" name="questionQuatorzeHeuresUnC"
                           value={visitForm.questionQuatorzeHeuresUnC} onChange={e => handleChange(e)} required/>
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="questionQuatorzeHeuresUnD"
                           value={visitForm.questionQuatorzeHeuresUnD} onChange={e => handleChange(e)} required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>De</label>
                    <input type="number" name="questionQuatorzeHeuresUnE"
                           value={visitForm.questionQuatorzeHeuresUnE} onChange={e => handleChange(e)} required/>
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="questionQuatorzeHeuresUnF"
                           value={visitForm.questionQuatorzeHeuresUnF} onChange={e => handleChange(e)} required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <>
                    <label/>
                    <div className='d-flex justify-content-around align-items-center flex-row'>
                        <input type="radio" onChange={e => handleChange(e)} name='questionQuinze' value='Oui'
                               required/>
                        <label>Oui</label>
                        <input type="radio" onChange={e => handleChange(e)} name='questionQuinze' value='Non'
                               required/>
                        <label>Non</label>
                    </div>
                </>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Signature de l'enseignant responsable du stagiaire</label>
                    <input type="text" name="signatureSuperviseur" value={visitForm.signatureSuperviseur} onChange={e => handleChange(e)} required/>
                </FormField>
            </FormGroup>
        </div>
        <button type='submit' className='btn btn-primary w-100 mt-4'>Créer un formulaire de visite</button>
    </form>
}
