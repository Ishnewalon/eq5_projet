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
                setNotifications(notifications.sort((a, b) => b.createdDate - a.createdDate));
            })
    }, [userId]);

    const setSeen = (notification) => {
        updateSeen(notification.id).then(() => setNotifications(notifList =>
            notifList.map(item => {
                if (item.id === notification.id) item.seen = true;
                return item;
            })));
    }

    const timeFormatMessage = (date) => {
        let timeNow = new Date();
        let dateTimeFormat = new Date(date);

        let dateAfficher = timeNow - dateTimeFormat;
        let dateAfficherMinutes = dateAfficher / (1000 * 60);
        let dateAfficherHours = dateAfficher / (1000 * 60 * 60);

        if (dateAfficherMinutes < 60) {
            return ` Il y a ${Math.floor(dateAfficherMinutes)} minutes`;
        } else if (dateAfficherHours < 24) {
            return ` Il y a ${Math.floor(dateAfficherHours)} heures`;
        } else {
            return ` Il y a ${Math.floor(dateAfficherHours / 24)} jours`;
        }
    };

    if (notifications?.length === 0)
        return <p className={"text-muted"}> Aucune notifications</p>

    return <>
        {notifications.map(notification => (
            <div className="row my-2 d-flex justify-content-center align-content-center" key={notification.id}>
                <Column col={{md: 1}}>
                    <button className={"link-button"}>
                        {notification.seen ?
                            <MdOutlineNotificationsNone size={20} color={"grey"} title={"Notification vue"}/>
                            :
                            <MdOutlineNotificationsActive size={20} color={"orange"} title={"Cliquez pour mettre à vue"}
                                                          onClick={() => setSeen(notification)}/>
                        }
                    </button>
                </Column>
                <Column col={{md: 8}}>
                    <p>{notification.message}</p>
                </Column>
                <Column col={{md: 3}}>
                    <p className="text-muted">
                        <BsClockHistory className={"me-1"}/>
                        {timeFormatMessage(notification.createdDate)}
                    </p>
                </Column>
            </div>
        ))}
    </>


}

export default Notifications;