export class Monitor extends User, Location {
    companyName = '';
}

export class Student extends User, Cegep, Location {
    constructor() {
        super();
    }
}

export class Supervisor extends User, Cegep {
    constructor() {
        super();
    }
}

class User {

    email = '';
    password = '';
    last_name = '';
    first_name = '';
    phone = ''

}


class Cegep {
    matricule = '';
}

class Location {
    address = '';
    city = '';
    postalCode = '';

}
