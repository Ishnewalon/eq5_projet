export default class OfferDTO {
    title
    department
    description
    address
    salary
    creator_id


    constructor($title, $department, $description, $address, $salary, $creator_id) {
        this.title = $title;
        this.department = $department;
        this.description = $description;
        this.address = $address;
        this.salary = $salary;
        this.creator_id = $creator_id;
    }
}
