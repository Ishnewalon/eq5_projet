import PropTypes from "prop-types";

export function FormTextarea(props) {
    const {register, error, label, name, placeholder, validation} = props;
    return <div className="form-floating mb-2">
        <textarea id="name" name={name} placeholder={placeholder}
                  className={"form-control" + (error ? " is-invalid" : "")}{...register(name, validation)}/>
        <label htmlFor={name}>{label}</label>
        {error && <span className="text-danger">{error.message}</span>}
    </div>
}

FormTextarea.propTypes = {
    label: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    placeholder: PropTypes.string,
    register: PropTypes.func.isRequired,
    error: PropTypes.object,
    validation: PropTypes.object.isRequired
};
FormTextarea.defaultProps = {
    placeholder: "",
};
