package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<Contract> getAllUnsignedContracts() {
        return contractRepository.getAllByManagerSignatureNull();
    }
}
