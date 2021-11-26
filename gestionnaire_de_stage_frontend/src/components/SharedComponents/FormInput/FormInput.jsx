import {FormField} from "../FormField/FormField";
import PropTypes from "prop-types";

export function FormInput(props) {
    const {register, error, label, name, placeholder, type, validation} = props;
    return <FormField htmlFor={name}>
        <label>{label}</label>
        <input name={name} placeholder={placeholder} className={error ? "border-danger" : ""}
               type={type} {...register(name, validation)} defaultValue={(type === "number" ? 0 : "")}/>
        {error && <span>{error.message}</span>}
    </FormField>;
}

FormInput.propTypes = {
    register: PropTypes.func.isRequired,
    error: PropTypes.object,
    name: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    placeholder: PropTypes.string,
    type: PropTypes.oneOf(['text', 'number', 'tel', 'email', 'password', 'date']).isRequired,
    errorMessage: PropTypes.string,
    validation: PropTypes.object.isRequired
};
FormInput.defaultProps = {
    placeholder: "",
};
