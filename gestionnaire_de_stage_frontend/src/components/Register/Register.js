import './Register.css'
import React, {Component} from "react";

export class Register extends Component {
    state = {
        step: 1,
        email: '',
        username: '',
        password: '',
        nom: '',
        prenom: '',
    }
    prevStep = () => {
        const {step} = this.state;
        this.setState({step: step - 1});
    }

    nextStep = () => {
        const {step} = this.state;
        this.setState({step: step + 1});
    }
    handleChange = input => e => {
        this.setState({[input]: e.target.value});
    }

    render() {
        const {step} = this.state;
        const {email, username, password, firstName, lastName, country, levelOfEducation} = this.state;
        const values = {email, username, password, firstName, lastName, country, levelOfEducation}
        return <div>{step}{email}
            <InformationGeneral nextStep={this.nextStep} prevStep={this.prevStep} handleChange={this.handleChange}
                                values={values}/>
        </div>;

        // return <div className="form-container">
        //     <form className="bg-dark px-3 py-4 rounded shadow-lg mt-5" action=" " method="post" id="contact_form">
        //         <fieldset>
        //             <legend>
        //                 <center><h2>Inscription</h2></center>
        //             </legend>
        //             <br/>
        //             <div className="form-group row">
        //                 <div className="col-md-6">
        //                     <label>Prenom</label>
        //                     <div className="input-group">
        //                         <input name="first_name" placeholder="First Name" className="form-control"
        //                                type="text"/>
        //                     </div>
        //                 </div>
        //                 <div className="col-md-6">
        //                     <label>Nom</label>
        //                     <div>
        //                         <div className="input-group">
        //                             <input name="last_name" placeholder="Last Name" className="form-control"
        //                                    type="text"/>
        //                         </div>
        //                     </div>
        //                 </div>
        //             </div>
        //             <div className="form-group">
        //                 <label>Votre rôle</label>
        //                 <div>
        //                     <div className="input-group">
        //                         <select name="department" className="form-control">
        //                             <option value="">Choissez votre rôle</option>
        //                             <option>Gestionnaire</option>
        //                             <option>Étudiant</option>
        //                             <option>Moniteur</option>
        //                         </select>
        //                     </div>
        //                 </div>
        //             </div>
        //             <div className="form-group row">
        //                 <div className="col-md-6">
        //                     <label>Mot de passe</label>
        //                     <div className="input-group">
        //                         <input name="user_password" placeholder="Password" className="form-control"
        //                                type="password"/>
        //                     </div>
        //                 </div>
        //                 <div className="col-md-6">
        //                     <label className="control-label">Confirmez votre mot de passe</label>
        //                     <div className="input-group">
        //                         <input name="confirm_password" placeholder="Confirmez votre mot de passe"
        //                                className="form-control"
        //                                type="password"/>
        //                     </div>
        //                 </div>
        //             </div>
        //             <div className="form-group">
        //                 <label>E-Mail</label>
        //                 <div className="input-group">
        //                     <input name="email" placeholder="E-Mail Address" className="form-control"
        //                            type="text"/>
        //                 </div>
        //             </div>
        //             <div className="form-group">
        //                 <label>Téléphone</label>
        //                 <div className="input-group">
        //                     <input name="contact_no" placeholder="000 000 000" className="form-control"
        //                            type="text"/>
        //                 </div>
        //             </div>
        //             <div className="form-group text-center">
        //                 <label/>
        //                 <div>
        //                     <button type="submit"
        //                             className="btn btn-primary">S'inscrire
        //                     </button>
        //                 </div>
        //             </div>
        //         </fieldset>
        //     </form>
        // </div>;
    }
}

const InformationGeneral = ({prevStep, nextStep, handleChange, values}) => {


    const Continue = e => {
        e.preventDefault();
        nextStep();
    }
    const Previous = e => {
        e.preventDefault();
        prevStep();
    }

    return (<div className="form-container">
        <form className="bg-dark px-3 py-4 rounded shadow-lg mt-5" id="contact_form">
            <fieldset>
                <legend>
                    <center><h2>Inscription</h2></center>
                </legend>
                <br/>
                <div className="form-group row">
                    <div className="col-md-6">
                        <label>Prenom</label>
                        <div className="input-group">
                            <input name="first_name" placeholder="First Name" className="form-control"
                                   type="text"
                                   value={values.prenom}
                                   onChange={handleChange('prenom')}
                            />
                        </div>
                    </div>
                    <div className="col-md-6">
                        <label>Nom</label>
                        <div>
                            <div className="input-group">
                                <input name="last_name" placeholder="Last Name" className="form-control"
                                       type="text"
                                       value={values.nom}
                                       onChange={handleChange('nom')}
                                />
                            </div>
                        </div>
                    </div>
                </div>
                <div className="form-group">
                    <label>E-Mail</label>
                    <div className="input-group">
                        <input name="email" placeholder="E-Mail Address" className="form-control"
                               type="text"
                               value={values.email}
                               onChange={handleChange("email")}/>
                    </div>
                </div>
                <div className="form-group">
                    <label>Téléphone</label>
                    <div className="input-group">
                        <input name="contact_no" placeholder="000 000 000" className="form-control"
                               type="text"
                               value={values.tel}
                               onChange={handleChange('tel')}/>
                    </div>
                </div>
                <div className="form-group text-center">
                    <label/>
                    <div>
                        <button className="btn btn-primary" type={"button"} onClick={Previous}>prev</button>
                        <button className="btn btn-primary" type={"button"} onClick={Continue}>next</button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>)


}
