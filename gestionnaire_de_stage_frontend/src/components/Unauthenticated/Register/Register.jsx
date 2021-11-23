import React from "react";
import {ContainerBox} from "../../SharedComponents/ContainerBox/ContainerBox";
import {Link} from "react-router-dom";


export default function Register() {


    return (<ContainerBox>
        <div className="form-group text-center">
            <div>
                <Link className="btn btn-primary">Membre du cégep</Link>
                <Link className="btn btn-primary">Compagnie</Link>
            </div>
        </div>
    </ContainerBox>);
}
