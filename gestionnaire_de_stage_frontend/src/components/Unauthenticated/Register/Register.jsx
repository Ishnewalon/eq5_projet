import React, {useState} from "react";
import StepPassword from "./Steps/StepPassword";
import StepInformationGeneral from "./Steps/StepInformationGeneral";
import StepMonitor from "./Steps/StepMonitor";
import Choice from "./Steps/StepChoices";
import StepCegep from "./Steps/StepCegep";
import {MonitorModel, Student, Supervisor} from "../../../models/User";
import {Step} from "../../../enums/Steps";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../../services/use-auth";
import {UserType} from "../../../enums/UserTypes";
import {ContainerBox} from "../../SharedComponents/ContainerBox/ContainerBox";
import {regexPassword, toastErr} from "../../../utility";


export default function Register() {
    let history = useHistory();
    let auth = useAuth();
    const [step, setStep] = useState(Step.CHOICE)
    const [previousStep, setPrevStep] = useState([])
    const [userType, setUserType] = useState(null)
    const [userInfo, setUserInfo] = useState({
        matricule: '',
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        phone: '',
        address: '',
        city: '',
        postalCode: '',
        companyName: '',
    })
    const {
        matricule,
        firstName,
        lastName,
        email,
        password,
        phone,
        address,
        city,
        postalCode,
        companyName
    } = userInfo;

    const prevStep = () => {
        setStep(previousStep[previousStep.length - 1]);
        setPrevStep(previousStep.filter(item => item !== previousStep[previousStep.length - 1]))
    }

    const nextStep = (val) => {
        previousStep.push(step)
        setPrevStep(previousStep);
        setStep(val);
    }

    const updateUserType = type => setUserType(type);

    const handleChange = (event) => {
        const {value, name} = event.target;

        setUserInfo((prev) => {
            return {
                ...prev,
                [name]: value
            }
        })
    }

    const endThis = (e) => {
        e.preventDefault();
        let user = null

        if(!regexPassword.test(password)){
            toastErr.fire({title: 'Le mot de passe doit être entre 8 et 64 caractères'}).then();
            return;
        }

        if (userType === UserType.STUDENT) {
            user = new Student(email, password, lastName, firstName, phone, matricule);
            auth.signupStudent(user).then(() => history.push("/login"));
        }
        if (userType === UserType.SUPERVISOR) {
            user = new Supervisor(email, password, lastName, firstName, phone, matricule);
            auth.signupSupervisor(user).then(() => history.push("/login"))
        }
        if (userType === UserType.MONITOR) {
            user = new MonitorModel(email, password, lastName, firstName, phone, companyName, address, city, postalCode);
            auth.signupMonitor(user).then(() => history.push("/login"))
        }

    }


    let show = null;

    switch (step) {
        case Step.CHOICE:
            show = <Choice prevStep={prevStep} nextStep={nextStep}/>
            break;
        case Step.CEGEP:
            show = <StepCegep prevStep={prevStep} nextStep={nextStep} updateUserType={updateUserType}
                              handleChange={handleChange}
                              matricule={matricule}/>
            break;
        case Step.MONITOR:
            show =
                <StepMonitor prevStep={prevStep} nextStep={nextStep} updateUserType={updateUserType}
                             handleChange={handleChange}
                             address={address} postalCode={postalCode} city={city} companyName={companyName}/>
            break;
        case Step.GENERAL:
            show = <StepInformationGeneral prevStep={prevStep} nextStep={nextStep}
                                           handleChange={handleChange}
                                           email={email} firstName={firstName} lastName={lastName} phone={phone}/>
            break;
        case Step.PASSWORD:
            show = <StepPassword prevStep={prevStep} handleChange={handleChange}
                                 password={password}/>

            break;
        default:
            break;
    }
    return (<ContainerBox>
        <form className="form-container" onSubmit={endThis}>
            <fieldset>
                {show}
            </fieldset>
        </form>
    </ContainerBox>);
}
