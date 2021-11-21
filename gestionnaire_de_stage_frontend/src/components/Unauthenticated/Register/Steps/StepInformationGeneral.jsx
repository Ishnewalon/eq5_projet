import React from "react";
import FieldEmail from "../../../SharedComponents/Fields/FieldEmail";
import {Step} from "../../../../enums/Steps";
import {regexEmail, regexName, regexPhone, toastErr} from "../../../../utility";
import {FormGroup} from "../../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../../SharedComponents/FormField/FormField";


export default function StepInformationGeneral({prevStep, nextStep, handleChange, lastName, firstName, email, phone}) {

    const next = (val) => {
        nextStep(val);
    }


    return (<>
        <FormGroup>
            <FormField htmlFor="firstName">
                <label>Prénom</label>
                <input name="firstName" placeholder="Prenom" type="text" value={firstName} onChange={handleChange}/>
            </FormField>
            <FormField htmlFor="lastName">
                <label>Nom</label>
                <input name="lastName" placeholder="Nom" type="text" value={lastName} onChange={handleChange}/>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FieldEmail email={email} label="Email" placeholder="Votre Email"
                        handleChanges={handleChange}/>
        </FormGroup>
        <FormGroup>
            <FormField htmlFor="phone">
                <label>Téléphone</label>
                <input name="phone" placeholder="000 000 000" type="tel"
                       value={phone} onChange={handleChange}/>
            </FormField>
        </FormGroup>
        <div className="form-group text-center">
            <label/>
            <div>
                <button className="btn btn-primary" type={"button"} onClick={prevStep}>Précédent</button>
                <button className="btn btn-primary" type={"button"} onClick={() => {
                    if (verification(firstName, lastName, email, phone))
                        next(Step.PASSWORD)
                }}>Suivant
                </button>
            </div>
        </div>
    </>)

}

export function verification(firstName, lastName, email, phone) {
    if (!firstName) {
        toastErr.fire({title: "Le champs prénom est vide"}).then()
        return false
    }
    if (!lastName) {
        toastErr.fire({title: "Le champs nom est vide"}).then()
        return false
    }
    if (!email) {
        toastErr.fire({title: "Le champs courriel est vide"}).then()
        return false
    }
    if (!phone) {
        toastErr.fire({title: "Le champs numéro de téléphone est vide"}).then()
        return false
    }
    if (!regexName.test(firstName)) {
        toastErr.fire({title: "Le champs prénom est invalide"}).then()
        return false;
    }
    if (!regexName.test(lastName)) {
        toastErr.fire({title: "Le champs nom est invalide"}).then()
        return false;
    }
    if (!regexEmail.test(email)) {
        toastErr.fire({title: "Le champs courriel est invalide"}).then()
        return false;
    }
    if (!regexPhone.test(phone)) {
        toastErr.fire({title: "Le champs numéro de téléphone est invalide"}).then()
        return false;
    }
    return true;
}
