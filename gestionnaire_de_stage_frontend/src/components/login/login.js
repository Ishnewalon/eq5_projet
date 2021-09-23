import './login.css'
import {Component} from "react";

export default class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            matricule: '',
            password: ''
        }
    }

    handleChange = input => e => {
        this.setState({[input]: e.target.value});
    }


    render() {
        return (<div>
                <div className="form-group row">
                    <div className="col-md-6">
                        <label>Matricule</label>
                        <div className="input-group">
                            <input name="matricule" placeholder="Matricule" className="form-control" type="text"
                                   value={this.state.matricule} onChange={() => this.handleChange('matricule')}/>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <label>Nom</label>
                        <div>
                            <div className="input-group">
                                <input name="password" placeholder="Votre mot de passe" className="form-control"
                                       type="password"
                                       value={this.state.password} onChange={() => this.handleChange('password')}/>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="form-group text-center">
                    <label/>
                    <button className="btn btn-primary" type={"button"}>Connexion</button>
                </div>
            </div>
        )
    }
}

