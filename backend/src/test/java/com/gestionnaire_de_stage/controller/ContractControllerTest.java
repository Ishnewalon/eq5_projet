package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.service.ContractService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ContractController.class)
public class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testGetContractReadySign_withValidEntries() throws Exception {
        List<Contract> dummyContractList = getDummyContractList();
        when(contractService.getAllUnsignedContracts()).thenReturn(dummyContractList);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/contracts/ready_to_sign")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Contract> actualContractList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualContractList.size()).isEqualTo(dummyContractList.size());
    }

    private List<Contract> getDummyContractList() {
        List<Contract> dummyContractList = new ArrayList<>();
        Contract contract1 = new Contract();
        contract1.setId(1L);
        dummyContractList.add(contract1);
        Contract contract2 = new Contract();
        contract2.setId(2L);
        dummyContractList.add(contract2);
        Contract contract3 = new Contract();
        contract3.setId(3L);
        dummyContractList.add(contract3);

        return dummyContractList;
    }
}
