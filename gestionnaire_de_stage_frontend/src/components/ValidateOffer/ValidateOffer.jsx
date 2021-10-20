import React, {Component} from 'react';
import PreviewOffer from "../PreviewOffer/PreviewOffer";
import OfferService from '../../services/offer-service';
import {swalErr} from "../../utility";
import Swal from "sweetalert2";
import "./ValidateOffer.css";

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
                if(typeof v === "string") {
                    console.trace(v);
                    swalErr(v).fire({}).then();
                    return;
                }
                this.setState({valid})
                Swal.fire(valid ? 'Offre validé!' : 'Offre invalidé!', '', valid ? 'success': 'error').then();
            })
            .catch(e => {
                console.trace(e)
                swalErr(e).fire({}).then();
            });
    }

    render() {
        const {offer} = this.props;
        const {valid} = this.state;

        return <div className={`${valid ? 'border-left border-success' : 'border-left border-danger'}`}>
                <PreviewOffer offer={offer} />
                <div className="d-flex justify-content-between align-items-center">
                    <button id="validateBtn" className="btn btn-success fw-bold text-white w-50"
                            onClick={() => this.validateOffer(offer, true)}>Valid</button>
                    <button id="invalidateBtn" className="btn btn-danger fw-bold text-white w-50"
                            onClick={() => this.validateOffer(offer, false)}>Incorrect</button>
                </div>
            </div>
    }
}
