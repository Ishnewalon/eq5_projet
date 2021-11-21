import React from "react";
import CreateOffer from "../Offers/CreateOffer";
import OfferApplicationsMonitorListOfApplications
    from "../OfferApplications/OfferApplicationsMonitorListOfApplications";
import {Route, useRouteMatch} from "react-router-dom";
import ContractsToSign from "../Contract/ContractsToSign";
import {UserType} from "../../enums/UserTypes";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {useAuth} from "../../services/use-auth";
import EvaluationIntern from "../Evaluation/EvaluationIntern";
import ContractsSigned from "../Contract/ContractsSigned";
import {Title} from "../SharedComponents/Title/Title";

export default function MonitorView() {
    let {path} = useRouteMatch();
    let auth = useAuth();
    return <>
        <Route exact path={`${path}/offres/ajouter`}>
            <Title>Ajouter une offre</Title>
            <ContainerBox>
                <CreateOffer/>
            </ContainerBox>
        </Route>
        <Route exact path={`${path}/monitor/contracts/signed`}>
            <Title>Contrats signés</Title>
            <ContainerBox>
                <ContractsSigned userType={UserType.MONITOR[0]}/>
            </ContainerBox>
        </Route>
        <Route exact path={`${path}/applications`}>
            <Title>Appliquants</Title>
            <OfferApplicationsMonitorListOfApplications/>
        </Route>
        <Route exact path={`${path}/voir/futures_stagiaires`}>
            <Title>Contrats de futures stagiaires à valider</Title>
            <ContainerBox>
                <ContractsToSign userType={UserType.MONITOR[0]}/>
            </ContainerBox>
        </Route>
        <Route exact path={`${path}/monitor_eval_stagiaire`}>
            <ContainerBox>
                <EvaluationIntern/>
            </ContainerBox>
        </Route>
        <Route exact path={`${path}`}>
            <h1 className="text-center">Bonjour {auth.user.firstName}!</h1>
        </Route>
    </>;
}
