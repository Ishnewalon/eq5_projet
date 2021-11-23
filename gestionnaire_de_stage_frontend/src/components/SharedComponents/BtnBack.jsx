import {useHistory} from "react-router-dom";
import React from "react";

export function BtnBack() {
    let history = useHistory();
    if (history.length === 0) return null;
    return <button className="btn btn-primary"
                   onClick={() => history.length > 0 ? history.goBack() : null}>Retour</button>
}
