import React, {Component} from "react";
import AuthService from "../../services/auth-service";
import Monitor from "../Monitor/Monitor";
import Manager from "../Manager/Manager";
import StudentView from "../StudentView/StudentView";

function SupervisorView() {
    return (<div className="container bg-dark px-3 py-4 rounded shadow-lg mt-5">
        <h2>Vous êtes connecté!</h2>
    </div>);
}

export default class Dashboard extends Component {
    constructor(props) {
        super(props);
        this.service = AuthService
    }

    render() {
        return (<>
            {/*{JSON.stringify(this.service.user)}*/}
            {this.service.isMonitor() ? <Monitor/> : <></>}
            {this.service.isManager() ? <Manager/> : <></>}
            {this.service.isStudent() ? <StudentView/> : <></>}
            {this.service.isSupervisor() ? <SupervisorView/> : <></>}
        </>)
    }
}
