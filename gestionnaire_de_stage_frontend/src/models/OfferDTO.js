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


    /**
     *
     * @param $title titre de l'offre
     * @param $department departement de l'offre
     * @param $description description de l'offre
     * @param $address adresse de l'offre
     * @param $salary salaire de l'offre
     * @param $creator_email email de l'offre
     * @param $nbSemaine nombre de semaine de l'offre
     * @param $dateDebut date de debut de l'offre
     * @param $dateFin date de fin de l'offre
     * @param $horaireTravail horaire de travail de l'offre
     * @param $nbHeureSemaine nombre d'heure de travail de l'offre
     * @param $idSession id de la session de l'offre
     */
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
