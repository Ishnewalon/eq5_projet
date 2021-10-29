import React, {Component} from "react";
import AddOffer from "../AddOffer/AddOffer";
import ReviewOffers from "../ReviewOffers/ReviewOffers";
import ViewOffers from "../ViewOffers/ViewOffers";
import ValiderCv from "../ValidateCv/ValiderCv";
import LinkSupervisorToStudent from "../LinkSupervisorToStudent/LinkSupervisorToStudent";
import ManagerStartContract from "../ContractSigning/ManagerStartContract/ManagerStartContract";

export default class Manager extends Component {

    render() {
        return (<>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <AddOffer/>
                </div>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ViewOffers/>
                </div>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ReviewOffers/>
                </div>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ValiderCv />
                </div>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <LinkSupervisorToStudent/>
                </div>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ManagerStartContract />
                </div>
            </>
        )
    }
}
