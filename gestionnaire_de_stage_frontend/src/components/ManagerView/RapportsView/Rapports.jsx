import OffersValidView from "../OffersValidView/OffersValidView";
import OfferNotValidView from "../OffersNotValidView/OfferNotValidView";
import {Link} from "react-router-dom";
import StudentWithoutCvView from "../StudentWithoutCvView/StudentWithoutCvView";
import StudentSignIn from "../StudentSignIn/StudentSignIn";
import StudentWithInvalidCv from "../StudentWithInvalidCv/StudentWithInvalidCv";
import AllStudentStatusView from "../AllStudentStatusView/AllStudentStatusView";
import React from "react";

export function RapportOfferValid() {
    return (
        <>
            <h2 className="text-center">Offres de Stage</h2>
            <OffersValidView/>
            <BtnBack/>
        </>
    );
}

export function RapportOfferInvalid() {
    return (
        <>
            <h2 className="text-center mb-4">Offres de Stage non validées</h2>
            <OfferNotValidView/>
            <BtnBack/>
        </>
    );
}

export function RapportStudentWithoutCv() {
    return (
        <>
            <h2 className="text-center mb-4">Liste des étudiants sans Cv</h2>
            <StudentWithoutCvView/>
            <BtnBack/>
        </>
    );
}

export function RapportStudentSignIn() {
    return (
        <>
            <h2 className="text-center mb-4">Liste des étudiants inscrits</h2>
            <StudentSignIn/>
            <BtnBack/>
        </>
    );
}

export function RapportStudentWithInvalidCv() {
    return (
        <>
            <h2 className="text-center mb-4">Liste des étudiants avec des Cv Invalides</h2>
            <StudentWithInvalidCv/>
            <BtnBack/>
        </>
    );
}

export function RapportStudentStatus() {
    return (
        <>
            <h2 className="text-center">Status de tous les étudiants</h2>
            <AllStudentStatusView/>
            <BtnBack/>
        </>
    );
}

export function BtnBack() {
    return (
        <Link to="/dashboard/rapports" className="btn btn-primary">Retour</Link>
    );
}
