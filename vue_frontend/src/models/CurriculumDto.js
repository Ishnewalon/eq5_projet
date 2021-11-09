export default class CurriculumDto {
    firstName
    lastName
    fileName
    offerDTO
    file

    constructor($firstName, $lastName, $fileName, $offerDTO, $file) {
        this.firstName = $firstName;
        this.lastName = $lastName;
        this.fileName = $fileName;
        this.offerDTO = $offerDTO;
        this.file = $file;
    }
}
