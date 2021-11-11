import OffersValidView from "../OffersValidView/OffersValidView";
import OfferNotValidView from "../OffersNotValidView/OfferNotValidView";
import {Link} from "react-router-dom";

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
            <StudentWithoutCv/>
            <BtnBack/>
        </>
    );
}

export function BtnBack() {
    return (
        <Link to="/dashboard/rapports" className="btn btn-primary">Retour</Link>
    );
}
