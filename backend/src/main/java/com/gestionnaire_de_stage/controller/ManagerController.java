package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.dto.ValidationCurriculum;
import com.gestionnaire_de_stage.service.AuthService;
import com.gestionnaire_de_stage.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class ManagerController {

    private final AuthService authService;

    private final ManagerService managerService;

    public ManagerController(AuthService authService, ManagerService managerService) {
        this.authService = authService;
        this.managerService = managerService;
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email,@PathVariable String password){
        HttpStatus status = HttpStatus.OK;
        Manager manager = null;
        try{
            manager = authService.loginManager(email, password);
        }catch (RuntimeException re){
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(manager);
    }

    @PostMapping("/validate_curriculum")
    public ResponseEntity<Boolean> validateCurriculum(@Valid @RequestBody ValidationCurriculum validationCurriculum) throws Exception {
        boolean validated =  managerService.validateCurriculum(validationCurriculum.isValid(), validationCurriculum.getId());
        return ResponseEntity.status(validated ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(validated);
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseMessage> handleEmptyRequestBody(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ResponseMessage(ex.getMessage()));
    }
}
