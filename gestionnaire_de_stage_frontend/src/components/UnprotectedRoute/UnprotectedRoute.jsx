import React from "react";
import {Redirect, Route} from "react-router-dom";
import AuthService from "../../services/auth-service";

class UnprotectedRoute extends React.Component {
    render() {
        const authService = AuthService;
        let {component: Component, ...restOfProps} = this.props;
        return (
            <Route
                {...restOfProps}
                render={(props) =>
                    authService.isAuthenticated() === true ? <Redirect to='/dashboard'/> : <Component {...props} />
                }
            />
        );
    }
}

export default UnprotectedRoute;
