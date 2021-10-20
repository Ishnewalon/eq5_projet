import React, {Component} from "react";
import AddOffer from "../AddOffer/AddOffer";
import OfferService from "../../services/offer-service";
import ReviewOffers from "../ReviewOffers/ReviewOffers";
import ViewOffers from "../ViewOffers/ViewOffers";
import ValiderCv from "../ValidateCv/ValiderCv";

export default class Manager extends Component {


    constructor(props) {
        super(props);
        this.serviceOffer = OfferService
    }

    addOffer = (offer) => {
        this.serviceOffer.createOfferManager(offer).then()
    }

    render() {
        return (<>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <AddOffer addOffer={this.addOffer}/>
                </div>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ViewOffers />
                </div>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ReviewOffers />
                </div>
                {/*<div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">*/}
                {/*    <ValiderCv />*/}
                {/*</div>*/}
            </>
        )
    }
}
