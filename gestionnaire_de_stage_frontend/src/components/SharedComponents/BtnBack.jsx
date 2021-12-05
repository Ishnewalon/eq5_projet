import {useHistory} from "react-router-dom";
import React from "react";
import styles from "./BtnBack.module.css";
import {FaArrowLeft} from "react-icons/all";

export function BtnBack() {
    let history = useHistory();

    const goBack = e => {
        e.preventDefault();
        if (history.location.state)
            history.goBack()
        history.push("/login", {from: history.location})
    };
    return <button type="button" className={styles.btnBack}
                   onClick={goBack}><FaArrowLeft size={40} title="Retour vers le futur"/></button>
}
