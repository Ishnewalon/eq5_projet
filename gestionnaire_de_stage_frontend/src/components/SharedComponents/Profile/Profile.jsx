import {useAuth} from "../../../hooks/use-auth";
import Swal from "sweetalert2";
import {regexPassword} from "../../../utility";
import {FaPen} from "react-icons/all";
import styles from "./Profile.module.css";
import {UserType} from "../../../enums/UserTypes";
import {changePassword} from "../../../services/user-service";
import Notifications from "../Notifications/Notifications";
import {Column} from "../Column";

export default function Profile() {
    let auth = useAuth();
    let user = auth.user;

    function updatePassword() {
        Swal.fire({
            title: 'Changement de mot de passe',
            showCancelButton: true,
            confirmButtonText: 'Changer',
            cancelButtonText: 'Annuler',
            html:
                `<form>
                        <input type="text" name="email" value="..." autocomplete="username email" style="display: none;">
                        <label for="oldPassword">Ancien mot de passe</label>
                        <input type="password" id="oldPassword" class="form-control" autocomplete="current-password" />
                        <label for="newPassword">Nouveau mot de passe</label>
                        <input type="password" id="newPassword" class="form-control" autocomplete="new-password" />
                        <label for="confirmPassword">Confirmer le mot de passe</label>
                        <input type="password" id="confirmPassword" class="form-control" autocomplete="new-password" />
                </form>`,
            preConfirm: () => {
                let oldPassword = document.getElementById('oldPassword').value;
                let password = document.getElementById('newPassword').value;
                let confirmation = document.getElementById('confirmPassword').value;

                if (oldPassword === '' || password === '' || confirmation === '')
                    return Swal.showValidationMessage('Veuillez remplir tous les champs');
                if (oldPassword !== user.password)
                    return Swal.showValidationMessage('L\'ancien mot de passe est incorrect');
                if (!regexPassword.test(password))
                    return Swal.showValidationMessage('Le mot de passe doit contenir au moins 8 caractères dont au moins une majuscule, une minuscule et un chiffre');
                if (password !== confirmation)
                    return Swal.showValidationMessage("Les mots de passe ne correspondent pas");

                return new Promise(resolve => resolve(password))
            }
        }).then(result => {
            if (result.isConfirmed) {
                let type
                if (auth.isMonitor())
                    type = UserType.MONITOR[0]
                else if (auth.isStudent())
                    type = UserType.STUDENT[0]
                else if (auth.isSupervisor())
                    type = UserType.SUPERVISOR[0]
                changePassword(type, user.id, result.value).then(ok => {
                    if (ok)
                        user.password = result.value;
                })
            }
        })
    }

    function getRole() {
        if (auth.isMonitor())
            return "Moniteur"
        else if (auth.isStudent())
            return "Étudiant"
        else if (auth.isSupervisor())
            return "Superviseur"
        else if (auth.isManager())
            return "Administrateur"
    }

    function getAddress(user) {
        if (auth.isMonitor())
            return (<>
                    <h5 className={"mt-2 " + styles.borderTitle}>Compagnie</h5>
                    <div className="row">
                        <Column>
                            <h6 className={styles.titleCol}>Adresse</h6>
                            <h6 className="text-muted">{user.address} {user.postalCode}, {user.city}</h6>
                        </Column>
                    </div>
                </>
            )
        return null;
    }

    return (
        <div className="row d-flex justify-content-center my-5">
            <Column>
                <div className={`${styles.userCardFull}`}>
                    <div className="row">
                        <Column col={{sm: 4}} className={styles.userProfile}>
                            <div className={"text-center text-white " + styles.cardBlock}>
                                <div className="mb-4">
                                    <img src="https://img.icons8.com/bubbles/100/000000/user.png"
                                         className={styles.imgRadius} alt="Profile"/>
                                </div>
                                <h4 className={styles.fullName}>{user.firstName + " " + user.lastName}</h4>
                                <h6 className={styles.role}>{getRole()}</h6>
                            </div>
                        </Column>
                        <Column col={{sm: 8}}>
                            <div className={styles.cardBlock}>
                                <h5 className={styles.informations}>Informations</h5>
                                <div className="row">
                                    <Column col={{md: 6}}>
                                        <h6 className={styles.titleCol}>Email</h6>
                                        <p className="text-muted">{user.email}</p>
                                    </Column>
                                    <Column col={{md: 6}}>
                                        <h6 className={styles.titleCol}>
                                            Mot de passe {!auth.isManager() ?
                                            <FaPen className={"btn-link link-button"} onClick={updatePassword}/> : ""}
                                        </h6>
                                        <p className="text-muted">********</p>
                                    </Column>
                                    <Column col={{md: 6}}>
                                        <h6 className={styles.titleCol}>Téléphone</h6>
                                        <p className="text-muted">{formatPhoneNumber(user.phone)}</p>
                                    </Column>
                                    {getAddress(user)}
                                    <div className="row">
                                        <Column col={{sm: 12}}>
                                            <h5 className={"mt-2 " + styles.borderTitle}>Notifications</h5>
                                            <Notifications/>
                                        </Column>
                                    </div>
                                </div>
                            </div>
                        </Column>
                    </div>
                </div>
            </Column>
        </div>
    )
}

function formatPhoneNumber(phoneNumberString) {
    let cleaned = ('' + phoneNumberString).replace(/\D/g, '');
    let match = cleaned.match(/^(\d{3})(\d{3})(\d{4})$/);
    if (match) {
        return '(' + match[1] + ') ' + match[2] + '-' + match[3];
    }
    return null;
}
