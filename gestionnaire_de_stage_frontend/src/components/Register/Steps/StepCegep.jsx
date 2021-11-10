import React from "react";
import {UserType} from "../../../enums/UserTypes";
import {Step} from "../../../enums/Steps";
import {toastErr} from "../../../utility";

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
            <div className="form-group row">
                <div className="col-md-12">
                    <label>Matricule</label>
                    <div className="input-group">
                        <input name="matricule" placeholder="Matricule" className="form-control" type="number"
                               value={matricule} onChange={handleChange}/>
                    </div>
                </div>
            </div>
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

