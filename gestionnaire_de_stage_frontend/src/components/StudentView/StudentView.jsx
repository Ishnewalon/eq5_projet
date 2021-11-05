import React from "react";
import TeleverserCv from "./TeleverserCv/TeleverserCv";
import ViewOffersAndApply from "./ViewOffersAndApply/ViewOffersAndApply";
import {Route, useRouteMatch} from "react-router-dom";
import {ViewContract} from "./ViewContract";
import StudentOfferApplicationList from "./StudentOfferApplication/StudentOfferApplicationList";

export default function StudentView() {
    let {path} = useRouteMatch();
    return (<>
            <Route exact path={`${path}/televerser`}>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <TeleverserCv/>
                </div>
            </Route>
            <Route exact path={`${path}/offres`}>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ViewOffersAndApply/>
                </div>
            </Route>
            <Route exact path={`${path}/view/status`}>
                <div>
                    <StudentOfferApplicationList/>
                </div>
            </Route>
            <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                <ViewContract/>
            </div>
        </>
    )
}
