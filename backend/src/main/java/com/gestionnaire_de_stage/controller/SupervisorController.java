package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.AssignDto;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.SupervisorAlreadyExistsException;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.service.StudentService;
import com.gestionnaire_de_stage.service.SupervisorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/supervisor")
public class SupervisorController {

    private final SupervisorService supervisorService;
    private final StudentService studentService;

    public SupervisorController(SupervisorService supervisorService, StudentService studentService) {
        this.supervisorService = supervisorService;
        this.studentService = studentService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Supervisor supervisor) {
        Supervisor createdSupervisor;
        try {
            createdSupervisor = supervisorService.create(supervisor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Le courriel ne peut pas être null"));
        } catch (SupervisorAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Ce courriel existe déjà!"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupervisor);
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        Supervisor supervisor;
        try {
            supervisor = supervisorService.getOneByEmailAndPassword(email, password);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(supervisor);
    }

    @GetMapping
    public ResponseEntity<?> getAllSupervisor() {
        List<Supervisor> supervisorList = supervisorService.getAll();
        return ResponseEntity.ok(supervisorList);
    }

    @PostMapping("/assign/student")
    public ResponseEntity<?> AssignSupervisor(@RequestBody AssignDto assignDto) {
        Student student;
        Supervisor supervisor;
        try {
            student = studentService.getOneByID(assignDto.getIdStudent());
            supervisor = supervisorService.getOneByID(assignDto.getIdSupervisor());
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Inexistant"));
        }
        boolean assign = studentService.assign(student, supervisor);

        String response = assign ? "Assignement fait!" : "Assignement rejeté, l'étudiant est déjà assigné!";
        return ResponseEntity.ok(new ResponseMessage(response));
    }

    @GetMapping("/students_status/{supervisor_id}")
    public ResponseEntity<?> getAllStudentsStatus(@PathVariable Long supervisor_id) {
        List<OfferApplication> offerApplicationList;
        try {
            offerApplicationList = supervisorService.getStudentsStatus(supervisor_id);
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Ce superviseur n'existe pas");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        return ResponseEntity
                .ok(offerApplicationList);
    }
}
