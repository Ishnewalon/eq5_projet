import React, {Component} from 'react';
import PreviewOffer from "../PreviewOffer/PreviewOffer";
import OfferService from '../../services/offer-service';
import Offer from "../../models/Offer";
import {swalErr} from "../../utility";

export default class ValidateOffer extends Component{
    constructor(props) {
        super(props);
        this.state = {
            valid: false
        }
        this.service = OfferService
    }

    validateOffer = (offer, valid) => {
        offer.valid = valid;
        this.service.validateOffer(offer)
            .then(v => {
                if((!v instanceof Offer)) {
                    swalErr(v);
                    return;
                }
                this.props.offer = v;
                this.setState({valid})
            })
            .catch(e => console.trace(e));
    }

    render() {
        const {offer} = this.props;

        return <React.Fragment>
            <PreviewOffer offer={offer} />
            <div className={`col ${this.state.valid ? 'border border-bottom border-success' : 'border border-bottom border-danger'}`}>
                <div className="col">
                    <button className="btn btn-success fw-bold text-white" onClick={() => this.validateOffer(offer, true)}>Valid</button>
                </div>
                <div className="col">
                    <button className="btn btn-danger fw-bold text-white" onClick={() => this.validateOffer(offer, false)}>Incorrect</button>
                </div>
            </div>
        </React.Fragment>
    }
}
