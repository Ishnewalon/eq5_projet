import './Login.css'

function Login({prevStep, nextStep, handleChange, values}) {

    const Continue = (val) => {
        nextStep(val);
    }

    function verification() {

    }

    return (<div>
            <div className="form-group row">
                <div className="col-md-6">
                    <label>Matricule</label>
                    <div className="input-group">
                        <input name="matricule" placeholder="Matricule" className="form-control" type="text"
                               value={values.matricule} onChange={handleChange('first_name')}/>
                    </div>
                </div>
                <div className="col-md-6">
                    <label>Nom</label>
                    <div>
                        <div className="input-group">
                            <input name="last_name" placeholder="Nom" className="form-control" type="text"
                                   value={values.last_name} onChange={handleChange('last_name')}/>
                        </div>
                    </div>
                </div>
            </div>
            <div className="form-group text-center">
                <label/>
                    <button className="btn btn-primary" type={"button"} >Connexion</button>
                </div>
            </div>
    )
}

export default Login;
