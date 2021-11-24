import React from "react";
import {FormGroup} from "../../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../../SharedComponents/FormField/FormField";
import {regexEmail, regexName, regexPhone} from "../../../../utility";


export default function StepInformationGeneral({register, errors, prev}) {
    return (<>
        <FormGroup>
            <FormField htmlFor="firstName">
                <label>Prénom</label>
                <input name="firstName" placeholder="Prenom" className={errors.matricule ? "border-danger" : ""}
                       type="text" {...register("firstName", {required: true, pattern: regexName})}/>
                {errors.firstName && <span>Ce champ est obligatoire!</span>}
            </FormField>
            <FormField htmlFor="lastName">
                <label>Nom</label>
                <input name="lastName" placeholder="Nom" type="text" className={errors.matricule ? "border-danger" : ""}
                       {...register("lastName", {
                           required: true,
                           minLength: 2,
                           pattern: regexName
                       })}/>
                {errors.lastName && <span>Ce champ est obligatoire!</span>}
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField htmlFor="email">
                <label>Votre Email</label>
                <input placeholder="exemple@email.com" type="email" className={errors.matricule ? "border-danger" : ""}
                       name="email" {...register("email", {required: true, pattern: regexEmail})}/>
                {errors.email && <span>Il faut un email valide!</span>}
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField htmlFor="phone">
                <label>Téléphone</label>
                <input name="phone" placeholder="000 000 000" type="tel"
                       className={errors.matricule ? "border-danger" : ""}
                       {...register("phone", {
                           required: true,
                           pattern: regexPhone
                       })}/>
                {errors.phone && <span>Il faut un numéro de téléphone valide!</span>}
            </FormField>
        </FormGroup>
        <div className="form-group text-center">
            <label/>
            <div className="btn-group">
                <input className="btn btn-primary" type="button" name="prev" value="Précédent" onClick={prev}/>
                <input className="btn btn-primary" type="submit" name="next" value="Suivant"/>
            </div>
        </div>
    </>)

}


