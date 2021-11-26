import PropTypes from "prop-types";

export function FormSelect({register, error, validation, options, name, label, fieldValue, displayed, defaultMessage}) {
    validation.validate = value => defaultMessage !== value || "Ce champ est obligatoire!"
    return (
        <div className="form-floating">
            <select id={name} {...register(name, validation)}
                    className={"form-control" + (error ? " is-invalid" : "")}
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
            <label htmlFor={name}>{label}</label>
            {error && <span className="text-danger">{error.message}</span>}
        </div>
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

