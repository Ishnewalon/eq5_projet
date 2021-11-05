import React from "react";
import Monitor from "../Monitor/Monitor";
import Manager from "../Manager/Manager";
import StudentView from "../StudentView/StudentView";
import SupervisorView from "../SupervisorView/SupervisorView";
import {useAuth} from "../../services/use-auth";


export default function Dashboard() {
    let auth = useAuth();

    if (auth.isMonitor())
        return <Monitor/>
    if (auth.isManager())
        return <Manager/>
    if (auth.isStudent())
        return <StudentView/>
    if (auth.isSupervisor())
        return <SupervisorView/>

    return <></>
}
