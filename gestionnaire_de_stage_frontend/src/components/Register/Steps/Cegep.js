import {Step} from "../Register";

export const step_Cegep = ({prevStep, nextStep, handleChange, matricule}) => {

    const Previous = e => {
        e.preventDefault()
        prevStep();
    }
    const Continue = val => {
        nextStep(val);
    }

    return (<div>
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
                    <button className="btn btn-primary" type={"button"} onClick={Previous}>Precedent</button>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        Continue(Step.GENERAL)
                    }}>Suivant
                    </button>
                </div>
            </div>
        </div>
    )
}
