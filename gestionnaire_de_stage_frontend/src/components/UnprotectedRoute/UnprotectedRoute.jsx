import React from "react";
import {Redirect, Route} from "react-router-dom";
import AuthService from "../../services/auth-service";

class UnprotectedRoute extends React.Component {
    render() {
        const servoioce = AuthService.getInstance();
        let {component: Component, isAuthenticated, ...restOfProps} = this.props;
        console.log(isAuthenticated)
        return (
            <Route
                {...restOfProps}
                render={(props) =>
                    servoioce.isAuthenticated() === true ? <Redirect to='/dashboard'/> : <Component {...props} />
                }
            />
        );
    }
}

export default UnprotectedRoute;
