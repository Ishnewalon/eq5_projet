import {Step, UserType} from "../Register";
import React from "react";

export default function StepCegep({prevStep, nextStep, updateUserType, handleChange, matricule}) {

    const next = (val) => {
        if (matricule.length === 5)
            updateUserType(UserType.SUPERVISOR)
        if (matricule.length === 7)
            updateUserType(UserType.STUDENT)
        nextStep(val);
    }

    return (<>
            <div className="form-group row">
                <div className="col-md-12">
                    <label>Matricule</label>
                    <div className="input-group">
                        <input name="matricule" placeholder="Matricule" className="form-control" type="number"
                               value={matricule} onChange={handleChange('matricule')}/>
                    </div>
                </div>
            </div>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={prevStep}>Précédent</button>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        next(Step.GENERAL)
                    }}>Suivant
                    </button>
                </div>
            </div>
        </>
    )

}

