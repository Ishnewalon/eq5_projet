import {Step, UserType} from "../register";
import {Component} from "react";

const regexCodePostal = /^([A-Za-z]\s?[0-9]){3}$/;

export default class Monitor extends Component {

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
            alert("companyName empty")
            return false
        }
        if (!this.props.values.city) {
            alert("city empty")
            return false
        }
        if (!this.props.values.address) {
            alert("address empty")
            return false
        }
        if (!this.props.values.codePostal) {
            alert("codePostal empty")
            return false
        }
        if (!this.props.values.companyName.match(/^[a-zA-Z0-9]+$/)) {
            alert("companyName invalid")
            return false;
        }
        if (!this.props.values.city.match(/^[a-zA-Z]+$/)) {
            alert("city invalid")
            return false;
        }
        if (!regexCodePostal.test(this.props.values.codePostal)) {
            alert("codePostal invalid")
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
                    <label>Adresse de la compagnie</label>
                    <div className="input-group">
                        <input name="address" placeholder="Rue, boulevard, avenue.." className="form-control"
                               type="text"
                               value={this.props.values.address} onChange={this.props.handleChange("address")}/>
                    </div>
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
                        <button className="btn btn-primary" type={"button"} onClick={this.previous}>Precedent
                        </button>
                        <button className="btn btn-primary" type={"button"} onClick={() => {
                            if (this.verification()) this.continue(Step.GENERAL)
                        }}>Suivant
                        </button>
                    </div>
                </div>
            </div>
        )
    }
}
