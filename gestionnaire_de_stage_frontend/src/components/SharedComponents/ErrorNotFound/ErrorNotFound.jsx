import "./ErrorNotFound.css"
import React from "react"
import {useHistory} from "react-router-dom";

export default function ErrorNotFound() {
    const history = useHistory();
    return (<>
            <section id="not-found" className="mt-5">
                <div className="circles">
                    <p>404<br/>
                        <small>Page non trouvée</small>
                    </p>
                    <span className="circle big"/>
                    <span className="circle med"/>
                    <span className="circle small"/>
                        <button className="btn btn-primary" onClick={() => history.push("/login")}>
                            Retour à l'accueil
                        </button>
                </div>
            </section>
        </>
    );
}