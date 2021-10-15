package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.service.OfferAppService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
class OfferAppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferAppService offerAppService;

    @Test
    public void testStudentApplyToOffer() throws Exception {

        OfferAppDTO offerAppDTO = getDummyOfferAppDto();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(offerAppDTO)))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Le departement n'est pas precise");
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private OfferAppDTO getDummyOfferAppDto() {
        OfferAppDTO offerAppDTO = new OfferAppDTO();
        offerAppDTO.setIdOffer(1L);
        offerAppDTO.setIdCurriculum(1L);

        return offerAppDTO;
    }
}