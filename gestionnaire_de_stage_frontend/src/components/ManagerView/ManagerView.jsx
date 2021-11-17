import React from "react";
import AddOffer from "../MonitorView/AddOffer/AddOffer";
import OfferValidationView from "./OfferValidationView/OfferValidationView";
import CurriculumValidation from "./CurriculumValidation/CurriculumValidation";
import ContractsToBeSigned from "../Contract/ContractsToBeSigned";
import LinkSupervisorToStudent from "./LinkSupervisorToStudent/LinkSupervisorToStudent";
import {Route, useRouteMatch} from "react-router-dom";
import {UserType} from "../../enums/UserTypes";
import StartContract from "./StartContract/StartContract";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {useAuth} from "../../services/use-auth";
import RapportsView from "./RapportsView/RapportsView";
import {
    RapportOfferInvalid,
    RapportOfferValid,
    RapportStudentSignIn,
    RapportStudentWithInvalidCv,
    RapportStudentWithoutCv
} from "./RapportsView/Rapports";
import CreateSession from "./CreateSession/CreateSession";
import ViewSignedContracts from '../ViewSignedContracts/ViewSignedContracts';
import OfferAppRapportView from "./AllStudentStatusView/OfferAppRapportView";
import {Title} from "../SharedComponents/Title/Title";
    RapportStudentStatus,

export default function ManagerView() {
    const {path} = useRouteMatch();
    let auth = useAuth();
    return (<>
            <Route exact path={`${path}`}>
                <Title>Bonjour {auth.user.firstName}!</Title>
            </Route>
            <Route exact path={`${path}/manager/contracts/signed`}>
                <Title>Contrats signés</Title>
                <ContainerBox>
                    <ViewSignedContracts userType={UserType.MANAGER[0]}/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}/offres/ajouter`}>
                <Title>Ajouter une offre de stage</Title>
                <ContainerBox>
                    <AddOffer/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}/offres/review`}>
                <Title>Validation des offres</Title>
                <ContainerBox>
                    <OfferValidationView/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}/curriculum/review`}>
                <Title>Validation des curriculums</Title>
                <CurriculumValidation/>
            </Route>
            <Route path={`${path}/students/applied`}>
                <Title>Attribuer des superviseurs aux étudiants</Title>
                <ContainerBox>
                    <LinkSupervisorToStudent/>
                </ContainerBox>
            </Route>
            <Route path={`${path}/students/start`}>
                <Title>Liste des applications prêtes à être signer</Title>
                <ContainerBox>
                    <StartContract/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}/contrats/a_signer`}>
                <Title>Contrats à signer</Title>
                <ContainerBox>
                    <ContractsToBeSigned userType={UserType.MANAGER[0]}/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}/session`}>
                <Title>Ajouter une session</Title>
                <ContainerBox>
                    <CreateSession/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}/rapports`}>
                <Title>Rapports</Title>
                <RapportsView/>
            </Route>
            <Route path={`${path}/rapports/1`}>
                <Title>Offres de Stage</Title>
                <ContainerBox>
                    <RapportOfferValid/>
                </ContainerBox>
            </Route>
            <Route path={`${path}/rapports/2`}>
                <Title>Offres de Stage non validées</Title>
                <ContainerBox>
                    <RapportOfferInvalid/>
                </ContainerBox>
            </Route>
            <Route path={`${path}/rapports/3`}>
                <Title>Liste des étudiants sans Cv</Title>
                <ContainerBox>
                    <RapportStudentWithoutCv/>
                </ContainerBox>
            </Route>
            <Route path={`${path}/rapports/4`}>
                <Title>Liste des étudiants inscrits</Title>
                <ContainerBox>
                    <RapportStudentSignIn/>
                </ContainerBox>
            </Route>
            <Route path={`${path}/rapports/5`}>
                <Title>Liste des étudiants avec des Cv Invalides</Title>
                <ContainerBox>
                    <RapportStudentWithInvalidCv/>
                </ContainerBox>
            </Route>
            <Route path={`${path}/rapports/6`}>
                <ContainerBox>
                    <RapportStudentStatus/>
                </ContainerBox>
            </Route>
            <Route path={`${path}/rapports/offer`}>
                <ContainerBox>
                    <OfferAppRapportView/>
                </ContainerBox>
            </Route>
        </>
    )
}
