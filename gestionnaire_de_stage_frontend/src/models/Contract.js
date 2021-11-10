export default class Contract {
    id
    managerSignDate
    monitorSignDate
    StudentSignDate

    managerSignature
    monitorSignature
    studentSignature

    student
    offer
    manager
    contractPDF


    constructor(
        $id,
        $managerSignDate,
        $monitorSignDate,
        $StudentSignDate,
        $managerSignature,
        $monitorSignature,
        $studentSignature,
        $student,
        $offer,
        $manager,
        $contractPDF
    ) {
        this.id = $id;
        this.managerSignDate = $managerSignDate;
        this.monitorSignDate = $monitorSignDate;
        this.StudentSignDate = $StudentSignDate;
        this.managerSignature = $managerSignature;
        this.monitorSignature = $monitorSignature;
        this.studentSignature = $studentSignature;
        this.student = $student;
        this.offer = $offer;
        this.manager = $manager;
        this.contractPDF = $contractPDF;
    }
}
