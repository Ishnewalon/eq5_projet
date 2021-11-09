import React from "react";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {Route, useRouteMatch} from "react-router-dom";
import {useAuth} from "../../services/use-auth";

export default function SupervisorView() {
    const {path} = useRouteMatch();
    let auth = useAuth();
    return (<ContainerBox>
        <h2>Vous êtes connecté!</h2>
        <Route exact path={`${path}`}>
            <h1 className="text-center">Bonjour {auth.user.firstName}!</h1>
        </Route>
    </ContainerBox>);
}
