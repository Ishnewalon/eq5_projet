import React from "react";
import {Link, useLocation, useRouteMatch} from "react-router-dom";


export default function Register() {
    let {path} = useRouteMatch();
    let location = useLocation();

    return (<>
        <div className="text-center">
            <div className="">
                <Link className="btn btn-primary" to={{pathname: `${path}/cegep`, state: {from: location}}}
                      from={"Register"}>Membre du cégep</Link>
                <Link className="btn btn-primary"
                      to={{pathname: `${path}/monitor`, state: {from: location}}}>Compagnie</Link>
            </div>
        </div>
    </>);
}
