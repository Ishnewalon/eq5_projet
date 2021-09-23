import './Login.css'
import {Component} from "react";

class Login extends Component {

    Continue = (val) => {
        nextStep(val);
    }

render(){
    return (<div>
            <div className="form-group row">
                <div className="col-md-6">
                    <label>Matricule</label>
                    <div className="input-group">
                        <input name="matricule" placeholder="Matricule" className="form-control" type="text"
                               value={values.matricule} onChange={handleChange('first_name')}/>
                    </div>
                </div>
                <div className="col-md-6">
                    <label>Nom</label>
                    <div>
                        <div className="input-group">
                            <input name="password" placeholder="Votre mot de passe" className="form-control" type="password"
                                   value={values.password} onChange={handleChange('password')}/>
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
