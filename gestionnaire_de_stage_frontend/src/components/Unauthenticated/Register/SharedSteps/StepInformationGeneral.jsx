import React from "react";
import {regexEmail, regexName, regexPhone} from "../../../../utility";
import {FormInput} from "../../../SharedComponents/FormInput/FormInput";
import {checkEmail} from "../../../../services/user-service";
import {Column, FormGroup} from "../../../SharedComponents/FormGroup/FormGroup";


export default function StepInformationGeneral({register, errors, prev}) {
    return (<>
        <FormGroup>
            <Column col={{lg: 6}}>
                <FormInput register={register}
                           error={errors.firstName}
                           name="firstName"
                           label="Prénom"
                           autoComplete="given-name"
                           placeholder="Prénom"
                           type="text"
                           validation={{
                               required: "Ce champ est obligatoire!",
                               pattern: {
                                   value: regexName,
                                   message: "Le prénom doit contenir que des lettres!"
                               }
                           }}/>
            </Column>
            <Column col={{lg: 6}}>
                <FormInput register={register}
                           error={errors.lastName}
                           name="lastName"
                           label="Nom"
                           placeholder="Nom"
                           type="text"
                           autoComplete="family-name"
                           validation={{
                               required: "Ce champ est obligatoire!",
                               pattern: {
                                   value: regexName,
                                   message: "Le nom doit contenir que des lettres!"
                               }
                           }}/>
            </Column>
            <Column>
                <FormInput label="Votre Email"
                           validation={{
                               required: "Ce champ est obligatoire!",
                               pattern: {
                                   value: regexEmail,
                                   message: "Le courriel n'est pas valide!"
                               },
                               validate: async (email) =>
                                   await checkEmail(email) || "Ce courriel est déjà utilisé"
                           }}
                           register={register}
                           error={errors.email}
                           name="email"
                           autoComplete="email"
                           placeholder="Votre Email"
                           type="email"
                />
            </Column>
            <Column>
                <FormInput register={register}
                           error={errors.phone}
                           name="phone"
                           label="Votre Téléphone"
                           placeholder="000 000 000"
                           type="tel"
                           autoComplete="tel-country-code"
                           validation={{
                               required: "Ce champ est obligatoire!",
                               pattern: {
                                   value: regexPhone,
                                   message: "Le numéro de téléphone n'est pas valide!"
                               }
                           }}/>
            </Column>
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


