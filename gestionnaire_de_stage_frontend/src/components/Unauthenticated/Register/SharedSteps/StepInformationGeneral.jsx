import React from "react";
import FieldEmail from "../../../SharedComponents/Fields/FieldEmail";
import {FormGroup} from "../../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../../SharedComponents/FormField/FormField";


export default function StepInformationGeneral({handleChange, lastName, firstName, email, phone}) {
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
            <div className="btn-group">
                <input className="btn btn-primary" type="submit" name="prev" value="Précédent"/>
                <input className="btn btn-primary" type="submit" name="next" value="Suivant"/>
            </div>
        </div>
    </>)

}


