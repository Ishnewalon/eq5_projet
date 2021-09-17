import './Register.css'
import React, {Component} from "react";

const Step = {
    CHOICE: "choice",
    CEGEP: "cegep",
    GENERAL: "general",
    MONITOR: "monitor",
    STUDENT: "student",
    PASSWORD: "password",
}

export class Register extends Component {

    state = {
        cegep: {
            active: true,
            matricule: ''
        },
        step: Step.CHOICE,
        lastStep: [],
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
    prevStep = () => {
        const {lastStep} = this.state;
        this.setState({step: lastStep[lastStep.length-1]});
        lastStep.pop()
    }

    nextStep = (val) => {
        const {lastStep} = this.state;
        const {step} = this.state;
        lastStep.push(step)
        this.setState({lastStep: lastStep});
        this.setState({step: val});

    }
    handleChange = input => e => {
        this.setState({[input]: e.target.value});
    }

    render() {
        const {step} = this.state;
        const {
            email,
            password,
            first_name,
            last_name,
            city,
            phone,
            companyName,
            address,
            codePostal,
            matricule
        } = this.state;
        const values = {email, password, first_name, last_name, phone}
        const valMoniteur = {companyName, city, address, codePostal}
        const valPwd = {password}
        const mat = {matricule}
        let show = null;

        switch (step) {
            case Step.CEGEP:
                show = <Cegep prevStep={this.prevStep} nextStep={this.nextStep} handleChange={this.handleChange} values={mat}/>
                break;
            case Step.CHOICE:
                // show = <InformationGeneral nextStep={this.nextStep} handleChange={this.handleChange}
                //                            values={values}/>
                show = <Choice prevStep={this.prevStep} nextStep={this.nextStep}/>
                break;
            case Step.GENERAL:
                show = <InformationGeneral nextStep={this.nextStep} handleChange={this.handleChange}
                                           values={values}/>
                break;
            case Step.MONITOR:
                show = <Moniteur prevStep={this.prevStep} nextStep={this.nextStep} handleChange={this.handleChange}
                                 values={valMoniteur}/>
                break;
            case Step.PASSWORD:
                show = <PwdPart prevStep={this.prevStep} nextStep={this.nextStep} handleChange={this.handleChange}
                                values={valPwd}/>
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
                        </legend>
                        <br/>
                        {show}
                    </fieldset>
                </form>
            </div>
        </div>;


    }
}

const InformationGeneral = ({nextStep, handleChange, values}) => {

    const Continue = (val) => {
        nextStep(val);
    }

    return (<div>
            <div className="form-group row">
                <div className="col-md-6">
                    <label>Prenom</label>
                    <div className="input-group">
                        <input name="first_name" placeholder="Prenom" className="form-control" type="text"
                               value={values.first_name} onChange={handleChange('first_name')}/>
                    </div>
                </div>
                <div className="col-md-6">
                    <label>Nom</label>
                    <div>
                        <div className="input-group">
                            <input name="last_name" placeholder="Nom" className="form-control" type="text"
                                   value={values.last_name} onChange={handleChange('last_name')}/>
                        </div>
                    </div>
                </div>
            </div>
            <div className="form-group">
                <label>E-Mail</label>
                <div className="input-group">
                    <input name="email" placeholder="Adresse E-mail" className="form-control" type="text"
                           value={values.email} onChange={handleChange("email")}/>
                </div>
            </div>
            <div className="form-group">
                <label>Téléphone</label>
                <div className="input-group">
                    <input name="contact_no" placeholder="000 000 000" className="form-control" type="text"
                           value={values.phone} onChange={handleChange('phone')}/>
                </div>
            </div>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        Continue(Step.PASSWORD)
                    }}>Suivant</button>
                </div>
            </div>
        </div>
    )
}
const Moniteur = ({prevStep, nextStep, handleChange, values}) => {

    const Previous = e => {
        e.preventDefault();
        prevStep();
    }
    const Continue = e => {
        e.preventDefault();
        nextStep();
    }

    return (<div>
            <div className="form-group row">
                <div className="col-md-6">
                    <label>Nom de la compagnie</label>
                    <div className="input-group">
                        <input name="companyName" placeholder="Nom de compagnie" className="form-control" type="text"
                               value={values.companyName} onChange={handleChange('companyName')}/>
                    </div>
                </div>
                <div className="col-md-6">
                    <label>Ville</label>
                    <div>
                        <div className="input-group">
                            <input name="city" placeholder="Ville" className="form-control" type="text"
                                   value={values.city} onChange={handleChange('city')}/>
                        </div>
                    </div>
                </div>
            </div>
            <div className="form-group">
                <label>Adresse de la compagnie</label>
                <div className="input-group">
                    <input name="address" placeholder="Rue, boulevard, avenue.." className="form-control" type="text"
                           value={values.address} onChange={handleChange("address")}/>
                </div>
            </div>
            <div className="form-group">
                <label>Code Postale</label>
                <div className="input-group">
                    <input name="codePostal" placeholder="XXX 123" className="form-control" type="text"
                           value={values.codePostal} onChange={handleChange('codePostal')}/>
                </div>
            </div>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={Previous}>Precedent</button>
                    <button className="btn btn-primary" type={"button"} onClick={Continue}>Suivant</button>
                </div>
            </div>
        </div>
    )
}

const PwdPart = ({prevStep, nextStep, handleChange, values}) => {

    const Previous = e => {
        e.preventDefault();
        prevStep();
    }
    const Continue = e => {
        e.preventDefault();
        nextStep();
    }
    return (<div>
            <div className="form-group row">
                <div className="col-md-12">
                    <label>Mot de passe</label>
                    <div className="input-group">
                        <input name="pwd" placeholder="Votre mot de passe" className="form-control" type="password"
                               value={values.password} onChange={handleChange('password')}/>
                    </div>
                </div>

                {/*TODO REVOIR LA CONFIRMATION DU MDP*/}
                {/*<div className="col-md-6">*/}
                {/*    <label>Confirmez votre mot de passe</label>*/}
                {/*    <div className="input-group">*/}
                {/*        <input name="pwd2" placeholder="Confirmez votre mot de passe" className="form-control" type="text"*/}
                {/*               value={values.pwd2} onChange={handleChange('pwd2')}/>*/}
                {/*    </div>*/}
                {/*</div>*/}
                <div className="form-group text-center">
                    <label/>
                    <div>
                        <button className="btn btn-primary" type={"button"} onClick={Previous}>Precedent</button>
                        <button className="btn btn-primary" type={"button"} onClick={Continue}>Suivant</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

const Cegep = ({prevStep,nextStep, handleChange, values}) => {

    const Previous = e => {
        e.preventDefault()
        prevStep();
    }
    const Continue = val => {
        nextStep(val);
    }

    return (<div>
            <div className="form-group row">
                <div className="col-md-6">
                    <label>Matricule</label>
                    <div className="input-group">
                        <input name="matricule" placeholder="Matricule" className="form-control" type="text"
                               value={values.matricule} onChange={handleChange('matricule')}/>
                    </div>
                </div>
            </div>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={Previous}>Precedent</button>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        Continue(Step.GENERAL)
                    }}>Suivant</button>
                </div>
            </div>
        </div>
    )
}
const Choice = ({nextStep}) => {

    const Continue = (value) => {
        console.log(value)
        nextStep(value);

    }

    return (<div>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        Continue(Step.CEGEP)
                    }}>Membre du cegep
                    </button>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        Continue(Step.MONITOR)
                    }}>Companie
                    </button>
                </div>
            </div>
        </div>
    )
}


