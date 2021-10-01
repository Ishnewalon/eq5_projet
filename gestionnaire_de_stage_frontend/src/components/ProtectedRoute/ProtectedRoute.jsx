import React from "react";
import {Redirect, Route} from "react-router-dom";
import AuthService from "../../services/auth-service";

class ProtectedRoute extends React.Component {
    render() {
        const service = AuthService.getInstance();
        let {component: Component, isAuthenticated, ...restOfProps} = this.props;
        console.log(isAuthenticated)
        return (
            <Route
                {...restOfProps}
                render={(props) =>
                    service.isAuthenticated() === true ? <Component {...props} /> : <Redirect to='/login'/>
                }
            />
        );
    }
}

export default ProtectedRoute;
