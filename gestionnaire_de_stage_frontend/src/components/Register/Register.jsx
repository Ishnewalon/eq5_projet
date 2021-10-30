import './Register.css'
import React, {Component} from "react";
import StepPassword from "./Steps/StepPassword";
import StepInformationGeneral from "./Steps/StepInformationGeneral";
import StepMonitor from "./Steps/StepMonitor";
import Choice from "./Steps/StepChoices";
import StepCegep from "./Steps/StepCegep";
import {MonitorModel, Student, Supervisor} from "../../models/User";
import AuthService from "../../services/auth-service"

export const Step = {
    CHOICE: "choice",
    CEGEP: "cegep",
    GENERAL: "general",
    MONITOR: "monitor",
    STUDENT: "student",
    PASSWORD: "password",
}
export const UserType = {
    MONITOR: ["monitor", "moniteur"],
    STUDENT: ["student", "etudiant"],
    SUPERVISOR: ["supervisor", "superviseur"],
    MANAGER: ["manager", "gestionnaire"]
}

export default class Register extends Component {

    constructor(props) {
        super(props);
        this.state = {
            hideFields: true,
            step: Step.CHOICE,
            previousStep: [],
            userType: null,
            matricule: '',
            email: '',
            password: '',
            lastName: '',
            firstName: '',
            phone: '',
            companyName: '',
            address: '',
            codePostal: '',
            city: '',
        }
        this.service = AuthService
    }


    prevStep = () => {
        const {previousStep} = this.state;
        this.setState({step: previousStep[previousStep.length - 1]});
        previousStep.pop()
    }

    nextStep = (val) => {
        console.log(val)
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

    endThis = () => {
        const {
            email, password, firstName, lastName, phone, companyName, address, codePostal, city, matricule
        } = this.state;
        let user = null
        if (this.state.userType === UserType.STUDENT) {
            user = new Student(email, password, lastName, firstName, phone, matricule);
            this.service.signupStudent(user).then(value => {
                console.log(value)

                this.props.history.push("/login")
            });
        }
        if (this.state.userType === UserType.SUPERVISOR) {
            user = new Supervisor(email, password, lastName, firstName, phone, matricule);
            this.service.signupSupervisor(user).then(value => {
                console.log(value)
                this.props.history.push("/login")
            })
        }
        if (this.state.userType === UserType.MONITOR) {
            user = new MonitorModel(email, password, lastName, firstName, phone, companyName, address, city, codePostal);
            this.service.signupMonitor(user).then(value => {
                console.log(value)
                this.props.history.push("/login")
            })
        }

    }


    render() {
        const {
            step,
            email,
            password,
            firstName,
            lastName,
            city,
            phone,
            companyName,
            address,
            codePostal,
            matricule
        } = this.state;
        let show = null;

        switch (step) {
            case Step.CHOICE:
                show = <Choice prevStep={this.prevStep} nextStep={this.nextStep}/>
                break;
            case Step.CEGEP:
                show = <StepCegep prevStep={this.prevStep} nextStep={this.nextStep} updateUserType={this.updateUserType}
                                  handleChange={this.handleChange}
                                  matricule={matricule}/>
                break;
            case Step.MONITOR:
                show =
                    <StepMonitor prevStep={this.prevStep} nextStep={this.nextStep} updateUserType={this.updateUserType}
                                 handleChange={this.handleChange}
                                 address={address} codePostal={codePostal} city={city} companyName={companyName}/>
                break;
            case Step.GENERAL:
                show = <StepInformationGeneral prevStep={this.prevStep} nextStep={this.nextStep}
                                               handleChange={this.handleChange}
                                               email={email} firstName={firstName} lastName={lastName} phone={phone}/>
                break;
            case Step.PASSWORD:
                console.log("lol oui")
                show = <StepPassword prevStep={this.prevStep} endThis={this.endThis} handleChange={this.handleChange}
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
}
