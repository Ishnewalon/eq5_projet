import React, {Component} from "react";
import TeleverserCv from "./TeleverserCv/TeleverserCv";
import ViewOffersAndApply from "./ViewOffersAndApply/ViewOffersAndApply";

export default class StudentView extends Component {


    render() {
        return (<>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <TeleverserCv/>
                </div>
                <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
                    <ViewOffersAndApply/>
                </div>
            </>
        )
    }
}
