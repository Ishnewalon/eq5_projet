import {Component} from "react";
import {user} from "../../services/auth-service";

export default class Dashboard extends Component {

    render() {
        console.log(user)
        return (<div>
            <p>Allo</p>
            {JSON.stringify(user)}
        </div>)
    }
}
