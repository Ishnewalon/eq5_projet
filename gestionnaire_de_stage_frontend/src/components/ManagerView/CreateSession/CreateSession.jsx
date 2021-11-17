import {FormGroup} from "../../SharedComponents/FormGroup/FormGroup";
import {createSession} from "../../../services/session-service";
import {useState} from "react";
import {toastErr} from "../../../utility";
import {FormField} from "../../SharedComponents/FormField/FormField";

export default function CreateSession() {
    let currentYear = new Date().getFullYear();
    let years = [];

    const [year, setYear] = useState(currentYear);
    const [typeSession, setTypeSession] = useState(null);

    for (let i = 0; i < 3; i++)
        years.push(currentYear + i);

    const onclick = () => {
        if (!typeSession) {
            toastErr.fire({title: "La période de la session doit être choisi"}).then()
            return
        }
        createSession({
            typeSession: typeSession,
            year: year

        }).then();
    };

    return <>
        <FormGroup>
            <FormField>
                <label>Année</label>
                <select onChange={(e) => setYear(e.target.value)}>
                    {years.map((year) =>
                        <option key={year} value={year}>{year}</option>
                    )}
                </select>
            </FormField>
            <FormField>
                <label>Type de session</label>
                <select onChange={(e) => setTypeSession(e.target.value)} defaultValue="">
                    <option disabled value="">Type de session</option>
                    <option value="HIVER">Session d'hiver</option>
                    <option value="ETE">Session d'été</option>
                </select>
            </FormField>
        </FormGroup>
        <div className="text-center">
            <button className="btn btn-primary mt-3" onClick={onclick}>Créez la session</button>

        </div>
    </>
}
