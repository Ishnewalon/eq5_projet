import React from "react";
import {Route, useRouteMatch} from "react-router-dom";
import {useAuth} from "../../hooks/use-auth";
import SupervisorVisitForm from "../Evaluation/SupervisorEvaluation/SupervisorVisitForm";
import StudentStatusView from "../Admin/StudentManagement/StudentStatusView";
import {Title} from "../SharedComponents/Title";
import {ContainerBox} from "../SharedComponents/ContainerBox";
import Profile from "../SharedComponents/Profile/Profile";
import ShowAllEvaluations from "../Evaluation/ShowAllEvaluations";

export default function SupervisorView() {
    const {path} = useRouteMatch();
    let auth = useAuth();
    return (<>
        <Route exact path={`${path}/forms/supervisor/view`}>
            <ContainerBox>
                <Title>Mes formulaires de visites</Title>
                <ShowAllEvaluations userType='supervisor'/>
            </ContainerBox>
        </Route>
        <Route exact path={`${path}/forms/supervisor/create`}>
            <ContainerBox>
                <Title>Créer un formulaire de visite</Title>
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
