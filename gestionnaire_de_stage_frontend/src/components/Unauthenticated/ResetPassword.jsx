import {useHistory, useRouteMatch} from "react-router-dom";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {resetPassword} from "../../services/user-service";
import StepPassword from "./Register/SharedSteps/StepPassword";
import {useForm} from "react-hook-form";

export default function ResetPassword() {
    const {register, handleSubmit, watch, formState: {errors}} = useForm({
        mode: "onSubmit",
    });
    let history = useHistory();
    let match = useRouteMatch();

    const submit = (data, e) => {
        e.preventDefault();
        const {password} = data;
        resetPassword(match.params.token, password).then(
            bool => {
                if (bool)
                    history.push('/login');
            }
        );
    };

    return (
        <ContainerBox className="w-75">
            <form onSubmit={handleSubmit(submit)}>
                <StepPassword register={register} watch={watch} errors={errors}/>
            </form>
        </ContainerBox>
    );
}
