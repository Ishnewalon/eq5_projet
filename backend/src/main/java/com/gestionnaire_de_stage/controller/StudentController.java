package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyExistsException;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Student student) {
        Student createdStudent;
        try {
            createdStudent = studentService.create(student);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Le courriel ne peut pas être null"));
        } catch (StudentAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Ce courriel existe déjà!"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        Student student;
        try {
            student = studentService.getOneByEmailAndPassword(email, password);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Le courriel et le mot de passe ne peuvent pas être null"));
        } catch (EmailAndPasswordDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Courriel ou Mot de Passe Invalide"));
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }
}
