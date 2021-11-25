import {FormField} from "./FormField/FormField";
import PropTypes from "prop-types";

export function Select({register, error, validation, options, name, label}) {
    return (<FormField htmlFor={name}>
            <label>{label}</label>
            <select {...register(name, validation)} className={error ? "border-danger" : ""}>
                {options.map(value => (
                    <option key={value} value={value[0]}>
                        {value[1]}
                    </option>
                ))}
            </select>
            {error && <span>{error.message}</span>}
        </FormField>
    );
}

Select.propTypes = {
    register: PropTypes.func.isRequired,
    error: PropTypes.object,
    name: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    validation: PropTypes.object.isRequired,
    options: PropTypes.array
};
