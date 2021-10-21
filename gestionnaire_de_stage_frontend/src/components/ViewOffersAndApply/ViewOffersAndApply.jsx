import React, {Component} from 'react';
import OfferService from '../../services/offer-service';
import AuthService from '../../services/auth-service';
import ApplyOnOffer from "../ApplyOnOffer/ApplyOnOffer";

export default class ViewOffersAndApply extends Component {

    constructor(props) {
        super(props);
        this.service = OfferService;
        this.department = AuthService.user.department;
        this.state = {
            offers: []
        }
        this._getAllOffers();
    }

    _getAllOffers() {
        this.service.getAllOffers()
            .then(offers => {
                this.setState({offers});
            }).catch(e => {
                console.trace(e);
            });
    }

    _extractRelevantOffers(offers) {
        let relOffers = [];
        offers.map(offer => {
            if(offer.department === this.department &&
                offer.valid){
                relOffers.push(offer);
            }
        });
        return relOffers;
    }

    render() {
        const {offers} = this.state;
        const exOffers = this._extractRelevantOffers(offers);

        return (
            <div className='container'>
                <h2 className="text-center">Offres de Stage</h2>
                <ul>
                    {exOffers.map((offer, index) => <li key={index}><ApplyOnOffer offer={offer}/></li>)}
                </ul>
            </div>
        );
    }
}