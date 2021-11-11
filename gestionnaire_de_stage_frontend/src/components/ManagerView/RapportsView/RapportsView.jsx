import {ContainerBox} from "../../SharedComponents/ContainerBox/ContainerBox";
import {Link} from "react-router-dom";
import "./RapportsView.css"

export default function RapportsView() {
    return (<>
            <h1 className={"text-center"}>Rapports:</h1>
            <ContainerBox>
                <Link className={"text-white text-center fst-italic"} to="/dashboard/rapports/1">
                    <h3>Rapport des offres validées</h3>
                </Link>
            </ContainerBox>

            <ContainerBox>
                <Link className={"text-white text-center fst-italic"} to="/dashboard/rapports/2">
                    <h3>Rapport des offres non validées</h3>
                </Link>
            </ContainerBox>

            <ContainerBox>
                <Link className={"text-white text-center fst-italic"} to="/dashboard/rapports/2">
                    <h3>Rapport des offres non valides</h3>
                </Link>
            </ContainerBox>


        </>

    )
}
