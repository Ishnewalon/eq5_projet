import {Link, useLocation} from "react-router-dom";
import React from "react";
import {useAuth} from "../../hooks/use-auth";
import PropTypes from "prop-types";
import {BiLogIn, BiLogOut, GiHamburgerMenu} from "react-icons/all";
import style from "./Navbar.module.css";

export default function Navbar() {
    let location = useLocation();
    return (
        <nav className={`navbar navbar-expand-lg bg-light navbar-light shadow ${style.bar}`}>
            <div className={"container-fluid"}>
                <Link className={`navbar-brand font-monospace ${style.brand}`}
                      to={{pathname: "/", state: {from: location}}}>JI<span
                    className="color-emphasis-1">SOS</span></Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText"
                        aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                    <GiHamburgerMenu/>
                </button>
                <div className="navbar-collapse collapse justify-content-between" id="navbarText">
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
    const location = useLocation();
    const auth = useAuth();
    if (!auth.user)
        return <NavItemList>
            <Link to={{pathname: "/register", state: {from: location}}}>Créer un compte</Link>
        </NavItemList>
    return <>
        <NavItemList>
            <Link to={{pathname: "/dashboard", state: {from: location}}}>Compte
                ({auth.user.firstName})</Link>
        </NavItemList>
        <NavItemSpecificForUser/>

    </>;
}

function NavItemSpecificForUser() {
    let location = useLocation();
    let auth = useAuth();
    if (auth.isMonitor())
        return (
            <NavItemList>
                <Dropdown title="Offres">
                    <Link to={{pathname: "/dashboard/offres/ajouter", state: {from: location}}}>Ajouter des
                        offres</Link>
                    <Link to={{pathname: "/dashboard/applications", state: {from: location}}}>Applications</Link>
                </Dropdown>
                <Dropdown title="Contrats">
                    <Link to={{pathname: "/dashboard/voir/futures_stagiaires", state: {from: location}}}>Contrats à
                        signer</Link>
                    <Link to={{pathname: "/dashboard/monitor/contracts/signed", state: {from: location}}}>Contrats
                        signés</Link>
                </Dropdown>
                <Dropdown title='Évaluation stagiaire'>
                    <Link to={{pathname: "/dashboard/monitor/eval/create", state: {from: location}}}>Créer une évaluation de stagiaire</Link>
                    <Link to={{pathname: "/dashboard/monitor/eval/view", state: {from: location}}}>Voir mes évaluations de stagiaires</Link>
                </Dropdown>
            </NavItemList>
        )
    if (auth.isManager())
        return <NavItemList>
            <Dropdown title="Offres">
                <Link to={{pathname: "/dashboard/offres/ajouter", state: {from: location}}}>Ajouter des offres</Link>
                <Link to={{pathname: "/dashboard/offres/review", state: {from: location}}}>Valider offres</Link>
            </Dropdown>
            <Dropdown title="Étudiants">
                <Link to={{pathname: "/dashboard/students/applied", state: {from: location}}}>Associer
                    Superviseur</Link>
                <Link to={{pathname: "/dashboard/curriculum/review", state: {from: location}}}>Valider Curriculum</Link>
            </Dropdown>
            <Dropdown title="Contrats">
                <Link to={{pathname: "/dashboard/contrats/a_signer", state: {from: location}}}>Contrats à signer</Link>
                <Link to={{pathname: "/dashboard/students/start", state: {from: location}}}>Commencer signature</Link>
                <Link to={{pathname: "/dashboard/manager/contracts/signed", state: {from: location}}}>Contrats
                    signés</Link>
            </Dropdown>
            <Link to={{pathname: "/dashboard/session", state: {from: location}}}>Gestion des sessions</Link>
            <Link to={{pathname: "/dashboard/rapports", state: {from: location}}}>Rapports</Link>
        </NavItemList>
    if (auth.isStudent())
        return <NavItemList>
            <Dropdown title="Curriculum">
                <Link to={{pathname: "/dashboard/televerser", state: {from: location}}}>Téléverser un curriculum</Link>
                <Link to={{pathname: "/dashboard/mes_cv", state: {from: location}}}>Mes curriculums</Link>
            </Dropdown>
            <Link to={{pathname: "/dashboard/offres", state: {from: location}}}>Offres</Link>
            <Dropdown title="Applications">
                <Link to={{pathname: "/dashboard/view/status", state: {from: location}}}>Mes applications</Link>
                <Link to={{pathname: "/dashboard/offer/setdate", state: {from: location}}}>Ajouter une date
                    d'entrevue</Link>
            </Dropdown>
            <Dropdown title="Contrat">
                <Link to={{pathname: "/dashboard/voir_mon_contrat", state: {from: location}}}>Mon contrat</Link>
                <Link to={{pathname: "/dashboard/student/contract/signed", state: {from: location}}}>Mon contrat
                    signé</Link>
            </Dropdown>
        </NavItemList>
    if (auth.isSupervisor())
        return <NavItemList>
            <Dropdown title='Formulaire de visite'>
                <Link to={{pathname: "/dashboard/forms/supervisor/view", state: {from: location}}}>Voir mes formulaires</Link>
                <Link to={{pathname: "/dashboard/forms/supervisor/create", state: {from: location}}}>Créer un formulaire</Link>
            </Dropdown>
            <Link to={{pathname: "/dashboard/students/status", state: {from: location}}}>Status des étudiants</Link>
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
