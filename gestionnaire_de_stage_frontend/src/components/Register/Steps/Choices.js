import {Step} from "../Register";

function Choice({nextStep}) {

    const Continue = (value) => {
        console.log(value)
        nextStep(value);
    }

    return (<div>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        Continue(Step.CEGEP)
                    }}>Membre du cegep
                    </button>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        Continue(Step.MONITOR)
                    }}>Companie
                    </button>
                </div>
            </div>
        </div>
    )
}

export default Choice;
