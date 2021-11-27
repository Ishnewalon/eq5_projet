import React, {useRef} from "react";
import {FormGroup} from "../../../SharedComponents/FormGroup/FormGroup";
import {FormInput} from "../../../SharedComponents/FormInput/FormInput";

export default function StepPassword({register, errors, watch, prev}) {
    const password = useRef({});
    password.current = watch("password", "");
    return (<>
            <FormGroup>
                <FormInput label="Mot de passe"
                           validation={{
                               required: "Ce champ est obligatoire!",
                               minLength: {
                                   value: 8,
                                   message: "Le mot de passe doit contenir au moins 8 caractères!"
                               }
                           }}
                           error={errors.password}
                           name="password"
                           register={register}
                           type="password"
                           placeholder="Votre mot de passe"/>
                <FormInput label="Confirmez votre mot de passe"
                           validation={{
                               required: "Ce champ est obligatoire!",
                               validate: value => value === password.current || "Les mots de passe ne correspondent pas!"
                           }}
                           error={errors.confirmation}
                           name="confirmation"
                           register={register}
                           type="password"
                           placeholder="Confirmez votre mot de passe"/>
            </FormGroup>
            <div className="form-group text-center">
                <div className="btn-group">
                    {prev && <button className="btn btn-primary" onClick={prev}>Précédent</button>}
                    <input className="btn btn-primary" type="submit" name="next" value={prev ? "Suivant" : "Changer"}/>
                </div>
            </div>
        </>
    )

}
