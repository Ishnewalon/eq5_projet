import './ValiderCv.css'
import React, {Component, useState} from "react";
import {Document, Page} from "react-pdf";
import CurriculumService, {validateCV} from "../../services/curriculum-service";

export default class ValiderCv extends Component{
    constructor(props) {
        super(props);
        this.state = {
            valid: false
        }
        this.service = CurriculumService
    }

    validateCV(file){

    }
    render() {
        return (<div>

            </div>
        )
    }
}
