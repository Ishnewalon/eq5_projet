import React from "react";
import FieldPassword from "../../SharedComponents/Fields/FieldPassword";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";

export default function StepPassword({prevStep, handleChange, password}) {

    return (<>
            <FormGroup>
                <FieldPassword password={password} label="Mot de passe"
                               placeholder="Votre mot de passe" handleChange={handleChange}/>

                {/* REVOIR LA CONFIRMATION DU MDP*/}
                {/*<div className="col-md-6">*/}
                {/*    <label>Confirmez votre mot de passe</label>*/}
                {/*    <div className="input-group">*/}
                {/*        <input name="pwd2" placeholder="Confirmez votre mot de passe" className="form-control" type="text"*/}
                {/*               value={values.pwd2} onChange={handleChange('pwd2')}/>*/}
                {/*    </div>*/}
                {/*</div>*/}
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
