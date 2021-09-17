export class User {

    email = '';
    password = '';
    last_name = '';
    first_name = '';
    phone = ''

    localisation=null
}

class Monitor extends User{
    companyName = '';
}

class Localisation {
    address = '';
    codePostal = '';
    city = ''
}
class Cegep extends User{
    matricule = '';
}
