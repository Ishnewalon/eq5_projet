import React, {Component} from "react";
import {FieldPassword} from "../../Fields/FieldPassword";

export default class StepPassword extends Component {

    previous = (e) => {
        e.preventDefault();
        this.props.prevStep();
    }

    continue = (e) => {
        e.preventDefault();
        this.props.finish();
    }

    render() {
        return (<div>
                <div className="form-group row">
                    <div className="col-md-12">
                        <FieldPassword password={this.props.password} label="Mot de passe" placeholder="Votre mot de passe" handleChange={this.props.handleChange('password')}/>
                    </div>

                    {/* REVOIR LA CONFIRMATION DU MDP*/}
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
                            <button className="btn btn-primary" type={"button"}
                                    onClick={this.previous}>Precedent
                            </button>
                            <button className="btn btn-primary" type={"button"} onClick={this.continue}>Suivant
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
