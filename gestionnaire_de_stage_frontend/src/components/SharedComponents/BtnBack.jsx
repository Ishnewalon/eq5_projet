import {useHistory} from "react-router-dom";
import React from "react";

export function BtnBack() {
    let locationStateHistory = useHistory();
    return <button className="btn btn-primary" onClick={() => locationStateHistory.goBack()}>Retour</button>
}
