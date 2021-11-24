import React from "react";
import {FormGroup} from "../../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../../SharedComponents/FormField/FormField";
import {regexEmail, regexName, regexPhone} from "../../../../utility";


export default function StepInformationGeneral({register, errors, watch}) {
    return (<>
        <FormGroup>
            <FormField htmlFor="firstName">
                <label>Prénom</label>
                <input name="firstName" placeholder="Prenom"
                       type="text" {...register("firstName", {pattern: regexName})}/>
                {errors.matricule && <span>Ce champ est obligatoire!</span>}
            </FormField>
            <FormField htmlFor="lastName">
                <label>Nom</label>
                <input name="lastName" placeholder="Nom" type="text" {...register("lastName", {pattern: regexName})}/>
                {errors.matricule && <span>Ce champ est obligatoire!</span>}
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField htmlFor="email">
                <label>Votre Email</label>
                <input placeholder="exemple@email.com" type="email"
                       name="email" {...register("email", {pattern: regexEmail})}/>
                {errors.matricule && <span>Il faut un email valide!</span>}
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField htmlFor="phone">
                <label>Téléphone</label>
                <input name="phone" placeholder="000 000 000" type="tel"{...register("phone", {pattern: regexPhone})}/>
                {errors.matricule && <span>Il faut un numéro de téléphone valide!</span>}
            </FormField>
        </FormGroup>
        <div className="form-group text-center">
            <label/>
            <div className="btn-group">
                <input className="btn btn-primary" type="submit" name="prev" value="Précédent"/>
                <input className="btn btn-primary" type="submit" name="next" value="Suivant"/>
            </div>
        </div>
    </>)

}


