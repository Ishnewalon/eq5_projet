import {Link} from "react-router-dom";
import React from "react";

export default function RapportsView() {
    return (<>
            <CardsView>
                <RapportCard title="Rapport des offres validées"
                             description="Liste de toutes les offres enregistrées et que vous avez validées"
                             to="/dashboard/rapports/1"/>
                <RapportCard title="Rapport des offres non validées"
                             description="Liste de toutes les offres enregistrées et refusées"
                             to="/dashboard/rapports/2"/>
                <RapportCard title="Rapport des étudiants sans Cv"
                             description="Liste des étudiants inscrits et qui n'ont pas encore téleverser de Cv"
                             to="/dashboard/rapports/3"/>
                <RapportCard title="Rapport des étudiants inscrits dans la plateforme"
                             description="Liste de TOUS les étudiants inscrits"
                             to="/dashboard/rapports/4"/>
                <RapportCard title="Rapport des étudiants avec des Cv Invalides"
                             description="Liste des étudiants inscrits avec des cv invalides
                             (Notifiez-les)"
                             to="/dashboard/rapports/5"/>
                <RapportCard title="Rapport des étudiants pas évalués par le moniteur"
                             description="Liste des étudiants qui ne sont pas encore évalués par le
                             moniteur suite à leur stage"
                             to="/dashboard/rapports/7"/>
            </CardsView>
        </>
    )
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
