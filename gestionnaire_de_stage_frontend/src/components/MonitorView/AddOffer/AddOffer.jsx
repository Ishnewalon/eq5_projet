import React, {useEffect, useState} from "react";
import {FormField} from "../../SharedComponents/FormField/FormField";
import {getCurrentAndFutureSession} from "../../../services/session-service";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {DepartmentEnum} from "../../../enums/Departement";
import {useAuth} from "../../../services/use-auth";
import {createOffer} from "../../../services/offer-service";
import FieldAddress from "../../SharedComponents/Fields/FieldAddress";
import OfferDTO from "../../../models/OfferDTO";


export default function AddOffer() {
    let auth = useAuth();
    const [title, setTitle] = useState('')
    const [department, setDepartement] = useState(DepartmentEnum.info)
    const [description, setDescription] = useState('')
    const [address, setAddress] = useState('')
    const [salary, setSalary] = useState(0)
    const [creator_email, setCreatorId] = useState(auth.isMonitor() ? auth.user.email : '')
    const [session_id, setSessionId] = useState(null)
    const [sessions, setSessions] = useState([])

    const resetFields = () => {
        setTitle('')
        setDescription('')
        setDepartement(DepartmentEnum.info)
        setAddress('')
        setSalary(0)
        setCreatorId(auth.isMonitor() ? auth.user.email : '')
    };
    useEffect(() => {
        getCurrentAndFutureSession().then(res => {
            setSessions(res)
        })
    }, [])

    const addOffer = () => {
        let offer = new OfferDTO(title, department, description, address, salary, creator_email, session_id)
        createOffer(offer).then((b) => {
            if (b === null)
                return
            resetFields()
        })
    }

    const monitorEmail = (
        <FormField>
            <label>Email</label>
            <input name="email" placeholder="Email" type="text"
                   value={creator_email} onChange={(e) => setCreatorId(e.target.value)}/>
        </FormField>
    )

    return (<>
        <FormGroup>
            <FormField>
                <label>Titre</label>
                <input name="title" placeholder="Titre" type="text"
                       value={title} onChange={(e) => setTitle(e.target.value)}/>
            </FormField>
            <FormField>
                <label>Département</label>
                <select name="choice" id="userTypes"
                        onChange={(e) => setDepartement(e.target.value)}>
                    <option value={DepartmentEnum.info}>{DepartmentEnum.info}</option>
                    <option value={DepartmentEnum.art}>{DepartmentEnum.art}</option>
                </select>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField>
                <label className="label">Session</label>
                <select name="sessions" id="session"
                        onChange={(e) => setSessionId(e.target.value)} defaultValue="">
                    <option disabled value="">Choisisez une session</option>
                    {sessions.map(session =>
                        <option key={session.id} value={session.id}>{session.typeSession + session.year}</option>)}
                </select>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Description</label>
                <input name="description" placeholder="Description" type="text"
                       value={description} onChange={(e) => setDescription(e.target.value)}/>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FieldAddress label="Adresse ou le stage se situe" address={address}
                          handleChange={(e) => setAddress(e.target.value)}/>
        </FormGroup>
        <FormGroup>
            <FormField>
                <label>Salaire</label>
                <input name="salaire" placeholder="Salaire" type="number"
                       value={salary} onChange={(e) => setSalary(e.target.value)}/>
            </FormField>
            {(auth.isManager()) ? monitorEmail : <></>}
        </FormGroup>
        <div className="form-group text-center">
            <button className="btn btn-primary" type={"button"} onClick={addOffer}>Ajouter</button>
        </div>
    </>);
}

