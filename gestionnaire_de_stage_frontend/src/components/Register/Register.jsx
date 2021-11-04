import './Register.css'
import React, {useState} from "react";
import StepPassword from "./Steps/StepPassword";
import StepInformationGeneral from "./Steps/StepInformationGeneral";
import StepMonitor from "./Steps/StepMonitor";
import Choice from "./Steps/StepChoices";
import StepCegep from "./Steps/StepCegep";
import {MonitorModel, Student, Supervisor} from "../../models/User";
import {Step} from "../../enums/Steps";
import {UserType} from "../../enums/UserTypes";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../services/use-auth";


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

    const updateUserType = (type) => {
        setUserType(type)
    }

    const handleChange = (event) => {
        let value = event.target.value;
        let name = event.target.name;

        setUserInfo((prevalue) => {
            return {
                ...prevalue,
                [name]: value
            }
        })
    }

    const endThis = () => {
        let user = null
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
                             address={address} codePostal={postalCode} city={city} companyName={companyName}/>
            break;
        case Step.GENERAL:
            show = <StepInformationGeneral prevStep={prevStep} nextStep={nextStep}
                                           handleChange={handleChange}
                                           email={email} firstName={firstName} lastName={lastName} phone={phone}/>
            break;
        case Step.PASSWORD:
            show = <StepPassword prevStep={prevStep} endThis={endThis} handleChange={handleChange}
                                 password={password}/>

            break;
        default:
            break;
    }
    return (<>
        <div className="form-container">
            <form className="bg-dark px-3 py-4 rounded shadow-lg mt-5" id="contact_form">
                <fieldset>
                    <legend>
                        <center><h2>Inscription</h2></center>
                    </legend>
                    <br/>
                    {show}
                </fieldset>
            </form>
        </div>
    </>);
}
