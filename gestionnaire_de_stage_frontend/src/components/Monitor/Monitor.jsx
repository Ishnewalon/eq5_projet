import React, {Component} from "react";
import AddOffer from "../AddOffer/AddOffer";
import OfferService from "../../services/offer-service";
import ViewAppliedStudents from "../ViewAppliedStudents/ViewAppliedStudents";

export default class Monitor extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <AddOffer/>
                </div>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ViewAppliedStudents />
                </div>
            </>
    }
}
