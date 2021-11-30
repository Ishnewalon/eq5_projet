import React from "react";
import MonitorView from "./MonitorView";
import ManagerView from "./ManagerView";
import StudentView from "./StudentView";
import SupervisorView from "./SupervisorView";
import {useAuth} from "../../hooks/use-auth";


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
