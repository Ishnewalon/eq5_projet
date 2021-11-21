import React from "react";
import CreateOffer from "../Offers/CreateOffer";
import OffersManagerValidation from "../Offers/OffersManagerValidation";
import CurriculumValidation from "../Curriculums/CurriculumValidation";
import ContractsToSign from "../Contract/ContractsToSign";
import LinkSupervisorToStudent from "../Admin/StudentManagement/LinkSupervisorToStudent";
import {Route, useRouteMatch} from "react-router-dom";
import {UserType} from "../../enums/UserTypes";
import StartContract from "../Contract/StartContract";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {useAuth} from "../../services/use-auth";
import RapportsView from "../Admin/RapportsView";
import {
    RapportOfferInvalid,
    RapportOfferValid,
    RapportStudentSignIn,
    RapportStudentsNotYetEvaluated,
    RapportStudentStatus,
    RapportStudentsWithCompanyNotYetEvaluated,
    RapportStudentWithInvalidCv,
    RapportStudentWithoutCv
} from "../Admin/Rapports";
import CreateSession from "../Sessions/CreateSession";
import ContractsSigned from '../Contract/ContractsSigned';
import OfferApplicationsRapportView from "../OfferApplications/OfferApplicationsRapportView";
import {Title} from "../SharedComponents/Title/Title";

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
                    <ContractsSigned userType={UserType.MANAGER[0]}/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}/offres/ajouter`}>
                <Title>Ajouter une offre de stage</Title>
                <ContainerBox>
                    <CreateOffer/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}/offres/review`}>
                <Title>Validation des offres</Title>
                <ContainerBox>
                    <OffersManagerValidation/>
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
                <StartContract/>
            </Route>
            <Route exact path={`${path}/contrats/a_signer`}>
                <Title>Contrats à signer</Title>
                <ContainerBox>
                    <ContractsToSign userType={UserType.MANAGER[0]}/>
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
                <RapportStudentWithoutCv/>
            </Route>
            <Route path={`${path}/rapports/4`}>
                <Title>Liste des étudiants inscrits</Title>
                <RapportStudentSignIn/>
            </Route>
            <Route path={`${path}/rapports/5`}>
                <Title>Liste des étudiants avec des Cv Invalides</Title>
                <RapportStudentWithInvalidCv/>
            </Route>
            <Route path={`${path}/rapports/6`}>
                <Title>Status de tous les étudiants</Title>
                <RapportStudentStatus/>
            </Route>
            <Route path={`${path}/rapports/7`}>
                <Title>Les étudiants pas encore évalués</Title>
                <RapportStudentsNotYetEvaluated/>
            </Route>
            <Route path={`${path}/rapports/8`}>
                <Title>Les étudiants dont la compagnie n'a pas encore été évaluée</Title>
                <RapportStudentsWithCompanyNotYetEvaluated/>
            </Route>
            <Route path={`${path}/rapports/offer`}>
                <OfferApplicationsRapportView/>
            </Route>
        </>
    )
}
