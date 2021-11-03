package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.ContractRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ContractServiceTest {

    @InjectMocks
    ContractService contractService;

    @Mock
    ContractRepository contractRepository;

    @Test
    public void testGetAllByManagerSignatureNull() {
        when(contractRepository.getAllByManagerSignatureNull()).thenReturn(getDummyContractList());

        final List<Contract> actualContractList = contractService.getAllUnsignedContracts();

        assertThat(actualContractList.size()).isEqualTo(getDummyContractList().size());
    }

    /*@Test
    public void testManagerSignContract_withValidEntries() throws Exception{
        when(contractRepository.getContractByStudent_Matricule(any())).thenReturn(getDummyContract());
        when(contractRepository.save(any())).thenReturn(getDummyContract());

        final Contract actualContract = contractService.managerSignContract(getDummyOfferApp());

        assertThat(actualContract.getStudent()).isEqualTo(getDummyStudent());
    }*/

    private List<Contract> getDummyContractList() {
        List<Contract> dummyContractList = new ArrayList<>();
        Contract contract1 = new Contract();
        contract1.setId(1L);
        contract1.setStudent(new Student());
        dummyContractList.add(contract1);
        Contract contract2 = new Contract();
        contract2.setId(2L);
        contract1.setStudent(new Student());
        dummyContractList.add(contract2);
        Contract contract3 = new Contract();
        contract3.setId(3L);
        contract1.setStudent(new Student());
        dummyContractList.add(contract3);

        return dummyContractList;
    }

    private Contract getDummyContract() {
        Contract dummyContract = new Contract();
        dummyContract.setId(1L);
        dummyContract.setStudent(getDummyStudent());
        return dummyContract;
    }

    private OfferApplication getDummyOfferApp() {
        OfferApplication dummyOfferApplicationDTO = new OfferApplication();
        dummyOfferApplicationDTO.setOffer(getDummyOffer());
        dummyOfferApplicationDTO.setCurriculum(getDummyCurriculum());
        dummyOfferApplicationDTO.setId(1L);

        return dummyOfferApplicationDTO;
    }

    private Curriculum getDummyCurriculum() {
        Curriculum dummyCurriculum = new Curriculum();
        dummyCurriculum.setId(1L);
        dummyCurriculum.setStudent(getDummyStudent());
        return dummyCurriculum;
    }

    private Student getDummyStudent() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        dummyStudent.setLastName("Candle");
        dummyStudent.setFirstName("Tea");
        dummyStudent.setEmail("cant@outlook.com");
        dummyStudent.setPassword("cantPass");
        dummyStudent.setDepartment("info");
        dummyStudent.setMatricule("4673943");
        return dummyStudent;
    }

    private Offer getDummyOffer() {
        Offer dummyOffer = new Offer();
        dummyOffer.setDepartment("Un departement");
        dummyOffer.setAddress("ajsaodas");
        dummyOffer.setId(1L);
        dummyOffer.setDescription("oeinoiendw");
        dummyOffer.setSalary(10);
        dummyOffer.setTitle("oeinoiendw");
        return dummyOffer;
    }
}
