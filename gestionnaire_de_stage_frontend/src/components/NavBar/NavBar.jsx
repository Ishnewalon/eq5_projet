import {Link} from "react-router-dom";
import React from "react";
import {useAuth} from "../../services/use-auth";

export default function Navbar() {
    const auth = useAuth();
    return (
        <nav className="navbar navbar-expand-sm bg-dark">
            <div className="container-fluid">
                <ul className="navbar-nav">
                    <li className="nav-item">
                        <Link className="nav-link" to="/">Accueil</Link>
                    </li>
                    {auth.user ? (<>
                            <li className="nav-item">
                                <Link className="nav-link" to="/dashboard">Compte ({auth.user.firstName})</Link>
                                <button onClick={() => auth.signOut()}>Se déconnecter</button>
                            </li>
                            {auth.isMonitor() || auth.isManager() ?
                                <li>
                                    <Link className="nav-link" to="/dashboard/offres/ajouter">Ajouter des offres</Link>
                                </li> : <></>}
                        </>
                    ) : (<>
                            <li className="nav-item">
                                <Link className="nav-link" to="/login">Se connecter</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/register">Créer un compte</Link>
                            </li>
                        </>
                    )}
                </ul>
            </div>
        </nav>
    );
}
