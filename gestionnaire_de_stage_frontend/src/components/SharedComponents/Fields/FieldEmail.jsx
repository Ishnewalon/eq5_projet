import {InputGroup} from "../InputGroup/InputGroup";

export default function FieldEmail({email,label,placeholder,handleChanges}) {
  return (
    <>
      <label className="label">{label}</label>
      <InputGroup>
        <input placeholder={placeholder} type="email" name="email" value={email} onChange={handleChanges}/>
      </InputGroup>
    </>
  );
}
