import {UserType} from "../../enums/UserTypes";
import {forgotPassword} from "../../services/user-service";
import {ContainerBox} from "../SharedComponents/ContainerBox";
import Swal from "sweetalert2";
import {toast, toastErr} from "../../utility";
import {useHistory} from "react-router-dom";
import {BtnBack} from "../SharedComponents/BtnBack";
import {useForm} from "react-hook-form";
import {FormGroup} from "../SharedComponents/Form/FormGroup";
import {FieldInput, FieldSelect} from "../SharedComponents/Form/FormFields";
import {Column} from "../SharedComponents/Column";

export default function ForgotPassword() {
    const history = useHistory();
    const {register, handleSubmit, formState: {errors}} = useForm({
        mode: "onSubmit",
    });

    const submit = (data, e) => {
        const {email, userType} = data;
        e.preventDefault();
        let status
        let body
        toast.fire({
            title: 'Envoi du mail...',
            timer: 120000,
            willOpen: () => {
                Swal.showLoading()
                forgotPassword(userType, email).then(
                    response => {
                        response.json().then(b => {
                            status = response.status
                            body = b
                            Swal.close()
                        })
                    }
                );
            }
        }).then(() => {
            if (status === 200)
                toast.fire({title: body.message}).then(
                    () => history.push('/login'));
            if (status === 400)
                toastErr.fire({title: body.message}).then()
        })
    };

    return (
        <ContainerBox className="w-50">
            <form onSubmit={handleSubmit(submit)}>
                <FormGroup>
                    <Column col={{sm: 6}}>
                        <FieldSelect label="Type d'utilisateur"
                                     name="userType"
                                     defaultMessage="Choisissez un type d'utilisateur!"
                                     options={Object.values(UserType).filter(type => type !== UserType.MANAGER)}
                                     fieldValue={0}
                                     displayed={[1]}
                                     register={register}
                                     error={errors.userType}
                                     validation={{required: 'Ce champ est requis!'}}
                        />
                    </Column>
                    <Column col={{sm: 6}}>
                        <FieldInput label="Entrez votre courriel"
                                    name="email"
                                    type="email"
                                    placeholder="Email"
                                    register={register}
                                    error={errors.email}
                                    validation={{required: 'Ce champ est requis!'}}
                        />
                    </Column>
                </FormGroup>
                <div className="d-flex justify-content-center mt-3">
                    <div className="btn-group">
                        <BtnBack/>
                        <input type="submit" className="btn btn-primary" value="Envoyer"/>
                    </div>
                </div>
            </form>
        </ContainerBox>
    );
}
