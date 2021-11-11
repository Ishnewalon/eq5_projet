import {useState} from "react";
import {monitorCreateForm} from "../../../services/stage-service";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {useAuth} from "../../../services/use-auth";

export default function MonitorVisitForm() {

    const auth = useAuth();

    const [monitorVisitForm, setMonitorVisitForm] = useState({
        entrepriseNom: '',
        personne_contact: auth.user.name,
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
        question_quinze: '',
        fonction: '',
        programme: '',
        commentaires_un: '',
        question_quatorze: '',
        question_seize: '',
        commentaires_deux: ''
    });

    const handleChange = (event) => {
        const {value, name} = event.target;

        setMonitorVisitForm((prevalue) => {
            return {
                ...prevalue,
                [name]: value
            }
        })
    }

    function convertAllNumberFieldsToNumbers() {
        Object.keys(monitorVisitForm).forEach(key => {
            if (!isNaN(monitorVisitForm[key])) {
                setMonitorVisitForm((prevalue) => {
                    return {
                        ...prevalue,
                        [key]: parseInt(monitorVisitForm[key])
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
                    <input type="text" name='nom_stagiaire' value={monitorVisitForm.nom_stagiaire}
                           onChange={e => handleChange(e)} title='Entrez votre numéro de téléphone'
                           placeholder="Entrez le nom du stagiare" required/>
                </FormField>
                <FormField>
                    <label>Nom de l'entreprise</label>
                    <input type="text" name='entrepriseNom' value={monitorVisitForm.entrepriseNom}
                           onChange={e => handleChange(e)} autoComplete='off' placeholder="Nom de l'entreprise"
                           required/>
                </FormField>
                <FormField>
                    <label>Fonction</label>
                    <input type="text" name='fonction' value={monitorVisitForm.fonction}
                           onChange={e => handleChange(e)} autoComplete='off' placeholder="Fonction"
                           required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Téléphone</label>
                    <input type="tel" name='phone' value={monitorVisitForm.phone} onChange={e => handleChange(e)}
                           autoComplete='off' placeholder="Téléphone" required/>
                </FormField>
                <FormField>
                    <label>Programme d'études</label>
                    <input type="text" name='telecopieur' value={monitorVisitForm.programme}
                           onChange={e => handleChange(e)}
                           autoComplete='off' placeholder="Entre le programme d'études" required/>
                </FormField>
            </FormGroup>
        </div>
        <hr/>
        <div className='px-3 pb-3 pt-1 rounded'>
            <h2 className='mt-4 mb-0 text-decoration-underline'>Productivité</h2>
            <h4 className='mt-4 mb-0'>Capacité d’optimiser son rendement au travail</h4>
            <FormGroup>
                <FormField>
                    <label>Planifier et organiser son travail de façon efficace</label>
                    <select required name='question_un' value={monitorVisitForm.question_un}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement eb désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Comprendre rapidement les directives relatives à son
                        travail</label>
                    <select required name='question_deux' value={monitorVisitForm.question_deux}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot en désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement en désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Maintenir un rythme de travail soutenu</label>
                    <select required name='question_trois' value={monitorVisitForm.question_trois}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Établir ses priorités</label>
                    <select required name='question_quatre' value={monitorVisitForm.question_quatre}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Respecter les échéanciers</label>
                    <select required name='question_cinq' value={monitorVisitForm.question_cinq}
                            onChange={e => handleChange(e)}>
                        <option value="TOTALEMENT_EN_ACCORD">Totalement en accord</option>
                        <option value="PLUTOT_EN_ACCORD">Plutôt en accord</option>
                        <option value='PLUTOT_EN_DESACCORD'>Plûtot désaccord</option>
                        <option value='TOTALEMENT_DESACCORD'>Totalement désaccord</option>
                        <option value='NON_APPLICABLE'>N/A*</option>
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Commentaires</label>
                    <textarea name='commentaires_un' value={monitorVisitForm.commentaires_un}
                              onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
            <hr/>
            <h2 className='mt-4 mb-0 text-decoration-underline'>Qualité du travail</h2>
            <h4 className='mt-4 mb-0'>Capacité de s’acquitter des tâches sous sa responsabilité en s’imposant personnellement des normes de qualité</h4>
            <FormGroup>
                <FormField>
                    <label>Respecter les mandats qui lui ont été confiés</label>
                    <select required name='question_six' value={monitorVisitForm.question_six}
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
                    <select required name='question_sept' value={monitorVisitForm.question_sept}
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
                    <select required name='question_huit' value={monitorVisitForm.question_huit}
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
                    <select required name='question_neuf' value={monitorVisitForm.question_neuf}
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
                    <select required name='question_dix' value={monitorVisitForm.question_dix}
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
                    <textarea required name='commentaires_deux' value={monitorVisitForm.commentaires_deux}
                              onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
        </div>
        <hr/>





        <div className='px-3 pb-3 pt-1 rounded'>
            <h2 className='mt-4 mb-0 text-decoration-underline'>Qualités des relations interpersonnelles</h2>
            <h4 className='mt-4 mb-0'>Capacité d’établir des interrelations harmonieuses dans son milieu de travail</h4>
            <FormGroup>
                <FormField>
                    <label>Établir facilement des contacts avec les gens</label>
                    <select required name='question_onze' value={monitorVisitForm.question_onze}
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
                    <select required name='question_douze' value={monitorVisitForm.question_douze}
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
                    <select required name='question_treize' value={monitorVisitForm.question_treize}
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
                    <select required name='question_quatorze' value={monitorVisitForm.question_quatorze}
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
                    <select required name='question_quinze' value={monitorVisitForm.question_quinze}
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
                    <select required name='question_seize' value={monitorVisitForm.question_seize}
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
                    <textarea required name='commentaires_deux' value={monitorVisitForm.commentaires_deux}
                              onChange={e => handleChange(e)}/>
                </FormField>
            </FormGroup>
        </div>






        <div className='px-3 pb-3 pt-1 rounded'>
            <h4 className='fw-bold p-2 rounded mt-4 mb-0 text-decoration-underline'>Observations générales</h4>
            <FormGroup>
                <FormField>
                    <label>Ce milieu est à privilégier pour le stage</label>
                    <select required name='question_onze' value={monitorVisitForm.question_onze}
                            onChange={e => handleChange(e)}>
                        <option value="Choisissez une option" selected disabled>Choisissez une option</option>
                        <option value='Premier stage'>Premier stage</option>
                        <option value='Deuxième stage'>Deuxième stage</option>
                    </select>
                </FormField>
                <FormField>
                    <label>Ce milieu est ouvert à accueillir deux stagiaires
                    </label>
                    <select required name='question_douze' value={monitorVisitForm.question_douze}
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
                        <input type="radio" onChange={e => handleChange(e)} name='question_treize' value='Oui'
                               required/>
                        <label>Oui</label>
                        <input type="radio" onChange={e => handleChange(e)} name='question_treize' value='Non'
                               required/>
                        <label>Non</label>
                    </div>
                </>
            </FormGroup>
            <FormGroup repartition={[12, 6, 6]}>
                <h5>Ce milieu offre des quarts de travail variables?</h5>
                <FormField>
                    <label>De</label>
                    <input type="number" name="question_quatorze_heures_un_a"
                           value={monitorVisitForm.question_quatorze_heures_un_a} onChange={e => handleChange(e)}
                           required/>
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="question_quatorze_heures_un_b"
                           value={monitorVisitForm.question_quatorze_heures_un_b} onChange={e => handleChange(e)}
                           required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>De</label>
                    <input type="number" name="question_quatorze_heures_un_c"
                           value={monitorVisitForm.question_quatorze_heures_un_c} onChange={e => handleChange(e)}
                           required/>
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="question_quatorze_heures_un_d"
                           value={monitorVisitForm.question_quatorze_heures_un_d} onChange={e => handleChange(e)}
                           required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>De</label>
                    <input type="number" name="question_quatorze_heures_un_e"
                           value={monitorVisitForm.question_quatorze_heures_un_e} onChange={e => handleChange(e)}
                           required/>
                </FormField>
                <FormField>
                    <label>À</label>
                    <input type="number" name="question_quatorze_heures_un_f"
                           value={monitorVisitForm.question_quatorze_heures_un_f} onChange={e => handleChange(e)}
                           required/>
                </FormField>
            </FormGroup>
            <FormGroup>
                <>
                    <label/>
                    <div className='d-flex justify-content-around align-items-center flex-row'>
                        <input type="radio" onChange={e => handleChange(e)} name='question_quinze' value='Oui'
                               required/>
                        <label>Oui</label>
                        <input type="radio" onChange={e => handleChange(e)} name='question_quinze' value='Non'
                               required/>
                        <label>Non</label>
                    </div>
                </>
            </FormGroup>
            <FormGroup>
                <FormField>
                    <label>Signature de l'enseignant responsable du stagiaire</label>
                    <input type="text" name="signature_superviseur" onChange={e => handleChange(e)} required/>
                </FormField>
            </FormGroup>
        </div>
        <button type='submit' className='btn btn-primary w-100 mt-4'>Créer un formulaire de visite</button>
    </form>
}
