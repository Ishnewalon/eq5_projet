import React, {useState} from "react";
import FieldEmail from "../SharedComponents/Fields/FieldEmail";
import {Link, useHistory} from "react-router-dom";
import {UserType} from "../../enums/UserTypes";
import {useAuth} from "../../services/use-auth";
import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {FormField} from "../SharedComponents/FormField/FormField";


export default function Login() {
    const history = useHistory();
    let auth = useAuth();
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [userType, setUserType] = useState(UserType.MONITOR[0])

    const connect = e => {
        e.preventDefault()
        auth.signIn(userType, email, password).then(() => {
            if (auth.user)
                history.push('/')
        })
    }

    return (
        <ContainerBox className="w-50">
            <FormGroup>
                <ul className="nav nav-pills nav-fill mx-md-5 mx-auto">
                    <li className="nav-item">
                        <input type="radio" className="btn-check" name="options-outlined" id="monitor"
                               autoComplete="off" onClick={() => setUserType(UserType.MONITOR[0])} defaultChecked/>
                        <label className="btn btn-outline-primary" htmlFor="monitor">{UserType.MONITOR[1]}</label>
                    </li>
                    <li className="nav-item">
                        <input type="radio" className="btn-check" name="options-outlined" id="manager"
                               autoComplete="off" onClick={() => setUserType(UserType.MANAGER[0])}/>
                        <label className="btn btn-outline-primary" htmlFor="manager">{UserType.MANAGER[1]}</label>
                    </li>
                    <li className="nav-item">
                        <input type="radio" className="btn-check" name="options-outlined" id="student"
                               autoComplete="off" onClick={() => setUserType(UserType.STUDENT[0])}/>
                        <label className="btn btn-outline-primary" htmlFor="student">{UserType.STUDENT[1]}</label>
                    </li>
                    <li className="nav-item">
                        <input type="radio" className="btn-check" name="options-outlined" id="supervisor"
                               autoComplete="off" onClick={() => setUserType(UserType.SUPERVISOR[0])}/>
                        <label className="btn btn-outline-primary" htmlFor="supervisor">{UserType.SUPERVISOR[1]}</label>
                    </li>
                </ul>
            </FormGroup>
            <FormGroup repartition={[12, 12]}>
                <FieldEmail email={email} label="Email" placeholder="Votre Email"
                            handleChanges={(e) => setEmail(e.target.value)}/>
                <FormField htmlFor="password">
                    <label>Mot de passe</label>
                    <input name="password" placeholder="Votre mot de passe" type="password" value={password}
                           onChange={e => setPassword(e.target.value)}/>
                </FormField>
            </FormGroup>
            <div className="form-group text-center">
                <label/>
                <button className="btn btn-primary btn-login" type={"button"} onClick={connect}>Connexion</button>
            </div>
            <div className="form-group text-center mb-3">
                <Link className={"float-end mb-3 link"} to="/forgot_password">Mot de passe oublié?</Link>
            </div>
        </ContainerBox>
    )
}



