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
                            <NavItemSpecificForUser/>
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

function NavItemSpecificForUser() {
    let auth = useAuth();
    if (auth.isMonitor())
        return (
            <>
                <li>
                    <Link className="nav-link" to="/dashboard/offres/ajouter">Ajouter des offres</Link>
                </li>
                <li>
                    <Link className="nav-link" to="/dashboard/applications">Applications</Link>
                </li>
            </>
        )
    if (auth.isManager())
        return (<>
            <li>
                <Link className="nav-link" to="/dashboard/offres/ajouter">Ajouter des offres</Link>
            </li>
            <li>
                <Link className="nav-link" to="/dashboard/offres">Offres valides</Link>
            </li>
            <li>
                <Link className="nav-link" to="/dashboard/offres/review">Validation Offre</Link>
            </li>
            <li>
                <Link className="nav-link" to="/dashboard/students/applied">Assossier</Link>
            </li>
        </>)
    if (auth.isStudent())
        return <>
            <li>
                <Link className="nav-link" to="/dashboard/televerser">Téléverser CV</Link>
            </li>
            <li>
                <Link className="nav-link" to="/dashboard/offres">Offres</Link>
            </li>
        </>
    return <></>
}
