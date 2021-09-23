import {Step, UserType} from "../register";
import {Component} from "react";

export default class Cegep extends Component {

    previous(e) {
        e.preventDefault()
        this.props.prevStep();
    }

    continue(val) {
        if (this.props.matricule.length === 5)
            this.props.updateUserType(UserType.SUPERVISOR)
        if (this.props.matricule.length === 7)
            this.props.updateUserType(UserType.STUDENT)
        this.props.nextStep(val);
    }

    render() {
        return (<div>
                <div className="form-group row">
                    <div className="col-md-12">
                        <label>Matricule</label>
                        <div className="input-group">
                            <input name="matricule" placeholder="Matricule" className="form-control" type="number"
                                   value={this.props.matricule} onChange={this.props.handleChange('matricule')}/>
                        </div>
                    </div>
                </div>
                <div className="form-group text-center">
                    <label/>
                    <div>
                        <button className="btn btn-primary" type={"button"} onClick={this.previous}>Precedent</button>
                        <button className="btn btn-primary" type={"button"} onClick={() => {
                            this.continue(Step.GENERAL)
                        }}>Suivant
                        </button>
                    </div>
                </div>
            </div>
        )
    }
}

