import React, {Component} from "react";
import {getCurriculumWithValidCV} from "../../services/curriculum-service";
import ListStudentValidCVView from "./ListStudentValidCVView/ListStudentValidCVView";
import authService from "../../services/auth-service";
import Swal from "sweetalert2";
import {swalErr} from "../../utility";

export default class LinkSupervisorToStudent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            cvList: [],
            supervisorList: [],
            supervisorID: '',
        };

        getCurriculumWithValidCV()
            .then(cvList => this.setState({cvList}))
            .catch(e => {
                this.setState({cvList: []})
                console.trace(e);
            });
                authService.getSupervisors()
            .then(supervisorList => this.setState({supervisorList}))
            .catch(e => {
                this.setState({supervisorList: []})
                console.trace(e);
            });
    }

    assign = (idStudent) => e => {
        e.preventDefault();
        authService.assign(idStudent, this.state.supervisorID)
            .then(responseMessage => {
                Swal.fire({title: responseMessage.message, icon: 'success'})
                    .then();
            })
            .catch(e => {
                console.trace(e)
                swalErr(e).fire({}).then();
            });

    }

    handleChange = input => e => {
        e.preventDefault()
        this.setState({[input]: e.target.value});
    }

    render() {
        return (
            <div className="container">
                <h2 className="text-center">Attribuer des superviseurs aux étudiants</h2>
                {this.state.cvList.map((cv, index) =>
                <div>
                    <ul>
                        <li ><ListStudentValidCVView key={index} student={cv.student} /></li>
                    </ul>

                    <div className="text-center">
                        <select onChange={this.handleChange('supervisorID')}>
                            {this.state.supervisorList.map((supervisor) =>
                                    <option value={supervisor.id}>{supervisor.lastName}, {supervisor.firstName}</option>
                            )}
                        </select>
                    </div>

                       <div className="form-group text-center">
                           <label/>
                           <div>
                               <button className="btn btn-success" onClick={this.assign(cv.student.id)}>Accepter</button>
                           </div>
                       </div>
                </div>)}
            </div>
        )
    }

}