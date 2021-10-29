export default class SignContract{
    idStudent
    offerApp
    studentAccepted
    managerAccepted
    monitorAccepted

    constructor($idStudent, $offerApp, $studentAccepted, $managerAccepted, $monitorAccepted) {
        this.idStudent = $idStudent;
        this.offerApp = $offerApp;
        this.studentAccepted = $studentAccepted;
        this.managerAccepted = $managerAccepted;
        this.monitorAccepted = $monitorAccepted;
    }
}
