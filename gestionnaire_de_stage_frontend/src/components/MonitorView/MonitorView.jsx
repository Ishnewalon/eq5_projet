import React from "react";
import AddOffer from "./AddOffer/AddOffer";
import ViewAppliedStudents from "./ViewAppliedStudents/ViewAppliedStudents";
import {Route, useRouteMatch} from "react-router-dom";
import ContractsToBeSigned from "../Contract/ContractsToBeSigned";
import {UserType} from "../../enums/UserTypes";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {useAuth} from "../../services/use-auth";
import ViewSignedContracts from "../Contract/ViewSignedContracts/ViewSignedContracts";
import {Title} from "../SharedComponents/Title/Title";

export default function MonitorView() {
    let {path} = useRouteMatch();
    let auth = useAuth();
    return <>
        <Route exact path={`${path}/offres/ajouter`}>
            <Title>Ajouter une offre</Title>
            <ContainerBox>
                <AddOffer/>
            </ContainerBox>
        </Route>
        <Route exact path={`${path}/monitor/contracts/signed`}>
            <Title>Contrats signés</Title>
            <ContainerBox>
                <ViewSignedContracts userType={UserType.MONITOR[0]}/>
            </ContainerBox>
        </Route>
        <Route exact path={`${path}/applications`}>
            <Title>Appliquants</Title>
            <ViewAppliedStudents/>
        </Route>
        <Route exact path={`${path}/voir/futures_stagiaires`}>
            <Title>Contrats de futures stagiaires à valider</Title>
            <ContainerBox>
                <ContractsToBeSigned userType={UserType.MONITOR[0]}/>
            </ContainerBox>
        </Route>
        <Route exact path={`${path}`}>
            <h1 className="text-center">Bonjour {auth.user.firstName}!</h1>
        </Route>
    </>;
}
