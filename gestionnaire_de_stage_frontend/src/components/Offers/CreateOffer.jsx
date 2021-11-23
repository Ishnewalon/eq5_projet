import React, {useEffect, useState} from "react";
import {FormField} from "../SharedComponents/FormField/FormField";
import {getCurrentAndFutureSession} from "../../services/session-service";
import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {DepartmentEnum} from "../../enums/Departement";
import {useAuth} from "../../services/use-auth";
import {createOffer} from "../../services/offer-service";
import FieldAddress from "../SharedComponents/Fields/FieldAddress";
import OfferDTO from "../../models/OfferDTO";


export default function CreateOffer() {
    let auth = useAuth();
    const [title, setTitle] = useState('')
    const [department, setDepartement] = useState(DepartmentEnum.info)
    const [description, setDescription] = useState('')
    const [address, setAddress] = useState('')
    const [salary, setSalary] = useState(0)
    const [creator_email, setCreatorId] = useState(auth.isMonitor() ? auth.user.email : '')
    const [idSession, setSessionId] = useState(null)
    const [sessions, setSessions] = useState([])
    const [dateDebut, setDateDebut] = useState('');
    const [dateFin, setDateFin] = useState('');
    const [nbSemaine, setNbSemaine] = useState('');
    const [horaireTravail, setHoraireTravail] = useState('');
    const [nbHeureSemaine, setNbHeureSemaine] = useState('');

    const resetFields = () => {
        setTitle('')
        setDescription('')
        setDepartement(DepartmentEnum.info)
        setAddress('')
        setSalary(0)
        setCreatorId(auth.isMonitor() ? auth.user.email : '')
        setDateDebut('');
        setDateFin('');
        setNbSemaine('');
        setHoraireTravail('');
        setNbHeureSemaine('');
    };
    useEffect(() => {
        getCurrentAndFutureSession().then(sessions => setSessions(sessions))
    }, [])

    const addOffer = e => {
        e.preventDefault();
        let offer = new OfferDTO(title, department, description, address, salary, creator_email, nbSemaine, dateDebut, dateFin, horaireTravail, nbHeureSemaine, parseInt(idSession));
        createOffer(offer).then((b) => {
            if (b === null)
                return
            resetFields()
        })
    }

    const monitorEmail = (
        <FormField>
            <label>Email du moniteur</label>
            <input name="email" placeholder="Entrez l'email du moniteur" type="text"
                   value={creator_email} onChange={(e) => setCreatorId(e.target.value)}/>
        </FormField>
    )

    return (<>
        <FormGroup>
            <FormField>
                <label>Titre</label>
                <input name="title" placeholder="Titre" type="text"
                       value={title} onChange={e => setTitle(e.target.value)}/>
            </FormField>
            <FormField>
                <label>Département</label>
                <select name="choice" id="userTypes"
                        onChange={e => setDepartement(e.target.value)}>
                    <option value={DepartmentEnum.info}>{DepartmentEnum.info}</option>
                    <option value={DepartmentEnum.art}>{DepartmentEnum.art}</option>
                </select>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField>
                <label className="label">Session</label>
                <select name="sessions" id="session"
                        onChange={e => setSessionId(e.target.value)} defaultValue="">
                    <option disabled value="">Choisissez une session</option>
                    {sessions.map(session =>
                        <option key={session.id} value={session.id}>{session.typeSession + session.year}</option>)}
                </select>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Description</label>
                <input name="description" placeholder="Description" type="text"
                       value={description} onChange={e => setDescription(e.target.value)}/>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FieldAddress label="Adresse ou le stage se situe" address={address}
                          handleChange={e => setAddress(e.target.value)}/>
            <FormField>
                <label>Salaire</label>
                <input name="salary" placeholder="Salaire" type="number"
                       value={salary} onChange={e => setSalary(e.target.value)}/>
            </FormField>
        </FormGroup>
        <FormGroup>
            {(auth.isManager()) ? monitorEmail : <></>}
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Date de début</label>
                <input name="dateDebut" placeholder="Date de début" type="date"
                       value={dateDebut} onChange={(e) => setDateDebut(e.target.value)}/>
            </FormField>
            <FormField>
                <label>Date de fin</label>
                <input name="dateFin" placeholder="Date de fin" type="date"
                       value={dateFin} onChange={e => setDateFin(e.target.value)}/>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField htmlFor='nbSemaine'>
                <label>Nombre de semaines</label>
                <input name='nbSemaine' type="text" value={nbSemaine} placeholder='Nombre de semaines'
                       onChange={e => setNbSemaine(e.target.value)}/>
            </FormField>
            <FormField htmlFor='horaireTravail'>
                <label>Horaire de travail</label>
                <input name='horaireTravail' type="text" placeholder='Horaire de travail' value={horaireTravail}
                       onChange={e => setHoraireTravail(e.target.value)}/>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField htmlFor='nbHeureSemaine'>
                <label>Nombre de heures par semaine</label>
                <input name='nbHeureSemaine' type="text" placeholder='Nombre de heures par semaine' value={nbHeureSemaine} onChange={e => setNbHeureSemaine(e.target.value)}/>
            </FormField>
        </FormGroup>
        <div className="form-group text-center">
            <button className="btn btn-primary mt-3" onClick={addOffer}>Ajouter</button>
        </div>
    </>);
}

