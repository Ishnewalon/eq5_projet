import React from "react";
import FieldPassword from "../../../SharedComponents/Fields/FieldPassword";
import {FormGroup} from "../../../SharedComponents/FormGroup/FormGroup";

export default function StepPassword({handleChange, password}) {

    return (<>
            <FormGroup>
                <FieldPassword password={password} label="Mot de passe"
                               placeholder="Votre mot de passe" handleChange={handleChange}/>
            </FormGroup>
            <div className="form-group text-center">
                <label/>
                <div className="btn-group">
                    <input className="btn btn-primary" type="submit" name="prevv" value="Précédent"/>
                    <input className="btn btn-primary" type="submit" name="nextt" value="Suivant"/>
                </div>
            </div>
        </>
    )

}
