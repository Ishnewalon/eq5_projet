import React, {useState} from "react";
import StepPassword from "./SharedSteps/StepPassword";
import StepInformationGeneral from "./SharedSteps/StepInformationGeneral";
import {Student, Supervisor} from "../../../models/User";
import {Step} from "../../../enums/Steps";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../../services/use-auth";
import {verificationGeneral, verificationPassword} from "./Verifications";
import {regexMatricule, toastErr} from "../../../utility";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {BtnBack} from "../../SharedComponents/BtnBack";
import {Title} from "../../SharedComponents/Title/Title";


export default function RegisterCegep() {
    let history = useHistory();
    let auth = useAuth();
    const [curentStep, setCurentStep] = useState(Step.CEGEP)
    const [previousStep, setPrevStep] = useState([])
    const [userInfo, setUserInfo] = useState({
        matricule: '',
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        passwordConfirm: '',
        phone: '',
    })
    let show;
    const {
        matricule,
        firstName,
        lastName,
        email,
        password,
        passwordConfirm,
        phone,
    } = userInfo;

    const handleChange = (event) => {
        const {value, name} = event.target;

        setUserInfo((prev) => {
            return {
                ...prev,
                [name]: value
            }
        })
    }

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

    const endThis = () => {
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

    const submit = e => {
        e.preventDefault();
        // noinspection JSUnresolvedVariable
        let submitter = e.nativeEvent.submitter.value

        if (submitter === 'Suivant') {
            if (curentStep === Step.CEGEP) {
                if (!matricule || !regexMatricule.test(matricule)) {
                    toastErr.fire({title: 'Matricule invalide!'}).then()
                    return
                }
                nextStep(curentStep, Step.GENERAL)
            } else if (curentStep === Step.GENERAL && verificationGeneral(firstName, lastName, phone, email))
                nextStep(curentStep, Step.PASSWORD)
            else if (curentStep === Step.PASSWORD && verificationPassword(password) && password === passwordConfirm)
                endThis()
        } else if (submitter === 'Précédent')
            prevStep()
    };


    let typeUserText = matricule.toString().length === 5 ? 'Superviseur' : 'Étudiant'
    if (curentStep === Step.CEGEP)
        show = <StepCegep handleChange={handleChange} matricule={matricule}/>
    else if (curentStep === Step.GENERAL) {
        show = (<>
            <Title header="h2">{`Informations générales (${typeUserText})`}</Title>
            <StepInformationGeneral handleChange={handleChange} email={email} firstName={firstName}
                                    lastName={lastName} phone={phone}/>
        </>)
    } else if (curentStep === Step.PASSWORD)
        show = <StepPassword handleChange={handleChange} password={password} passwordConfirm={passwordConfirm}/>


    return (<>
        <form className="form-container" onSubmit={submit}>
            <fieldset>
                {show}
            </fieldset>
        </form>
    </>);
}

function StepCegep({handleChange, matricule}) {

    return (<>
            <FormGroup repartition={[12, 12]}>
                <FormField htmlFor="matricule">
                    <label>Matricule</label>
                    <input name="matricule" placeholder="Matricule" type="number"
                           value={matricule} onChange={handleChange}/>
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

