import React, {useState} from "react";
import StepPassword from "./SharedSteps/StepPassword";
import StepInformationGeneral from "./SharedSteps/StepInformationGeneral";
import {MonitorModel} from "../../../models/User";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../../services/use-auth";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {BtnBack} from "../../SharedComponents/BtnBack";
import {useForm} from "react-hook-form";
import {regexCodePostal, regexName} from "../../../utility";


export default function RegisterMonitor() {
    const {register, handleSubmit, watch, formState: {errors}} = useForm();
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
            else if (curentStep === 2)
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
        <form className="form-container" onSubmit={handleSubmit(submit)}>
            <fieldset>
                {show}
            </fieldset>
        </form>
    </>);
}


function StepMonitor({register, errors}) {
    return (<>
        <FormGroup>
            <FormField htmlFor="companyName">
                <label>Nom de la compagnie</label>
                <input name="companyName" placeholder="Nom de compagnie"
                       type="text"
                       {...register("companyName", {required: true, pattern: regexName})}/>
                {errors.companyName && <span className="error">Ce champ est obligatoire!</span>}
            </FormField>
            <FormField htmlFor="city">
                <label>Ville</label>
                <input name="city" placeholder="Ville" type="text"
                       {...register("city", {required: true, pattern: regexName})}/>
                {errors.city && <span className="error">Ce champ est obligatoire!</span>}
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField htmlFor="address">
                <label>Adresse de la compagnie</label>
                <input name="address" placeholder="Rue, boulevard, avenue.." type="text"
                       {...register("address", {required: true, pattern: regexName})}/>
                {errors.address && <span className="error">Ce champ est obligatoire!</span>}
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField htmlFor="postalCode">
                <label>Code Postale</label>
                <input name="postalCode" placeholder="H0H 0H0" type="text"
                       {...register("postalCode", {required: true, pattern: regexCodePostal})}/>
                {errors.postalCode && <span className="error">Ce champ est obligatoire!</span>}
            </FormField>
        </FormGroup>
        <div className="form-group text-center">
            <div className="btn-group">
                <BtnBack/>
                <input className="btn btn-primary" type="submit" name="next" value="Suivant"/>
            </div>
        </div>
    </>)

}

