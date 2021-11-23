package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ContractStarterDto;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyHaveAContractException;
import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.service.ContractService;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/contracts")
public class ContractController {

    private final ServletContext servletContext;

    private final ContractService contractService;

    private final TemplateEngine templateEngine;

    public ContractController(ContractService contractService, TemplateEngine templateEngine, ServletContext servletContext) {
        this.servletContext = servletContext;
        this.contractService = contractService;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/ready_to_sign")//SESSION : get only contract of current or futur session
    public ResponseEntity<?> contractsNeedSignature() {
        List<Contract> contractList = contractService.getAllUnsignedContracts();
        return ResponseEntity.ok(contractList);
    }

    @PutMapping("/managerSign/{managerSignature}/{contract_id}")
//SESSION : sign only contract of current or futur session
    public ResponseEntity<?> managerSignContract(HttpServletRequest request, HttpServletResponse response, @PathVariable String managerSignature, @PathVariable Long contract_id) {
        try {
            Contract contract = contractService.addManagerSignature(managerSignature, contract_id);
            WebContext context = new WebContext(request, response, servletContext);
            context.setVariable("contract", contract);
            String contractHtml = templateEngine.process("contractTemplate", context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(contractHtml, baos);
            contractService.fillPDF(contract, baos);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Signature fait"));
    }

    @PostMapping("/start")//SESSION : can start only contract of current or futur session
    public ResponseEntity<?> createContract(HttpServletRequest request, HttpServletResponse response, @RequestBody ContractStarterDto contractStarterDto) {
        try {
            Contract contract = new Contract();
            contract = contractService.gsStartContract(contract, contractStarterDto);

            WebContext context = new WebContext(request, response, servletContext);
            context.setVariable("contract", contract);

            String contractHtml = templateEngine.process("contractTemplate", context);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(contractHtml, baos);
            contract.setContractPDF(baos.toByteArray());
            contractService.updateContract(contract);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("L'id du gestionnaire et de l'application doivent exister!"));
        } catch (StudentAlreadyHaveAContractException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("L'étudiant a déjà un contrat!"));
        }
        return ResponseEntity.ok(new ResponseMessage("Création de contrat réussi!"));
    }

    @GetMapping("/monitor/{monitor_id}")//SESSION : get only contract of current or futur session
    public ResponseEntity<?> ContractNeedsMonitorSignature(@PathVariable Long monitor_id) {
        List<Contract> contractList;
        try {
            contractList = contractService.getAllUnsignedContractForMonitor(monitor_id);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

        return ResponseEntity.ok(contractList);
    }

    @PutMapping("/monitorSign/{monitorSignature}/{contract_id}")
//SESSION : sign only contract of current or futur session
    public ResponseEntity<?> monitorSignContract(HttpServletRequest request, HttpServletResponse response, @PathVariable String monitorSignature, @PathVariable Long contract_id) {
        try {
            Contract contract = contractService.addMonitorSignature(monitorSignature, contract_id);
            WebContext context = new WebContext(request, response, servletContext);
            context.setVariable("contract", contract);
            String contractHtml = templateEngine.process("contractTemplate", context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(contractHtml, baos);
            contractService.fillPDF(contract, baos);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Signature fait"));
    }

    @GetMapping("/student/{student_id}")//SESSION : get only contract of current or futur session
    public ResponseEntity<?> ContractNeedsStudentSignature(@PathVariable Long student_id) {
        Contract contract;
        try {
            contract = contractService.getContractByStudentId(student_id);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        return ResponseEntity.ok(contract);
    }

    @PutMapping("/studentSign/{studentSignature}/{contract_id}")
//SESSION : sign only contract of current or futur session
    public ResponseEntity<?> studentSignContract(HttpServletRequest request, HttpServletResponse response, @PathVariable String studentSignature, @PathVariable Long contract_id) {
        try {
            Contract contract = contractService.addStudentSignature(studentSignature, contract_id);
            WebContext context = new WebContext(request, response, servletContext);
            context.setVariable("contract", contract);
            String contractHtml = templateEngine.process("contractTemplate", context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(contractHtml, baos);
            contractService.fillPDF(contract, baos);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Signature fait"));
    }

    @GetMapping("/manager/signed/{managerId}")
    public ResponseEntity<?> getAllSignedContractsByManager(@PathVariable Long managerId) {
        List<Contract> signedContractsByManager = contractService.getAllSignedContractsByManager(managerId);
        return ResponseEntity.ok(signedContractsByManager);
    }

    @GetMapping("/monitor/signed/{monitor_id}")
    public ResponseEntity<?> getAllSignedContractsByMonitor(@PathVariable Long monitor_id) {
        List<Contract> allSignedContractsByMonitor = contractService.getAllSignedContractsByMonitor(monitor_id);
        return ResponseEntity.ok(allSignedContractsByMonitor);
    }

    @GetMapping("/student/signed/{student_id}")
    public ResponseEntity<?> getSignedContractByStudent(@PathVariable Long student_id) {
        Contract studentContract;
        try {
            studentContract = contractService.getSignedContractByStudentId(student_id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(studentContract);
    }
}
