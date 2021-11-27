import React, {useState} from "react";
import StepPassword from "./SharedSteps/StepPassword";
import StepInformationGeneral from "./SharedSteps/StepInformationGeneral";
import {MonitorModel} from "../../../models/User";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../../hooks/use-auth";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {BtnBack} from "../../SharedComponents/BtnBack";
import {useForm} from "react-hook-form";
import {regexCodePostal, regexName} from "../../../utility";
import {FormInput} from "../../SharedComponents/FormInput/FormInput";


export default function RegisterMonitor() {
    const {register, handleSubmit, watch, formState: {errors}} = useForm({
        mode: "onSubmit",
        reValidateMode: "onSubmit",
    });
    let history = useHistory();
    let auth = useAuth();
    const [curentStep, setCurentStep] = useState(0)
    let show;


    const endThis = (email, password, lastName, firstName, phone, companyName, address, city, postalCode) => {
        let user = new MonitorModel(email, password, lastName, firstName, phone, companyName, address, city, postalCode);
        auth.signupMonitor(user).then(() => history.push("/login"))
    }

    const submit = (data, e) => {
        e.preventDefault();
        // noinspection JSUnresolvedVariable
        let submitter = e.nativeEvent.submitter.value
        const {
            firstName,
            lastName,
            email,
            password,
            confirmation,
            phone,
            address,
            city,
            postalCode,
            companyName
        } = data;
        if (submitter === 'Suivant') {
            if (curentStep === 0)
                setCurentStep(1)
            else if (curentStep === 1)
                setCurentStep(2)
            else if (curentStep === 2 && password === confirmation)
                endThis(email, password, lastName, firstName, phone, companyName, address, city, postalCode);
        } else if (submitter === 'Précédent')
            setCurentStep(curentStep - 1)
    };


    if (curentStep === 0)
        show = <StepMonitor register={register} errors={errors} watch={watch}/>
    else if (curentStep === 1)
        show = <StepInformationGeneral register={register} errors={errors} watch={watch}
                                       prev={() => setCurentStep(curentStep - 1)}/>
    else if (curentStep === 2)
        show =
            <StepPassword register={register} errors={errors} watch={watch} prev={() => setCurentStep(curentStep - 1)}/>


    return (<>
        <form className="form-container" onSubmit={handleSubmit(submit)} noValidate>
            <fieldset>
                {show}
            </fieldset>
        </form>
    </>);
}


function StepMonitor({register, errors}) {
    return (<>
        <FormGroup>
            <FormInput label="Nom de la compagnie"
                       validation={{
                           required: "Ce champ est obligatoire!",
                           pattern: {
                               value: regexName,
                               message: "Le nom de la compagnie doit contenir au moins 3 caractères et ne doit contenir que des lettres"
                           }
                       }}
                       error={errors.companyName}
                       name="companyName"
                       register={register}
                       type="text"
                       placeholder="Nom de compagnie"/>
            <FormInput label="Ville"
                       validation={{
                           required: "Ce champ est obligatoire!",
                           pattern: {
                               value: regexName,
                               message: "Le nom de la ville doit contenir au moins 3 caractères et ne doit contenir que des lettres"
                           }
                       }}
                       error={errors.city}
                       name="city"
                       register={register}
                       type="text"
                       placeholder="Ville"/>
        </FormGroup>
        <FormGroup>
            <FormInput label="Adresse"
                       validation={{
                           required: "Ce champ est obligatoire!",
                           minLength: {
                               value: 8,
                               message: "L'adresse doit contenir au moins 8 caractères "
                           }
                       }} error={errors.address}
                       name="address"
                       register={register}
                       type="text"
                       placeholder="Rue, boulevard, avenue.."/>
        </FormGroup>
        <FormGroup>
            <FormInput label="Code postal"
                       validation={{
                           required: "Ce champ est obligatoire!",
                           pattern: {
                               value: regexCodePostal,
                               message: "Le code postal doit contenir être sous le format suivant: H0H 0H0"
                           }
                       }}
                       error={errors.postalCode}
                       name="postalCode"
                       register={register}
                       type="text"
                       placeholder="H0H 0H0"/>
        </FormGroup>
        <div className="form-group text-center">
            <div className="btn-group">
                <BtnBack/>
                <input className="btn btn-primary" type="submit" name="next" value="Suivant"/>
            </div>
        </div>
    </>)

}

