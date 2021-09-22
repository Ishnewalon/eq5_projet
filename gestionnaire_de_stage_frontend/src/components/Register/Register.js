import './Register.css'
import React, {Component} from "react";
import Password from "./Steps/Password";
import InformationGeneral from "./Steps/InformationGeneral";
import Monitor from "./Steps/Monitor";
import Choice from "./Steps/Choices";
import Cegep from "./Steps/Cegep";

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

export class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            step: Step.CHOICE,
            previousStep: [],
            userType: null,
            active: true,
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

    async getData() {
        const response = await fetch("http://localhost:4000/posts", {
            method: 'POST',
            mode: 'cors', // no-cors, *cors, same-origin
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.state)
        });
        const data = await response.json();

        console.log(data)
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
        console.log(this.state)
    }

    handleChange = input => e => {
        this.setState({[input]: e.target.value});
    }
    finish = () => {
        this.getData().then()
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
                                 values={this.state}/>

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
