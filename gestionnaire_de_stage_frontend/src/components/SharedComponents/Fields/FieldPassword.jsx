import {FormField} from "../FormField/FormField";

export default function FieldPassword({label, placeholder, password, handleChange}) {

    return (
        <FormField myFor="password">
            <label>{label}</label>
            <input name="password" placeholder={placeholder} type="password" value={password} onChange={handleChange}/>
        </FormField>
    );
}
