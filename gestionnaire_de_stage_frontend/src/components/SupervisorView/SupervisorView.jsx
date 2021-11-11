import React from "react";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {Route, useRouteMatch} from "react-router-dom";
import {useAuth} from "../../services/use-auth";
import VisitForm from "./VisitForm/VisitForm";
import StudentStatusView from "./StudentStatusView/StudentStatusView";

export default function SupervisorView() {
    const {path} = useRouteMatch();
    let auth = useAuth();
    return (<ContainerBox>
        <Route exact path={`${path}/form_visit_company`}>
            <VisitForm/>
        </Route>
        <Route exact path={`${path}/students/status`}>
            <StudentStatusView/>
        </Route>
        <Route exact path={`${path}`}>
            <h1 className="text-center">Bonjour {auth.user.firstName}!</h1>
        </Route>
    </ContainerBox>);
}
