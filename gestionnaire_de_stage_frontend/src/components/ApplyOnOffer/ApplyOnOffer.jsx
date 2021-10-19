import React, {Component} from 'react';
import AuthService from "../../services/auth-service";
import OfferAppService from "../../services/offerApp-service";
import OfferApp from "../../models/OfferApp";

export default class ApplyOnOffer extends Component{

    constructor(props) {
        super(props);
        this.service = OfferAppService;
        this.userId = AuthService.getUserId();
    }

    apply = offerId => (e) => {
        console.log(offerId);
        console.log(e);
        e.preventDefault();
        this.service.apply(new OfferApp(this.userId, offerId)).then();
    }

    render() {
        return (
            <div className="form-group text-center">
                <label/>
                <button className="btn btn-primary" type={"button"} onClick={this.apply()}>Soumettre votre candidature</button>
            </div>
        );
    }

}