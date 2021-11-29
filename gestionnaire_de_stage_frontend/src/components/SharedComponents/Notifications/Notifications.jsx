import {useAuth} from "../../../services/use-auth";
import {useEffect, useState} from "react";
import {getNotificationsByUser} from "../../../services/user-service";
import {Title} from "../Title/Title";
import {ContainerBox} from "../ContainerBox/ContainerBox";
import {Table, TableHeader, TableRow} from "../Table/Table";
import {AiOutlineAlert, BsClockHistory} from "react-icons/all";
import MessageNothingToShow from "../MessageNothingToShow/MessageNothingToShow";


const Notifications = props => {

    const userId = useAuth().user.id;
    const [notifications, setNotifications] = useState([]);

    useEffect(() => {
        getNotificationsByUser(userId).then(
            notifications => {
                setNotifications(notifications);
            })
    }, [userId]);
    
    const buildTable = () => {
        if (notifications && notifications.length > 0){
            console.log(notifications);
            return <Table>
                <TableHeader>
                    <th>Seen</th>
                    <th>Message</th>
                    <th>date</th>
                </TableHeader>
                {notifications.map(notification => (
                    <TableRow key={notification.id}>
                        <td>{notification.seen ? '' : <AiOutlineAlert color={'orange'}/>}</td>
                        <td>{notification.message}</td>
                        <td><BsClockHistory className={"me-1"}/> Il y
                            a {Math.ceil((new Date().getTime() - new Date(notification.createdDate).getTime()) / (1000 * 3600 * 24))} jour(s)</td>
                    </TableRow>
                ))}</Table>}
        return <MessageNothingToShow message="Aucune nouvelle notification"/>
    }

    return <>
        <Title>
            Vos Notifications
        </Title>
        {buildTable()}
    </>


}

export default Notifications;