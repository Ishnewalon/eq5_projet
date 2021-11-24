import {useState} from "react";
import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../SharedComponents/FormField/FormField";
import {UserType} from "../../enums/UserTypes";
import {forgotPassword} from "../../services/user-service";
import {useHistory} from "react-router-dom";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import Swal from "sweetalert2";
import {toast} from "../../utility";

export default function ForgotPassword() {
    let history = useHistory();
    const [email, setEmail] = useState('');
    const [type, setType] = useState(UserType.MONITOR[0]);


    const submit = e => {
        e.preventDefault();
        let isOpen = true
        forgotPassword(type, email).then(
            bool => {
                if (bool)
                    history.push("/login")
                isOpen = false
            }
        );
        toast.fire({
            title: 'Envoi du mail...',
            didOpen: () => Swal.showLoading(),
            willClose() {
                if (isOpen)
                    Swal.close()
            }
        }).then()
    };

    return (
        <ContainerBox className="w-50">
            <form onSubmit={submit}>
                <FormGroup repartition={[12, 12]}>
                    <FormField htmlFor="userType">
                        <label>Type d'utilisateur</label>
                        <select onChange={e => setType(e.target.value)}>
                            <option value={UserType.MONITOR[0]}>{UserType.MONITOR[1]}</option>
                            <option value={UserType.SUPERVISOR[0]}>{UserType.SUPERVISOR[1]}</option>
                            <option value={UserType.STUDENT[0]}>{UserType.STUDENT[1]}</option>
                        </select>
                    </FormField>
                    <FormField htmlFor="email">
                        <label>Email</label>
                        <input type="email" aria-describedby="emailHelp"
                               placeholder="Entrez votre courriel" value={email}
                               onChange={e => setEmail(e.target.value)}/>
                    </FormField>
                </FormGroup>
                <div className="d-flex justify-content-center mt-3">
                    <button type="submit" className="btn btn-primary">Envoyer</button>
                </div>
            </form>
        </ContainerBox>
    );
}
