import './ValiderCv.css'
import React, {Component} from "react";
import {getStudentsWithInvalidCV, validateCV} from "../../services/curriculum-service";
import ListStudentView from "./ListStudentView/ListStudentView";
import {swalErr} from "../../utility";
import Swal from "sweetalert2";


export default class ValiderCv extends Component {

    constructor(props) {
        super(props);
        this.state = {
            students: [],
            valid: false
        };
        getStudentsWithInvalidCV()
            .then(students => this.setState({students}))
            .catch(e => {
                this.setState({students: []})
                console.trace(e);
            });
    }

    validateCV = (id, valid) => {
        validateCV(id, valid)
            .then(x => {
                if (typeof x === "string") {
                    console.trace(x);
                    swalErr(x).fire({}).then();
                    return;
                }
                this.setState({valid})
                Swal.fire(valid ? 'Cv validé!' : 'Cv invalide!', '', valid ? 'success' : 'error').then();
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
                {this.state.students.map((student, index) =>
                    <div>
                        <ul>
                            <li key={index}><ListStudentView student={student}/></li>
                        </ul>
                        <div className={`${valid ? 'border-left border-success' : 'border-left border-danger'}`}>
                            <div className="d-flex justify-content-between align-items-center">
                                <button id="validateBtn" className="btn btn-success fw-bold text-white w-50"
                                        onClick={() => this.validateCV(student.curriculumPath, true)}>Valide
                                </button>
                                <button id="invalidateBtn" className="btn btn-danger fw-bold text-white w-50"
                                        onClick={() => this.validateCV(student.curriculumPath, false)}>Invalide
                                </button>
                            </div>
                        </div>
                    </div>)}
            </div>
        )
    }
}
