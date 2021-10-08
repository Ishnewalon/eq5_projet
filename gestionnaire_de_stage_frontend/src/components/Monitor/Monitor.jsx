import {Component} from "react";
import AddOffer from "../AddOffer/AddOffer";
import OfferService from "../../services/offer-service";

export default class Monitor extends Component {
    constructor(props) {
        super(props);
        this.serviceOffer = OfferService
    }

    addOffer = (offer) => {
        this.serviceOffer.createOfferMonitor(offer).then()
    }

    render() {
        return (
            <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                <AddOffer addOffer={this.addOffer}/>
            </div>
        )
    }
}
