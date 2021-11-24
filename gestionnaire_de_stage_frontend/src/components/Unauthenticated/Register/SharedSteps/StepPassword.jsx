import React from "react";
import {FormGroup} from "../../../SharedComponents/FormGroup/FormGroup";
import {FormInput} from "../../../SharedComponents/FormInput/FormInput";

export default function StepPassword({register, errors, prev}) {
    return (<>
            <FormGroup>
                <FormInput label="Mot de passe" validation={{required: true, minLength: 8}} error={errors.password}
                           name="password" register={register} type="password" placeholder="Votre mot de passe"/>
                <FormInput label="Confirmez votre mot de passe" validation={{required: true, minLength: 8}}
                           error={errors.passwordConfirm} name="passwordConfirm" register={register} type="password"
                           placeholder="Confirmez votre mot de passe"/>
            </FormGroup>
            <div className="form-group text-center">
                <div className="btn-group">
                    <input className="btn btn-primary" type="button" name="prev" value="Précédent" onClick={prev}/>
                    <input className="btn btn-primary" type="submit" name="next" value="Suivant"/>
                </div>
            </div>
        </>
    )

}
