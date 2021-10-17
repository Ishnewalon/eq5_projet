package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyExistsException;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.StudentRepository;
import com.gestionnaire_de_stage.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Student student) {
        Student createdStudent;
        try {
            createdStudent = studentService.create(student);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Il faut un courriel"));
        }
        catch (StudentAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Ce courriel existe deja!"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }
/*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidRequests(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
*/
    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) throws EmailAndPasswordDoesNotExistException {
        Student student = studentService.getOneByEmailAndPassword(email, password);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.badRequest().body(new ResponseMessage("Erreur: Courriel ou Mot de Passe Invalide"));
    }
}
