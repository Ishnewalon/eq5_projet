import './Register.css'
import React, {Component} from "react";
import Password from "./Steps/Password";
import InformationGeneral from "./Steps/InformationGeneral";
import Monitor from "./Steps/Monitor";
import Choice from "./Steps/Choices";
import Cegep from "./Steps/Cegep";
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
        this.service = AuthService.getInstance()
        this.handleChange = this.handleChange.bind(this);
        this.goHome = this.goHome.bind(this)

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

    goHome = () => {
        // this.props.history.push('/')
    }

    finish = () => {
        const {
            email, password, firstName, lastName, phone, companyName, address, codePostal, city, matricule
        } = this.state;
        let user = null
        if (this.state.userType === UserType.STUDENT) {
            user = new Student(email, password, lastName, firstName, phone, matricule);
            this.service.signupStudent(user).then(value => {
                console.log(value)
                this.goHome()
            });
        }
        if (this.state.userType === UserType.SUPERVISOR) {
            user = new Supervisor(email, password, lastName, firstName, phone, matricule);
            this.service.signupSupervisor(user).then(value => {
                console.log(value)
                this.goHome()
            })
        }
        if (this.state.userType === UserType.MONITOR) {
            user = new MonitorModel(email, password, lastName, firstName, phone, companyName, address, city, codePostal);
            this.service.signupMonitor(user).then(value => {
                console.log(value)
                this.goHome()
            })
        }

    }


    render() {
        const {step} = this.state;
        const {
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
        const valGeneral = {email, firstName, lastName, phone}
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
        return (<div>
            <button className="btn btn-primary" onClick={() => {
                this.setState({hideFields: !this.state.hideFields})
            }}>Show/hide
            </button>
            <div hidden={this.state.hideFields}>
                email:{email}<br/>
                lastname:{lastName}<br/>
                firstname:{firstName}<br/>
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
                <button className="btn btn-primary" onClick={() => {
                    this.goHome()
                }}>go home
                </button>
            </div>
        </div>);
    }
}
