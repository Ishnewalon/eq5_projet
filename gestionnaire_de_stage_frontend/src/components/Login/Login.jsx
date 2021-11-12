import './Login.css'
import React, {useState} from "react";
import FieldPassword from "../SharedComponents/Fields/FieldPassword";
import FieldEmail from "../SharedComponents/Fields/FieldEmail";
import {useHistory} from "react-router-dom";
import {UserType} from "../../enums/UserTypes";
import {useAuth} from "../../services/use-auth";
import {FormField} from "../SharedComponents/FormField/FormField";
import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";


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

    let myTypes = [];
    for (let type in UserType) {
        myTypes.push(type)
    }

    return (<>
            <h2 className="text-center">Se connecter</h2>
            <FormGroup>
                <FormField>
                    <label>Type d'utilisateur</label>
                    <select name="choice" id="userTypes"
                            onChange={(e) => setUserType(e.target.value)}>
                        {myTypes.map(type => <option key={type}
                                                     value={UserType[type][0]}>{UserType[type][1]}</option>)}
                    </select>
                </FormField>
            </FormGroup>
            <FormGroup>
                <FieldEmail email={email} label="Email" placeholder="Votre Email"
                            handleChanges={(e) => setEmail(e.target.value)}/>
                <FieldPassword password={password} label="Mot de passe"
                               placeholder="Votre mot de passe"
                               handleChange={(e) => setPassword(e.target.value)}/>
            </FormGroup>
            <div className="form-group text-center">
                <label/>
                <button className="btn btn-primary btn-login" type={"button"} onClick={connect}>Connexion</button>
            </div>
        </>
    )
}



