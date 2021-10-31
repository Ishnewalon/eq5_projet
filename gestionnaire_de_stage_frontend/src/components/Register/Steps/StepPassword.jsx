import React from "react";
import FieldPassword from "../../Fields/FieldPassword";

export default function StepPassword({prevStep, endThis, handleChange, password}) {

    return (<div>
            <div className="form-group row">
                <div className="col-md-12">
                    <FieldPassword password={password} label="Mot de passe"
                                   placeholder="Votre mot de passe" handleChange={handleChange('password')}/>
                </div>

                {/* REVOIR LA CONFIRMATION DU MDP*/}
                {/*<div className="col-md-6">*/}
                {/*    <label>Confirmez votre mot de passe</label>*/}
                {/*    <div className="input-group">*/}
                {/*        <input name="pwd2" placeholder="Confirmez votre mot de passe" className="form-control" type="text"*/}
                {/*               value={values.pwd2} onChange={handleChange('pwd2')}/>*/}
                {/*    </div>*/}
                {/*</div>*/}
                <div className="form-group text-center">
                    <label/>
                    <div>
                        <button className="btn btn-primary" type={"button"} onClick={prevStep}>Précédent</button>
                        <button className="btn btn-primary" type={"button"} onClick={endThis}>Suivant
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )

}
