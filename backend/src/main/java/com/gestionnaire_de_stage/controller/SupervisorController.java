package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.AssignDto;
import com.gestionnaire_de_stage.dto.ResponseMessage;
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
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupervisor);
    }

    @GetMapping("/matricule/{matricule}")
    public ResponseEntity<?> checkValidMatricule(@PathVariable String matricule) {
        return ResponseEntity.ok(!supervisorService.isMatriculeValid(matricule));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> checkValidEmail(@PathVariable String email) {
        return ResponseEntity.ok(supervisorService.isEmailInvalid(email));
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
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        boolean assign = studentService.assign(student, supervisor);
        if (assign) {
            return ResponseEntity.ok(new ResponseMessage("Affectation faite!"));
        }
        return ResponseEntity
                .badRequest()
                .body(new ResponseMessage("Affectation rejetée, l'étudiant est déjà assigné!"));
    }

    @GetMapping("/students_status/{supervisor_id}")
    public ResponseEntity<?> getAllStudentsStatus(@PathVariable Long supervisor_id) {
        List<OfferApplication> offerApplicationList;
        try {
            offerApplicationList = supervisorService.getStudentsStatus(supervisor_id);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        return ResponseEntity
                .ok(offerApplicationList);
    }
}
