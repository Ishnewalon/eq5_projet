import React, {useState} from "react";
import StepPassword from "./SharedSteps/StepPassword";
import StepInformationGeneral from "./SharedSteps/StepInformationGeneral";
import {Student, Supervisor} from "../../../models/User";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../../services/use-auth";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {BtnBack} from "../../SharedComponents/BtnBack";
import {Title} from "../../SharedComponents/Title/Title";
import {useForm} from "react-hook-form";
import {regexMatricule} from "../../../utility";
import {FormField} from "../../SharedComponents/FormField/FormField";


export default function RegisterCegep() {
    const {register, handleSubmit, formState: {errors}} = useForm();
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
            passwordConfirm,
            phone,
        } = data;
        const submitter = e.nativeEvent.submitter.value
        if (submitter === 'Suivant') {
            if (curentStep === 0) {
                setTypeUserText(matricule.toString().length === 5 ? 'Superviseur' : 'Étudiant')
                setCurentStep(curentStep + 1)
            } else if (curentStep === 1)
                setCurentStep(curentStep + 1)
            else if (curentStep === 2 && password === passwordConfirm)
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
        show = <StepPassword register={register} errors={errors} prev={() => setCurentStep(curentStep - 1)}/>


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
                    <input type="number" name="matricule" className={errors.matricule ? "border-danger" : ""}
                           placeholder="Matricule" {...register("matricule", {
                        required: true,
                        pattern: regexMatricule
                    })} />
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

