import React from "react";
import {UserType} from "../../../enums/UserTypes";
import {Step} from "../../../enums/Steps";
import {toastErr} from "../../../utility";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {FormField} from "../../SharedComponents/FormField/FormField";

export default function StepCegep({prevStep, nextStep, updateUserType, handleChange, matricule}) {

    const next = (matricule, val) => {
        if (!matricule) {
            toastErr.fire({title: 'Matricule invalid!'}).then()
            return
        }
        if (matricule.toString().length === 5)
            updateUserType(UserType.SUPERVISOR)
        else if (matricule.toString().length === 7)
            updateUserType(UserType.STUDENT)

        nextStep(val);
    }

    return (<>
            <FormGroup>
                <FormField myFor="matricule">
                    <label>Matricule</label>
                    <input name="matricule" placeholder="Matricule" type="number"
                           value={matricule} onChange={handleChange}/>
                </FormField>
            </FormGroup>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={prevStep}>Précédent</button>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        next(matricule, Step.GENERAL)
                    }}>Suivant
                    </button>
                </div>
            </div>
        </>
    )

}

