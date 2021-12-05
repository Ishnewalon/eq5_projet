import React from "react";
import CreateOffer from "../Offers/CreateOffer";
import OfferApplicationsMonitorListOfApplications
    from "../OfferApplications/OfferApplicationsMonitorListOfApplications";
import {Route, useRouteMatch} from "react-router-dom";
import ContractsToSign from "../Contract/ContractsToSign";
import {UserType} from "../../enums/UserTypes";
import {useAuth} from "../../hooks/use-auth";
import EvaluationIntern from "../Evaluation/MonitorEvaluation/EvaluationIntern";
import ContractsSigned from "../Contract/ContractsSigned";
import {Title} from "../SharedComponents/Title";
import Profile from "../SharedComponents/Profile/Profile";
import ShowAllEvaluations from "../Evaluation/ShowAllEvaluations";

export default function MonitorView() {
    let {path} = useRouteMatch();
    let auth = useAuth();
    return <>
        <Route exact path={`${path}/offres/add`}>
            <Title>Ajouter une offre</Title>
            <CreateOffer/>
        </Route>
        <Route exact path={`${path}/monitor/contracts/signed`}>
            <Title>Contrats signés</Title>
            <ContractsSigned userType={UserType.MONITOR[0]}/>
        </Route>
        <Route exact path={`${path}/applications`}>
            <Title>Appliquants</Title>
            <OfferApplicationsMonitorListOfApplications/>
        </Route>
        <Route exact path={`${path}/voir/futures_stagiaires`}>
            <Title>Contrats de futures stagiaires à valider</Title>
            <ContractsToSign userType={UserType.MONITOR[0]}/>
        </Route>
        <Route exact path={`${path}/monitor/evals/create`}>
            <Title>Évaluer un stagiaire</Title>
            <EvaluationIntern/>
        </Route>
        <Route exact path={`${path}/monitor/evals/view`}>
            <Title>Évaluations de stagiaires</Title>
            <ShowAllEvaluations userType={UserType.MONITOR[0]}/>
        </Route>
        <Route exact path={`${path}`}>
            <Title>Bonjour {auth.user.firstName}!</Title>
            <Profile/>
        </Route>
    </>;
}
