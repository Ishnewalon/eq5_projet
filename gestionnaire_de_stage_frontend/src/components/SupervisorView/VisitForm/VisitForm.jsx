import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {InputGroup} from "../../SharedComponents/InputGroup/InputGroup";


export default function VisitForm() {

    return <form>
        <h1 className='text-center'>Évaluation de stage</h1>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='mt-4 mb-0 text-decoration-underline'>Identification de l'entreprise</h4>
            <FormGroup>
                <>
                    <label>Nom de l'entreprise</label>
                    <InputGroup>
                        <input type="text" name='enterpriseName' autoComplete='off' placeholder="Nom de l'entreprise" required/>
                    </InputGroup>
                </>
                <>
                    <label>Personne contact</label>
                    <InputGroup>
                        <input type="text" name='contact' autoComplete='off' placeholder="Personne contact" required/>
                    </InputGroup>
                </>
            </FormGroup>
            <FormGroup>
                <>
                    <label>Téléphone</label>
                    <InputGroup>
                        <input type="text" name='phone' placeholder="Téléphone" required/>
                    </InputGroup>
                </>
                <>
                    <label>Télécopieur</label>
                    <InputGroup>
                        <input type="text" name='fax' placeholder="Télécopieur" required/>
                    </InputGroup>
                </>
            </FormGroup>
            <FormGroup>
                <>
                    <label>Adresse</label>
                    <InputGroup>
                        <input type="text" name='address' placeholder="Adresse" required/>
                    </InputGroup>
                </>
                <>
                    <label>Code postal</label>
                    <InputGroup>
                        <input type="text" name='postalCode' placeholder="Code postal" required/>
                    </InputGroup>
                </>
                <>
                    <label>Ville</label>
                    <InputGroup>
                        <input type="text" name='city' placeholder="Ville" required/>
                    </InputGroup>
                </>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='mt-4 mb-0 text-decoration-underline'>Identification du stagiaire</h4>
            <FormGroup>
                <>
                    <label>Nom du stagiaire</label>
                    <InputGroup>
                        <input type="tel" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" title='Entrez votre numéro de téléphone'
                               name='phone' placeholder="514-123-4567" required/>
                    </InputGroup>
                </>
                <>
                    <label>Date du stage</label>
                    <InputGroup>
                        <input type="date" name='date' placeholder="Date du stage" required/>
                    </InputGroup>
                </>
                <>
                    <label className='d-flex justify-content-center'>Stage</label>
                    <div className='d-flex justify-content-around align-items-center flex-row'>
                        <input type="radio" name='stage' value='1' required/>
                        <label>1</label>
                        <input type="radio" name='stage' value='2' required/>
                        <label>2</label>
                    </div>
                </>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='fw-bold p-2 rounded mt-4 mb-0'>Évaluation</h4>
            <FormGroup>
                <>
                    <label>Les tâches confiées au stagiaire sont conformes aux tâches annoncées dans l’entente de stage.</label>
                    <InputGroup>
                        <select>
                            <option value="Choisissez une option" selected disabled>Choisissez une option</option>
                            <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                            <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                            <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                            <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                            <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                        </select>
                    </InputGroup>
                </>
                <>
                    <label>Des mesures d’accueil facilitent l’intégration du nouveau stagiaire.</label>
                    <InputGroup>
                        <select>
                            <option value="Choisissez une option" selected disabled>Choisissez une option</option>
                            <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                            <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                            <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                            <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                            <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                        </select>
                    </InputGroup>
                </>
                <>
                    <label>Le temps réel consacré à l’encadrement du stagiaire est suffisant.</label>
                    <InputGroup>
                        <select>
                            <option value="Choisissez une option" selected disabled>Choisissez une option</option>
                            <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                            <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                            <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                            <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                            <option value='IMPOSSIBLE_DE_SE_PRONONCER'>Impossible de se prononcer</option>
                        </select>
                    </InputGroup>
                </>
            </FormGroup>
            <FormGroup>
                <>
                    <label>Nombre d'heures pour le 1er mois</label>
                    <InputGroup>
                        <input type="number" name='heures' placeholder="Nombre d'heures" required/>
                    </InputGroup>
                </>
                <>
                    <label>Nombre d'heures pour le 2ième mois</label>
                    <InputGroup>
                        <input type="number" min='0' name='heures' placeholder="Nombre d'heures" required/>
                    </InputGroup>
                </>
                <>
                    <label>Nombre d'heures pour le 3ième mois</label>
                    <InputGroup>
                        <input type="number" name='heures' min='0' placeholder="Nombre d'heures" required/>
                    </InputGroup>
                </>
            </FormGroup>
        </div>
    </form>
}
