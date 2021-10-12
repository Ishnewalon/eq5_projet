import React,{Component} from "react";
import {FieldAddress} from "../Fields/FieldAddress";
import OfferService from "../../services/offer-service";
import OfferDTO from "../../models/OfferDTO";
import AuthService from "../../services/auth-service";
import {DepartmentEnum} from "../../enums/Departement";


export default class AddOffer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            title: '',
            department: DepartmentEnum.info,
            description: '',
            address: '',
            salary: 0,
            creator_id: 0,
        }
        this.service = OfferService
        this.authService = AuthService
    }

    addOffer = () => {
        const {title, department, description, address, salary} = this.state
        const id = this.authService.getUserId()

        let offer = new OfferDTO(title, department, description, address, salary, id)
        this.props.addOffer(offer)
    }

    handleChange = input => e => {
        this.setState({[input]: e.target.value});
    }


    render() {
        return (<>
            <h2 className="text-center">Ajouter une offre de stage</h2>
            <div className="form-group row">
                <div className="col-md-6">
                    <label>Titre</label>
                    <div>
                        <div className="input-group">
                            <input name="title" placeholder="Titre" className="form-control" type="text"
                                   value={this.state.title} onChange={this.handleChange('title')}/>
                        </div>
                    </div>
                </div>
                <div className="col-md-6">
                    <label>Département</label>
                    <div className="input-group">
                        <select className="form-control" name="choice" id="userTypes"
                                onChange={this.handleChange('department')}>
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
                           value={this.state.description} onChange={this.handleChange('description')}/>
                </div>
            </div>
            <div className="form-group">
                <FieldAddress label="Adresse ou le stage se situe" address={this.state.address}
                              handleChange={this.handleChange}/>
            </div>
            <div className="form-group">
                <label>Salaire</label>
                <div className="input-group">
                    <input name="description" placeholder="Description" className="form-control" type="number"
                           value={this.state.salary} onChange={this.handleChange('salary')}/>
                </div>
            </div>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={this.addOffer}>Ajouter</button>
                </div>
            </div>
        </>);
    }
}

