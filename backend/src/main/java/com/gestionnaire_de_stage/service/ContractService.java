package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.repository.ContractRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.util.List;


@Service
public class ContractService {

    private final ContractRepository contractRepository;

    private final ManagerService managerService;

    public ContractService(ContractRepository contractRepository, ManagerService managerService) {
        this.contractRepository = contractRepository;
        this.managerService = managerService;
    }

    public List<Contract> getAllUnsignedContracts() {
        return contractRepository.getAllByManagerSignatureNull();
    }

    public Contract addManagerSignature(String managerSignature, Long contract_id, Long manager_id) throws Exception {
        Contract contract = contractRepository.getContractById(contract_id);
        contract.setManagerSignDate(LocalDate.now());
        contract.setManagerSignature(managerSignature);
        contract.setManager(managerService.getOneByID(manager_id));
        return contractRepository.save(contract);
    }

    public Contract fillPDF(Long contract_id) {
        Contract contract = contractRepository.getContractById(contract_id);
        final String uri = "http://127.0.0.1:8181/pdf/pdf/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(uri + contract.getId(), byte[].class);
        contract.setContractPDF(responseEntity.getBody());
        return contractRepository.save(contract);
    }

    public Contract getContractById(Long id) {
        return contractRepository.getContractById(id);
    }
}
