import React, {useState} from 'react';
import {swalErr} from "../../../utility";
import {setInterview} from "../../../services/offerAppService";

export default function SetInterviewDate({offerApp, removeOffer}) {

    const [date, setDate] = useState('')

    const min = new Date();
    const max = new Date();
    max.setMonth(min.getMonth() + 2)

    const setInterviewDate = offerAppID => e => {
        e.preventDefault();

        if (date === ''){
            swalErr.fire("Vous devez selectionner une date!");
            return;
        }

        setInterview(offerAppID, date).then(
            () => removeOffer(offerAppID));
    }

    return (
        <div className="row mt-4">
            <div className="col-2">
                #{offerApp.id}
            </div>
            <div className="col-2">
                {offerApp.offer.title}
            </div>
            <div className="col">
                <input
                    onChange={(e) => setDate(e.target.value)}
                    id="date"
                    min={min.toISOString().slice(0, -8)}
                    max={max.toISOString().slice(0, -8)}
                    type="datetime-local" />
            </div>
            <div className="col">
                <button className="btn btn-primary" onClick={setInterviewDate(offerApp.id)}>
                    Setter votre date d'entrevue
                </button>
            </div>
        </div>
    )
}