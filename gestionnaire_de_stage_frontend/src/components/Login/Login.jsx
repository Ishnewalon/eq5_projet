import './Login.css'
import {Component} from "react";
import {UserType} from "../Register/Register";
import {signIn} from "../../services/auth-service";


export default class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
            userType: UserType.MONITOR
        }
    }

    handleChange = input => e => {
        e.preventDefault()
        this.setState({[input]: e.target.value});
    }
    clickyClick = (e) => {
        e.preventDefault()
        signIn(this.state.userType, this.state.email, this.state.password).then(value => {
            console.log(value)
            this.props.history.push('/dashboard')
        })
    }

    render() {
        return (<div>
                <div className="form-group">
                    <label>User Type</label>
                    <div className="input-group">
                        <select className="form-control" name="choice" id="userTypes"
                                onChange={this.handleChange('userType')}>
                            <option defaultChecked={true} value={UserType.MONITOR}>{UserType.MONITOR}</option>
                            <option value={UserType.SUPERVISOR}>{UserType.SUPERVISOR}</option>
                            <option value={UserType.STUDENT}>{UserType.STUDENT}</option>
                        </select>
                    </div>
                </div>
                <div className="form-group row">
                    <div className="col-md-6">
                        <label>Email</label>
                        <div className="input-group">
                            <input name="email" placeholder="Email" className="form-control" type="text"
                                   value={this.state.email} onChange={this.handleChange('email')}/>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <label>Password</label>
                        <div>
                            <div className="input-group">
                                <input name="password" placeholder="Votre mot de passe" className="form-control"
                                       type="password"
                                       value={this.state.password} onChange={this.handleChange('password')}/>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="form-group text-center">
                    <label/>
                    <button className="btn btn-primary" type={"button"} onClick={this.clickyClick}>Connexion</button>
                </div>
            </div>
        )
    }
}

