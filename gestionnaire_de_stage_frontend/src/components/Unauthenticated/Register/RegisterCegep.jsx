import React, {useState} from "react";
import StepPassword from "./SharedSteps/StepPassword";
import StepInformationGeneral from "./SharedSteps/StepInformationGeneral";
import {Student, Supervisor} from "../../../models/User";
import {Step} from "../../../enums/Steps";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../../services/use-auth";
import {verificationGeneral, verificationPassword} from "./Verifications";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {BtnBack} from "../../SharedComponents/BtnBack";
import {Title} from "../../SharedComponents/Title/Title";
import {useForm} from "react-hook-form";
import {regexMatricule} from "../../../utility";
import {FormField} from "../../SharedComponents/FormField/FormField";


export default function RegisterCegep() {
    const {register, handleSubmit, watch, formState: {errors}} = useForm();
    let history = useHistory();
    let auth = useAuth();
    const [curentStep, setCurentStep] = useState(Step.CEGEP)
    const [previousStep, setPrevStep] = useState([])
    const [typeUserText, setTypeUserText] = useState("");
    let show;


    const prevStep = () => {
        setCurentStep(previousStep[previousStep.length - 1]);
        setPrevStep(prev => {
            prev.pop()
            return prev
        });
    }

    const nextStep = (current, next) => {
        setPrevStep(prev => {
            prev.push(current);
            return prev;
        })
        setCurentStep(next);
    }

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
        console.log(user)
    }

    const submit = (data, e) => {
        // e.preventDefault();
        const {
            matricule,
            firstName,
            lastName,
            email,
            password,
            passwordConfirm,
            phone,
        } = data;
        let submitter = e.nativeEvent.submitter.value
        if (submitter === 'Suivant') {
            if (curentStep === Step.CEGEP) {
                setTypeUserText(matricule.toString().length === 5 ? 'Superviseur' : 'Étudiant')
                nextStep(curentStep, Step.GENERAL)
            } else if (curentStep === Step.GENERAL && verificationGeneral(firstName, lastName, phone, email))
                nextStep(curentStep, Step.PASSWORD)
            else if (curentStep === Step.PASSWORD && verificationPassword(password) && password === passwordConfirm)
                endThis(matricule, email, password, lastName, firstName, phone)
        } else if (submitter === 'Précédent')
            prevStep()
    };


    if (curentStep === Step.CEGEP)
        show = <StepCegep register={register} errors={errors}
                          watch={watch}/>
    else if (curentStep === Step.GENERAL) {
        show = (<>
            <Title header="h2">{`Informations générales (${typeUserText})`}</Title>
            <StepInformationGeneral register={register} errors={errors}
                                    watch={watch}/>
        </>)
    } else if (curentStep === Step.PASSWORD)
        show = <StepPassword register={register} errors={errors}
                             watch={watch}/>


    return <>
        <form className="form-container" onSubmit={handleSubmit(submit)}>
            <fieldset>
                {show}
            </fieldset>
        </form>
    </>;
}

function StepCegep({register, errors}) {
    return (<>
            <FormGroup repartition={[12, 12]}>
                <FormField htmlFor="matricule">
                    <label>Matricule</label>
                    <input type="number" name="matricule"
                           placeholder="Matricule" {...register("matricule", {pattern: regexMatricule})} />
                    {errors.matricule && <span>Le matricule dois être de 5 ou 7 chiffres</span>}
                </FormField>
                <div className="form-group text-center">
                    <div className="btn-group">
                        <BtnBack/>
                        <input className="btn btn-primary" type="submit" name="next" value="Suivant"/>
                    </div>
                </div>
            </FormGroup>
        </>
    )

}

