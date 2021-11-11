import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {useState} from "react";


export default function VisitForm() {

    const [visitForm, setVisitForm] = useState({
        entrepriseNom: '',
        personne_contact: '',
        phone: '',
        telecopieur: '',
        adresse: '',
        zip: '',
        ville: '',
        nom_stagiaire: '',
        date_stage: undefined,
        stageCourant: 1,
        question_un: 'TOTALEMENT_EN_ACCORD',
        question_deux: 'TOTALEMENT_EN_ACCORD',
        question_trois: 'TOTALEMENT_EN_ACCORD',
        nb_heures_mois_un: 0,
        nb_heures_mois_deux: 0,
        nb_heures_mois_trois: 0,
        question_quatre: 'TOTALEMENT_EN_ACCORD',
        question_cinq: 'TOTALEMENT_EN_ACCORD',
        question_six: 'TOTALEMENT_EN_ACCORD',
        question_sept: 'TOTALEMENT_EN_ACCORD',
        salaire_stagiaire: 0,
        question_huit: 'TOTALEMENT_EN_ACCORD',
        question_neuf: 'TOTALEMENT_EN_ACCORD',
        question_dix: 'TOTALEMENT_EN_ACCORD',
        commentaires: '',
        question_onze: 'Premier stage',
        question_douze: 'Un stagiaire',
        question_treize: '',
        question_quatorze_heures_un_a: '',
        question_quatorze_heures_un_b: '',
        question_quatorze_heures_un_c: '',
        question_quatorze_heures_un_d: '',
        question_quatorze_heures_un_e: '',
        question_quatorze_heures_un_f: '',
        question_quinze: ''
    });

    const handleChange = (event) => {
        let value = event.target.value;
        let name = event.target.name;

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

    };

    return <form onSubmit={sendVisitForm}>
        <h1 className='text-center text-decoration-underline'>Évaluation de stage</h1>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='mt-4 mb-0 text-decoration-underline'>Identification de l'entreprise</h4>
            <FormGroup>
                <FormField>
                    <label>Nom de l'entreprise</label>
                    <input type="text" name='entrepriseNom' value={visitForm.entrepriseNom} onChange={e => handleChange(e)} autoComplete='off' placeholder="Nom de l'entreprise"
                           required/>
                </FormField>
                <FormField>
                    <label>Personne contact</label>
                    <input type="text" name='personne_contact' value={visitForm.personne_contact} onChange={e => handleChange(e)} placeholder="Personne contact" required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Téléphone</label>
                    <input type="text" name='phone' value={visitForm.phone} onChange={e => handleChange(e)} autoComplete='off' placeholder="Téléphone" required/>
                </FormField>
                <FormField>
                    <label>Télécopieur</label>
                    <input type="text" name='telecopieur' value={visitForm.telecopieur} onChange={e => handleChange(e)} autoComplete='off' placeholder="Télécopieur" required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Adresse</label>
                    <input type="text" name='adresse' value={visitForm.adresse} onChange={e => handleChange(e)} placeholder="Adresse" required/>
                </FormField>
                <FormField>
                    <label>Code postal</label>
                    <input type="text" name='zip' value={visitForm.zip} onChange={e => handleChange(e)} placeholder="Code postal" required/>
                </FormField>
                <FormField>
                    <label>Ville</label>
                    <input type="text" name='ville' value={visitForm.ville} onChange={e => handleChange(e)} placeholder="Ville" required/>
                </FormField>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='mt-4 mb-0 text-decoration-underline'>Identification du stagiaire</h4>
            <FormGroup>
                <FormField>
                    <label>Nom du stagiaire</label>
                    <input type="text" name='nom_stagiaire' value={visitForm.nom_stagiaire} onChange={e => handleChange(e)} title='Entrez votre numéro de téléphone'
                           placeholder="Entrez le nom du stagiare" required/>
                </FormField>
                <FormField>
                    <label>Date du stage</label>
                    <input type="date" name='date_stage' value={visitForm.date_stage} onChange={e => handleChange(e)} placeholder="Date du stage" required/>
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
            <h4 className='fw-bold p-2 rounded mt-4 mb-0'>Évaluation</h4>
            <FormGroup>
                <FormField>
                    <label>Les tâches confiées au stagiaire sont conformes aux tâches annoncées dans l’entente de
                        stage?</label>
                    <select required name='question_un' value={visitForm.question_un} onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Des mesures d’accueil facilitent l’intégration du nouveau stagiaire?</label>
                    <select required name='question_deux' value={visitForm.question_deux} onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Le temps réel consacré à l’encadrement du stagiaire est suffisant?</label>
                    <select  required name='question_trois' value={visitForm.question_trois} onChange={e => handleChange(e)}>
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
                    <input type="number" min='0' max='240' name="nb_heures_mois_un" value={visitForm.nb_heures_mois_un} onChange={e => handleChange(e)} placeholder="Nombre d'heures" required/>
                </FormField>
                <FormField>
                    <label>Nombre d'heures pour le 2ième mois?</label>
                    <input type="number" min='0' max='240' name='nb_heures_mois_deux' value={visitForm.nb_heures_mois_deux} onChange={e => handleChange(e)} placeholder="Nombre d'heures" required/>
                </FormField>
                <FormField>
                    <label>Nombre d'heures pour le 3ième mois?</label>
                    <input type="number" max='240' name='nb_heures_mois_trois' min='0' value={visitForm.nb_heures_mois_trois} onChange={e => handleChange(e)} placeholder="Nombre d'heures" required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>L’environnement de travail respecte les normes d’hygiène et de sécurité au travail?</label>

                    <select required name='question_quatre' value={visitForm.question_quatre} onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Le climat de travail est agréable?</label>
                    <select required name='question_cinq' value={visitForm.question_cinq} onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Le milieu de stage est accessible par transport en commun?</label>
                    <select required name='question_six' value={visitForm.question_six} onChange={e => handleChange(e)}>
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
                    <select required name='question_sept' value={visitForm.question_sept} onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Préciser le salaire par heure</label>
                    <input required type="number" min='10' name='salaire_stagiaire' value={visitForm.salaire_stagiaire} onChange={(e) => handleChange(e)} />
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>La communication avec le superviseur de stage facilite le déroulement du stage?</label>
                    <select required name='question_huit' value={visitForm.question_huit} onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>L’équipement fourni est adéquat pour réaliser les tâches confiées?</label>
                    <select required name='question_neuf' value={visitForm.question_neuf} onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Le volume de travail est acceptable?</label>
                    <select required name='question_dix' value={visitForm.question_dix} onChange={e => handleChange(e)}>
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
                    <textarea  name='commentaires' value={visitForm.commentaires} onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='fw-bold p-2 rounded mt-4 mb-0 text-decoration-underline'>Observations générales</h4>
            <FormGroup>
                <FormField>
                    <label>Ce milieu est à privilégier pour le stage</label>
                    <select required name='question_onze' value={visitForm.question_onze} onChange={e => handleChange(e)}>
                        <option value="Choisissez une option" selected disabled>Choisissez une option</option>
                        <option value='Premier stage'>Premier stage</option>
                        <option value='Deuxième stage'>Deuxième stage</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Ce milieu est ouvert à accueillir deux stagiaires
                    </label>
                    <select required name='question_douze' value={visitForm.question_douze} onChange={e => handleChange(e)}>
                        <option value="Choisissez une option" selected disabled>Choisissez une option</option>
                        <option value='Un stagiaire'>Un stagiaire</option>
                        <option value='Deux stagiaires'>Deux stagiaires</option>
                        <option value='Trois stagiaires'>Trois stagiaires</option>
                        <option value='Plus de trois stagiaires'>Plus de trois stagiaires</option>
                    </select>
                </FormField>
                <>
                    <label className='d-flex justify-content-center'>Ce milieu désire accueillir le même stagiaire pour un prochain stage</label>
                    <div className='d-flex justify-content-around align-items-center flex-row'>
                        <input type="radio" onChange={e => handleChange(e)} name='question_treize' value='Oui' required/>
                        <label>Oui</label>
                        <input type="radio" onChange={e => handleChange(e)} name='question_treize' value='Non' required/>
                        <label>Non</label>
                    </div>
                </>
            </FormGroup>
            <FormGroup repartition={[12, 6, 6]}>
                <h5>Ce milieu offre des quarts de travail variables?</h5>
                <FormField>
                    <label>De</label>
                    <input type="number" name="question_quatorze_heures_un_a" value={visitForm.question_quatorze_heures_un_a}  onChange={e => handleChange(e)} required />
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="question_quatorze_heures_un_b" value={visitForm.question_quatorze_heures_un_b}  onChange={e => handleChange(e)} required />
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>De</label>
                    <input type="number" name="question_quatorze_heures_un_c" value={visitForm.question_quatorze_heures_un_c}  onChange={e => handleChange(e)} required />
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="question_quatorze_heures_un_d" value={visitForm.question_quatorze_heures_un_d}  onChange={e => handleChange(e)} required />
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>De</label>
                    <input type="number" name="question_quatorze_heures_un_e" value={visitForm.question_quatorze_heures_un_e}  onChange={e => handleChange(e)} required />
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="question_quatorze_heures_un_f" value={visitForm.question_quatorze_heures_un_f}  onChange={e => handleChange(e)} required />
                </FormField>
            </FormGroup>
            <FormGroup>
                <>
                    <label/>
                    <div className='d-flex justify-content-around align-items-center flex-row'>
                        <input type="radio" onChange={e => handleChange(e)} name='question_quinze' value='Oui' required/>
                        <label>Oui</label>
                        <input type="radio" onChange={e => handleChange(e)} name='question_quinze' value='Non' required/>
                        <label>Non</label>
                    </div>
                </>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Signature de l'enseignant responsable du stagiaire</label>
                    <input type="text" name="signature_superviseur" onChange={e => handleChange(e)} required />
                </FormField>
            </FormGroup>
        </div>
        <button type='submit' className='btn btn-primary w-100 mt-4'>Créer un formulaire de visite</button>
    </form>
}
