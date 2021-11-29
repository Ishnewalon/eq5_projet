import React from "react";
import CurriculumUpload from "../Curriculums/CurriculumUpload";
import OffersStudentApply from "../Offers/OffersStudentApply";
import {Route, useRouteMatch} from "react-router-dom";
import OfferApplicationsStudentSetStatusEnAttenteEntrevue
    from "../OfferApplications/OfferApplicationsStudentSetStatusEnAttenteEntrevue";
import {ContractToSignStudent} from "../Contract/ContractToSignStudent";
import OfferApplicationsStudentSetStatusFinal from "../OfferApplications/OfferApplicationsStudentSetStatusFinal";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {useAuth} from "../../hooks/use-auth";
import ContractSignedStudent from "../Contract/ContractSignedStudent";
import CurriculumsStudent from "../Curriculums/CurriculumsStudent";
import {Title} from "../SharedComponents/Title/Title";
import Profile from "../SharedComponents/Profile/Profile";

export default function StudentView() {
    let {path} = useRouteMatch();
    let auth = useAuth();
    return (<>
            <Route exact path={`${path}/televerser`}>
                <Title>Téléverser mon CV</Title>
                <ContainerBox>
                    <CurriculumUpload/>
                </ContainerBox>
            </Route>
            <Route exact path={`${path}/mes_cv`}>
                <Title>Vos Curriculums</Title>
                <CurriculumsStudent/>
            </Route>
            <Route exact path={`${path}/offres`}>
                <Title>Offres de stage</Title>
                <OffersStudentApply/>
            </Route>
            <Route exact path={`${path}/view/status`}>
                <Title>Les status de mes applications</Title>
                <OfferApplicationsStudentSetStatusFinal/>
            </Route>
            <Route exact path={`${path}/offer/setdate`}>
                <Title>Ajouter vos dates d'entrevue</Title>
                <OfferApplicationsStudentSetStatusEnAttenteEntrevue/>
            </Route>
            <Route exact path={`${path}/voir_mon_contrat`}>
                <Title>Contrat à signer</Title>
                <ContractToSignStudent/>
            </Route>
            <Route exact path={`${path}/student/contract/signed`}>
                <Title>Mes contrats</Title>
                <ContractSignedStudent/>
            </Route>
            <Route exact path={`${path}`}>
                <Title>Bonjour {auth.user.firstName}!</Title>
                <Profile/>
            </Route>
        </>
    )
}
