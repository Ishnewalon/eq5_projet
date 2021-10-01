import {Component} from "react";
import AuthService from "../../services/auth-service";

export default class Dashboard extends Component {
    constructor(props) {
        super(props);
        this.service = AuthService
    }

    render() {
        console.log(this.service.user)
        return (<div>
            <p>Allo</p>
            {JSON.stringify(this.service.user)}
        </div>)
    }
}
