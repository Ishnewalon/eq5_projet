import {Component} from "react";
import {getCurriculumWithInvalidCV} from "../../services/curriculum-service";

export default class LinkSupervisorToStudent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            studentList: [],
            supervisorList: [],
        };

/*        getStudentsWithValidCV()
            .then(studentList => this.setState({studentList}))
            .catch(e => {
                this.setState({studentList: []})
                console.trace(e);
            });*/
        /*        getSupervisors()
            .then(supervisorList => this.setState({supervisorList}))
            .catch(e => {
                this.setState({supervisorList: []})
                console.trace(e);
            });*/
    }

    render() {
        return (
            <div className="container">
                <h2>Attribuer des superviseurs aux étudiants</h2>
                {this.state.studentList.map((student, index) =>
                <div>
                    <ul>
                        <li key={student.id}></li>
                    </ul>
                    {this.state.supervisorList.map((supervisor, index) =>
                    <div>
                        <select>
                            <div key={supervisor.id}></div>
                        </select>
                    </div>
                    )}
                </div>)}
            </div>
        )
    }

}