import React from "react";
import FieldPassword from "../../../SharedComponents/Fields/FieldPassword";
import {FormGroup} from "../../../SharedComponents/FormGroup/FormGroup";

export default function StepPassword({prevStep, handleChange, password}) {

    return (<>
            <FormGroup>
                <FieldPassword password={password} label="Mot de passe"
                               placeholder="Votre mot de passe" handleChange={handleChange}/>
            </FormGroup>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={prevStep}>Précédent</button>
                    <button className="btn btn-primary" type={"submit"}>Créé le compte</button>
                </div>
            </div>
        </>
    )

}
