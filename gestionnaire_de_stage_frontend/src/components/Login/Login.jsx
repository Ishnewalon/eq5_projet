import './Login.css'
import React, {useState} from "react";
import FieldPassword from "../Fields/FieldPassword";
import FieldEmail from "../Fields/FieldEmail";
import {useHistory} from "react-router-dom";
import {UserType} from "../../enums/UserTypes";
import {useAuth} from "../../services/use-auth";


export default function Login() {
    const history = useHistory();
    let auth = useAuth();
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [userType, setUserType] = useState(UserType.MONITOR[0])

    const connect = (e) => {
        e.preventDefault()
        auth.signIn(userType, email, password).then(() => {
            if (auth.user)
                history.push('/')
        })
    }


    return (<>
            <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                <h2 className="text-center">Se connecter</h2>
                <div className="form-group">
                    <label>Type d'utilisateur</label>
                    <div className="input-group">
                        <select className="form-control" name="choice" id="userTypes"
                                onChange={(e) => setUserType(e.target.value)}>
                            <option defaultChecked={true} value={UserType.MONITOR[0]}>{UserType.MONITOR[1]}</option>
                            <option value={UserType.SUPERVISOR[0]}>{UserType.SUPERVISOR[1]}</option>
                            <option value={UserType.STUDENT[0]}>{UserType.STUDENT[1]}</option>
                            <option value={UserType.MANAGER[0]}>{UserType.MANAGER[1]}</option>
                        </select>
                    </div>
                </div>
                <div className="form-group row">
                    <div className="col-md-6">
                        <FieldEmail email={email} label="Email" placeholder="Votre Email"
                                    handleChanges={(e) => setEmail(e.target.value)}/>
                    </div>
                    <div className="col-md-6">
                        <FieldPassword password={password} label="Mot de passe"
                                       placeholder="Votre mot de passe"
                                       handleChange={(e) => setPassword(e.target.value)}/>
                    </div>
                </div>
                <div className="form-group text-center">
                    <label/>
                    <button className="btn btn-primary btn-login" type={"button"} onClick={connect}>Connexion</button>
                </div>
            </div>
        </>
    )
}

