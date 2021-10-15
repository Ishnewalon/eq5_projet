import React, {Component} from "react";
import TeleverserCv from "./TeleverserCv/TeleverserCv";
import ViewOffers from "./ViewOffers/ViewOffers";

export default class StudentView extends Component {


    render() {
        return (<>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <TeleverserCv/>
                </div>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ViewOffers/>
                </div>
            </>
        )
    }
}
