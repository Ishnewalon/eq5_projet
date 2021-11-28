import React, {useState} from "react";
import StepPassword from "./SharedSteps/StepPassword";
import StepInformationGeneral from "./SharedSteps/StepInformationGeneral";
import {Student, Supervisor} from "../../../models/User";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../../hooks/use-auth";
import {BtnBack} from "../../SharedComponents/BtnBack";
import {Title} from "../../SharedComponents/Title/Title";
import {useForm} from "react-hook-form";
import {regexMatricule} from "../../../utility";
import {FormInput} from "../../SharedComponents/FormInput/FormInput";
import {checkMatricule} from "../../../services/user-service";
import {Column, FormGroupV2} from "../../SharedComponents/FormGroup/FormGroupV2";


export default function RegisterCegep() {
    const {register, handleSubmit, watch, formState: {errors}} = useForm({
        mode: "onSubmit",
        reValidateMode: "onSubmit",
    });
    let history = useHistory();
    let auth = useAuth();
    const [curentStep, setCurentStep] = useState(0)
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
            if (curentStep === 0) {
                setTypeUserText(matricule.toString().length === 5 ? 'Superviseur' : 'Étudiant')
                setCurentStep(curentStep + 1)
            } else if (curentStep === 1)
                setCurentStep(curentStep + 1)
            else if (curentStep === 2 && password === confirmation)
                endThis(matricule, email, password, lastName, firstName, phone)
        } else if (submitter === 'Précédent')
            setCurentStep(curentStep - 1)
    };


    if (curentStep === 0)
        show = <StepCegep register={register} errors={errors}/>
    else if (curentStep === 1) {
        show = (<>
            <Title header="h2">{`Informations générales (${typeUserText})`}</Title>
            <StepInformationGeneral register={register} errors={errors} prev={() => setCurentStep(curentStep - 1)}/>
        </>)
    } else if (curentStep === 2)
        show =
            <StepPassword register={register} errors={errors} watch={watch} prev={() => setCurentStep(curentStep - 1)}/>


    return <>
        <form className="form-container" onSubmit={handleSubmit(submit)} noValidate>
            <fieldset>
                {show}
            </fieldset>
        </form>
    </>;
}

function StepCegep({register, errors}) {
    return (
        <FormGroupV2>
            <Column>
                <FormInput label="Matricule"
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
                           type="number"
                           placeholder="0000000 (Étudiant) ou 00000 (Superviseur)"/>
            </Column>
            <Column>
                <div className="form-group text-center">
                    <div className="btn-group mt-3">
                        <BtnBack/>
                        <input className="btn btn-primary" type="submit" name="next" value="Suivant"/>
                    </div>
                </div>
            </Column>
        </FormGroupV2>
    )

}

