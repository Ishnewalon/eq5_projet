import React,{Component} from "react";

export class FieldPassword extends Component {
    render() {
        return (<>
            <label>{this.props.label}</label>
            <div>
                <div className="input-group">
                    <input name="password" placeholder={this.props.placeholder} className="form-control"
                           type="password"
                           value={this.props.password} onChange={this.props.handleChange}/>
                </div>
            </div>
        </>);
    }
}
