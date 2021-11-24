import React from "react";
import {Link, useRouteMatch} from "react-router-dom";


export default function Register() {
    let {path} = useRouteMatch();

    return (<>
        <div className="text-center">
            <div className="">
                <Link className="btn btn-primary" to={`${path}/cegep`}>Membre du cégep</Link>
                <Link className="btn btn-primary" to={`${path}/monitor`}>Compagnie</Link>
            </div>
        </div>
    </>);
}
