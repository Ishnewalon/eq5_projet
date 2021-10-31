import {Step, UserType} from "../Register";
import React from "react";
import FieldAddress from "../../Fields/FieldAddress";
import {toast} from "../../../utility";

const regexCodePostal = /^([A-Za-z]\s?[0-9]){3}$/;

export default function StepMonitor({
                                        prevStep,
                                        nextStep,
                                        updateUserType,
                                        handleChange,
                                        companyName,
                                        address,
                                        codePostal,
                                        city
                                    }) {

    const next = (val) => {
        updateUserType(UserType.MONITOR);
        nextStep(val);
    }
    const verification = () => {
        if (!companyName) {
            toast.fire({title: 'Nom de compagnie est vide', icon: 'error'}).then()
            return false
        }
        if (!city) {
            toast.fire({title: 'Nom de ville est vide', icon: 'error'}).then()
            return false
        }
        if (!address) {
            toast.fire({title: "L'adresse est vide", icon: 'error'}).then()
            return false
        }
        if (!codePostal) {
            toast.fire({title: 'Code postal est vide', icon: 'error'}).then()
            return false
        }
        if (!companyName.match(/^[a-zA-Z0-9]+$/)) {
            toast.fire({title: 'Nom de compagnie est invalide', icon: 'error'}).then()
            return false;
        }
        if (!city.match(/^[a-zA-Z]+$/)) {
            toast.fire({title: 'Nom de ville est invalide', icon: 'error'}).then()
            return false;
        }
        if (!regexCodePostal.test(codePostal)) {
            toast.fire({title: 'Code postal est invalide', icon: 'error'}).then()
            return false;
        }
        return true;
    }

    return (<>
        <div className="form-group row">
            <div className="col-md-6">
                <label>Nom de la compagnie</label>
                <div className="input-group">
                    <input name="companyName" placeholder="Nom de compagnie" className="form-control"
                           type="text"
                           value={companyName} onChange={handleChange('companyName')}/>
                </div>
            </div>
            <div className="col-md-6">
                <label>Ville</label>
                <div>
                    <div className="input-group">
                        <input name="city" placeholder="Ville" className="form-control" type="text"
                               value={city} onChange={handleChange('city')}/>
                    </div>
                </div>
            </div>
        </div>
        <div className="form-group">
            <FieldAddress label="Adresse de la compagnie" address={address} handleChange={handleChange('address')}/>
        </div>
        <div className="form-group">
            <label>Code Postale</label>
            <div className="input-group">
                <input name="codePostal" placeholder="XXX 123" className="form-control" type="text"
                       value={codePostal} onChange={handleChange('codePostal')}/>
            </div>
        </div>
        <div className="form-group text-center">
            <label/>
            <div>
                <button className="btn btn-primary" type={"button"} onClick={prevStep}>Précédent
                </button>
                <button className="btn btn-primary" type={"button"} onClick={() => {
                    if (verification()) next(Step.GENERAL)
                }}>Suivant
                </button>
            </div>
        </div>
    </>)

}
