import React from "react";
import {FormGroup} from "../../../SharedComponents/FormGroup/FormGroup";
import {regexEmail, regexName, regexPhone} from "../../../../utility";
import {FormInput} from "../../../SharedComponents/FormInput/FormInput";


export default function StepInformationGeneral({register, errors, prev}) {
    return (<>
        <FormGroup>
            <FormInput register={register}
                       error={errors.firstName}
                       name="firstName"
                       label="Prénom"
                       placeholder="Prénom"
                       type="text"
                       validation={{
                           required: "Ce champ est obligatoire!",
                           pattern: {
                               value: regexName,
                               message: "Le prénom doit contenir que des lettres!"
                           }
                       }}/>
            <FormInput register={register}
                       error={errors.lastName}
                       name="lastName"
                       label="Nom"
                       placeholder="Nom"
                       type="text"
                       validation={{
                           required: "Ce champ est obligatoire!",
                           pattern: {
                               value: regexName,
                               message: "Le nom doit contenir que des lettres!"
                           }
                       }}/>
        </FormGroup>
        <FormGroup>
            <FormInput register={register}
                       error={errors.email}
                       name="email"
                       label="Votre Email"
                       placeholder="Votre Email"
                       type="email"
                       validation={{
                           required: "Ce champ est obligatoire!",
                           pattern: {
                               value: regexEmail,
                               message: "L'email n'est pas valide!"
                           }
                       }}/>
        </FormGroup>
        <FormGroup>
            <FormInput register={register}
                       error={errors.phone}
                       name="phone"
                       label="Votre Téléphone"
                       placeholder="000 000 000"
                       type="tel"
                       validation={{
                           required: "Ce champ est obligatoire!",
                           pattern: {
                               value: regexPhone,
                               message: "Le numéro de téléphone n'est pas valide!"
                           }
                       }}/>
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


