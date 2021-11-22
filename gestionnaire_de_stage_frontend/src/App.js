import './App.css';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import Dashboard from "./components/UserView/Dashboard";
import Register from "./components/Unauthenticated/Register/Register";
import React from "react";
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar/Navbar";
import {AuthProvider, RequireAuth, RequireNoAuth} from "./services/use-auth";
import Login from "./components/Unauthenticated/Login";
import {ContainerBox} from "./components/SharedComponents/ContainerBox/ContainerBox";
import {Title} from "./components/SharedComponents/Title/Title";
import ForgotPassword from "./components/Unauthenticated/ForgotPassword";
import ResetPassword from "./components/Unauthenticated/ResetPassword";

function App() {

    return <AuthProvider>
        <Router>
            <Navbar/>
            <div className="container">
                <Switch>
                    <Route path="/dashboard">
                        <RequireAuth>
                            <Dashboard/>
                        </RequireAuth>
                    </Route>
                    <Route path="/register">
                        <RequireNoAuth>
                            <Title>Inscription</Title>
                            <ContainerBox>
                                <Register/>
                            </ContainerBox>
                        </RequireNoAuth>
                    </Route>
                    <Route path="/login">
                        <RequireNoAuth>
                            <Title>Se connecter</Title>
                            <Login/>
                        </RequireNoAuth>
                    </Route>
                    <Route exact path="/">
                        <Redirect to="/login"/>
                    </Route>
                    <Route path="/reset/:token">
                        <RequireNoAuth>
                            <Title>Réinitialiser votre mot de passe</Title>
                            <ResetPassword/>
                        </RequireNoAuth>
                    </Route>
                    <Route exact path="/forgot_password">
                        <RequireNoAuth>
                            <Title>Mot de passe oublié</Title>
                            <ForgotPassword/>
                        </RequireNoAuth>
                    </Route>
                </Switch>
            </div>
        </Router>
    </AuthProvider>
}

export default App;
