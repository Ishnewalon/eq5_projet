import {createSession} from "../../services/session-service";
import {ContainerBox} from "../SharedComponents/ContainerBox";
import {useForm} from "react-hook-form";
import {FormGroup} from "../SharedComponents/Form/FormGroup";
import {FieldSelect} from "../SharedComponents/Form/FormFields";
import {Column} from "../SharedComponents/Column";

export default function CreateSession() {
    const {register, handleSubmit, formState: {errors}} = useForm({mode: "onSubmit",});
    let currentYear = new Date().getFullYear();
    let years = [];

    for (let i = 0; i < 3; i++)
        years.push(currentYear + i);

    const submit = (data) => createSession(data).then();

    return <ContainerBox className={"w-50"}>
        <form onSubmit={handleSubmit(submit)}>
            <FormGroup>
                <Column col={{md: 6}}>
                    <FieldSelect label="Année"
                                 name={"year"}
                                 options={years}
                                 self
                                 register={register}
                                 error={errors.year}
                                 defaultMessage="Choisissez une année"
                                 validation={{}}/>
                </Column>
                <Column col={{md: 6}}>
                    <FieldSelect label="Type de session"
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
            </FormGroup>
            <div className="text-center">
                <input className="btn btn-primary mt-3" type="submit" value="Créez la session"/>
            </div>
        </form>
    </ContainerBox>
}
