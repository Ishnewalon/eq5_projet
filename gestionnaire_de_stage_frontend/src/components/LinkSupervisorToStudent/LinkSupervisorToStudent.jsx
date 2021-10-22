import {Component} from "react";
import {getCurriculumWithValidCV} from "../../services/curriculum-service";
import ListStudentValidCVView from "./ListStudentValidCVView/ListStudentValidCVView";
import ListSupervisorsList from "./ListSupervisorsList/ListSupervisorsList";
import authService from "../../services/auth-service";

export default class LinkSupervisorToStudent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            cvList: [],
            supervisorList: [],
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

    render() {
        return (
            <div className="container">
                <h2 className="text-center">Attribuer des superviseurs aux étudiants</h2>
                {this.state.cvList.map((cv, index) =>
                <div>
                    <ul>
                        <li ><ListStudentValidCVView key={index} student={cv.student} /></li>
                    </ul>

                    <div>
                        <select>
                            {this.state.supervisorList.map((supervisor, index) =>
                            <ListSupervisorsList key={index} supervisor={supervisor}/>
                            )}
                        </select>
                    </div>

                  {/* <div className={`${valid ? 'border-left border-success' : 'border-left border-danger'}`}>
                        <div className="d-flex justify-content-between align-items-center">
                            <button id="validateBtn" className="btn btn-success fw-bold text-white w-50"
                                    onClick={() => this.valideCV(cv.id, true)}>Valide
                            </button>
                            <button id="invalidateBtn" className="btn btn-danger fw-bold text-white w-50"
                                    onClick={() => this.valideCV(cv.id, false)}>Invalide
                            </button>
                        </div>
                    </div>*/}
                </div>)}
            </div>
        )
    }

}