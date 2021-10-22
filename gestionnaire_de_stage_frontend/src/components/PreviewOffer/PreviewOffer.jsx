import React, {Component} from 'react';
import './PreviewOffer.css';

export default function PreviewOffer(offer){

    return <div className="shadow-lg rounded-top p-3 border-left border-right border-light">
                <div className={'d-flex align-items-center flex-column'}>
                    <h2 className={'d-inline-block text-dark display-6 fst-italic'}>
                        <span className={"badge bg-secondary"}>{offer.title}</span></h2>
                    <div className={'row mb-0'}>
                        <span className={"col badge bg-dark rounded-pill px-2 py-1"}>{offer.department}</span>
                        <span className="ms-2 col badge bg-success px-2 py-1">{offer.salary + '$/h'}</span>
                    </div>
                    <p>Adresse : {offer.address}</p>
                    <p className={'mb-0'}>Description: <br/> {offer.description}</p>
                </div>
            </div>
}
