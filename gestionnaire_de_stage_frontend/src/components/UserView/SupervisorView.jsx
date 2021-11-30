import React from "react";
import {Route, useRouteMatch} from "react-router-dom";
import {useAuth} from "../../hooks/use-auth";
import SupervisorVisitForm from "../Evaluation/SupervisorVisitForm";
import StudentStatusView from "../Admin/StudentManagement/StudentStatusView";
import {Title} from "../SharedComponents/Title/Title";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import Profile from "../SharedComponents/Profile/Profile";

export default function SupervisorView() {
    const {path} = useRouteMatch();
    let auth = useAuth();
    return (<>
        <Route exact path={`${path}/supervisor_form_visit_company`}>
            <ContainerBox>
                <SupervisorVisitForm/>
            </ContainerBox>
        </Route>
        <Route exact path={`${path}/students/status`}>
            <Title>Statut des étudiants qui vous sont attribués</Title>
            <StudentStatusView/>
        </Route>
        <Route exact path={`${path}`}>
            <h1 className="text-center">Bonjour {auth.user.firstName}!</h1>
            <Profile/>
        </Route>
    </>);
}
