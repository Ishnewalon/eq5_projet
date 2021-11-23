import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";
import FieldAddress from "../../SharedComponents/Fields/FieldAddress";
import React from "react";
import {BtnBack} from "../../SharedComponents/BtnBack";

export function StepMonitorr({
                                 handleChange,
                                 companyName,
                                 address,
                                 postalCode,
                                 city
                             }) {


    return (<>
        <FormGroup>
            <FormField htmlFor="companyName">
                <label>Nom de la compagnie</label>
                <input name="companyName" placeholder="Nom de compagnie"
                       type="text"
                       value={companyName} onChange={handleChange}/>
            </FormField>
            <FormField htmlFor="city">
                <label>Ville</label>
                <input name="city" placeholder="Ville" type="text"
                       value={city} onChange={handleChange}/>
            </FormField>
        </FormGroup>
        <FormGroup>
            <FieldAddress label="Adresse de la compagnie" address={address} handleChange={handleChange}/>
        </FormGroup>
        <FormGroup>
            <FormField htmlFor="postalCode">
                <label>Code Postale</label>
                <input name="postalCode" placeholder="H0H 0H0" type="text"
                       value={postalCode} onChange={handleChange}/>
            </FormField>
        </FormGroup>
        <div className="form-group text-center">
            <div className="btn-group">
                <BtnBack/>
                <input className="btn btn-primary" type="submit" name="nextt" value="Suivant"/>
            </div>
        </div>
    </>)

}
