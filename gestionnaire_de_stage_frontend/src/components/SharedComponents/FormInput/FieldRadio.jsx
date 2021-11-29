import PropTypes from "prop-types";

export function FieldRadio(props) {
    const {list, register, name, label} = props;


    Radio.propTypes = {
        name: PropTypes.string.isRequired,
        register: PropTypes.func.isRequired,
        id: PropTypes.string.isRequired,
        value: PropTypes.string.isRequired,
        text: PropTypes.string.isRequired,
    };

    function Radio(props) {
        const {name, register, id, value, text, defaultChecked} = props;
        return <div className="form-check">
            <input id={id} className="form-check-input" name={name} value={value}
                   type="radio" {...register(name, {required: "Ce champs est obligatoire"})}
                   defaultChecked={defaultChecked}/>
            <label className="form-check-label" htmlFor={id}>{text}</label>
        </div>
    }

    return <>
        <label>{label}</label>
        {
            Object.values(list).map((item, index) => {
                return <Radio key={index} value={item[0]} name={name} register={register} text={item[1]}
                              id={name + index} defaultChecked={index === 0}/>;

            })
        }
    </>
}

FieldRadio.propTypes = {
    name: PropTypes.string.isRequired,
    register: PropTypes.func.isRequired,
    list: PropTypes.object.isRequired,
    label: PropTypes.string.isRequired,
};
