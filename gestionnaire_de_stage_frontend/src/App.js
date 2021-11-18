import './App.css';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import Dashboard from "./components/Dashboard/Dashboard";
import Register from "./components/Register/Register";
import React from "react";
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar/Navbar";
import {AuthProvider, RequireAuth, RequireNoAuth} from "./services/use-auth";
import Login from "./components/Login/Login";
import {ContainerBox} from "./components/SharedComponents/ContainerBox/ContainerBox";
import {Title} from "./components/SharedComponents/Title/Title";

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
                            <h2 className="text-center mt-4">Se connecter</h2>
                            <Login/>
                        </RequireNoAuth>
                    </Route>
                    <Route exact path="/">
                        <Redirect to="/login"/>
                    </Route>
                </Switch>
            </div>
        </Router>
    </AuthProvider>
}

export default App;
