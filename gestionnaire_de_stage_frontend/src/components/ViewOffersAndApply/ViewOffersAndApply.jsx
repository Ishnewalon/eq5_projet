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
        this.service.getAllOffersByDepartment(this.department)
            .then(offers => {
                this.setState({offers});
            }).catch(e => {
                console.trace(e);
            });
    }

    render() {
        const {offers} = this.state;
        console.log(offers)
        return (
            <div className='container'>
                <h2 className="text-center">Offres de Stage</h2>
                <ul>
                    {offers.map((offer, index) => <li key={index}><ApplyOnOffer offer={offer}/></li>)}
                </ul>
            </div>
        );
    }
}