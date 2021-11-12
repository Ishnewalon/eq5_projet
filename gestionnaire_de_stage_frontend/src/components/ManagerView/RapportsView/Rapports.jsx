import OffersValidView from "../OffersValidView/OffersValidView";
import OfferNotValidView from "../OffersNotValidView/OfferNotValidView";
import {Link} from "react-router-dom";
import StudentWithoutCvView from "../StudentWithoutCvView/StudentWithoutCvView";
import StudentSignIn from "../StudentSignIn/StudentSignIn";
import StudentWithInvalidCv from "../StudentWithInvalidCv/StudentWithInvalidCv";

export function RapportOfferValid() {
    return (
        <>
            <OffersValidView/>
            <BtnBack/>
        </>
    );
}

export function RapportOfferInvalid() {
    return (
        <>
            <OfferNotValidView/>
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

export function BtnBack() {
    return (
        <Link to="/dashboard/rapports" className="btn btn-primary">Retour</Link>
    );
}
