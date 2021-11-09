import './App.css';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Dashboard from "./components/Dashboard/Dashboard";
import Register from "./components/Register/Register";
import React from "react";
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/NavBar/NavBar";
import {AuthProvider, RequireAuth, RequireNoAuth} from "./services/use-auth";
import Login from "./components/Login/Login";
import {ContainerBox} from "./components/SharedComponents/ContainerBox/ContainerBox";

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
                            <ContainerBox>
                                <Register/>
                            </ContainerBox>
                        </RequireNoAuth>
                    </Route>
                    <Route path="/login">
                        <RequireNoAuth>
                            <ContainerBox>
                                <Login/>
                            </ContainerBox>
                        </RequireNoAuth>
                    </Route>
                </Switch>
            </div>
        </Router>
    </AuthProvider>
}

export default App;
