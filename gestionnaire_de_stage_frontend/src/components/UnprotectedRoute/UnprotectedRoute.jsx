import React from "react";
import {Redirect, Route} from "react-router-dom";
import AuthService from "../../services/auth-service";

export default function UnprotectedRoute(props) {
    let {component: Component, ...restOfProps} = props;
    return (
        <Route
            {...restOfProps}
            render={(props) =>
                AuthService.isAuthenticated() === true ? <Redirect to='/dashboard'/> : <Component {...props} />
            }
        />
    );
}
