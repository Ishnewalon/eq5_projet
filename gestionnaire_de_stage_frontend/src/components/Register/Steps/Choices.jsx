import {Step} from "../Register";
import {Component} from "react";

export default class Choice extends Component {
    continue = (value) => {
        this.props.nextStep(value);
    }

    render() {
        return (<div>
            <div className="form-group text-center">
                <label/>
                <div>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        this.continue(Step.CEGEP)
                    }}>Membre du cegep
                    </button>
                    <button className="btn btn-primary" type={"button"} onClick={() => {
                        this.continue(Step.MONITOR)
                    }}>Companie
                    </button>
                </div>
            </div>
        </div>)

    }
}

