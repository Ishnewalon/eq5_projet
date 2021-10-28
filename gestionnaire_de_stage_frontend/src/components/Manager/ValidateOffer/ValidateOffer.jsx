import React, {useState} from 'react';
import PreviewOffer from "../../PreviewOffer/PreviewOffer";
import OfferService from '../../../services/offer-service';
import {swalErr} from "../../../utility";
import Swal from "sweetalert2";
import "./ValidateOffer.css";

export default function ValidateOffer({offer}) {
    const [valid, setValid] = useState(false);
    const service = OfferService

    const validateOffer = (offer, isValid) => {
        offer.valid = isValid;
        service.validateOffer(offer)
            .then(v => {
                if (typeof v === "string") {
                    console.error(v);
                    swalErr(v).fire({}).then();
                    return;
                }
                setValid(isValid)
                let title = isValid ? 'Offre validée!' : 'Offre invalidée!'
                Swal.fire({title: title, icon: 'success'}).then();
            })
            .catch(e => {
                console.error(e)
                swalErr(e).fire({}).then();
            });

    }


    return <div className={`${valid ? 'border-left border-success' : 'border-left border-danger'}`}>
        <PreviewOffer offer={offer}/>
        <div className="d-flex justify-content-between align-items-center">
            <button id="validateBtn" className="btn btn-success fw-bold text-white w-50"
                    onClick={() => validateOffer(offer, true)}>Valide
            </button>
            <button id="invalidateBtn" className="btn btn-danger fw-bold text-white w-50"
                    onClick={() => validateOffer(offer, false)}>Invalide
            </button>
        </div>
    </div>
}
