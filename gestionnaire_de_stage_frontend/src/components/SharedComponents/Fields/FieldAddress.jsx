import {FormField} from "../FormField/FormField";

export default function FieldAddress({label, address, handleChange}) {

    return (
        <FormField>
            <label>{label}</label>
            <input name="address" placeholder="Rue, boulevard, avenue.." type="text" value={address}
                   onChange={handleChange}/>
        </FormField>
    );

}
