import React from "react";
import TeleverserCv from "./TeleverserCv/TeleverserCv";
import ViewOffersAndApply from "./ViewOffersAndApply/ViewOffersAndApply";
import {Route, useRouteMatch} from "react-router-dom";
import OfferApplicationListView from "./OfferApplicationListView/OfferApplicationListView";
import {ContractView} from "../Contract/ContractView";
import OfferApplicationSetFinalStatus from "./OfferApplicationSetFinalStatus/OfferApplicationSetFinalStatus";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {useAuth} from "../../services/use-auth";
import StudentContractView from "./StudentContractView/StudentContractView";
import {Title} from "../SharedComponents/Title/Title";

export default function StudentView() {
    let {path} = useRouteMatch();
    let auth = useAuth();
    return (<>
            <Route exact path={`${path}/televerser`}>
                <Title>Téléverser mon CV</Title>
                <ContainerBox>
                    <TeleverserCv/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}/offres`}>
                <Title>Offres de stage</Title>
                <ContainerBox>
                    <ViewOffersAndApply/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}/view/status`}>
                <Title>Les status de mes applications</Title>
                <OfferApplicationSetFinalStatus/>
            </Route>
            <Route exact path={`${path}/offer/setdate`}>
                <Title>Ajouter vos dates d'entrevue</Title>
                <OfferApplicationListView/>
            </Route>
            <Route exact path={`${path}/voir_mon_contrat`}>
                <Title>Contrat à signer</Title>
                <ContainerBox>
                    <ContractView/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}/student/contract/signed`}>
                <Title>Mes contrats</Title>
                <ContainerBox>
                    <StudentContractView/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}`}>
                <Title>Bonjour {auth.user.firstName}!</Title>
            </Route>
        </>
    )
}
