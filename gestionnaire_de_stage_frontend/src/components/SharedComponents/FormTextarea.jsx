import PropTypes from "prop-types";
import {FormField} from "./FormField/FormField";

export function FormTextarea(props) {
    const {register, error, label, name, placeholder, validation} = props;
    return <FormField htmlFor={name}>
        <label>{label}</label>
        <textarea name={name} placeholder={placeholder}
                  className={error ? "border-danger" : ""}{...register(name, validation)}/>
        {error && <span>{error.message}</span>}
    </FormField>;
}

FormTextarea.propTypes = {
    label: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    placeholder: PropTypes.string,
    register: PropTypes.func.isRequired,
    error: PropTypes.object,
    validation: PropTypes.object
};
FormTextarea.defaultProps = {
    placeholder: "",
};
