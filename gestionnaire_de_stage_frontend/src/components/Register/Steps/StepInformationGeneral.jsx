import React from "react";
import FieldEmail from "../../Fields/FieldEmail";
import {Step} from "../../../enums/Steps";
import {toastErr} from "../../../utility";

// eslint-disable-next-line
const regexEmail = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
const regexPhone = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;

export default function StepInformationGeneral({prevStep, nextStep, handleChange, lastName, firstName, email, phone}) {

    const next = (val) => {
        nextStep(val);
    }
    const verification = (firstName, lastName, email, phone) => {
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
        if (!firstName.match(/^[a-zA-Z\-\s]+$/)) {
            toastErr.fire({title: "Le champs prénom est invalide"}).then()
            return false;
        }
        if (!lastName.match(/^[a-zA-Z\-\s]+$/)) {
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

    return (<>
        <div className="form-group row">
            <div className="col-md-6">
                <label>Prénom</label>
                <div className="input-group">
                    <input name="firstName" placeholder="Prenom" className="form-control" type="text"
                           value={firstName} onChange={handleChange}/>
                </div>
            </div>
            <div className="col-md-6">
                <label>Nom</label>
                <div>
                    <div className="input-group">
                        <input name="lastName" placeholder="Nom" className="form-control" type="text"
                               value={lastName} onChange={handleChange}/>
                    </div>
                </div>
            </div>
        </div>
        <div className="form-group">
            <FieldEmail email={email} label="Email" placeholder="Votre Email"
                        handleChanges={handleChange}/>
        </div>
        <div className="form-group">
            <label>Téléphone</label>
            <div className="input-group">
                <input name="phone" placeholder="000 000 000" className="form-control" type="tel"
                       value={phone} onChange={handleChange}/>
            </div>
        </div>
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
