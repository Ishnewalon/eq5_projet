import {InputGroup} from "../InputGroup/InputGroup";

export default function FieldPassword({label, placeholder, password, handleChange}) {

    return (<>
        <label className="label">{label}</label>
        <InputGroup>
            <input name="password" placeholder={placeholder} type="password" value={password} onChange={handleChange}/>
        </InputGroup>
    </>);
}
