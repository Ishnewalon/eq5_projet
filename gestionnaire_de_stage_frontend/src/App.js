import './App.css';
import {BrowserRouter as Router, Link, Route, Switch} from "react-router-dom";
import TeleverserCv from "./components/TeleverserCv/TeleverserCv";
import UnprotectedRoute from "./components/UnprotectedRoute/UnprotectedRoute";
import ProtectedRoute from "./components/ProtectedRoute/ProtectedRoute";
import Dashboard from "./components/Dashboard/Dashboard";
import Register from "./components/Register/Register";
import Login from "./components/Login/Login";
import Monitor from "./components/Monitor/Monitor";

function App() {

    return <Router>
        <nav>
            <ul>
                <li>
                    <Link to="/">Accueil</Link>
                </li>
                <li>
                    <Link to="/login">Se connecter</Link>
                </li>
                <li>
                    <Link to="/register">Cree un compte</Link>
                </li>
                <li>
                    <Link to="/televerser-cv">Deposer cv</Link>
                </li>
                <li>
                    <Link to="/dashboard">Dash</Link>
                </li>
                <li>
                    <Link to="/monitor">Monitor</Link>
                </li>
            </ul>
        </nav>
        <div className="container">
            <Switch>

                <UnprotectedRoute exact path="/login"
                                  component={Login}/>
                <UnprotectedRoute exact path="/register"
                                  component={Register}/>
                <ProtectedRoute exact path="/dashboard"
                                component={Dashboard}/>
                <Route exact path="/televerser-cv" component={TeleverserCv}/>
                <Route path="/monitor" component={Monitor}/>
            </Switch>
        </div>
    </Router>

}

export default App;
