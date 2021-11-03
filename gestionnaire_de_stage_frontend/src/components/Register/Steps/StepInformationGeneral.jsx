import React from "react";
import FieldEmail from "../../Fields/FieldEmail";
import {Step} from "../../../enums/Steps";

// eslint-disable-next-line
const regexEmail = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
const regexPhone = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;

export default function StepInformationGeneral({prevStep, nextStep, handleChange, lastName, firstName, email, phone}) {

    const next = (val) => {
        nextStep(val);
    }

    const verification = () => {
        if (!firstName) {
            alert("Le champs prénom est vide")
            return false
        }
        if (!lastName) {
            alert("Le champs nom est vide")
            return false
        }
        if (!email) {
            alert("Le champs courriel est vide")
            return false
        }
        if (!phone) {
            alert("Le champs numéro de téléphone est vide")
            return false
        }
        if (!firstName.match(/^[a-zA-Z\-\s]+$/)) {
            alert("Le champs prénom est invalide")
            return false;
        }
        if (!lastName.match(/^[a-zA-Z\-\s]+$/)) {
            alert("Le champs nom est invalide")
            return false;
        }
        if (!regexEmail.test(email)) {
            alert("Le champs courriel est invalide")
            return false;
        }
        if (!regexPhone.test(phone)) {
            alert("Le champs numéro de téléphone est invalide")
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
                           value={firstName} onChange={handleChange('firstName')}/>
                </div>
            </div>
            <div className="col-md-6">
                <label>Nom</label>
                <div>
                    <div className="input-group">
                        <input name="lastName" placeholder="Nom" className="form-control" type="text"
                               value={lastName} onChange={handleChange('lastName')}/>
                    </div>
                </div>
            </div>
        </div>
        <div className="form-group">
            <FieldEmail email={email} label="Email" placeholder="Votre Email"
                        handleChanges={handleChange("email")}/>
        </div>
        <div className="form-group">
            <label>Téléphone</label>
            <div className="input-group">
                <input name="contact_no" placeholder="000 000 000" className="form-control" type="tel"
                       value={phone} onChange={handleChange('phone')}/>
            </div>
        </div>
        <div className="form-group text-center">
            <label/>
            <div>
                <button className="btn btn-primary" type={"button"} onClick={prevStep}>Précédent</button>
                <button className="btn btn-primary" type={"button"} onClick={() => {
                    if (verification())
                        next(Step.PASSWORD)
                    else
                        console.log("fuck you sam")
                }}>Suivant
                </button>
            </div>
        </div>
    </>)

}
