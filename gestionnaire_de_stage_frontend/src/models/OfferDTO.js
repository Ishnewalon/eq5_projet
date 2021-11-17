export default class OfferDTO {
    title
    department
    description
    address
    salary
    creator_email
    idSession

    constructor($title, $department, $description, $address, $salary, $creator_email, $idSession) {
        this.title = $title;
        this.department = $department;
        this.description = $description;
        this.address = $address;
        this.salary = $salary;
        this.creator_email = $creator_email;
        this.idSession = $idSession;
    }
}
