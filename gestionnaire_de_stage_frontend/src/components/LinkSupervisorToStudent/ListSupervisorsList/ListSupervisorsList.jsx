import React, {Component} from "react";

export default class ListSupervisorsList extends Component {
   constructor(props) {
       super(props);
   }

    render() {
       const {key,supervisor} = this.props
        return (
                <option value={key}>{supervisor.lastName}, {supervisor.firstName}</option>
        );
    }
}