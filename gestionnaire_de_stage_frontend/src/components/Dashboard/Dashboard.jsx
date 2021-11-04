import React from "react";
import Monitor from "../Monitor/Monitor";
import Manager from "../Manager/Manager";
import StudentView from "../StudentView/StudentView";
import SupervisorView from "../SupervisorView/SupervisorView";
import {useAuth} from "../../services/use-auth";


export default function Dashboard() {
    let auth = useAuth();
    return (<>
        {auth.isMonitor() ? <Monitor/> : <></>}
        {auth.isManager() ? <Manager/> : <></>}
        {auth.isStudent() ? <StudentView/> : <></>}
        {auth.isSupervisor() ? <SupervisorView/> : <></>}
    </>)
}
