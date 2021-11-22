import {useState} from "react";
import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../SharedComponents/FormField/FormField";
import {useHistory, useRouteMatch} from "react-router-dom";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {resetPassword} from "../../services/user-service";

export default function ResetPassword() {
    let history = useHistory();
    let match = useRouteMatch();

    const [password, setPassword] = useState('');


    const submit = e => {
        e.preventDefault();
        resetPassword(match.params.token, password).then(
            bool => {
                if (bool)
                    history.push('/login');
            }
        );
    };

    return (
        <ContainerBox className="w-50">
            <form onSubmit={submit}>
                <FormGroup repartition={[12, 12]}>
                    <FormField htmlFor="password">
                        <label>Nouveau mot de passe</label>
                        <input type="text"
                               placeholder="Entrez votre nouveau mot de passe" value={password}
                               onChange={e => setPassword(e.target.value)}/>
                    </FormField>
                </FormGroup>
                <div className="d-flex justify-content-center mt-3">
                    <button type="submit" className="btn btn-primary">Changer</button>
                </div>
            </form>
        </ContainerBox>
    );
}
