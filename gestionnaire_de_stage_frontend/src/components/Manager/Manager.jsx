import {Component} from "react";
import AddOffer from "../AddOffer/AddOffer";
import OfferService from "../../services/offer-service";

export default class Manager extends Component {


    constructor(props) {
        super(props);
        this.serviceOffer = OfferService
    }

    addOffer = (offer) => {
        this.serviceOffer.createOfferManager(offer).then()
    }

    render() {
        return (
            <AddOffer addOffer={this.addOffer}/>
        )
    }
}
