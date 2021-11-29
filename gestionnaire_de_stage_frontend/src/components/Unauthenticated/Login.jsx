import React from "react";
import {Link, useHistory, useLocation} from "react-router-dom";
import {UserType} from "../../enums/UserTypes";
import {useAuth} from "../../hooks/use-auth";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {useForm} from "react-hook-form";
import {FormInput} from "../SharedComponents/FormInput/FormInput";
import {regexEmail} from "../../utility";
import PropTypes from "prop-types";
import {Column, FormGroup} from "../SharedComponents/FormGroup/FormGroup";

export default function Login() {
    const {register, handleSubmit, formState: {errors}} = useForm({
        mode: "onSubmit",
    });
    let location = useLocation();
    const history = useHistory();
    let auth = useAuth();

    const connect = (data, e) => {
        e.preventDefault()
        const {userType, email, password} = data
        auth.signIn(userType, email, password).then(() => {
            if (auth.user)
                history.push('/')
        })
    }

    return (
        <ContainerBox className="w-50">
            <form onSubmit={handleSubmit(connect)} noValidate>
                <FormGroup className={"mb-3"}>
                    <Column>
                        <LoginRadio name="userType" register={register} list={Object.values(UserType)}
                                    validation={{}} error={errors.userType}/>
                    </Column>
                </FormGroup>
                <FormGroup>
                    <Column>
                        <FormInput label="Votre Email"
                                   name="email"
                                   type="email"
                                   placeholder="Votre Email"
                                   register={register}
                                   error={errors.email}
                                   validation={{
                                       required: "Ce champ est requis",
                                       pattern: {
                                           value: regexEmail,
                                           message: "Veuillez entrer un email valide"
                                       }
                                   }}
                                   autoComplete="email"/>
                    </Column>
                    <Column>
                        <FormInput label="Mot de passe"
                                   name="password"
                                   type="password"
                                   placeholder="Votre mot de passe"
                                   register={register}
                                   error={errors.password}
                                   validation={{
                                       required: "Ce champ est requis",
                                   }}
                                   autoComplete="current-password"
                        />
                    </Column>
                </FormGroup>
                <div className="form-group text-center">
                    <label/>
                    <input className="btn btn-primary btn-login" type="submit" value="Connexion"/>
                </div>
            </form>
            <div className="form-group text-center mb-3">
                <Link className={"float-end mb-3 link"}
                      to={{pathname: "/forgot_password", state: {from: location}}}>Mot
                    de passe oublié?</Link>
            </div>
        </ContainerBox>
    )
}


function LoginRadio(props) {
    const {list, register, name} = props;

    return <ul className={`nav nav-pills nav-fill`}>
        {Object.values(list).map((item, index) => {
            return <li className={`nav-item col-sm-12 col-md-6 col-lg-4 col-xl-2 m-1`} key={index}>
                <input id={item[0]} className="btn-check" name={name} value={item[0]}
                       type="radio" {...register(name, {required: "Ce champs est obligatoire"})}
                       defaultChecked={index === 0}/>
                <label className={`btn btn-outline-primary w-100`} htmlFor={item[0]}>{item[1]}</label>
            </li>
        })}
    </ul>
}

LoginRadio.propTypes = {
    name: PropTypes.string.isRequired,
    register: PropTypes.func.isRequired,
    list: PropTypes.array.isRequired,
};
