import React from "react";
import {Step} from "../../../enums/Steps";

export default function Choice({nextStep}) {
    return (<>
        <div className="form-group text-center">
            <label/>
            <div>
                <button data-testid="cegep" className="btn btn-primary" type={"button"} onClick={() => nextStep(Step.CEGEP)
                }>Membre du cégep
                </button>
                <button data-testid="company" className="btn btn-primary" type={"button"} onClick={() => nextStep(Step.MONITOR)
                }>Compagnie
                </button>
            </div>
        </div>
    </>)

}

