import React from "react";
import AddOffer from "../AddOffer/AddOffer";
import ViewAppliedStudents from "./ViewAppliedStudents/ViewAppliedStudents";
import {Route, useRouteMatch} from "react-router-dom";
import ViewContractToBeSigned from "../Manager/ViewContractToBeSigned";
import {UserType} from "../../enums/UserTypes";

export default function Monitor() {
    let {path} = useRouteMatch();
    return <>
        <Route exact path={`${path}/offres/ajouter`}>
            <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                <AddOffer/>
            </div>
        </Route>
        <Route exact path={`${path}/applications`}>
            <div className="container bg-dark px-3 py-4 rounded mt-5">
                <ViewAppliedStudents/>
            </div>
        </Route>
        <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
            <h1 className='text-center'>Contrats d'étudiants à valider</h1>
            <ViewContractToBeSigned userType={UserType.MONITOR[0]}/>
        </div>
    </>;
}
