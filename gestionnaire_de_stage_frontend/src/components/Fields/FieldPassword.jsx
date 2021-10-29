import React from "react";

export default function FieldPassword({label, placeholder, password, handleChange}) {

    return (<>
        <label>{label}</label>
        <div>
            <div className="input-group">
                <input name="password" placeholder={placeholder} className="form-control"
                       type="password"
                       value={password} onChange={handleChange}/>
            </div>
        </div>
    </>);
}
