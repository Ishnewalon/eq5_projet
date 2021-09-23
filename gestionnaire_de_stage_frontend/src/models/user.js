class User {
    email = '';
    password = '';
    last_name = '';
    first_name = '';
    phone = ''


    constructor($email, $password, $last_name, $first_name, $phone) {
        this.email = $email;
        this.password = $password;
        this.last_name = $last_name;
        this.first_name = $first_name;
        this.phone = $phone;
    }
}

export class MonitorModel extends User {
    companyName = '';
    address = '';
    city = '';
    postalCode = '';

    constructor($email, $password, $last_name, $first_name, $phone, $companyName, $address, $city, $postalCode) {
        super($email, $password, $last_name, $first_name, $phone);
        this.companyName = $companyName;
        this.address = $address;
        this.city = $city;
        this.postalCode = $postalCode;
    }
}

export class Student extends User {
    matricule = '';

    constructor($email, $password, $last_name, $first_name, $phone, $matricule) {
        super($email, $password, $last_name, $first_name, $phone);
        this.matricule = $matricule;
    }
}

export class Supervisor extends User {
    matricule = '';

    constructor($email, $password, $last_name, $first_name, $phone, $matricule) {
        super($email, $password, $last_name, $first_name, $phone);
        this.matricule = $matricule;
    }
}


