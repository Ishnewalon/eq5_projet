import React from "react";
import './ViewOffers.css'
import OfferService from '../../services/offer-service'
import PreviewOffer from '../PreviewOffer/PreviewOffer';
import AuthService from '../../services/auth-service';
import OfferDTO from "../../models/OfferDTO";

export default class ViewOffers extends React.Component {

    constructor(props) {
        super(props);
        console.log(AuthService.user);
        this.state = {
            department: AuthService.user.department || 'Informatique',
            offers: []
        };
        OfferService.getAllOffersByDepartment(this.state.department)
            .then(offers => this.setState({offers}))
            .catch(e => {
                this.setState({offers: []})
                console.trace(e);
            });
    }




    render() {
        return (
            <div className='container'>
                <h2 className="text-center">Offres de Stage</h2>
                <ul>
                    {this.state.offers.map((offer, index) => <li key={index}><PreviewOffer offer={offer}/></li>)}
                </ul>
            </div>
        );
    }
}

