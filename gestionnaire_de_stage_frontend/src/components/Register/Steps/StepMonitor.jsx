import React from "react";
import FieldAddress from "../../SharedComponents/Fields/FieldAddress";
import {toastErr} from "../../../utility";
import {UserType} from "../../../enums/UserTypes";
import {Step} from "../../../enums/Steps";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {InputGroup} from "../../SharedComponents/InputGroup/InputGroup";

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
        <FormGroup>
            <label>Nom de la compagnie</label>
            <InputGroup>
                <input name="companyName" placeholder="Nom de compagnie"
                       type="text"
                       value={companyName} onChange={handleChange}/>
            </InputGroup>
            <label>Ville</label>
            <InputGroup>
                <input name="city" placeholder="Ville" type="text"
                       value={city} onChange={handleChange}/>
            </InputGroup>
        </FormGroup>
        <FormGroup>
            <FieldAddress label="Adresse de la compagnie" address={address} handleChange={handleChange}/>
        </FormGroup>
        <FormGroup>
            <label>Code Postale</label>
            <InputGroup>
                <input name="postalCode" placeholder="XXX 123" type="text"
                       value={postalCode} onChange={handleChange}/>
            </InputGroup>
        </FormGroup>
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
