import {Step} from "../Register";
import {Component} from "react";

export default class Choice extends Component {

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
            </div>
        )
    }

    continue(value) {
        console.log(value)
        this.props.nextStep(value);
    }
}

