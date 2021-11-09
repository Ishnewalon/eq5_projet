import {InputGroup} from "../InputGroup/InputGroup";

export default function FieldAddress({label, address, handleChange}) {

    return (<>
        <label className="label">{label}</label>
        <InputGroup>
            <input name="address" placeholder="Rue, boulevard, avenue.." type="text" value={address}
                   onChange={handleChange}/>
        </InputGroup>
    </>);

}
