import React, {useEffect, useState} from 'react';
import {setInterview} from "../../../services/offerAppService";

export default function SetInterviewDate({offerApp, studentID}) {

    const min = new Date();
    const max = new Date();
    max.setMonth(min.getMonth() + 2)

    const setInterviewDate = (offerAppID, date) => e => {
        e.preventDefault();

        setInterview(offerAppID, date).then();
    }

    return (
        <div className="row">
            <div className="col">
                <input
                    id="date"
                    min={min.toISOString().slice(0, -8)}
                    max={max.toISOString().slice(0, -8)}
                    type="datetime-local" />
            </div>
            <div className="col">
                <button className="btn btn-primary" onClick={setInterviewDate(offerApp.id, studentID)}>
                    Setter votre date d'entrevue
                </button>
            </div>
        </div>
    )
}