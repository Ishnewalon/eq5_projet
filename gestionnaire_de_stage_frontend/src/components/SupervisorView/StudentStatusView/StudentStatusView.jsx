import React, {useEffect, useState} from "react";
import {getStudentsStatus} from "../../../services/user-service";

export default function StudentStatusView() {

    const [offerAppList, setOfferAppList] = useState([])

    useEffect(() => {
        getStudentsStatus()
            .then(offerAppList => {
                setOfferAppList(offerAppList)
            })
            .catch(e => {
                setOfferAppList([])
                console.error(e);
            })
    }, [])



}