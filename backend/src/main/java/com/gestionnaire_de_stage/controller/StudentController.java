package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.StudentRepository;
import com.gestionnaire_de_stage.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody Student student) {
        if (student.getEmail() != null && studentRepository.existsByEmail(student.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Ce courriel existe deja!"));
        }
        Optional<Student> opt = studentService.create(student);
        if (opt.isEmpty()) {
            System.out.println("It is empty");
        }
        Student student1 = opt.get();

        return new ResponseEntity<>(student1, HttpStatus.CREATED);
       // return ResponseEntity.ok(studentService.create(student));
    }

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

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, String password) {
        Optional<Student> student = studentService.getOneByEmailAndPassword(email, password);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        return ResponseEntity.badRequest().body(new ResponseMessage("Erreur: Courriel ou Mot de Passe Invalid"));
    }
}
