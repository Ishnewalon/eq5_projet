import {Link} from "react-router-dom";
import React from "react";
import {useAuth} from "../../services/use-auth";


export default function Navbar() {
    return (
        <nav className="navbar navbar-expand-sm bg-dark">
            <div className="container-fluid">
                <ul className="navbar-nav">
                    <ThisIsAListItem>
                        <Link to="/">Accueil</Link>
                    </ThisIsAListItem>
                    <GetRestOfTheList/>
                </ul>
            </div>
        </nav>
    );
}

function GetRestOfTheList() {
    const auth = useAuth();
    if (!auth.user)
        return <ThisIsAListItem>
            <Link to="/login">Se connecter</Link>
            <Link to="/register">Créer un compte</Link>
        </ThisIsAListItem>
    return <>
        <ThisIsAListItem>
            <Link to="/dashboard">Compte
                ({auth.user.firstName})</Link>
        </ThisIsAListItem>
        <NavItemSpecificForUser/>
        <button className="fw-bold" onClick={() => auth.signOut()}>Se déconnecter</button>
    </>;
}

function NavItemSpecificForUser() {
    let auth = useAuth();
    if (auth.isMonitor())
        return (
            <ThisIsAListItem>
                <Link to="/dashboard/offres/ajouter">Ajouter des offres</Link>
                <Link to="/dashboard/applications">Applications</Link>
                <Link to="/dashboard/voir/futures_stagiaires">Contrats à
                    valider</Link>
                <Link to="/dashboard/monitor_form_visit_company">Formulaire de visite d'entreprise</Link>
            </ThisIsAListItem>
        )
    if (auth.isManager())
        return <ThisIsAListItem>
            <Link to="/dashboard/offres/ajouter">Ajouter des offres</Link>
            <Link to="/dashboard/offres/review">Validation Offre</Link>
            <Link to="/dashboard/curriculum/review">Valider Cv</Link>
            <Link to="/dashboard/students/start">Commencer signature</Link>
            <Link to="/dashboard/students/applied">Associer</Link>
            <Link to="/dashboard/contrats/a_signer">Contrats à valider</Link>
            <Link to="/dashboard/rapports">Rapports</Link>
        </ThisIsAListItem>
    if (auth.isStudent())
        return <ThisIsAListItem>
            <Link to="/dashboard/televerser">Téléverser CV</Link>
            <Link to="/dashboard/offres">Offres</Link>
            <Link to="/dashboard/voir_mon_contrat">Voir Contrat</Link>
            <Link to="/dashboard/view/status">Mes applications</Link>
            <Link to="/dashboard/offer/setdate">Ajouter une date d'entrevue</Link>
        </ThisIsAListItem>
    if (auth.isSupervisor())
        return <ThisIsAListItem>
            <Link to="/dashboard/supervisor_form_visit_company">Formulaire de visite d'entreprise</Link>
            <Link to="/dashboard/students/status">Status des étudiants</Link>
        </ThisIsAListItem>
    return <></>
}

function ThisIsAListItem({children}) {
    return React.Children.map(children, (child) => {
        return (<>
            <li className="nav-item">{
                React.cloneElement(child, {
                    className: `${child.props.className ? child.props.className : ""} nav-link text-white`
                })}
            </li>
        </>)
    });
}
