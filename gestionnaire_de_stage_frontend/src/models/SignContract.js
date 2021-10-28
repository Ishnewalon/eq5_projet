export default class SignContract{
    idStudent
    offer
    studentAccepted
    managerAccepted
    monitorAccepted


    constructor($idStudent, $offer, $studentAccepted, $managerAccepted, $monitorAccepted) {
        this.idStudent = $idStudent;
        this.offer = $offer;
        this.studentAccepted = $studentAccepted;
        this.managerAccepted = $managerAccepted;
        this.monitorAccepted = $monitorAccepted;
    }
}
