import React, {useEffect, useState} from 'react';
import {useAuth} from "../services/use-auth";
import {getStudentApplications} from "../services/offerAppService";

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
    return (<>
        <h2 className="text-center">Offer Applications</h2>
        <table className="table table-striped table-borderless  rounded shadow-lg">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Offer</th>
                <th scope="col">Status</th>
            </tr>
            </thead>
            <tbody>
            {offerApplications.map(offerApplication => (
                <tr key={offerApplication.id}>
                    <th scope="row">{offerApplication.id}</th>
                    <td>{offerApplication.offer.title}</td>
                    <td>{offerApplication.status}</td>
                </tr>
            ))}
            </tbody>
        </table>
    </>)
}
