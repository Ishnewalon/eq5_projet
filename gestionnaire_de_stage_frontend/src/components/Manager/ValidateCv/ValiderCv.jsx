import './ValiderCv.css'
import React, {Component} from "react";
import {getCurriculumWithInvalidCV, validateCV} from "../../../services/curriculum-service";
import ListStudentView from "./ListStudentView/ListStudentView";
import {swalErr} from "../../../utility";
import Swal from "sweetalert2";


export default class ValiderCv extends Component {

    constructor(props) {
        super(props);
        this.state = {
            curriculumList: [],
            valid: false
        };
        getCurriculumWithInvalidCV()
            .then(curriculumList => this.setState({curriculumList}))
            .catch(e => {
                this.setState({curriculumList: []})
                console.trace(e);
            });
    }

    refresh = () => {
        window.location.reload();
    }

    valideCV = (id, valid) => {
        validateCV(id, valid)
            .then(responseMessage => {
                this.setState({valid})
                Swal.fire({title: responseMessage.message, icon: valid ? 'success' : 'error'})
                    .then();
                    // .then(v => this.refresh());
            })
            .catch(e => {
                console.trace(e)
                swalErr(e).fire({}).then();
            });

    }

    render() {
        const {valid} = this.state;
        return (
            <div className='container'>
                <h2 className="text-center">Liste des étudiants</h2>
                {this.state.curriculumList.map((cv, index) =>
                    <div key={index}>
                        <ul>
                            <li><ListStudentView cv={cv}/></li>
                        </ul>
                        <div className={`${valid ? 'border-left border-success' : 'border-left border-danger'}`}>
                            <div className="d-flex justify-content-between align-items-center">
                                <button id="validateBtn" className="btn btn-success fw-bold text-white w-50"
                                        onClick={() => this.valideCV(cv.id, true)}>Valide
                                </button>
                                <button id="invalidateBtn" className="btn btn-danger fw-bold text-white w-50"
                                        onClick={() => this.valideCV(cv.id, false)}>Invalide
                                </button>
                            </div>
                        </div>
                    </div>)}
            </div>
        )
    }
}
