import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {useState} from "react";


export default function VisitForm() {

    const [shitDansChose, setShitDansChose] = useState({
        entrepriseNom: '',
        personne_contact: '',
        phone: '',
        telecopieur: '',
        adresse: '',
        zip: '',
        ville: '',
        nom_stagiaire: '',
        date_stage: null,
        stageCourant: 1,
        question1: '',
        question2: '',
        question3: '',
        nb_heures_mois_un: 0,
        nb_heures_mois_deux: 0,
        nb_heures_mois_trois: 0
    });

    const handleChange = (event) => {
        let value = event.target.value;
        let name = event.target.name;

        setShitDansChose((prevalue) => {
            return {
                ...prevalue,
                [name]: value
            }
        })
    }

    const sendVisitForm = (e) => {
        e.preventDefault();
        alert(JSON.stringify(shitDansChose));
    };

    return <form onSubmit={sendVisitForm}>
        <h1 className='text-center'>Évaluation de stage</h1>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='mt-4 mb-0 text-decoration-underline'>Identification de l'entreprise</h4>
            <FormGroup>
                <FormField>
                    <label>Nom de l'entreprise</label>
                    <input type="text" name='entrepriseNom' value={shitDansChose.entrepriseNom} onChange={(e) => handleChange(e)} autoComplete='off' placeholder="Nom de l'entreprise"
                           required/>
                </FormField>
                <FormField>
                    <label>Personne contact</label>
                    <input type="text" name='personne_contact' value={shitDansChose.personne_contact} onChange={(e) => handleChange(e)} placeholder="Personne contact" required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Téléphone</label>
                    <input type="text" name='phone' value={shitDansChose.phone} onChange={(e) => handleChange(e)} autoComplete='off' placeholder="Téléphone" required/>
                </FormField>
                <FormField>
                    <label>Télécopieur</label>
                    <input type="text" name='telecopieur' value={shitDansChose.telecopieur} onChange={(e) => handleChange(e)} autoComplete='off' placeholder="Télécopieur" required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Adresse</label>
                    <input type="text" name='adresse' value={shitDansChose.adresse} onChange={(e) => handleChange(e)} placeholder="Adresse" required/>
                </FormField>
                <FormField>
                    <label>Code postal</label>
                    <input type="text" name='zip' value={shitDansChose.zip} onChange={(e) => handleChange(e)} placeholder="Code postal" required/>
                </FormField>
                <FormField>
                    <label>Ville</label>
                    <input type="text" name='ville' value={shitDansChose.ville} onChange={(e) => handleChange(e)} placeholder="Ville" required/>
                </FormField>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='mt-4 mb-0 text-decoration-underline'>Identification du stagiaire</h4>
            <FormGroup>
                <FormField>
                    <label>Nom du stagiaire</label>
                    <input type="text" name='nom_stagiaire' value={shitDansChose.nom_stagiaire} onChange={e => handleChange(e)} title='Entrez votre numéro de téléphone'
                           placeholder="Entrez le nom du stagiare" required/>
                </FormField>
                <FormField>
                    <label>Date du stage</label>
                    <input type="date" name='date_stage' value={shitDansChose.date_stage} onChange={e => handleChange(e)} placeholder="Date du stage" required/>
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
                        stage.</label>
                    <select required name='question1' value={shitDansChose.question1} onChange={(e) => handleChange(e)}>
                        <option value="Choisissez une option" selected disabled>Choisissez une option</option>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Des mesures d’accueil facilitent l’intégration du nouveau stagiaire.</label>
                    <select required name='question2' value={shitDansChose.question2} onChange={(e) => handleChange(e)}>
                        <option value="Choisissez une option" selected disabled>Choisissez une option</option>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Le temps réel consacré à l’encadrement du stagiaire est suffisant.</label>
                    <select required name='question3' value={shitDansChose.question3} onChange={(e) => handleChange(e)}>
                        <option value="Choisissez une option" selected disabled>Choisissez une option</option>
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
                    <label>Nombre d'heures pour le 1er mois</label>
                    <input type="number" min='0' max='240' name="nb_heures_mois_un" value={shitDansChose.nb_heures_mois_un} onChange={(e) => handleChange(e)} placeholder="Nombre d'heures" required/>
                </FormField>
                <FormField>
                    <label>Nombre d'heures pour le 2ième mois</label>
                    <input type="number" min='0' max='240' name='nb_heures_mois_deux' value={shitDansChose.nb_heures_mois_deux} onChange={(e) => handleChange(e)} placeholder="Nombre d'heures" required/>
                </FormField>
                <FormField>
                    <label>Nombre d'heures pour le 3ième mois</label>
                    <input type="number" max='240' name='nb_heures_mois_trois' min='0' value={shitDansChose.nb_heures_mois_trois} onChange={(e) => handleChange(e)} placeholder="Nombre d'heures" required/>
                </FormField>
            </FormGroup>
            <button type='submit' className='btn btn-primary w-100 mt-4'>Créer formulaire de visite</button>
        </div>
    </form>
}
