import React from "react";

export class RegisterGS extends React.Component {
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
        </div>;
    }
}
