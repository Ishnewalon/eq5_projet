import React, {useEffect, useState} from "react";
import {getNotificationsByUser, updateSeen} from "../../../services/user-service";
import {Title} from "../Title/Title";
import {Table, TableHeader, TableRow} from "../Table/Table";
import {AiOutlineAlert, BsClockHistory} from "react-icons/all";
import MessageNothingToShow from "../MessageNothingToShow/MessageNothingToShow";
import {useAuth} from "../../../hooks/use-auth";
import styles from "../Profile/Profile.module.css";
import {Column} from "../FormGroup/FormGroupV2";


const Notifications = () => {

    const userId = useAuth().user.id;
    const [notifications, setNotifications] = useState([]);

    useEffect(() => {
        getNotificationsByUser(userId).then(
            notifications => {
                setNotifications(notifications);
            })
    }, [userId]);

    const setSeen = (notification) => {
      updateSeen(notification.id).then( () => setNotifications(notifList=>
            notifList.map(item=> {
                if (item.id === notification.id) item.seen= true;
                return item;
            })));
    }

    const timeFormatMessage = (date) => {
        console.log(date)
        let dateTimeFormat = new Date(date);
        let day = dateTimeFormat.getDate();
        let month = dateTimeFormat.getMonth() + 1;
        let year = dateTimeFormat.getFullYear();
        let hours = dateTimeFormat.getHours();
        let minutes = dateTimeFormat.getMinutes();
        let seconds = dateTimeFormat.getSeconds();
        let parsedDate = date.split("-");
        date = new Date(parsedDate[0], parsedDate[1] -1, parsedDate[2])
        console.log(date)
        return '' + day + ' ' + month + ' ' + year;
    };
    
    const getTimeFromNow = date => {
        let parsedDate = date.split("-");
        date = new Date(parsedDate[0], parsedDate[1] -1, parsedDate[2])
        return Math.floor((new Date().getTime() - date.getTime()) / (1000 * 3600 * 24))
    }

    return <>
        {notifications.map(notification => (
            <div className="row mb-3" key={notification.id}>
                <Column col={{md: 1}}>
                    <button className={"btn btn-outline-primary"} onClick={() => setSeen(notification)}>
                        <AiOutlineAlert size={25} color={notification.seen ? 'grey' : 'orange'}/>
                    </button>
                </Column>
                <Column col={{md: 9}}>
                    <p>{notification.message}</p>
                </Column>
                <Column col={{md: 2}}>
                    <p className="text-muted">
                        <BsClockHistory className={"me-1"}/>
                        {getTimeFromNow(notification.createdDate) === 0 ? "Aujourd'hui" : "Il y a " + getTimeFromNow(notification.createdDate) + " jour(s)"}
                    </p>
                </Column>
            </div>
        ))}
    </>


}

export default Notifications;