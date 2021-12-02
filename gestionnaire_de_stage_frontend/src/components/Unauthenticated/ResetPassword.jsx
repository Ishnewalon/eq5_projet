import {useHistory, useRouteMatch} from "react-router-dom";
import {ContainerBox} from "../SharedComponents/ContainerBox";
import {resetPassword} from "../../services/user-service";
import StepPassword from "./Register/SharedSteps/StepPassword";
import {useForm} from "react-hook-form";

export default function ResetPassword() {
    const history = useHistory();
    const match = useRouteMatch();
    const {register, handleSubmit, watch, formState: {errors}} = useForm({
        mode: "onSubmit",
    });

    const submit = (data, e) => {
        e.preventDefault();
        const {password} = data;
        resetPassword(match.params.token, password).then(
            bool => bool && history.push('/login'));
    };

    return (
        <ContainerBox className="w-75">
            <form onSubmit={handleSubmit(submit)}>
                <StepPassword register={register} watch={watch} errors={errors}/>
            </form>
        </ContainerBox>
    );
}
