import React from "react";
import CreateOffer from "../Offers/CreateOffer";
import OffersManagerValidation from "../Offers/OffersManagerValidation";
import ContractsToSign from "../Contract/ContractsToSign";
import LinkSupervisorToStudent from "../Admin/StudentManagement/LinkSupervisorToStudent";
import {Route, useRouteMatch} from "react-router-dom";
import {UserType} from "../../enums/UserTypes";
import StartContract from "../Contract/StartContract";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {useAuth} from "../../hooks/use-auth";
import RapportsView from "../Admin/RapportsView";
import CreateSession from "../Sessions/CreateSession";
import ContractsSigned from '../Contract/ContractsSigned';
import {Title} from "../SharedComponents/Title/Title";
import StudentsCurriculumsOverview from "../Admin/StudentManagement/StudentsCurriculumsOverview";
import StudentCurriculumValidation from "../Curriculums/StudentCurriculumsValidation";
import {BtnBack} from "../SharedComponents/BtnBack";

export default function ManagerView() {
    const {path} = useRouteMatch();
    let auth = useAuth();
    return (<>
            <Route exact path={`${path}`}>
                <div className="container">
                    <Title>Bonjour {auth.user.firstName}!</Title>
                </div>
            </Route>
            <Route exact path={`${path}/manager/contracts/signed`}>
                <Title>Contrats signés</Title>
                <ContainerBox>
                    <ContractsSigned userType={UserType.MANAGER[0]}/>
                </ContainerBox>
            </Route>
            <Route path={`${path}/offres`}>
                <Route exact path={`${path}/offres/ajouter`}>
                    <Title>Ajouter une offre de stage</Title>
                    <CreateOffer/>
                </Route>
                <Route exact path={`${path}/offres/review`}>
                    <Title>Validation des offres</Title>
                    <ContainerBox>
                        <OffersManagerValidation/>
                    </ContainerBox>
                </Route>
            </Route>
            <Route path={`${path}/curriculum/review`}>
                <Route exact path={`${path}/curriculum/review/student`}>
                    <StudentCurriculumValidation/>
                    <BtnBack/>
                </Route>
                <Route exact path={`${path}/curriculum/review`}>
                    <Title>Validation des curriculums</Title>
                    <StudentsCurriculumsOverview/>
                </Route>
            </Route>
            <Route path={`${path}/students/applied`}>
                <Title>Attribuer des superviseurs aux étudiants</Title>
                <LinkSupervisorToStudent/>
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
                <CreateSession/>
            </Route>
            <Route path={`${path}/rapports`}>
                <RapportsView/>
            </Route>
        </>
    )
}
