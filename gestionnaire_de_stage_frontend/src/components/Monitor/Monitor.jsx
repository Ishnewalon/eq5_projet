import React from "react";
import AddOffer from "../AddOffer/AddOffer";
import ViewAppliedStudents from "./ViewAppliedStudents/ViewAppliedStudents";

export default function Monitor() {

    return <>

        <div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
            <AddOffer/>
        </div>
        <div className="container bg-dark px-3 py-4 rounded mt-5">
            <ViewAppliedStudents/>
        </div>
    </>;
}
