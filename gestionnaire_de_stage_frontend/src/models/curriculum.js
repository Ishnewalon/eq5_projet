export default class curriculum{
    id
    name
    type
    data
    student
    isValid

    constructor(id, name, type, data, student, isValid) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.data = data;
        this.student = student;
        this.isValid = isValid;
    }
}