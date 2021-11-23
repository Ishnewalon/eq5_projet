import {regexCodePostal, regexEmail, regexName, regexPassword, regexPhone, toastErr} from "../../../utility";

export function verificationMonitor(companyName, city, address, postalCode) {
    if (!companyName) {
        toastErr.fire({title: 'Nom de compagnie est vide'}).then()
        return false
    }
    if (!city) {
        toastErr.fire({title: 'Nom de ville est vide'}).then()
        return false
    }
    if (!address) {
        toastErr.fire({title: "L'adresse est vide"}).then()
        return false
    }
    if (!postalCode) {
        toastErr.fire({title: 'Code postal est vide'}).then()
        return false
    }
    if (!regexName.test(companyName)) {
        toastErr.fire({title: 'Nom de compagnie est invalide'}).then()
        return false;
    }
    if (!regexName.test(city)) {
        toastErr.fire({title: 'Nom de ville est invalide'}).then()
        return false;
    }
    if (!regexCodePostal.test(postalCode)) {
        toastErr.fire({title: 'Code postal est invalide'}).then()
        return false;
    }
    return true;
}

export function verificationGeneral(firstName, lastName, phone, email) {
    if (!firstName) {
        toastErr.fire({title: "Le champs prénom est vide"}).then()
        return false
    }
    if (!lastName) {
        toastErr.fire({title: "Le champs nom est vide"}).then()
        return false
    }
    if (!email) {
        toastErr.fire({title: "Le champs courriel est vide"}).then()
        return false
    }
    if (!phone) {
        toastErr.fire({title: "Le champs numéro de téléphone est vide"}).then()
        return false
    }
    if (!regexName.test(firstName)) {
        toastErr.fire({title: "Le champs prénom est invalide"}).then()
        return false;
    }
    if (!regexName.test(lastName)) {
        toastErr.fire({title: "Le champs nom est invalide"}).then()
        return false;
    }
    if (!regexEmail.test(email)) {
        toastErr.fire({title: "Le champs courriel est invalide"}).then()
        return false;
    }
    if (!regexPhone.test(phone)) {
        toastErr.fire({title: "Le champs numéro de téléphone est invalide"}).then()
        return false;
    }
    return true;
}

export function verificationPassword(password) {
    if (regexPassword.test(password)) return true
    toastErr.fire({title: 'Le mot de passe doit être entre 8 et 64 caractères'}).then();
    return false;
}
