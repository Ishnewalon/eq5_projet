import React, {useState} from "react";
import StepPassword from "./SharedSteps/StepPassword";
import StepInformationGeneral from "./SharedSteps/StepInformationGeneral";
import {Student, Supervisor} from "../../../models/User";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../../hooks/use-auth";
import {BtnBack} from "../../SharedComponents/BtnBack";
import {Title} from "../../SharedComponents/Title";
import {useForm} from "react-hook-form";
import {regexMatricule} from "../../../utility";
import {checkMatricule} from "../../../services/user-service";
import {FormGroup} from "../../SharedComponents/Form/FormGroup";
import {ProgressBar} from "../../SharedComponents/ProgressBar";
import {FieldInput} from "../../SharedComponents/Form/FormFields";
import {Column} from "../../SharedComponents/Column";

export default function RegisterCegep() {
    const {register, handleSubmit, watch, formState: {errors}} = useForm({
        mode: "onSubmit",
        reValidateMode: "onSubmit",
    });
    let history = useHistory();
    let auth = useAuth();
    const [currentStep, setCurrentStep] = useState(0)
    const [typeUserText, setTypeUserText] = useState("");
    let show;
    const endThis = (matricule, email, password, lastName, firstName, phone) => {
        let user;
        if (matricule.toString().length === 5) {
            user = new Supervisor(email, password, lastName, firstName, phone, matricule);
            auth.signupSupervisor(user).then(ok => {
                if (ok) history.push("/login")
            })
        } else if (matricule.toString().length === 7) {
            user = new Student(email, password, lastName, firstName, phone, matricule);
            auth.signupStudent(user).then(ok => {
                if (ok) history.push("/login")
            })
        }
    }

    const submit = (data, e) => {
        const {
            matricule,
            firstName,
            lastName,
            email,
            password,
            confirmation,
            phone,
        } = data;
        const submitter = e.nativeEvent.submitter.value
        if (submitter === 'Suivant') {
            if (currentStep === 0) {
                setTypeUserText(matricule.toString().length === 5 ? 'Superviseur' : 'Étudiant')
                setCurrentStep(currentStep + 1)
            } else if (currentStep === 1)
                setCurrentStep(currentStep + 1)
            else if (currentStep === 2 && password === confirmation)
                endThis(matricule, email, password, lastName, firstName, phone)
        } else if (submitter === 'Précédent')
            setCurrentStep(currentStep - 1)
    };


    if (currentStep === 0)
        show = <StepCegep register={register} errors={errors}/>
    else if (currentStep === 1) {
        show = (<>
            <StepInformationGeneral register={register} errors={errors} prev={() => setCurrentStep(currentStep - 1)}/>
        </>)
    } else if (currentStep === 2)
        show =
            <StepPassword register={register} errors={errors} watch={watch}
                          prev={() => setCurrentStep(currentStep - 1)}/>


    function getTitle() {
        if (currentStep === 0)
            return <Title header="h5">Matricule</Title>
        else if (currentStep === 1)
            return <Title header="h5">{`Informations générales (${typeUserText})`}</Title>
        else if (currentStep === 2)
            return <Title header="h5">Mot de passe</Title>
    }

    return <>
        <form className="form-container" onSubmit={handleSubmit(submit)} noValidate>
            <ProgressBar totalSteps={3} currentStep={currentStep}/>
            {getTitle()}
            <fieldset>
                {show}
            </fieldset>
        </form>
    </>;
}

function StepCegep({register, errors}) {
    return (
        <FormGroup>
            <Column>
                <FieldInput label="Matricule"
                            validation={{
                                required: "Vous devez entrez votre matricule pour continuer",
                                pattern: {
                                    value: regexMatricule,
                                    message: "Votre matricule doit être de 5 ou 7 chiffres"
                                },
                                validate: async (matricule) =>
                                    await checkMatricule(matricule) || "Ce matricule est déjà utilisé"
                            }}
                            error={errors.matricule}
                            name="matricule"
                            register={register}
                            type="text"
                            placeholder="0000000 (Étudiant) ou 00000 (Superviseur)"
                            autoComplete="on"/>
            </Column>
            <Column>
                <div className="form-group text-center">
                    <div className="btn-group mt-3">
                        <BtnBack/>
                        <input className="btn btn-primary" type="submit" name="next" value="Suivant"/>
                    </div>
                </div>
            </Column>
        </FormGroup>
    )

}

