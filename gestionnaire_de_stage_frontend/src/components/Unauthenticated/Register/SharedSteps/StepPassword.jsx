import React from "react";
import {FormGroup} from "../../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../../SharedComponents/FormField/FormField";

export default function StepPassword({register, errors, prev}) {
    return (<>
            <FormGroup>
                <FormField htmlFor="password">
                    <label>Mot de passe</label>
                    <input name="password" placeholder="Votre mot de passe"
                           type="password" {...register("password", {required: true, minLength: 8})}/>
                    {errors.password && <span>Ce champs est requis!</span>}
                </FormField>
                <FormField htmlFor="passwordConfirm">
                    <label>Confirmez votre mot de passe</label>
                    <input name="passwordConfirm" placeholder="Confirmez votre mot de passe" type="password"
                           {...register("passwordConfirm", {required: true, minLength: 8})}/>
                    {errors.passwordConfirm && <span>Ce champs est requis!</span>}
                </FormField>
            </FormGroup>
            <div className="form-group text-center">
                <div className="btn-group">
                    <input className="btn btn-primary" type="button" name="prevv" value="Précédent" onClick={prev}/>
                    <input className="btn btn-primary" type="submit" name="nextt" value="Suivant"/>
                </div>
            </div>
        </>
    )

}
