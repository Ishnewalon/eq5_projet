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
import CurriculumTable from "./CurriculumTable/CurriculumTable";

export default function StudentView() {
    let {path} = useRouteMatch();
    let auth = useAuth();
    return (<ContainerBox>
            <Route exact path={`${path}/televerser`}>
                <TeleverserCv/>
            </Route>
            <Route exact path={`${path}/mes_cv`}>
                <CurriculumTable />
            </Route>
            <Route exact path={`${path}/offres`}>
                <ViewOffersAndApply/>
            </Route>
            <Route exact path={`${path}/view/status`}>
                <OfferApplicationSetFinalStatus/>
            </Route>
            <Route exact path={`${path}/offer/setdate`}>
                <OfferApplicationListView/>
            </Route>
            <Route exact path={`${path}/voir_mon_contrat`}>
                <ContractView/>
            </Route>
            <Route exact path={`${path}/student/contract/signed`}>
                <StudentContractView/>
            </Route>
            <Route exact path={`${path}`}>
                <h1 className="text-center">Bonjour {auth.user.firstName}!</h1>
            </Route>
        </ContainerBox>
    )
}
