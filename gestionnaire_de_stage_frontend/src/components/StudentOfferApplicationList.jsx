import React, {useEffect, useState} from 'react';
import {useAuth} from "../services/use-auth";
import {getStudentApplications, setApplicationsStatusWhenEnAttenteDeReponse} from "../services/offerAppService";
import {OfferApplicationStatus} from "../enums/OfferApplicationStatus";

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


    const updateStatus = (idOfferApp, status) => {
        setApplicationsStatusWhenEnAttenteDeReponse(idOfferApp, status).then((ok) => {
            if (ok)
                setOfferApplications((prevOffApp) => {
                    return prevOffApp.filter(offApp => offApp.id !== idOfferApp)
                })
        })
    };

    return (<>
        <h2 className="text-center mt-5 mb-3">Offer Applications</h2>
        <table className="table table-striped table-borderless text-center rounded shadow-lg">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Offer</th>
                <th scope="col">Status</th>
                <th scope="col">Déclaration</th>
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
                                    onClick={() => updateStatus(offerApplication.id, OfferApplicationStatus.STAGE_TROUVE)}>Stage
                                trouvé
                            </button>
                            <button className="btn btn-outline-danger"
                                    onClick={() => updateStatus(offerApplication.id, OfferApplicationStatus.STAGE_REFUSER)}>Refuser
                            </button>
                        </div>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    </>)
}
