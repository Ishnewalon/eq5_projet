import {Link} from "react-router-dom";
import React from "react";

export default function RapportsView() {
    return (<>
            <h1 className={"text-center"}>Rapports:</h1>
            <CardsView>
                <ButtonBlock title="Rapport des offres validées"
                             description="Ma description"
                             to="/dashboard/rapports/1"/>
                <ButtonBlock title="Rapport des offres non validées"
                             description="Ma description"
                             to="/dashboard/rapports/2"/>
                <ButtonBlock title="Rapport des étudiants sans Cv"
                             description="Ma description"
                             to="/dashboard/rapports/3"/>
                <ButtonBlock title="Rapport des étudiants inscrits dans la plateforme"
                             description="Ma description"
                             to="/dashboard/rapports/4"/>
                <ButtonBlock title="Rapport des étudiants avec des Cv Invalides"
                             description="Ma description"
                             to="/dashboard/rapports/5"/>
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

function ButtonBlock({title, description, to}) {
    return (
        <div className="card mb-3 bg-light">
            <div className="card-body">
                <h5 className="card-title">{title}</h5>
                <p className="card-text">{description}</p>
                <Link className={"btn btn-primary btn-block float-end"} to={to}>Ouvrir</Link>
            </div>
        </div>
    )
}
