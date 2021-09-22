import {Step} from "../Register";

// eslint-disable-next-line
const regexEmail = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
const regexPhone = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
export const step_InformationGeneral = ({prevStep, nextStep, handleChange, values}) => {

    const Previous = e => {
        e.preventDefault();
        prevStep();
    }
    const Continue = (val) => {
        nextStep(val);
    }

    function verification() {
        if (!values.first_name) {
            alert("firstname empty")
            return false
        }
        if (!values.last_name) {
            alert("lastname empty")
            return false
        }
        if (!values.email) {
            alert("email empty")
            return false
        }
        if (!values.phone) {
            alert("phone empty")
            return false
        }

        if (!values.first_name.match(/^[a-zA-Z]+$/)) {
            alert("firstname invalid")
            return false;
        }
        if (!values.last_name.match(/^[a-zA-Z]+$/)) {
            alert("lastname invalid")
            return false;
        }
        if (!regexEmail.test(values.email)) {
            alert("email invalid")
            return false;
        }
        if (!regexPhone.test(values.phone)) {
            alert("phone invalid")
            return false;
        }
        return true;
    }

    return (<div>
            <div className="form-group row">
                <div className="col-md-6">
                    <label>Prenom</label>
                    <div className="input-group">
                        <input name="first_name" placeholder="Prenom" className="form-control" type="text"
                               value={values.first_name} onChange={handleChange('first_name')}/>
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
            <div className="form-group">
                <label>E-Mail</label>
                <div className="input-group">
                    <input name="email" placeholder="Adresse E-mail" className="form-control" type="email"
                           value={values.email} onChange={handleChange("email")}/>
                </div>
            </div>
            <div className="form-group">
                <label>Téléphone</label>
                <div className="input-group">
                    <input name="contact_no" placeholder="000 000 000" className="form-control" type="tel"
                           value={values.phone} onChange={handleChange('phone')}/>
                </div>
            </div>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={Previous}>Precedent</button>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        if (verification())
                            Continue(Step.PASSWORD)
                    }}>Suivant
                    </button>
                </div>
            </div>
        </div>
    )
}
