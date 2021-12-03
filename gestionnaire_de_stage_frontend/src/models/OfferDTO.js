export default class OfferDTO {
    title
    department
    description
    address
    salary
    creator_email
    nbSemaine
    dateDebut
    dateFin
    horaireTravail;
    nbHeureSemaine
    idSession


    constructor($title,
                $department,
                $description,
                $address,
                $salary,
                $creator_email,
                $nbSemaine,
                $dateDebut,
                $dateFin,
                $horaireTravail,
                $nbHeureSemaine,
                $idSession
    ) {
        this.title = $title;
        this.department = $department;
        this.description = $description;
        this.address = $address;
        this.salary = $salary;
        this.creator_email = $creator_email;
        this.nbSemaine = $nbSemaine;
        this.dateDebut = $dateDebut;
        this.dateFin = $dateFin;
        this.horaireTravail = $horaireTravail;
        this.nbHeureSemaine = $nbHeureSemaine;
        this.idSession = $idSession;
    }
}
