import OffersRapportValidView from "../Offers/OffersRapportValidView";
import OffersRapportNotValid from "../Offers/OffersRapportNotValid";
import {Link} from "react-router-dom";
import StudentWithoutCvView from "./StudentManagement/StudentWithoutCvView";
import StudentSignIn from "./StudentManagement/StudentSignIn";
import StudentWithInvalidCv from "./StudentManagement/StudentWithInvalidCv";
import AllStudentStatusView from "./StudentManagement/AllStudentStatusView";
import React from "react";
import StudentsWithCompanyNotYetEvaluated from "./StudentManagement/StudentsWithCompanyNotYetEvaluated";
import StudentsNotYetEvaluated from "./StudentManagement/StudentsNotYetEvaluated";

export function RapportOfferValid() {
    return (
        <>
            <OffersRapportValidView/>
            <BtnBack/>
        </>
    );
}

export function RapportOfferInvalid() {
    return (
        <>
            <OffersRapportNotValid/>
            <BtnBack/>
        </>
    );
}

export function RapportStudentWithoutCv() {
    return (
        <>
            <StudentWithoutCvView/>
            <BtnBack/>
        </>
    );
}

export function RapportStudentSignIn() {
    return (
        <>
            <StudentSignIn/>
            <BtnBack/>
        </>
    );
}

export function RapportStudentWithInvalidCv() {
    return (
        <>
            <StudentWithInvalidCv/>
            <BtnBack/>
        </>
    );
}

export function RapportStudentStatus() {
    return (
        <>
            <AllStudentStatusView/>
            <BtnBack/>
        </>
    );
}

export function RapportStudentsNotYetEvaluated() {
    return (
        <>
            <StudentsNotYetEvaluated/>
            <BtnBack/>
        </>
    );
}

export function RapportStudentsWithCompanyNotYetEvaluated() {
    return (
        <>
            <StudentsWithCompanyNotYetEvaluated/>
            <BtnBack/>
        </>
    );
}

export function BtnBack() {
    return (
        <Link to="/dashboard/rapports" className="btn btn-primary">Retour</Link>
    );
}
