import {FormField} from "../FormField/FormField";

export default function FieldEmail({email, label, placeholder, handleChanges}) {
    return (
        <FormField>
            <label>{label}</label>
            <input placeholder={placeholder} type="email" name="email" value={email} onChange={handleChanges}/>
        </FormField>
    );
}
