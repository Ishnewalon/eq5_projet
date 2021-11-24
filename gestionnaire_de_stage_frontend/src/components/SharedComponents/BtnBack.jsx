import {useHistory} from "react-router-dom";
import React from "react";

export function BtnBack() {
    let history = useHistory();

    const goBack = e => {
        e.preventDefault();
        if (history.location.state)
            history.goBack()
        history.push("/login", {from: history.location})
    };
    return <button type="button" className="btn btn-primary"
                   onClick={goBack}>Retour</button>
}
