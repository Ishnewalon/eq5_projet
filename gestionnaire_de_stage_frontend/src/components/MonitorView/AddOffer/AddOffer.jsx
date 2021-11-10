import React, {useEffect, useState} from "react";
import FieldAddress from "../../SharedComponents/Fields/FieldAddress";
import {createOffer} from "../../../services/offer-service";
import {DepartmentEnum} from "../../../enums/Departement";
import OfferDTO from "../../../models/OfferDTO";
import {useAuth} from "../../../services/use-auth";
import {InputGroup} from "../../SharedComponents/InputGroup/InputGroup";
import {getCurrentAndFutureSession} from "../../../services/session-service";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";


export default function AddOffer() {//TODO select session
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
    useEffect(()=>{
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
        <div className="col-md-6">
            <label className="label">Email</label>
            <InputGroup>
                <input name="email" placeholder="Email" type="text"
                       value={creator_email} onChange={(e) => setCreatorId(e.target.value)}/>
            </InputGroup>
        </div>)

    return (<>
        <h2 className="text-center">Ajouter une offre de stage</h2>
        <div className="form-group row">
            <div className="col-md-6">
                <label className="label">Titre</label>
                <InputGroup>
                    <input name="title" placeholder="Titre" type="text"
                           value={title} onChange={(e) => setTitle(e.target.value)}/>
                </InputGroup>
            </div>
            <div className="col-md-6">
                <label className="label">Département</label>
                <InputGroup>
                    <select name="choice" id="userTypes"
                            onChange={(e) => setDepartement(e.target.value)}>
                        <option value={DepartmentEnum.info}>{DepartmentEnum.info}</option>
                        <option value={DepartmentEnum.art}>{DepartmentEnum.art}</option>
                    </select>
                </InputGroup>
            </div>
        </div>
        <FormGroup>
            <>
                <label className="label">Session</label>
                <InputGroup>
                    <select name="sessions" id="session"
                            onChange={(e) => setSessionId(e.target.value)}>
                        <option selected disabled>Choisisez une session</option>
                        {sessions.map(session => <option
                            value={session.id}>{session.typeSession + session.year}</option>)}
                    </select>
                </InputGroup>
            </>
        </FormGroup>
        <div className="form-group">
            <label className="label">Description</label>
            <InputGroup>
                <input name="description" placeholder="Description" type="text"
                       value={description} onChange={(e) => setDescription(e.target.value)}/>
            </InputGroup>
        </div>
        <div className="form-group">
            <FieldAddress label="Adresse ou le stage se situe" address={address}
                          handleChange={(e) => setAddress(e.target.value)}/>
        </div>
        <div className="form-group">
            <label className="label">Salaire</label>
            <InputGroup>
                <input name="salaire" placeholder="Salaire" type="number"
                       value={salary} onChange={(e) => setSalary(e.target.value)}/>
            </InputGroup>
        </div>
        {(auth.isManager()) ? monitorEmail : <></>}
        <div className="form-group text-center">
            <button className="btn btn-primary" type={"button"} onClick={addOffer}>Ajouter</button>
        </div>
    </>);
}

