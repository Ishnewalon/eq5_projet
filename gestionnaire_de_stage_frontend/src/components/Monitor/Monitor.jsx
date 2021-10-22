import React from "react";
import AddOffer from "../AddOffer/AddOffer";
import ViewAppliedStudents from "../ViewAppliedStudents/ViewAppliedStudents";
import AuthService from '../../services/auth-service';

export default function Monitor(){

    return <>
            <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                <AddOffer/>
            </div>
            <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                {ViewAppliedStudents(AuthService.user.email)}
            </div>
        </>;
}
