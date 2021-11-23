import React, {useState} from "react";
import StepPassword from "./SharedSteps/StepPassword";
import StepInformationGeneral from "./SharedSteps/StepInformationGeneral";
import {MonitorModel} from "../../../models/User";
import {Step} from "../../../enums/Steps";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../../services/use-auth";
import {verificationGeneral, verificationMonitor, verificationPassword} from "./Verifications";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";
import FieldAddress from "../../SharedComponents/Fields/FieldAddress";
import {BtnBack} from "../../SharedComponents/BtnBack";


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
        show = <StepMonitor handleChange={handleChange} address={address} postalCode={postalCode} city={city}
                            companyName={companyName}/>
    else if (curentStep === Step.GENERAL)
        show = <StepInformationGeneral handleChange={handleChange} email={email} firstName={firstName}
                                       lastName={lastName} phone={phone}/>
    else if (curentStep === Step.PASSWORD)
        show = <StepPassword handleChange={handleChange} password={password}/>


    return (<>
        <form className="form-container" onSubmit={submit}>
            <fieldset>
                {show}
            </fieldset>
        </form>
    </>);
}


function StepMonitor({
                         handleChange,
                         companyName,
                         address,
                         postalCode,
                         city
                     }) {


    return (<>
        <FormGroup>
            <FormField htmlFor="companyName">
                <label>Nom de la compagnie</label>
                <input name="companyName" placeholder="Nom de compagnie"
                       type="text"
                       value={companyName} onChange={handleChange}/>
            </FormField>
            <FormField htmlFor="city">
                <label>Ville</label>
                <input name="city" placeholder="Ville" type="text"
                       value={city} onChange={handleChange}/>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FieldAddress label="Adresse de la compagnie" address={address} handleChange={handleChange}/>
        </FormGroup>
        <FormGroup>
            <FormField htmlFor="postalCode">
                <label>Code Postale</label>
                <input name="postalCode" placeholder="H0H 0H0" type="text"
                       value={postalCode} onChange={handleChange}/>
            </FormField>
        </FormGroup>
        <div className="form-group text-center">
            <div className="btn-group">
                <BtnBack/>
                <input className="btn btn-primary" type="submit" name="nextt" value="Suivant"/>
            </div>
        </div>
    </>)

}

