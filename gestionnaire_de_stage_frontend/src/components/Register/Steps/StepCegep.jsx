import {Step, UserType} from "../Register";
import React,{Component} from "react";

export default class StepCegep extends Component {

    previous = (e) => {
        e.preventDefault()
        this.props.prevStep();
    }

    continue = (val) => {
        const {matricule} = this.props

        if (matricule.length === 5)
            this.props.updateUserType(UserType.SUPERVISOR)
        if (matricule.length === 7)
            this.props.updateUserType(UserType.STUDENT)
        this.props.nextStep(val);
    }

    render() {
        const matricule = this.props.matricule
        return (<div>
                <div className="form-group row">
                    <div className="col-md-12">
                        <label>Matricule</label>
                        <div className="input-group">
                            <input name="matricule" placeholder="Matricule" className="form-control" type="number"
                                   value={matricule} onChange={this.props.handleChange('matricule')}/>
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

