import React, {Component} from "react";

export class RegisterGS extends Component {
    render() {
        return <div className="container" >
            <form className="login-form" action=" " method="post" id="contact_form">
                <fieldset>

                    <legend>
                        <center><h2><b>Inscription</b></h2></center>
                    </legend>
                    <br />


                    <div className="form-group">
                        <label className="col-md-4 control-label">Prenom</label>
                        <div className="col-md-4 inputGroupContainer">
                            <div className="input-group">
                                <span className="input-group-addon"><i
                                    className="glyphicon glyphicon-user" /></span>
                                <input name="first_name" placeholder="First Name" className="form-control"
                                    type="text" />
                            </div>
                        </div>
                    </div>


                    <div className="form-group">
                        <label className="col-md-4 control-label">Nom</label>
                        <div className="col-md-4 inputGroupContainer">
                            <div className="input-group">
                                <span className="input-group-addon"><i
                                    className="glyphicon glyphicon-user" /></span>
                                <input name="last_name" placeholder="Last Name" className="form-control"
                                    type="text" />
                            </div>
                        </div>
                    </div>

                    <div className="form-group">
                        <label className="col-md-4 control-label">Votre rôle</label>
                        <div className="col-md-4 selectContainer">
                            <div className="input-group">
                                <span className="input-group-addon"><i
                                    className="glyphicon glyphicon-list" /></span>
                                <select name="department" className="form-control selectpicker">
                                    <option value="">Choissisez votre rôle</option>
                                    <option>Gestionnaire</option>
                                    <option>Étudiant</option>
                                    <option>Moniteur</option>
                                </select>
                            </div>
                        </div>
                    </div>


                    <div className="form-group">
                        <label className="col-md-4 control-label">Mot de passe</label>
                        <div className="col-md-4 inputGroupContainer">
                            <div className="input-group">
                                <span className="input-group-addon"><i
                                    className="glyphicon glyphicon-user" /></span>
                                <input name="user_password" placeholder="Password" className="form-control"
                                    type="password" />
                            </div>
                        </div>
                    </div>


                    <div className="form-group">
                        <label className="col-md-4 control-label">Confirmez votre mot de passe</label>
                        <div className="col-md-4 inputGroupContainer">
                            <div className="input-group">
                                <span className="input-group-addon"><i
                                    className="glyphicon glyphicon-user" /></span>
                                <input name="confirm_password" placeholder="Confirm Password"
                                    className="form-control"
                                    type="password" />
                            </div>
                        </div>
                    </div>

                    <div className="form-group">
                        <label className="col-md-4 control-label">E-Mail</label>
                        <div className="col-md-4 inputGroupContainer">
                            <div className="input-group">
                                <span className="input-group-addon"><i
                                    className="glyphicon glyphicon-envelope" /></span>
                                <input name="email" placeholder="E-Mail Address" className="form-control"
                                    type="text" />
                            </div>
                        </div>
                    </div>


                    <div className="form-group">
                        <label className="col-md-4 control-label">Téléphone</label>
                        <div className="col-md-4 inputGroupContainer">
                            <div className="input-group">
                                <span className="input-group-addon"><i
                                    className="glyphicon glyphicon-earphone" /></span>
                                <input name="contact_no" placeholder="123 123 1234" className="form-control" type="text" />
                            </div>
                        </div>
                    </div>


                    <div className="form-group">
                        <label className="col-md-4 control-label" />
                        <div
                            className="col-md-4">
                            <button type="submit"
                                className="btn btn-primary btn-ghost">S'inscrire
                                <span className="glyphicon glyphicon-send" />

                            </button>
                        </div>
                    </div>

                </fieldset>
            </form>
            <div>
                <form className="" action=" " method="post" id="contact_form">
                    <fieldset>

                        <legend>
                            <center><h2><b>Registration Form</b></h2></center>
                        </legend>
                        <br/>


                        <div className="form-group row">
                            <div className="col-6">
                                <label className="control-label">First Name</label>
                                <div className="inputGroupContainer">
                                    <div className="input-group">
                                            <span className="input-group-addon"><i
                                                className="glyphicon glyphicon-user"/></span>
                                        <input name="first_name" placeholder="First Name" className="form-control"
                                               type="text"/>
                                    </div>
                                </div>
                            </div>
                            <div className="col-6">
                                <label className="control-label">Last Name</label>
                                <div className="inputGroupContainer">
                                    <div className="input-group">
                                            <span className="input-group-addon"><i
                                                className="glyphicon glyphicon-user"/></span>
                                        <input name="last_name" placeholder="Last Name" className="form-control"
                                               type="text"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="form-group">
                            <label className="control-label">Department / Office</label>
                            <div className="selectContainer">
                                <div className="input-group">
                                            <span className="input-group-addon"><i
                                                className="glyphicon glyphicon-list"/></span>
                                    <select name="department" className="form-control selectpicker">
                                        <option value="">Select your Department/Office</option>
                                        <option>Department of Engineering</option>
                                        <option>Department of Agriculture</option>
                                        <option>Accounting Office</option>
                                        <option>Tresurer's Office</option>
                                        <option>MPDC</option>
                                        <option>MCTC</option>
                                        <option>MCR</option>
                                        <option>Mayor's Office</option>
                                        <option>Tourism Office</option>
                                    </select>
                                </div>
                            </div>
                        </div>


                        <div className="form-group">
                            <label className="control-label">Username</label>
                            <div className="inputGroupContainer">
                                <div className="input-group">
                                            <span className="input-group-addon"><i
                                                className="glyphicon glyphicon-user"/></span>
                                    <input name="user_name" placeholder="Username" className="form-control"
                                           type="text"/>
                                </div>
                            </div>
                        </div>


                        <div className="form-group">
                            <label className="control-label">Password</label>
                            <div className="inputGroupContainer">
                                <div className="input-group">
                                            <span className="input-group-addon"><i
                                                className="glyphicon glyphicon-user"/></span>
                                    <input name="user_password" placeholder="Password" className="form-control"
                                           type="password"/>
                                </div>
                            </div>
                        </div>


                        <div className="form-group">
                            <label className="control-label">Confirm Password</label>
                            <div className="inputGroupContainer">
                                <div className="input-group">
                                            <span className="input-group-addon"><i
                                                className="glyphicon glyphicon-user"/></span>
                                    <input name="confirm_password" placeholder="Confirm Password"
                                           className="form-control"
                                           type="password"/>
                                </div>
                            </div>
                        </div>

                        <div className="form-group">
                            <label className="control-label">E-Mail</label>
                            <div className="inputGroupContainer">
                                <div className="input-group">
                                            <span className="input-group-addon"><i
                                                className="glyphicon glyphicon-envelope"/></span>
                                    <input name="email" placeholder="E-Mail Address" className="form-control"
                                           type="text"/>
                                </div>
                            </div>
                        </div>


                        <div className="form-group">
                            <label className="col-md-4 control-label">Contact No.</label>
                            <div className="col-md-4 inputGroupContainer">
                                <div className="input-group">
                                            <span className="input-group-addon"><i
                                                className="glyphicon glyphicon-earphone"/></span>
                                    <input name="contact_no" placeholder="(639)" className="form-control" type="text"/>
                                </div>
                            </div>
                        </div>


                        <div className="alert alert-success" role="alert" id="success_message">Success <i
                            className="glyphicon glyphicon-thumbs-up"/> Success!.
                        </div>
                        <div className="form-group">
                            <label className="col-md-4 control-label"/>
                            <div
                                className="col-md-4">
                                <button type="submit"
                                        className="btn btn-primary">SUBMIT
                                    <span className="glyphicon glyphicon-send"/>
                                </button>
                                <a href="https://me.bertsa.ca" className="link">Forgot your password?</a>
                            </div>
                        </div>

                    </fieldset>
                </form>
            </div>
        </div>
            ;
    }
}
