import './Register.css'
import React, {Component} from "react";
import Password from "./Steps/Password";
import InformationGeneral from "./Steps/InformationGeneral";
import Monitor from "./Steps/Monitor";
import Choice from "./Steps/Choices";
import Cegep from "./Steps/Cegep";


export const Step = {
    CHOICE: "choice",
    CEGEP: "cegep",
    GENERAL: "general",
    MONITOR: "monitor",
    STUDENT: "student",
    PASSWORD: "password",
}

export class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            step: Step.CHOICE,
            previousStep: [],
            cegep: {
                active: true,
                matricule: ''
            },
            generalInfo: {
                email: '',
                password: '',
                last_name: '',
                first_name: '',
                phone: '',
            },
            monitor: {
                companyName: '',
                address: '',
                codePostal: '',
                city: '',
            },
        }
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
    handleChange = input => e => {
        this.setState({[input]: e.target.value});
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
                show = <Cegep prevStep={this.prevStep} nextStep={this.nextStep} handleChange={this.handleChange}
                              matricule={matricule}/>
                break;
            case Step.MONITOR:
                show = <Monitor prevStep={this.prevStep} nextStep={this.nextStep} handleChange={this.handleChange}
                                values={valMonitor}/>
                break;
            case Step.GENERAL:
                show = <InformationGeneral prevStep={this.prevStep} nextStep={this.nextStep}
                                           handleChange={this.handleChange}
                                           values={valGeneral}/>
                break;
            case Step.PASSWORD:
                show = <Password prevStep={this.prevStep} handleChange={this.handleChange}
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
