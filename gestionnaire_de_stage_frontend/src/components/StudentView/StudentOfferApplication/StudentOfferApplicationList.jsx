import React, {useEffect, useState} from 'react';
import {useAuth} from "../../../services/use-auth";
import {getStudentApplications, setApplicationsStatusWhenEnAttenteDeReponse} from "../../../services/offerAppService";

export default function StudentOfferApplicationList() {
    const [offerApplications, setOfferApplications] = useState([])
    let auth = useAuth();
    useEffect(() => {
        if (!auth.user) {
            setOfferApplications([])
            return
        }
        getStudentApplications(auth.user.id).then(
            data => {
                setOfferApplications(data)
            })
    }, [auth.user])


    const updateStatus = (idOfferApp, isAccepted) => {
        setApplicationsStatusWhenEnAttenteDeReponse(idOfferApp, isAccepted).then((ok) => {
            if (ok)
                setOfferApplications((prevOffApp) => {
                    return prevOffApp.filter(offApp => offApp.id !== idOfferApp)
                })
        })
    };

    return (<>
        <h1 className="text-center mt-5 mb-3">Les status de mes applications</h1>
        <table className="table table-light table-striped table-borderless text-center rounded-3 shadow-lg">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Offre</th>
                <th scope="col">Status</th>
                <th scope="col">Changement de mon status</th>
            </tr>
            </thead>
            <tbody>
            {offerApplications.map(offerApplication => (
                <tr key={offerApplication.id}>
                    <th scope="row">{offerApplication.id}</th>
                    <td>{offerApplication.offer.title}</td>
                    <td>{offerApplication.status}</td>
                    <td>
                        <div className="btn-group">
                            <button className="btn btn-outline-success"
                                    onClick={() => updateStatus(offerApplication.id, true)}>Trouvé
                            </button>
                            <button className="btn btn-outline-danger"
                                    onClick={() => updateStatus(offerApplication.id, false)}>Refusé
                            </button>
                        </div>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    </>)
}
