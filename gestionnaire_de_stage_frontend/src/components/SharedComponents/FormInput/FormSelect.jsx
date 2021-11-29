import PropTypes from "prop-types";
import {FormField} from "../FormField/FormField";

export function FormSelect({register, error, validation, options, name, label, fieldValue, displayed, defaultMessage}) {
    validation.validate = value => defaultMessage !== value || "Ce champ est obligatoire!"
    return (
        <FormField htmlFor={name}>
            <label>{label}</label>
            <select {...register(name, validation)} className={error ? "border-danger" : ""}
                    defaultValue={defaultMessage}>
                <option disabled value={defaultMessage}>{defaultMessage}</option>
                {options.map(value => (
                    <option key={value} value={value[fieldValue]}>
                        {displayed.map(x => (
                            value[x]
                        ))}
                    </option>
                ))}
            </select>
            {error && <span>{error.message}</span>}
        </FormField>
    );
}

FormSelect.propTypes = {
    register: PropTypes.func.isRequired,
    error: PropTypes.object,
    name: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    validation: PropTypes.object.isRequired,
    options: PropTypes.array.isRequired,
    fieldValue: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    displayed: PropTypes.arrayOf(PropTypes.oneOfType([PropTypes.string, PropTypes.number])).isRequired,
    defaultMessage: PropTypes.string.isRequired
};

