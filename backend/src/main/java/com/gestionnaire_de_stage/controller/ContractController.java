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

    @GetMapping("/ready_to_sign")
    public ResponseEntity<?> contractsNeedSignature() {
        List<Contract> contractList = contractService.getAllUnsignedContracts();
        return ResponseEntity.ok(contractList);
    }

    @PutMapping("/managerSign/{managerSignature}/{contract_id}")
    public ResponseEntity<?> managerSignContract(HttpServletRequest request, HttpServletResponse response, @PathVariable String managerSignature, @PathVariable Long contract_id) {
        try {
            Contract contract = contractService.addManagerSignature(managerSignature, contract_id);
            WebContext context = new WebContext(request, response, servletContext);
            context.setVariable("contract", contract);
            String contractHtml = templateEngine.process("contractTemplate", context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(contractHtml, baos);
            contractService.fillPDF(contract, baos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le id du contrat n'existe pas"));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Signature fait"));
    }

    @PostMapping("/start")
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

    @GetMapping("/monitor/{monitor_id}")
    public ResponseEntity<?> ContractNeedsMonitorSignature(@PathVariable Long monitor_id) {
        List<Contract> contractList;
        try {
            contractList = contractService.getAllUnsignedContractForMonitor(monitor_id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le id du moniteur n'existe pas"));
        }

        return ResponseEntity.ok(contractList);
    }

    @PutMapping("/monitorSign/{monitorSignature}/{contract_id}")
    public ResponseEntity<?> monitorSignContract(HttpServletRequest request, HttpServletResponse response, @PathVariable String monitorSignature, @PathVariable Long contract_id) {
        try {
            Contract contract = contractService.addMonitorSignature(monitorSignature, contract_id);
            WebContext context = new WebContext(request, response, servletContext);
            context.setVariable("contract", contract);
            String contractHtml = templateEngine.process("contractTemplate", context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(contractHtml, baos);
            contractService.fillPDF(contract, baos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le id du contrat n'existe pas"));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Signature fait"));
    }

    @GetMapping("/student/{student_id}")
    public ResponseEntity<?> ContractNeedsStudentSignature(@PathVariable Long student_id) {
        Contract contract;
        try {
            contract = contractService.getContractByStudentId(student_id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le id de l'étudiant n'existe pas"));
        }

        return ResponseEntity.ok(contract);
    }

    @PutMapping("/studentSign/{studentSignature}/{contract_id}")
    public ResponseEntity<?> studentSignContract(HttpServletRequest request, HttpServletResponse response, @PathVariable String studentSignature, @PathVariable Long contract_id) {
        try {
            Contract contract = contractService.addStudentSignature(studentSignature, contract_id);
            WebContext context = new WebContext(request, response, servletContext);
            context.setVariable("contract", contract);
            String contractHtml = templateEngine.process("contractTemplate", context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(contractHtml, baos);
            contractService.fillPDF(contract, baos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le id du contrat n'existe pas"));
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
}
