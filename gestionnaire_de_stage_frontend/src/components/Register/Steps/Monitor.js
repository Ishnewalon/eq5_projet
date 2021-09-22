import {Step, UserType} from "../Register";

const regexCodePostal = /^([A-Za-z]\s?[0-9]){3}$/;

function Monitor({prevStep, nextStep, updateUserType, handleChange, values}) {


    const Previous = e => {
        e.preventDefault();
        prevStep();
    }
    const Continue = val => {
        updateUserType(UserType.MONITOR);
        nextStep(val);
    }

    function verification() {
        if (!values.companyName) {
            alert("companyName empty")
            return false
        }
        if (!values.city) {
            alert("city empty")
            return false
        }
        if (!values.address) {
            alert("address empty")
            return false
        }
        if (!values.codePostal) {
            alert("codePostal empty")
            return false
        }

        if (!values.companyName.match(/^[a-zA-Z0-9]+$/)) {
            alert("companyName invalid")
            return false;
        }
        if (!values.city.match(/^[a-zA-Z]+$/)) {
            alert("city invalid")
            return false;
        }
        if (!regexCodePostal.test(values.codePostal)) {
            alert("codePostal invalid")
            return false;
        }
        return true;
    }

    return (<div>
            <div className="form-group row">
                <div className="col-md-6">
                    <label>Nom de la compagnie</label>
                    <div className="input-group">
                        <input name="companyName" placeholder="Nom de compagnie" className="form-control" type="text"
                               value={values.companyName} onChange={handleChange('companyName')}/>
                    </div>
                </div>
                <div className="col-md-6">
                    <label>Ville</label>
                    <div>
                        <div className="input-group">
                            <input name="city" placeholder="Ville" className="form-control" type="text"
                                   value={values.city} onChange={handleChange('city')}/>
                        </div>
                    </div>
                </div>
            </div>
            <div className="form-group">
                <label>Adresse de la compagnie</label>
                <div className="input-group">
                    <input name="address" placeholder="Rue, boulevard, avenue.." className="form-control" type="text"
                           value={values.address} onChange={handleChange("address")}/>
                </div>
            </div>
            <div className="form-group">
                <label>Code Postale</label>
                <div className="input-group">
                    <input name="codePostal" placeholder="XXX 123" className="form-control" type="text"
                           value={values.codePostal} onChange={handleChange('codePostal')}/>
                </div>
            </div>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={Previous}>Precedent</button>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        if (verification()) Continue(Step.GENERAL)
                    }}>Suivant
                    </button>
                </div>
            </div>
        </div>
    )
}

export default Monitor;
