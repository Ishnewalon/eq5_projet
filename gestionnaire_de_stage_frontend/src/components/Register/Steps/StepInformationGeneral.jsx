import {Step} from "../Register";
import {Component} from "react";

// eslint-disable-next-line
const regexEmail = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
const regexPhone = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;

export default class StepInformationGeneral extends Component {

    previous = (e) => {
        e.preventDefault();
        this.props.prevStep();
    }
    continue = (val) => {
        this.props.nextStep(val);
    }

    verification = () => {
        if (!this.props.values.firstName) {
            alert("Le champs prénom est vide")
            return false
        }
        if (!this.props.values.lastName) {
            alert("Le champs nom est vide")
            return false
        }
        if (!this.props.values.email) {
            alert("Le champs courriel est vide")
            return false
        }
        if (!this.props.values.phone) {
            alert("Le champs numéro de téléphone est vide")
            return false
        }
        if (!this.props.values.firstName.match(/^[a-zA-Z\-\s]+$/)) {
            alert("Le champs prénom est invalide")
            return false;
        }
        if (!this.props.values.lastName.match(/^[a-zA-Z\-\s]+$/)) {
            alert("Le champs nom est invalide")
            return false;
        }
        if (!regexEmail.test(this.props.values.email)) {
            alert("Le champs courriel est invalide")
            return false;
        }
        if (!regexPhone.test(this.props.values.phone)) {
            alert("Le champs numéro de téléphone est invalide")
            return false;
        }
        return true;
    }

    render() {
        return (<div>
            <div className="form-group row">
                <div className="col-md-6">
                    <label>Prenom</label>
                    <div className="input-group">
                        <input name="firstName" placeholder="Prenom" className="form-control" type="text"
                               value={this.props.values.firstName} onChange={this.props.handleChange('firstName')}/>
                    </div>
                </div>
                <div className="col-md-6">
                    <label>Nom</label>
                    <div>
                        <div className="input-group">
                            <input name="lastName" placeholder="Nom" className="form-control" type="text"
                                   value={this.props.values.lastName} onChange={this.props.handleChange('lastName')}/>
                        </div>
                    </div>
                </div>
            </div>
            <div className="form-group">
                <label>E-Mail</label>
                <div className="input-group">
                    <input name="email" placeholder="Adresse E-mail" className="form-control" type="email"
                           value={this.props.values.email} onChange={this.props.handleChange("email")}/>
                </div>
            </div>
            <div className="form-group">
                <label>Téléphone</label>
                <div className="input-group">
                    <input name="contact_no" placeholder="000 000 000" className="form-control" type="tel"
                           value={this.props.values.phone} onChange={this.props.handleChange('phone')}/>
                </div>
            </div>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={this.previous}>Precedent</button>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        if (this.verification())
                            this.continue(Step.PASSWORD)
                    }}>Suivant
                    </button>
                </div>
            </div>
        </div>)
    }
}
