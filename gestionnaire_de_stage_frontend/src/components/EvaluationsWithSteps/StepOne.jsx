import {withRouter} from "react-router-dom";
import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../SharedComponents/FormField/FormField";
import {regexEmail, regexPhone} from "../../utility";
import {useForm} from "react-hook-form";
import {BtnBack} from "../SharedComponents/BtnBack";

function StepOne({errors, register}){

    return <div className='px-3 pb-3 pt-1'>
        <h3>Informations générales</h3>
        <FormGroup>
            <FormField>
                <label>Email de l'élève</label>
                <input type="email"
                       aria-invalid={errors.emailEtudiant ? "true" : "false"}
                       {...register("emailEtudiant", {required: true, pattern: regexEmail})}
                       placeholder="Email de l'élève"/>
                {errors.emailEtudiant.type === "pattern" &&
                <span className="text-danger">Le email de l'étudiant est requis</span>}
                {errors.emailEtudiant.type === "required" &&
                <span className="text-danger">Doit être un courriel valide</span>}
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Nom de l'élève</label>
                <input type="text" {...register("nomEtudiant", {required: true})}/>
            </FormField>
            <FormField>
                <label>Programme d'études</label>
                <input type="text" {...register("programmeEtudes", {required: true})}/>
            </FormField>
            <FormField>
                <label>Nom de l'entreprise</label>
                <input type="text" {...register("entrepriseNom", {required: true})} />
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Fonction</label>
                <input type="text" name='fonctionUn' {...register("fonctionUn", {required:true})}/>
            </FormField>
            <FormField>
                <label>Téléphone</label>
                <input type="text" aria-label={errors.phone ? "true" : "false"} {...register("phone", {required:true, pattern:regexPhone})}/>
                {errors.phone.type === "pattern" && <span className="text-danger">Le téléphone doit avoir le format suivant : 555-444-3333</span>}
                {errors.phone.type === "required" && <span className="text-danger">Le téléphone est obligatoire</span>}
            </FormField>
        </FormGroup>
        <BtnBack message='Précédant' />
    </div>

}

export default StepOne;
