import PropTypes from "prop-types";

FieldInput.propTypes = {
    register: PropTypes.func.isRequired,
    error: PropTypes.object,
    name: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    placeholder: PropTypes.string,
    type: PropTypes.oneOf(['text', 'number', 'tel', 'email', 'password', 'date', 'time']).isRequired,
    validation: PropTypes.object.isRequired,
    autoComplete: PropTypes.string
};
FieldInput.defaultProps = {
    placeholder: "",
};

export function FieldInput(props) {
    const {register, error, label, name, placeholder, type, validation, autoComplete} = props;
    return <div className="form-floating mb-2">
        <input id={name} name={name} placeholder={placeholder} className={"form-control" + (error ? " is-invalid" : "")}
               type={type} {...register(name, validation)} defaultValue={(type === "number" ? 0 : "")}
               autoComplete={autoComplete}/>
        <label htmlFor={name}>{label}</label>
        {error && <span className="text-danger">{error.message}</span>}
    </div>
}

FieldRadio.propTypes = {
    name: PropTypes.string.isRequired,
    register: PropTypes.func.isRequired,
    list: PropTypes.array.isRequired,
    label: PropTypes.string.isRequired,
};

export function FieldRadio(props) {
    const {list, register, name, label, classNameLabel} = props;

    function Radio({id, value, text, defaultChecked}) {
        return <div className={'form-check ' + classNameLabel}>
            <input id={id} className="form-check-input" name={name} value={value}
                   type="radio" {...register(name, {required: "Ce champs est obligatoire"})}
                   defaultChecked={defaultChecked}/>
            <label className="form-check-label" htmlFor={id}>{text}</label>
        </div>
    }

    return <>
        <label>{label}</label>
        {
            list.map((item, index) => {
                return <Radio key={index} classNameLabel={classNameLabel} value={item[0]} name={name}
                              register={register} text={item[1]}
                              id={name + index} defaultChecked={index === 0}/>;

            })
        }
    </>
}

FieldSelect.propTypes = {
    register: PropTypes.func.isRequired,
    error: PropTypes.object,
    name: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    validation: PropTypes.object.isRequired,
    options: PropTypes.array.isRequired,
    fieldValue: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
    displayed: PropTypes.arrayOf(PropTypes.oneOfType([PropTypes.string, PropTypes.number])),
    self: PropTypes.bool,
    defaultMessage: PropTypes.string.isRequired
};

export function FieldSelect(props) {
    const {register, error, validation, options, name, label, fieldValue, displayed, defaultMessage, self} = props;
    validation.validate = value => defaultMessage !== value || "Ce champ est obligatoire!"

    return (
        <div className="form-floating  mb-2">
            <select id={name} {...register(name, validation)}
                    className={"form-select " + (error ? " is-invalid" : "")}
                    defaultValue={defaultMessage}>
                <option disabled value={defaultMessage}>{defaultMessage}</option>
                {options.map((value, index) => (
                    <option key={index} value={self ? value : value[fieldValue]}>
                        {self ? value : displayed.map(x => (
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

FieldTextarea.propTypes = {
    label: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    placeholder: PropTypes.string,
    register: PropTypes.func.isRequired,
    error: PropTypes.object,
    validation: PropTypes.object
};
FieldTextarea.defaultProps = {
    placeholder: "",
};

export function FieldTextarea(props) {
    const {register, error, label, name, placeholder, validation} = props;
    return <div className="form-floating mb-2">
        <textarea id="name" name={name} placeholder={placeholder}
                  className={"form-control" + (error ? " is-invalid" : "")}{...register(name, validation)}/>
        <label htmlFor={name}>{label}</label>
        {error && <span className="text-danger">{error.message}</span>}
    </div>
}
