import OffersValidView from "../OffersValidView/OffersValidView";
import OfferNotValidView from "../OffersNotValidView/OfferNotValidView";

export function RapportOfferValid() {
    return (
        <>
            <OffersValidView/>
        </>
    );
}

export function RapportOfferInvalid() {
    return (
        <>
            <OfferNotValidView/>
        </>
    );
}

