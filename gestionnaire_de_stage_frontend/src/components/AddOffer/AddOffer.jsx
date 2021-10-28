import React, {useState} from "react";
import FieldAddress from "../Fields/FieldAddress";
import OfferService from "../../services/offer-service";
import {DepartmentEnum} from "../../enums/Departement";
import authService from "../../services/auth-service";
import OfferDTO from "../../models/OfferDTO";


export default function AddOffer() {
    const serviceOffer = OfferService
    const serviceAuth = authService
    const [title, setTitle] = useState('')
    const [department, setDepartement] = useState(DepartmentEnum.info)
    const [description, setDescription] = useState('')
    const [address, setAddress] = useState('')
    const [salary, setSalary] = useState(0)
    const [creator_email, setCreatorId] = useState(serviceAuth.isMonitor() ? serviceAuth.user.email : '')

    const addOffer = () => {
        let offer = new OfferDTO(title, department, description, address, salary, creator_email)
        serviceOffer.createOffer(offer).then()
    }
    const x = <div className="col-md-6">
        <label>Email</label>
        <div className="input-group">
            <input name="email" placeholder="Email" className="form-control" type="text"
                   value={creator_email} onChange={(e) => setCreatorId(e.target.value)}/>
        </div>
    </div>
    return (<>
        <h2 className="text-center">Ajouter une offre de stage</h2>
        <div className="form-group row">
            <div className="col-md-6">
                <label>Titre</label>
                <div>
                    <div className="input-group">
                        <input name="title" placeholder="Titre" className="form-control" type="text"
                               value={title} onChange={(e) => setTitle(e.target.value)}/>
                    </div>
                </div>
            </div>
            <div className="col-md-6">
                <label>Département</label>
                <div className="input-group">
                    <select className="form-control" name="choice" id="userTypes"
                            onChange={(e) => setDepartement(e.target.value)}>
                        <option defaultChecked={true} value={DepartmentEnum.info}>{DepartmentEnum.info}</option>
                        <option value={DepartmentEnum.art}>{DepartmentEnum.art}</option>
                    </select>
                </div>
            </div>
        </div>
        <div className="form-group">
            <label>Description</label>
            <div className="input-group">
                <input name="description" placeholder="Description" className="form-control" type="text"
                       value={description} onChange={(e) => setDescription(e.target.value)}/>
            </div>
        </div>
        <div className="form-group">
            <FieldAddress label="Adresse ou le stage se situe" address={address}
                          handleChange={(e) => setAddress(e.target.value)}/>
        </div>
        <div className="form-group">
            <label>Salaire</label>
            <div className="input-group">
                <input name="salaire" placeholder="Salaire" className="form-control" type="number"
                       value={salary} onChange={(e) => setSalary(e.target.value)}/>
            </div>
        </div>
        {(serviceAuth.isManager()) ? x : <></>}
        <div className="form-group text-center">
            <label/>
            <div>
                <button className="btn btn-primary" type={"button"} onClick={addOffer}>Ajouter</button>
            </div>
        </div>
    </>);
}

