import React from "react";
import {Redirect, Route} from "react-router-dom";
import AuthService from "../../services/auth-service";

class ProtectedRoute extends React.Component {

    constructor(props) {
        super(props);
        this.service = AuthService
    }

    render() {
        const service = AuthService;
        let {component: Component, ...restOfProps} = this.props;
        return (
            <Route
                {...restOfProps}
                render={(props) =>
                    service.isAuthenticated() === true ? <Component {...props} /> : <Redirect to='/login'/>
                }>
            </Route>
        );
    }
}

export default ProtectedRoute;
