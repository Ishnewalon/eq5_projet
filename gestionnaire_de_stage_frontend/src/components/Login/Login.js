import './Login.css'
import {Component} from "react";

class Login extends Component {


render(){
    return (<div>
            <div className="form-group row">
                <div className="col-md-6">
                    <label>Matricule</label>
                    <div className="input-group">
                        <input name="matricule" placeholder="Matricule" className="form-control" type="text"
                               value={this.props.values.matricule} onChange={this.props.handleChange('matricule')}/>
                    </div>
                </div>
                <div className="col-md-6">
                    <label>Nom</label>
                    <div>
                        <div className="input-group">
                            <input name="password" placeholder="Votre mot de passe" className="form-control" type="password"
                                   value={this.props.values.password} onChange={this.props.handleChange('password')}/>
                        </div>
                    </div>
                </div>
            </div>
            <div className="form-group text-center">
                <label/>
                    <button className="btn btn-primary" type={"button"} >Connexion</button>
                </div>
            </div>
        )
    }
}
export default Login;
