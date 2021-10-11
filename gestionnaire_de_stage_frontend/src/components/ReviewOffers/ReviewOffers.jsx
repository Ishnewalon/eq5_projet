import './ReviewOffers.css'
import React, {Component} from 'react';
import OfferService from '../../services/offer-service';

export default class ReviewOffers extends Component{


    constructor(props){
        super(props);
        this.service = OfferService;
        this.state = {
            offers: []
        }
        this.service.getAllOffers()
            .then(v => this.setState({offers: v}))
            .catch(e => console.trace(e))
    }

    render() {
        return (
            <div className='container'>
                <ul>
                    {this.state.offers.map((offer, index) => <li key={index}><ValidateOffer offer={offer}/></li>)}
                </ul>
            </div>
        );
    }
}
