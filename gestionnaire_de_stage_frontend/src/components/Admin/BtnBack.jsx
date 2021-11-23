import {Link} from "react-router-dom";
import React from "react";

export function BtnBack() {
    return <Link to="/dashboard/rapports" className="btn btn-primary">Retour</Link>;
}
