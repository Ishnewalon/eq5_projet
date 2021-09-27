import './App.css';
import Register from "./components/Register/Register";
import Login from "./components/Login/Login";
import {BrowserRouter as Router, Link, Route, Switch} from "react-router-dom";
import Dashboard from "./components/Dashboard/Dashboard";

function App() {
    return (
        <Router>
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
                </ul>
            </nav>
            <div className="container">
                <Switch>
                    <Route path="/login" component={Login}/>
                    <Route path="/register" component={Register}/>
                    <Route path="/dashboard" component={Dashboard}/>
                </Switch>
            </div>
        </Router>
    );
}

export default App;
