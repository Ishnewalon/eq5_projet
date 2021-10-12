package com.gestionnaire_de_stage;

import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
@ComponentScan("com.gestionnaire_de_stage.*")
public class GestionnaireDeStageApplication implements CommandLineRunner {

    private final ManagerRepository managerRepository;
    private final MonitorRepository monitorRepository;
    private final StudentRepository studentRepository;
    private final SupervisorRepository supervisorRepository;
    private final OfferRepository offerRepository;

    @Autowired(required = false)
    public GestionnaireDeStageApplication(
        ManagerRepository managerRepository,
        SupervisorRepository supervisorRepository,
        StudentRepository studentRepository,
        MonitorRepository monitorRepository,
        OfferRepository offerRepository
    ){
        this.managerRepository = managerRepository;
        this.monitorRepository = monitorRepository;
        this.studentRepository = studentRepository;
        this.supervisorRepository = supervisorRepository;
        this.offerRepository = offerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(GestionnaireDeStageApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        studentRepository.deleteAll();
        supervisorRepository.deleteAll();
        monitorRepository.deleteAll();
        managerRepository.deleteAll();

        Student student = new Student();
        student.setId(1L);
        student.setLastName("Laflamme");
        student.setFirstName("Ernesto");
        student.setEmail("ernie@hotmail.com");
        student.setPhone("514-444-2222");
        student.setPassword("UnMotDePasse1");
        student.setMatricule("1231231");
        student.setDepartment("Informatique");

        studentRepository.save(student);

        Manager manager = new Manager();
        manager.setId(2L);
        manager.setLastName("Masta");
        manager.setFirstName("LeScrum");
        manager.setEmail("ernie@hotmail.com");
        manager.setPhone("514-444-2222");
        manager.setPassword("UnMotDePasse1");

        managerRepository.save(manager);

        Monitor monitor = new Monitor();
        monitor.setId(3L);
        monitor.setLastName("Suppa");
        monitor.setFirstName("LeScrum");
        monitor.setEmail("ernie@hotmail.com");
        monitor.setPhone("514-444-2222");
        monitor.setPassword("UnMotDePasse1");
        monitor.setDepartment("Informatique");

        monitor = monitorRepository.save(monitor);

        Supervisor supervisor = new Supervisor();
        supervisor.setId(4L);
        supervisor.setLastName("Toto");
        supervisor.setFirstName("Titi");
        supervisor.setEmail("ernie@hotmail.com");
        supervisor.setPhone("514-444-2222");
        supervisor.setPassword("UnMotDePasse1");
        supervisor.setDepartment("Informatique");
        supervisor.setMatricule("12331");

        supervisorRepository.save(supervisor);

        Offer offer1 = new Offer();
        offer1.setDepartment("Informatique");
        offer1.setAddress("1101 Rue Sainte-Catherine O, Montréal, QC H3B 1H8");
        offer1.setId(1L);
        offer1.setDescription("Employé de Desjardins");
        offer1.setSalary(16);
        offer1.setTitle("Développeur Frontend");
        offer1.setCreator(monitor);


        Offer offer2 = new Offer();
        offer2.setDepartment("Informatique");
        offer2.setAddress("1253 Av. McGill College, Montréal, QC H3B 2Y5");
        offer2.setId(2L);
        offer2.setDescription("Employé de Google");
        offer2.setSalary(87);
        offer2.setTitle("Developpeur Backend");
        offer2.setCreator(monitor);

        offerRepository.saveAll(List.of(offer1, offer2));
    }
}
