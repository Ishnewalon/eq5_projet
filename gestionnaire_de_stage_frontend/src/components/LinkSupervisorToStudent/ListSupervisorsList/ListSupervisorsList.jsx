import React, {Component} from "react";

export default class ListSupervisorsList extends Component {
   constructor(props) {
       super(props);
   }

    render() {
       const {supervisor} = this.props
        return (
                <option value={supervisor.id}>{supervisor.lastName}, {supervisor.firstName}</option>
        );
    }
}