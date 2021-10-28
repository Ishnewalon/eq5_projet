import React, {Component} from 'react';
import AuthService from "../../../services/auth-service";
import OfferAppService from "../../../services/offerAppService";
import OfferApp from "../../../models/OfferApp";
import PreviewOffer from "../../PreviewOffer/PreviewOffer";

export default class ApplyOnOffer extends Component {

    constructor(props) {
        super(props);
        this.service = OfferAppService;
        this.userId = AuthService.getUserId();
    }

    apply = offerId => e => {
        e.preventDefault();

        this.service.apply(new OfferApp(offerId, this.userId)).then();
    }

    render() {
        const {offer} = this.props;
        return (
            <div>
                <PreviewOffer offer={offer}/>
                <div className="form-group text-center mt-2">
                    <label/>
                    <button className="btn btn-primary" onClick={this.apply(offer.id)}>Soumettre votre candidature
                    </button>
                </div>
            </div>
        );
    }

}
