export default class OfferDTO {
    title
    department
    description
    address
    salary
    creator_email


    constructor($title, $department, $description, $address, $salary, $creator_email) {
        this.title = $title;
        this.department = $department;
        this.description = $description;
        this.address = $address;
        this.salary = $salary;
        this.creator_email = $creator_email;
    }
}
