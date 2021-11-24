import React from "react";
import CreateOffer from "../Offers/CreateOffer";
import OffersManagerValidation from "../Offers/OffersManagerValidation";
import ContractsToSign from "../Contract/ContractsToSign";
import LinkSupervisorToStudent from "../Admin/StudentManagement/LinkSupervisorToStudent";
import {Route, useRouteMatch} from "react-router-dom";
import {UserType} from "../../enums/UserTypes";
import StartContract from "../Contract/StartContract";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {useAuth} from "../../services/use-auth";
import RapportsView from "../Admin/RapportsView";
import CreateSession from "../Sessions/CreateSession";
import ContractsSigned from '../Contract/ContractsSigned';
import {Title} from "../SharedComponents/Title/Title";
import StudentsCurriculumsOverview from "../Admin/StudentManagement/StudentsCurriculumsOverview";
import StudentCurriculumValidation from "../Curriculums/StudentCurriculumsValidation";
import {BtnBack} from "../SharedComponents/BtnBack";
import IMFFF from "../../assets/images/img1.svg"
import dwdawd from "../../assets/images/dawdawda.svg"

export default function ManagerView() {
    const {path} = useRouteMatch();
    let auth = useAuth();
    return (<>
            <Route exact path={`${path}`}>
                <div className="container">
                    <Title>Bonjour {auth.user.firstName}!</Title>
                    <section data-description="Add emphasis with color">
                        <h1 className="mega montserrat bold text-left">
                            Movement is <span className="color-emphasis-1">here.</span><br/>
                            And it is here <span className="color-emphasis-1">to stay.</span>
                        </h1>
                        <div className="parent">
                            <img src={dwdawd} alt="dawdadad" className="image2"/>
                            <img src={IMFFF} alt="allo" className="center image1"/>
                        </div>
                    </section>
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
            <Route path={`${path}/rapports`}>
                <RapportsView/>
            </Route>
        </>
    )
}
