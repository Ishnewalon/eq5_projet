import React, {useState} from "react";
import StepPassword from "./SharedSteps/StepPassword";
import StepInformationGeneral from "./SharedSteps/StepInformationGeneral";
import {MonitorModel} from "../../../models/User";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../../hooks/use-auth";
import {BtnBack} from "../../SharedComponents/BtnBack";
import {useForm} from "react-hook-form";
import {regexCodePostal, regexName} from "../../../utility";
import {FormInput} from "../../SharedComponents/FormInput/FormInput";
import {Column, FormGroupV2} from "../../SharedComponents/FormGroup/FormGroupV2";
import {Title} from "../../SharedComponents/Title/Title";
import {ProgressBar} from "../../SharedComponents/ProgressBar";


export default function RegisterMonitor() {
    const {register, handleSubmit, watch, formState: {errors}} = useForm({
        mode: "onSubmit",
        reValidateMode: "onSubmit",
    });
    let history = useHistory();
    let auth = useAuth();
    const [currentStep, setCurrentStep] = useState(0)
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
            if (currentStep === 0)
                setCurrentStep(1)
            else if (currentStep === 1)
                setCurrentStep(2)
            else if (currentStep === 2 && password === confirmation)
                endThis(email, password, lastName, firstName, phone, companyName, address, city, postalCode);
        } else if (submitter === 'Précédent')
            setCurrentStep(currentStep - 1)
    };


    if (currentStep === 0)
        show = <StepMonitor register={register} errors={errors} watch={watch}/>
    else if (currentStep === 1)
        show = <StepInformationGeneral register={register} errors={errors} watch={watch}
                                       prev={() => setCurrentStep(currentStep - 1)}/>
    else if (currentStep === 2)
        show =
            <StepPassword register={register} errors={errors} watch={watch}
                          prev={() => setCurrentStep(currentStep - 1)}/>

    function getTitle() {
        if (currentStep === 0)
            return <Title header="h5">Informations de la compagnie</Title>
        else if (currentStep === 1)
            return <Title header="h5">{`Informations générales (Moniteur)`}</Title>
        else if (currentStep === 2)
            return <Title header="h5">Mot de passe</Title>
    }


    return (<>
        <form className="form-container" onSubmit={handleSubmit(submit)} noValidate>
            <ProgressBar totalSteps={3} currentStep={currentStep}/>
            {getTitle()}
            <fieldset>
                {show}
            </fieldset>
        </form>
    </>);
}


function StepMonitor({register, errors}) {
    return (<>
        <FormGroupV2>
            <Column>
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
                           placeholder="Nom de compagnie"
                           autoComplete="organization-title"/>
            </Column>
        </FormGroupV2>
        <FormGroupV2>
            <Column>
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
                           autoComplete="street-address"
                           type="text"
                           placeholder="Rue, boulevard, avenue.."/>
            </Column>
            <Column col={{md: 6}}>
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
                           autoComplete="address-level2"
                           placeholder="Ville"/>
            </Column>
            <Column col={{md: 6}}>
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
                           autoComplete="postal-code"
                           placeholder="H0H 0H0"/>
            </Column>
        </FormGroupV2>
        <div className="form-group text-center">
            <div className="btn-group">
                <BtnBack/>
                <input className="btn btn-primary" type="submit" name="next" value="Suivant"/>
            </div>
        </div>
    </>)

}

