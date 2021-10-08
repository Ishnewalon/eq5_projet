import './Login.css'
import React, {Component} from "react";
import {UserType} from "../Register/Register";
import AuthService from "../../services/auth-service";
import {FieldPassword} from "../Fields/FieldPassword";


export default class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
            userType: UserType.MONITOR[0]
        }
        this.service = AuthService
    }

    handleChange = input => e => {
        e.preventDefault()
        this.setState({[input]: e.target.value});
    }
    connect = (e) => {
        e.preventDefault()
        this.service.signIn(this.state.userType, this.state.email, this.state.password).then(() => {
            if (this.service.isAuthenticated())
                this.props.history.push('/dashboard')
        })
    }

    render() {
        return (<>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <h2 className="text-center">Se connecter</h2>
                    <div className="form-group">
                        <label>Type d'utilisateur</label>
                        <div className="input-group">
                            <select className="form-control" name="choice" id="userTypes"
                                    onChange={this.handleChange('userType')}>
                                <option defaultChecked={true} value={UserType.MONITOR[0]}>{UserType.MONITOR[1]}</option>
                                <option value={UserType.SUPERVISOR[0]}>{UserType.SUPERVISOR[1]}</option>
                                <option value={UserType.STUDENT[0]}>{UserType.STUDENT[1]}</option>
                                <option value={UserType.MANAGER[0]}>{UserType.MANAGER[1]}</option>
                            </select>
                        </div>
                    </div>
                    <div className="form-group row">
                        <div className="col-md-6">
                            <label>Email</label>
                            <div className="input-group">
                                <input name="email" placeholder="Email" className="form-control" type="text"
                                       value={this.state.email} onChange={this.handleChange('email')}/>
                            </div>
                        </div>
                        <div className="col-md-6">
                            <FieldPassword password={this.state.password} label="Mot de passe"
                                           placeholder="Votre mot de passe"
                                           handleChange={this.handleChange('password')}/>
                        </div>
                    </div>
                    <div className="form-group text-center">
                        <label/>
                        <button className="btn btn-primary" type={"button"} onClick={this.connect}>Connexion</button>
                    </div>
                </div>
            </>
        )
    }
}

