import {Link} from "react-router-dom";
import React from "react";

export default function RapportsView() {
    return (<>
            <h1 className={"text-center"}>Rapports:</h1>
            <div className="row">
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
            </div>
        </>

    )
}

// function ddd(props) {
//     let leee
//     let reactPortals = React.Children.toArray(props.children);
//     for (let i = 0; i < reactPortals.length; i+=2) {
//         let portal = reactPortals[i];
//         if ()
//         let portal2 = reactPortals[i+1];
//         leee.push (<>
//                 <div className="col-md-6">
//                     {portal}
//                 </div>
//                 <div className="col-md-6">
//                     {portal2}
//                 </div>
//             </>
//         )
//     }
//
//     }
// }

function ButtonBlock({title, description, to}) {
    return (
        <div className="col-sm-6">
            <div className="card mb-3 bg-light">
                <div className="card-body">
                    <h5 className="card-title">{title}</h5>
                    {/*<h6 className="card-subtitle mb-2 text-muted">Card subtitle</h6>*/}
                    <p className="card-text">{description}</p>
                    <Link className={"btn btn-primary btn-block float-end"} to={to}>Ouvrir</Link>
                </div>
            </div>
        </div>
    )
}
