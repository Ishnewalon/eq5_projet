import React from "react";
import MonitorView from "../MonitorView/MonitorView";
import ManagerView from "../ManagerView/ManagerView";
import StudentView from "../StudentView/StudentView";
import SupervisorView from "../SupervisorView/SupervisorView";
import {useAuth} from "../../services/use-auth";


export default function Dashboard() {
    let auth = useAuth();

    if (auth.isMonitor())
        return <MonitorView/>
    if (auth.isManager())
        return <ManagerView/>
    if (auth.isStudent())
        return <StudentView/>
    if (auth.isSupervisor())
        return <SupervisorView/>

    return <></>
}
