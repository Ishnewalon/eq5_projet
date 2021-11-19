class User {
    id = null;
    email = '';
    password = '';
    lastName = '';
    firstName = '';
    phone = '';

    constructor($email, $password, $lastName, $firstName, $phone) {
        this.email = $email;
        this.password = $password;
        this.lastName = $lastName;
        this.firstName = $firstName;
        this.phone = $phone;
    }
}


export class ManagerModel extends User{

}


export class MonitorModel extends User {
    companyName = '';
    address = '';
    city = '';
    postalCode = '';
    department = ''

    constructor($email, $password, $lastName, $firstName, $phone, $companyName, $address, $city, $postalCode,
                $department = 'informatique') {
        super($email, $password, $lastName, $firstName, $phone);
        this.companyName = $companyName;
        this.address = $address;
        this.city = $city;
        this.postalCode = $postalCode;
        this.department = $department
    }
}

export class Student extends User {
    matricule = '';
    department = ''
    address = '';
    city = '';
    postalCode = '';
    supervisor;

    constructor($email, $password, $lastName, $firstName, $phone, $matricule, $department = 'informatique',
                $address = '', $city = '', $postalCode = '') {
        super($email, $password, $lastName, $firstName, $phone);
        this.matricule = $matricule;
        this.department = $department
        this.address = $address;
        this.city = $city;
        this.postalCode = $postalCode;
    }
}

export class Supervisor extends User {
    matricule = '';
    department = ''

    constructor($email, $password, $lastName, $firstName, $phone, $matricule, $department = 'informatique') {
        super($email, $password, $lastName, $firstName, $phone);
        this.matricule = $matricule;
        this.department = $department
    }
}


