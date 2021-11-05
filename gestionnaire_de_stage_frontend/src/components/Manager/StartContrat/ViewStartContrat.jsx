import React, {useEffect, useState} from 'react';
import {useAuth} from "../../../services/use-auth";
import {getAllOfferAppReadyToSign, startSignerFetch} from "../../../services/contrat-service";

export default function ViewStartContrat() {
    const [offerApplications, setOfferApplications] = useState([])
    let auth = useAuth();
    useEffect(() => {
        if (!auth.user) {
            setOfferApplications([])
            return
        }
        getAllOfferAppReadyToSign(auth.user.id).then(
            data => {
                setOfferApplications(data)
            })
    }, [auth.user])


    const startSigner = (idOfferApp, idManager) => {
        startSignerFetch(idOfferApp, idManager).then((ok) => {
            if (ok)
                setOfferApplications((prevOffApp) => {
                    return prevOffApp.filter(offApp => offApp.id !== idOfferApp)
                })
        })
    };

    return (<>
        <h1 className="text-center mt-5 mb-3">Liste des applications prêtes à être signer</h1>
        <table className="table table-light table-striped table-borderless text-center rounded-3 shadow-lg">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Offre</th>
                <th scope="col">Commencer le processus de signature</th>
            </tr>
            </thead>
            <tbody>
            {offerApplications.map(offerApplication => (
                <tr key={offerApplication.id}>
                    <th scope="row">{offerApplication.id}</th>
                    <td>{offerApplication.offer.title}</td>
                    <td>
                        <div className="btn-group">
                            <button className="btn btn-outline-success"
                                    onClick={() => startSigner(offerApplication.id, auth.user.id)}>Lancer
                            </button>
                        </div>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    </>)
}
