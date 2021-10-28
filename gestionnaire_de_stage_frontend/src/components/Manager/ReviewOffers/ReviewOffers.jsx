import './ReviewOffers.css'
import React, {Component} from 'react';
import OfferService from '../../../services/offer-service';
import ValidateOffer from "../ValidateOffer/ValidateOffer";
import {swalErr} from "../../../utility";

export default class ReviewOffers extends Component{

    constructor(props){
        super(props);
        this.service = OfferService;
        this.state = {
            offers: []
        }
        this.service.getAllOffersInvalid()
            .then(offers => this.setState({offers}))
            .catch(e => {
                console.trace(e);
                swalErr(e).fire({}).then();
            })
    }

    render() {
        const {offers} = this.state;
        return (
            <div className='container'>
                <ul>
                    {offers.map((offer, index) => <li key={index}><ValidateOffer offer={offer}/></li>)}
                </ul>
            </div>
        );
    }
}
