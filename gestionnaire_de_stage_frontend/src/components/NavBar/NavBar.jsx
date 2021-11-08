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
                        <Link className="nav-link text-white" to="/">Accueil</Link>
                    </li>
                    {auth.user ? (<>
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/dashboard">Compte
                                    ({auth.user.firstName})</Link>
                            </li>
                            <NavItemSpecificForUser/>
                            <button className="fw-bold" onClick={() => auth.signOut()}>Se déconnecter</button>
                        </>
                    ) : (<>
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/login">Se connecter</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/register">Créer un compte</Link>
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
                    <Link className="nav-link text-white" to="/dashboard/offres/ajouter">Ajouter des offres</Link>
                </li>
                <li>
                    <Link className="nav-link text-white" to="/dashboard/applications">Applications</Link>
                </li>
                <li>
                    <Link className="nav-link text-white" to="/dashboard/voir/futures_stagiaires">Contrats à
                        valider</Link>
                </li>
            </>
        )
    if (auth.isManager())
        return (<>
            <li>
                <Link className="nav-link text-white" to="/dashboard/offres/ajouter">Ajouter des offres</Link>
            </li>
            <li>
                <Link className="nav-link text-white" to="/dashboard/offres">Offres valides</Link>
            </li>
            <li>
                <Link className="nav-link text-white" to="/dashboard/offres/review">Validation Offre</Link>
            </li>
            <li>
                <Link className="nav-link text-white" to="/dashboard/curriculum/review">Valider Cv</Link>
            </li>
            <li>
                <Link className="nav-link text-white" to="/dashboard/students/start">Commencer signature</Link>
            </li>
            <li>
                <Link className="nav-link text-white" to="/dashboard/students/applied">Associer</Link>
            </li>
            <li>
                <Link className="nav-link text-white" to="/dashboard/contrats/a_signer">Contrats à valider</Link>
            </li>
            <li>
                <Link className="nav-link text-white" to="/dashboard/rapports">Rapports</Link>
            </li>
        </>)
    if (auth.isStudent())
        return <>
            <li>
                <Link className="nav-link text-white" to="/dashboard/televerser">Téléverser CV</Link>
            </li>
            <li>
                <Link className="nav-link text-white" to="/dashboard/offres">Offres</Link>
            </li>
            <li>
                <Link className="nav-link text-white" to="/dashboard/voir_mon_contrat">Voir Contrat</Link>
            </li>
            <li>
                <Link className="nav-link text-white" to="/dashboard/view/status">Mes applications</Link>
            </li>
            <li>
                <Link className="nav-link text-white" to="/dashboard/offer/setdate">Ajouter une date d'entrevue</Link>
            </li>
        </>
    if (auth.isSupervisor())
        return <></>
    return <></>
}
