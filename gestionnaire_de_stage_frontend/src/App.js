import './App.css';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import Dashboard from "./components/UserView/Dashboard";
import React, {createElement} from "react";
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar/Navbar";
import {AuthProvider, RequireAuth, RequireNoAuth} from "./services/use-auth";
import Login from "./components/Unauthenticated/Login";
import {Title} from "./components/SharedComponents/Title/Title";
import Register from "./components/Unauthenticated/Register/Register";
import PropTypes from "prop-types";
import ForgotPassword from "./components/Unauthenticated/ForgotPassword";
import ResetPassword from "./components/Unauthenticated/ResetPassword";
import RegisterMonitor from "./components/Unauthenticated/Register/RegisterMonitor";

function App() {
    return <AuthProvider>
        <Router>
            <Navbar/>
            <div className="container">
                <RegisterMonitor/>
                <Switch>
                    <RequiredRoute path="/dashboard" component={RequireAuth}>
                        <Dashboard/>
                    </RequiredRoute>
                    <RequiredRoute exact path="/login" component={RequireNoAuth}>
                        <Title>Se connecter</Title>
                        <Login/>
                    </RequiredRoute>
                    <RequiredRoute exact={true} path="/register" component={RequireNoAuth}>
                        <Title>Inscription</Title>
                        <Register/>
                    </RequiredRoute>
                    <RequiredRoute path="/reset_password/:token" component={RequireNoAuth}>
                        <Title>Réinitialiser votre mot de passe</Title>
                        <ResetPassword/>
                    </RequiredRoute>
                    <RequiredRoute exact path="/forgot_password" component={RequireNoAuth}>
                        <Title>Mot de passe oublié</Title>
                        <ForgotPassword/>
                    </RequiredRoute>
                    <Route exact path="/404" component={NotFound}/>
                    <Redirect to="/404"/>
                </Switch>
            </div>
        </Router>
    </AuthProvider>
}

export default App;


function RequiredRoute(props) {
    const {exact, path, component, children} = props;

    return <Route exact={exact} path={path}>
        {createElement(component, {
            children: children
        })}
    </Route>
}

RequiredRoute.propTypes = {
    exact: PropTypes.bool,
    path: PropTypes.string.isRequired,
    component: PropTypes.elementType,
    children: PropTypes.node
};
RequiredRoute.defaultProps = {
    exact: false,
    component: () => null,
    children: null
};


//TODO: EMEME
function NotFound() {
    return (<>
            <Title>404</Title>
            <h1>Page not found</h1>
        </>
    )
}
