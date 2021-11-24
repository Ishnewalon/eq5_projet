import React from "react";
import {FormGroup} from "../../../SharedComponents/FormGroup/FormGroup";
import {regexEmail, regexName, regexPhone} from "../../../../utility";
import {FormInput} from "../../../SharedComponents/FormInput/FormInput";


export default function StepInformationGeneral({register, errors, prev}) {
    return (<>
        <FormGroup>
            <FormInput register={register} error={errors.firstName} name="firstName" label="Prénom" placeholder="Prénom"
                       type="text" validation={{required: true, pattern: regexName}}/>
            <FormInput register={register} error={errors.lastName} name="lastName" label="Nom" placeholder="Nom"
                       type="text" validation={{required: true, pattern: regexName}}/>
        </FormGroup>
        <FormGroup>
            <FormInput register={register} error={errors.email} name="email" label="Votre Email"
                       placeholder="Votre Email"
                       type="email" validation={{required: true, pattern: regexEmail}}
                       errorMessage="Il faut un email valide."/>
        </FormGroup>
        <FormGroup>
            <FormInput register={register} error={errors.phone} name="phone" label="Votre Téléphone"
                       placeholder="000 000 000" type="tel" validation={{required: true, pattern: regexPhone}}
                       errorMessage="Il faut un numéro de téléphone valide!"/>
        </FormGroup>
        <div className="form-group text-center">
            <label/>
            <div className="btn-group">
                <input className="btn btn-primary" type="button" name="prev" value="Précédent" onClick={prev}/>
                <input className="btn btn-primary" type="submit" name="next" value="Suivant"/>
            </div>
        </div>
    </>)

}


