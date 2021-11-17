import './Login.css'
import React, {useState} from "react";
import FieldPassword from "../SharedComponents/Fields/FieldPassword";
import FieldEmail from "../SharedComponents/Fields/FieldEmail";
import {useHistory} from "react-router-dom";
import {UserType} from "../../enums/UserTypes";
import {useAuth} from "../../services/use-auth";
import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";


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

    return (
        <ContainerBox >
            <FormGroup>
                <ul className="nav nav-pills nav-fill w-50 mx-auto">
                    <li className="nav-item">
                        <input type="radio" className="btn-check" name="options-outlined" id="monitor"
                               autoComplete="off" onClick={() => setUserType(UserType.MONITOR[0])} defaultChecked/>
                        <label className="btn btn-outline-light" htmlFor="monitor">{UserType.MONITOR[1]}</label>
                    </li>
                    <li className="nav-item">
                        <input type="radio" className="btn-check" name="options-outlined" id="manager"
                               autoComplete="off" onClick={() => setUserType(UserType.MANAGER[0])}/>
                        <label className="btn btn-outline-light" htmlFor="manager">{UserType.MANAGER[1]}</label>
                    </li>
                    <li className="nav-item">
                        <input type="radio" className="btn-check" name="options-outlined" id="student"
                               autoComplete="off" onClick={() => setUserType(UserType.STUDENT[0])}/>
                        <label className="btn btn-outline-light" htmlFor="student">{UserType.STUDENT[1]}</label>
                    </li>
                    <li className="nav-item">
                        <input type="radio" className="btn-check" name="options-outlined" id="supervisor"
                               autoComplete="off" onClick={() => setUserType(UserType.SUPERVISOR[0])}/>
                        <label className="btn btn-outline-light" htmlFor="supervisor">{UserType.SUPERVISOR[1]}</label>
                    </li>
                </ul>
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
        </ContainerBox>
    )
}



