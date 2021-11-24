import {Link} from "react-router-dom";
import React from "react";
import {useAuth} from "../../services/use-auth";
import PropTypes from "prop-types";
import {BiLogIn, BiLogOut, GiHamburgerMenu} from "react-icons/all";


export default function Navbar() {
    return (
        <nav className="navbar navbar-expand-lg sticky-top bg-light navbar-light shadow-sm mb-4 no-select">
            <div className="container-fluid">
                <Link className="navbar-brand bold font-monospace" to="/">JI<span
                    className="color-emphasis-1">SOS</span></Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText"
                        aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                    <GiHamburgerMenu/>
                </button>
                <div className="collapse navbar-collapse justify-content-between" id="navbarText">
                    <ul className="navbar-nav">
                        <GetNavItems/>
                    </ul>
                    <LoginLogout/>
                </div>
            </div>
        </nav>
    );
}

function LoginLogout() {
    let auth = useAuth();
    let btn = (<BiLogIn title="Se connecter" size="25" className={"link-dark"}/>)
    if (auth.user)
        btn = (<BiLogOut title="Se deconnecter" size="25" className={"link-dark"}/>)

    const onClick = (e) => {
        if (auth.user) {
            e.preventDefault()
            auth.signOut();
        }
    };

    return <Link className="me-2 d-flex" onClick={onClick} to="/login">{btn}</Link>
}

function GetNavItems() {
    const auth = useAuth();
    if (!auth.user)
        return <NavItemList>
            <Link to="/register">Créer un compte</Link>
        </NavItemList>
    return <>
        <NavItemList>
            <Link to="/dashboard">Compte
                ({auth.user.firstName})</Link>
        </NavItemList>
        <NavItemSpecificForUser/>

    </>;
}

function NavItemSpecificForUser() {
    let auth = useAuth();
    if (auth.isMonitor())
        return (
            <NavItemList>
                <Dropdown title="Offres">
                    <Link to="/dashboard/offres/ajouter">Ajouter des offres</Link>
                    <Link to="/dashboard/applications">Applications</Link>
                </Dropdown>
                <Dropdown title="Contrats">
                    <Link to="/dashboard/voir/futures_stagiaires">Contrats à
                        valider</Link>
                    <Link to="/dashboard/monitor/contracts/signed">Contrats signés</Link>
                </Dropdown>
                <Link to="/dashboard/monitor_eval_stagiaire">Formulaire d'évaluation de stagiaire</Link>
            </NavItemList>
        )
    if (auth.isManager())
        return <NavItemList>
            <Dropdown title="Offres">
                <Link to="/dashboard/offres/ajouter">Ajouter des offres</Link>
                <Link to="/dashboard/offres/review">Valider offres</Link>
            </Dropdown>
            <Dropdown title="Étudiants">
                <Link to="/dashboard/curriculum/review">Valider Curriculum</Link>
                <Link to="/dashboard/students/applied">Associer Superviseur</Link>
            </Dropdown>
            <Dropdown title="Contrats">
                <Link to="/dashboard/contrats/a_signer">Contrats à valider</Link>
                <Link to="/dashboard/students/start">Commencer signature</Link>
                <Link to="/dashboard/manager/contracts/signed">Contrats signés</Link>
            </Dropdown>
            <Link to="/dashboard/session">Gestion des sessions</Link>
            <Link to="/dashboard/rapports">Rapports</Link>
        </NavItemList>
    if (auth.isStudent())
        return <NavItemList>
            <Dropdown title="Curriculum">
                <Link to="/dashboard/televerser">Téléverser un curriculum</Link>
                <Link to="/dashboard/mes_cv">Mes curriculum</Link>
            </Dropdown>
            <Link to="/dashboard/offres">Offres</Link>
            <Dropdown title="Applications">
                <Link to="/dashboard/view/status">Mes applications</Link>
                <Link to="/dashboard/offer/setdate">Ajouter une date d'entrevue</Link>
            </Dropdown>
            <Dropdown title="Contrat">
                <Link to="/dashboard/voir_mon_contrat">Mon contrats</Link>
                <Link to="/dashboard/student/contract/signed">Mon contrat signé</Link>
            </Dropdown>
        </NavItemList>
    if (auth.isSupervisor())
        return <NavItemList>
            <Link to="/dashboard/supervisor_form_visit_company">Formulaire de visite d'entreprise</Link>
            <Link to="/dashboard/students/status">Status des étudiants</Link>
        </NavItemList>
    return <></>
}

function NavItemList(props) {
    const {children} = props;
    return React.Children.map(children || [], child => {
            if (child.type.name === "Dropdown")
                return child
            return <li className="nav-item">{
                React.cloneElement(child, {
                    className: `${child.props.className ? child.props.className : ""} nav-link`
                })}
            </li>
        }
    );
}

NavItemList.propTypes = {
    children: PropTypes.node
};


function Dropdown(props) {
    const {children, title} = props;
    return (
        <li className="nav-item dropdown">
            <button className="nav-link dropdown-toggle link-button" data-bs-toggle="dropdown"
                    aria-expanded="false">{title}</button>
            <ul className="dropdown-menu">
                {
                    React.Children.map(children || [], child => {
                        return <li className="nav-item dropdown">{
                            React.cloneElement(child, {
                                className: `${child.props.className ? child.props.className : ""} dropdown-item`
                            })}
                        </li>
                    })
                }
            </ul>
        </li>
    )
}

Dropdown.propTypes = {
    children: PropTypes.node,
    title: PropTypes.string
};
