import React from "react";
import AuthService from "../../services/auth-service";
import Monitor from "../Monitor/Monitor";
import Manager from "../Manager/Manager";
import StudentView from "../StudentView/StudentView";
import SupervisorView from "../SupervisorView/SupervisorView";



export default function Dashboard() {
    const service = AuthService

    return (<>
        {service.isMonitor() ? <Monitor/> : <></>}
        {service.isManager() ? <Manager/> : <></>}
        {service.isStudent() ? <StudentView/> : <></>}
        {service.isSupervisor() ? <SupervisorView/> : <></>}
    </>)
}
