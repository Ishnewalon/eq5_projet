import {Link, Route, useLocation, useRouteMatch} from "react-router-dom";
import React from "react";
import {Title} from "../SharedComponents/Title/Title";
import OfferApplicationsList from "../OfferApplications/OfferApplicationsList";
import OffersListValid from "../Offers/OffersListValid";
import OffersListNotValid from "../Offers/OffersListNotValid";
import StudentsStatus from "./StudentManagement/StudentsStatus";
import StudentsNotYetEvaluated from "./StudentManagement/StudentsNotYetEvaluated";
import StudentsWithCompanyNotYetEvaluated from "./StudentManagement/StudentsWithCompanyNotYetEvaluated";


export default function RapportsView() {
    const {path} = useRouteMatch();

    return (<>
            <Route exact path={path}>
                <Menu/>
            </Route>
            <Route exact path={`${path}/1`}>
                <Title>Offres de Stage</Title>
                <OffersListValid/>
            </Route>
            <Route exact path={`${path}/2`}>
                <Title>Offres de Stage non validées</Title>
                <OffersListNotValid/>
            </Route>
            <Route exact path={`${path}/3`}>
                <Title>Status de tous les étudiants</Title>
                <StudentsStatus/>
            </Route>
            <Route exact path={`${path}/4`}>
                <Title>Les étudiants pas encore évalués</Title>
                <StudentsNotYetEvaluated/>
            </Route>
            <Route exact path={`${path}/5`}>
                <Title>Les étudiants dont la compagnie n'a pas encore été évaluée</Title>
                <StudentsWithCompanyNotYetEvaluated/>
            </Route>
            <Route exact path={`${path}/offer`}>
                <OfferApplicationsList/>
            </Route>
        </>
    )
}

function Menu() {
    const location = useLocation();

    return <>
        <Title>Rapports</Title>
        <CardsView>
            <RapportCard title="Rapport des offres validées"
                         description="Liste de toutes les offres enregistrées et que vous avez validées"
                         to={{pathname:"/dashboard/rapports/1", state:{from:location}}}/>
            <RapportCard title="Rapport des offres non validées"
                         description="Liste de toutes les offres enregistrées et pas encore validées"
                         to={{pathname:"/dashboard/rapports/2", state:{from:location}}}/>
            <RapportCard title="Rapport des étudiants avec leur status"
                         description="Liste des étudiants qui ont appliqué au moins 1 fois"
                         to={{pathname:"/dashboard/rapports/3", state:{from:location}}}/>
            <RapportCard title="Rapport des étudiants pas évalués par le moniteur"
                         description="Liste des étudiants qui ne sont pas encore évalués par le
                             moniteur suite à leur stage"
                         to={{pathname:"/dashboard/rapports/4", state:{from:location}}}/>
            <RapportCard title="Rapport des étudiants dont la compagnie n'est pas évalués"
                         description="Liste des étudiants dont la compagnie n'a pas encore été évaluées par le superviseur"
                         to={{pathname:"/dashboard/rapports/5", state:{from:location}}}/>
        </CardsView>
    </>;
}

function CardsView(props) {
    let myColumn = []
    let myChildren = React.Children.toArray(props.children) || [];
    let lastChild
    if (myChildren.length % 2)
        lastChild = myChildren.pop();
    for (let i = 0; i < myChildren.length; i++)
        myColumn.push(
            <div className="col-sm-6" key={i}>
                {myChildren[i]}
            </div>)

    if (lastChild)
        myColumn.push(
            <div className="col-sm-6 mx-auto" key={myChildren.length + 1}>
                {lastChild}
            </div>)

    return (
        <div className="row">
            {myColumn}
        </div>
    )
}

function RapportCard({title, description, to}) {
    return (
        <div className="card mb-3 bg-white shadow-lg">
            <div className="card-body">
                <h5 className="card-title">{title}</h5>
                <p className="card-text">{description}</p>
                <Link className={"btn btn-primary btn-block float-end"} to={to}>Ouvrir</Link>
            </div>
        </div>
    )
}
