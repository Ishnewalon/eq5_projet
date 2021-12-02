import React, {useEffect, useState} from "react";
import {getNotificationsByUser, updateSeen} from "../../../services/user-service";
import {BsClockHistory, MdOutlineNotificationsActive, MdOutlineNotificationsNone} from "react-icons/all";
import {useAuth} from "../../../hooks/use-auth";
import {Column} from "../Column";


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
        updateSeen(notification.id).then(() => setNotifications(notifList =>
            notifList.map(item => {
                if (item.id === notification.id) item.seen = true;
                return item;
            })));
    }

    const getTimeFromNow = date => {
        let parsedDate = date.split("-");
        date = new Date(parsedDate[0], parsedDate[1] - 1, parsedDate[2])
        return Math.floor((new Date().getTime() - date.getTime()) / (1000 * 3600 * 24))
    }

    if (notifications?.length === 0)
        return <p className={"text-muted"}> Aucune notifications</p>

    return <>
        {notifications.map(notification => (
            <div className="row my-2 d-flex justify-content-center align-content-center" key={notification.id}>
                <Column col={{md: 1}}>
                    <button className={"link-button"}>
                        {notification.seen ?
                            <MdOutlineNotificationsNone size={20} color={"grey"}/>
                            : <MdOutlineNotificationsActive size={20} color={"orange"}
                                                            onClick={() => setSeen(notification)}/>
                        }
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