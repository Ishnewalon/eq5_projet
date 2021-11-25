import {useHistory} from "react-router-dom";
import React from "react";

export function BtnBack({message}) {
    let locationStateHistory = useHistory();
    return <button className="btn btn-primary" onClick={() => locationStateHistory.goBack()}>{message ? message : 'Retour'}</button>
}
