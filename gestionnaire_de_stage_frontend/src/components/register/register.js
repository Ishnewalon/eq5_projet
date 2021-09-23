import './register.css'
import React, {Component} from "react";
import Password from "./steps/password";
import InformationGeneral from "./steps/informationGeneral";
import Monitor from "./steps/monitor";
import Choice from "./steps/choices";
import Cegep from "./steps/cegep";
import {MonitorModel, Student, Supervisor} from "../../models/user";
import {getData} from "../../services/auth-service"

const header = new Headers()
header.append('content-type', 'application/json')


export const Step = {
    CHOICE: "choice",
    CEGEP: "cegep",
    GENERAL: "general",
    MONITOR: "monitor",
    STUDENT: "student",
    PASSWORD: "password",
}
export const UserType = {
    MONITOR: "monitor",
    STUDENT: "student",
    SUPERVISOR: "supervisor",

}

export default class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            step: Step.CHOICE,
            previousStep: [],
            userType: null,
            matricule: '',
            email: '',
            password: '',
            last_name: '',
            first_name: '',
            phone: '',
            companyName: '',
            address: '',
            codePostal: '',
            city: '',
        }
        this.handleChange = this.handleChange.bind(this);
    }


    prevStep = () => {
        const {previousStep} = this.state;
        this.setState({step: previousStep[previousStep.length - 1]});
        previousStep.pop()
    }

    nextStep = (val) => {
        const {previousStep} = this.state;
        const {step} = this.state;
        previousStep.push(step)
        this.setState({lastStep: previousStep});
        this.setState({step: val});
    }

    updateUserType = (type) => {
        this.setState({userType: type})
    }

    handleChange = input => e => {
        this.setState({[input]: e.target.value});
    }

    finish = () => {
        const {
            email, password, first_name, last_name, phone, companyName, address, codePostal, city, matricule
        } = this.state;
        let user = null
        if (this.state.userType === UserType.STUDENT) {
            user = new Student(email, password, last_name, first_name, phone, matricule);
        }
        if (this.state.userType === UserType.SUPERVISOR) {
            user = new Supervisor(email, password, last_name, first_name, phone, matricule);
        }
        if (this.state.userType === UserType.MONITOR) {
            user = new MonitorModel(email, password, last_name, first_name, phone, companyName, address, city, codePostal);
        }

        getData(user).then()
    }

    render() {
        const {step} = this.state;
        const {
            email, password, first_name, last_name, city, phone, companyName, address, codePostal, matricule
        } = this.state;
        const valGeneral = {email, first_name, last_name, phone}
        const valMonitor = {companyName, city, address, codePostal}
        let show = null;

        switch (step) {
            case Step.CHOICE:
                show = <Choice prevStep={this.prevStep} nextStep={this.nextStep}/>
                break;
            case Step.CEGEP:
                show = <Cegep prevStep={this.prevStep} nextStep={this.nextStep} updateUserType={this.updateUserType}
                              handleChange={this.handleChange}
                              matricule={matricule}/>
                break;
            case Step.MONITOR:
                show = <Monitor prevStep={this.prevStep} nextStep={this.nextStep} updateUserType={this.updateUserType}
                                handleChange={this.handleChange}
                                values={valMonitor}/>
                break;
            case Step.GENERAL:
                show = <InformationGeneral prevStep={this.prevStep} nextStep={this.nextStep}
                                           handleChange={this.handleChange}
                                           values={valGeneral}/>
                break;
            case Step.PASSWORD:
                show = <Password prevStep={this.prevStep} finish={this.finish} handleChange={this.handleChange}
                                 password={password}/>

                break;
            default:
                break;
        }
        return <div>
            <div>
                email:{email}<br/>
                lastname:{last_name}<br/>
                firstname:{first_name}<br/>
                password:{password}<br/>
                city:{city}<br/>
                companyName:{companyName}<br/>
                phone:{phone}<br/>
                address:{address}<br/>
                codePostal:{codePostal}<br/>
            </div>
            <div className="form-container">
                <form className="bg-dark px-3 py-4 rounded shadow-lg mt-5" id="contact_form">
                    <fieldset>
                        <legend>
                            <center><h2>Inscription</h2></center>
                            <center><h3>{step}</h3></center>
                        </legend>
                        <br/>
                        {show}
                    </fieldset>
                </form>
            </div>
        </div>;
    }
}
