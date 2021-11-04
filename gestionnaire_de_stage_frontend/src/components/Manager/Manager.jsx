import React from "react";
import AddOffer from "../AddOffer/AddOffer";
import ReviewOffers from "./ReviewOffers/ReviewOffers";
import ViewOffers from "./ViewOffers/ViewOffers";
import ValiderCv from "./ValidateCv/ValiderCv";
import ViewContractToBeSigned from "./ViewContractToBeSigned";
import LinkSupervisorToStudent from "./LinkSupervisorToStudent/LinkSupervisorToStudent";
import {Route, useRouteMatch} from "react-router-dom";
import {UserType} from "../../enums/UserTypes";

export default function Manager() {
    const {path} = useRouteMatch();
    return (<>
            <Route exact path={`${path}/offres/ajouter`}>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <AddOffer/>
                </div>
            </Route>
            <Route exact path={`${path}/offres`}>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ViewOffers/>
                </div>
            </Route>
            <Route exact path={`${path}/offres/review`}>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ReviewOffers/>
                </div>
            </Route>
            <Route exact path={`${path}/offres/valid`}>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ValiderCv/>
                </div>
            </Route>
            <Route path={`${path}/students/applied`}>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <LinkSupervisorToStudent/>
                </div>
            </Route>
            <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                <ViewContractToBeSigned userType={UserType.MANAGER[0]} />
            </div>
        </>
    )
}
