export default class Offer{
    title
    department
    description
    address
    salary
    id
    creator


    constructor($id, $title, $department, $description, $address, $salary, $creator) {
        this.id = $id;
        this.title = $title;
        this.department = $department;
        this.description = $description;
        this.address = $address;
        this.salary = $salary;
        this.creator = $creator;
    }
}
