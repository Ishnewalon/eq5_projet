import React from "react";

export default function FieldAddress({label, address, handleChange}) {

    return (<>
        <label>{label}</label>
        <div className="input-group">
            <input name="address" placeholder="Rue, boulevard, avenue.." className="form-control"
                   type="text"
                   value={address} onChange={handleChange}/>
        </div>
    </>);

}
