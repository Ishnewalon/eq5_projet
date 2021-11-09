import React from "react";
import {UserType} from "../../../enums/UserTypes";
import {Step} from "../../../enums/Steps";
import {toastErr} from "../../../utility";
import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {InputGroup} from "../../SharedComponents/InputGroup/InputGroup";

export default function StepCegep({prevStep, nextStep, updateUserType, handleChange, matricule}) {

    const next = (matricule, val) => {
        if (matricule.length === 5)
            updateUserType(UserType.SUPERVISOR)
        else if (matricule.length === 7)
            updateUserType(UserType.STUDENT)
        else {
            toastErr.fire({title: 'Matricule invalid!'}).then()
            return
        }

        nextStep(val);
    }

    return (<>
            <FormGroup>
                    <label>Matricule</label>
                    <InputGroup>
                        <input name="matricule" placeholder="Matricule" type="number"
                               value={matricule} onChange={handleChange}/>
                    </InputGroup>
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

