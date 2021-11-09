import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {InputGroup} from "../../SharedComponents/InputGroup/InputGroup";
import {createSession} from "../../../services/session-service";
import {useState} from "react";

export default function CreateSession() {
    const [dateDebut, setDateDebut] = useState('');
    const [dateFin, setDateFin] = useState('');
    const [typeSession, setTypeSession] = useState('');

    const onclick = () => {
        createSession({
            typeSession: typeSession,
            dateDebut: dateDebut,
            dateFin: dateFin,

        }).then();
    };

    return <>
        <h1 className="text-center">Ajouter une session</h1>
        <FormGroup>
            <>
                <label>Date de début</label>
                <InputGroup>
                    <input type="datetime-local" id="dateDebut" name="dateDebut"
                           onChange={(e) => setDateDebut(e.target.value)}/>
                </InputGroup>
            </>
            <>
                <label>Date de fin</label>
                <InputGroup>
                    <input type="datetime-local" id="dateFin" name="dateFin"
                           onChange={(e) => setDateFin(e.target.value)}/>
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
