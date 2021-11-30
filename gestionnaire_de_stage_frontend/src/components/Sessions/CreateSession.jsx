import {createSession} from "../../../../vue_frontend/src/services/session-service";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {FormSelect} from "../SharedComponents/FormInput/FormSelect";
import {useForm} from "react-hook-form";
import {Column, FormGroupV2} from "../SharedComponents/FormGroup/FormGroupV2";

export default function CreateSession() {
    const {register, handleSubmit, formState: {errors}} = useForm({mode: "onSubmit",});
    let currentYear = new Date().getFullYear();
    let years = [];

    for (let i = 0; i < 3; i++)
        years.push(currentYear + i);

    const submit = (data) => createSession(data).then();

    return <ContainerBox className={"w-50"}>
        <form onSubmit={handleSubmit(submit)}>
            <FormGroupV2>
                <Column col={{md: 6}}>
                    <FormSelect label="Année"
                                name={"year"}
                                options={years}
                                self
                                register={register}
                                error={errors.year}
                                defaultMessage="Choisissez une année"
                                validation={{}}/>
                </Column>
                <Column col={{md: 6}}>
                    <FormSelect label="Type de session"
                                name={"typeSession"}
                                options={[
                                    {value: "HIVER", label: "Session d'hiver"},
                                    {value: "ETE", label: "Session d'été"}]}
                                fieldValue={"value"}
                                displayed={["label"]}
                                register={register}
                                error={errors.typeSession}
                                defaultMessage="Choisissez une année"
                                validation={{}}/>
                </Column>
            </FormGroupV2>
            <div className="text-center">
                <input className="btn btn-primary mt-3" type="submit" value="Créez la session"/>
            </div>
        </form>
    </ContainerBox>
}
