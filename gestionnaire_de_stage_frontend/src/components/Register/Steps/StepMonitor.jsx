import React from "react";
import FieldAddress from "../../SharedComponents/Fields/FieldAddress";
import {toastErr} from "../../../utility";
import {UserType} from "../../../enums/UserTypes";
import {Step} from "../../../enums/Steps";

const regexCodePostal = /^([A-Za-z]\s?[0-9]){3}$/;

export default function StepMonitor({
                                        prevStep,
                                        nextStep,
                                        updateUserType,
                                        handleChange,
                                        companyName,
                                        address,
                                        postalCode,
                                        city
                                    }) {

    const next = (val) => {
        updateUserType(UserType.MONITOR);
        nextStep(val);
    }
    const verification = (companyName, city, address, postalCode) => {
        if (!companyName) {
            toastErr.fire({title: 'Nom de compagnie est vide'}).then()
            return false
        }
        if (!city) {
            toastErr.fire({title: 'Nom de ville est vide'}).then()
            return false
        }
        if (!address) {
            toastErr.fire({title: "L'adresse est vide"}).then()
            return false
        }
        if (!postalCode) {
            toastErr.fire({title: 'Code postal est vide'}).then()
            return false
        }
        if (!companyName.match(/^[a-zA-Z0-9]+$/)) {
            toastErr.fire({title: 'Nom de compagnie est invalide'}).then()
            return false;
        }
        if (!city.match(/^[a-zA-Z]+$/)) {
            toastErr.fire({title: 'Nom de ville est invalide'}).then()
            return false;
        }
        if (!regexCodePostal.test(postalCode)) {
            toastErr.fire({title: 'Code postal est invalide'}).then()
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
                           value={companyName} onChange={handleChange}/>
                </div>
            </div>
            <div className="col-md-6">
                <label>Ville</label>
                <div>
                    <div className="input-group">
                        <input name="city" placeholder="Ville" className="form-control" type="text"
                               value={city} onChange={handleChange}/>
                    </div>
                </div>
            </div>
        </div>
        <div className="form-group">
            <FieldAddress label="Adresse de la compagnie" address={address} handleChange={handleChange}/>
        </div>
        <div className="form-group">
            <label>Code Postale</label>
            <div className="input-group">
                <input name="postalCode" placeholder="XXX 123" className="form-control" type="text"
                       value={postalCode} onChange={handleChange}/>
            </div>
        </div>
        <div className="form-group text-center">
            <label/>
            <div>
                <button className="btn btn-primary" type={"button"} onClick={prevStep}>Précédent
                </button>
                <button className="btn btn-primary" type={"button"} onClick={() => {
                    if (verification(companyName, city, address, postalCode)) next(Step.GENERAL)
                }}>Suivant
                </button>
            </div>
        </div>
    </>)

}
