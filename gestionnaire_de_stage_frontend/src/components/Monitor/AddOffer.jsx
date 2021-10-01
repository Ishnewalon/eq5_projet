import {Component} from "react";
import {FieldAddress} from "../Register/Steps/FieldAddress";

export const DepartmentEnum = {
    info: 'informatique',
    art: 'Art et Cinemas'
};
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
    }

    addStage = () => {


    }
    handleChange = input => e => {
        this.setState({[input]: e.target.value});
    }


    render() {
        return (<div>
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
                    <label>Departement</label>
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
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={this.addStage}>Ajouter</button>
                </div>
            </div>
        </div>);
    }
}
