import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {InputGroup} from "../../SharedComponents/InputGroup/InputGroup";
import {createSession} from "../../../services/session-service";
import {useState} from "react";
import {toastErr} from "../../../utility";

export default function CreateSession() {
    let currentYear = new Date().getFullYear();
    let years = [];

    const [year, setYear] = useState(currentYear);
    const [typeSession, setTypeSession] = useState(null);

    for (let i = 0; i < 3; i++)
        years.push(currentYear + i);

    const onclick = () => {
        if (!typeSession) {
            toastErr.fire({title: "Le type de session doit être choisi"}).then()
            return
        }
        createSession({
            typeSession: typeSession,
            year: year

        }).then();
    };

    return <>
        <h1 className="text-center">Ajouter une session</h1>
        <FormGroup>
            <>
                <label>Année</label>
                <InputGroup>
                    <select onChange={(e) => setYear(e.target.value)}>
                        {years.map((year) =>
                            <option key={year} value={year}>{year}</option>
                        )}
                    </select>
                </InputGroup>
            </>
            <>
                <label>Type de session</label>
                <InputGroup>
                    <select onChange={(e) => setTypeSession(e.target.value)}>
                        <option selected disabled>Type de session</option>
                        <option value="HIVER">Session d'hiver</option>
                        <option value="ETE">Session d'été</option>
                    </select>
                </InputGroup>
            </>
        </FormGroup>
        <div className="text-center">
            <button className="btn btn-primary mt-3" onClick={onclick}>Create Session</button>

        </div>
    </>
}
