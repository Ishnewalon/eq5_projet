import {FormField} from "../FormField/FormField";
import PropTypes from "prop-types";

export function FormInput(props) {
    const {register, error, label, name, placeholder, type, errorMessage, validation} = props;
    return <FormField htmlFor={name}>
        <label>{label}</label>
        <input name={name} placeholder={placeholder} className={error ? "border-danger" : ""}
               type={type} {...register(name, validation)}/>
        {error && <span>{errorMessage}</span>}
    </FormField>;
}

FormInput.propTypes = {
    register: PropTypes.func.isRequired,
    error: PropTypes.object,
    name: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    placeholder: PropTypes.string,
    type: PropTypes.oneOf(['text', 'number', 'tel', 'email', 'password']).isRequired,
    errorMessage: PropTypes.string,
    validation: PropTypes.object.isRequired
};
FormInput.defaultProps = {
    placeholder: "",
    errorMessage: "Ce champ est obligatoire!"
};
