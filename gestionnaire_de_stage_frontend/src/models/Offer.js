export default class Offer {
    title
    department
    description
    address
    salary
    id
    creator
    valid = true
    dateFin
    dateDebut
    created
    nbSemaine


    constructor($id, $title, $department, $description, $address, $salary, $creator, $valid) {
        this.id = $id;
        this.title = $title;
        this.department = $department;
        this.description = $description;
        this.address = $address;
        this.salary = $salary;
        this.creator = $creator;
        this.valid = $valid;
    }
}
