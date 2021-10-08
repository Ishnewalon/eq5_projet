import React, {Component} from "react";
import AuthService from "../../services/auth-service";
import Monitor from "../Monitor/Monitor";
import Manager from "../Manager/Manager";

export default class Dashboard extends Component {
    constructor(props) {
        super(props);
        this.service = AuthService
    }

    render() {
        return (<>
            {JSON.stringify(this.service.user)}
            {this.service.isMonitor() ? <Monitor/> : <></>}
            {this.service.isManager() ? <Manager/> : <></>}
            {/*{this.service.isStudent() ? <Monitor/> : <></>}*/}
            {/*{this.service.isSupervisor() ? <Monitor/> : <></>}*/}
        </>)
    }
}
