import React,{Component} from "react";

export class FieldAddress extends Component {
    render() {
        return (<>
            <label>{this.props.label}</label>
            <div className="input-group">
                <input name="address" placeholder="Rue, boulevard, avenue.." className="form-control"
                       type="text"
                       value={this.props.address} onChange={this.props.handleChange("address")}/>
            </div>
        </>);
    }
}
