import React, {useState} from "react";
import StepPassword from "./Steps/StepPassword";
import StepInformationGeneral from "./Steps/StepInformationGeneral";
import {MonitorModel} from "../../../models/User";
import {Step} from "../../../enums/Steps";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../../services/use-auth";
import {ContainerBox} from "../../SharedComponents/ContainerBox/ContainerBox";
import {StepMonitorr} from "./StepMonitorr";
import {verificationGeneral, verificationMonitor, verificationPassword} from "./Verifications";


export default function RegisterMonitor() {
    let history = useHistory();
    let auth = useAuth();
    const [curentStep, setCurentStep] = useState(Step.MONITOR)
    const [previousStep, setPrevStep] = useState([])
    const [userInfo, setUserInfo] = useState({
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
    let show;
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
        setPrevStep(prev => prev ? prev.pop() : [])
    }

    const nextStep = (current, next) => {
        setPrevStep(prevs => prevs ? [...prevs, current] : [current]);
        setCurentStep(next);
    }

    const endThis = () => {
        let user = new MonitorModel(email, password, lastName, firstName, phone, companyName, address, city, postalCode);
        auth.signupMonitor(user).then(() => history.push("/login"))
    }

    const submit = e => {
        e.preventDefault();
        // noinspection JSUnresolvedVariable
        let submitter = e.nativeEvent.submitter.value

        if (submitter === 'Suivant') {
            if (curentStep === Step.MONITOR && verificationMonitor(companyName, address, city, postalCode))
                nextStep(curentStep, Step.GENERAL)
            else if (curentStep === Step.GENERAL && verificationGeneral(firstName, lastName, phone, email))
                nextStep(curentStep, Step.PASSWORD)
            else if (curentStep === Step.PASSWORD && verificationPassword(password))
                endThis()
        } else if (submitter === 'Précédent')
            prevStep()
    };


    if (curentStep === Step.MONITOR)
        show = <StepMonitorr handleChange={handleChange} address={address} postalCode={postalCode} city={city}
                             companyName={companyName}/>
    else if (curentStep === Step.GENERAL)
        show = <StepInformationGeneral handleChange={handleChange} email={email} firstName={firstName}
                                       lastName={lastName} phone={phone}/>
    else if (curentStep === Step.PASSWORD)
        show = <StepPassword handleChange={handleChange} password={password}/>


    return (<ContainerBox>
        <form className="form-container" onSubmit={submit}>
            <fieldset>
                {show}
            </fieldset>
        </form>
    </ContainerBox>);
}


