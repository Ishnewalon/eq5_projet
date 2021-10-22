import {Step, UserType} from "../Register";
import React,{Component} from "react";
import {FieldAddress} from "../../Fields/FieldAddress";

const regexCodePostal = /^([A-Za-z]\s?[0-9]){3}$/;

export default class StepMonitor extends Component {

    previous = (e) => {
        e.preventDefault();
        this.props.prevStep();
    }

    continue = (val) => {
        this.props.updateUserType(UserType.MONITOR);
        this.props.nextStep(val);
    }

    verification = () => {
        if (!this.props.values.companyName) {
            alert("Nom de compagnie est vide")
            return false
        }
        if (!this.props.values.city) {
            alert("Nom de ville est vide")
            return false
        }
        if (!this.props.values.address) {
            alert("L'adresse est vide")
            return false
        }
        if (!this.props.values.codePostal) {
            alert("Code postal est vide")
            return false
        }
        if (!this.props.values.companyName.match(/^[a-zA-Z0-9]+$/)) {
            alert("Nom de compagnie est invalide")
            return false;
        }
        if (!this.props.values.city.match(/^[a-zA-Z]+$/)) {
            alert("Nom de ville est invalide")
            return false;
        }
        if (!regexCodePostal.test(this.props.values.codePostal)) {
            alert("Code postal est invalide")
            return false;
        }
        return true;
    }

    render() {
        return (<div>
            <div className="form-group row">
                <div className="col-md-6">
                    <label>Nom de la compagnie</label>
                    <div className="input-group">
                        <input name="companyName" placeholder="Nom de compagnie" className="form-control"
                               type="text"
                               value={this.props.values.companyName}
                               onChange={this.props.handleChange('companyName')}/>
                    </div>
                </div>
                <div className="col-md-6">
                    <label>Ville</label>
                    <div>
                        <div className="input-group">
                            <input name="city" placeholder="Ville" className="form-control" type="text"
                                   value={this.props.values.city} onChange={this.props.handleChange('city')}/>
                        </div>
                    </div>
                </div>
            </div>
            <div className="form-group">
                <FieldAddress label="Adresse de la compagnie" address={this.props.values.address} handleChange={this.props.handleChange}/>
            </div>
            <div className="form-group">
                <label>Code Postale</label>
                <div className="input-group">
                    <input name="codePostal" placeholder="XXX 123" className="form-control" type="text"
                           value={this.props.values.codePostal} onChange={this.props.handleChange('codePostal')}/>
                </div>
            </div>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={this.previous}>Précédent
                    </button>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        if (this.verification()) this.continue(Step.GENERAL)
                    }}>Suivant
                    </button>
                </div>
            </div>
        </div>)
    }
}
