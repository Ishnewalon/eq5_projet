package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/contracts")
public class ContractController {
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/ready_to_sign")
    public ResponseEntity<?> contractsNeedSignature() {
        List<Contract> contractList = contractService.getAllUnsignedContracts();
        return ResponseEntity.ok(contractList);
    }

    @GetMapping("/managerSign/{managerSignature}/{manager_id}/{contract_id}")
    public ResponseEntity<?> managerSignContract(@PathVariable String managerSignature, @PathVariable Long manager_id, @PathVariable Long contract_id) throws Exception{
        contractService.addManagerSignature(managerSignature, contract_id, manager_id);
        contractService.fillPDF(contract_id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage("Signature fait"));
    }
}
