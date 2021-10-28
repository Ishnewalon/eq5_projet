import React, {useState} from 'react';
import OfferService from '../../../services/offer-service';
import {swalErr} from "../../../utility";
import Swal from "sweetalert2";
import "./ValidateOffer.css";
import PreviewOffer from "../../PreviewOffer/PreviewOffer";

export default function ValidateOffer({offer}) {
    const [valid, setValid] = useState(false);
    const service = OfferService

    const validateOffer = (offer, isValid) => {
        service.validateOffer(offer.id, isValid)
            .then(value => {
                if (value.status === 400) {
                    value.json().then(body => {
                        swalErr(body.message).fire({}).then();
                        console.error(body.message);
                    });
                    return;
                }
                setValid(isValid)
                let title = isValid ? 'Offre validée!' : 'Offre invalidée!'
                Swal.fire({title: title, icon: 'success'}).then();
            }, err => {
                console.error(err);
            })
            .catch(e => {
                console.error(e)
            });

    }


    return <>
        {valid ? <></> : <>
            <PreviewOffer offer={offer}/>
            <div className="d-flex justify-content-between align-items-center">
                <button id="validateBtn" className="btn btn-success fw-bold text-white w-50"
                        onClick={() => validateOffer(offer, true)}>Valide
                </button>
                <button id="invalidateBtn" className="btn btn-danger fw-bold text-white w-50"
                        onClick={() => validateOffer(offer, false)}>Invalide
                </button>
            </div>
        </>}
    </>
}
