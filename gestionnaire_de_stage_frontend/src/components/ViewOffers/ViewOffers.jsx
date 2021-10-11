import React from "react";
import './ViewOffers.css'
import OfferService from '../../services/offer-service'
import PreviewOffer from '../PreviewOffer/PreviewOffer';
import AuthService from '../../services/auth-service';

export default class ViewOffers extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            department: AuthService.user.department,
            offers: []
        };
        this.service = OfferService;
        this.service.getAllOffersByDepartment(this.state.department)
                .then(v => this.setState({offers: v}))
                .catch(e => {
                    this.setState({offers: []})
                    console.trace(e);
                });
    }

    render() {
        return (
            <div className='container'>
                <ul>
                    {this.state.offers.map((offer, index) => <li key={index}><PreviewOffer offer={offer}/></li>)}
                </ul>
            </div>
        );
    }
}

