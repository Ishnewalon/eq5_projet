package com.gestionnaire_de_stage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  OfferService offerService;

    private Offer offer;

/**
    @Test
    public void testUpdateOffer_withNullOffer() throws Exception{
        when(offerService.update(offer)).thenReturn(Optional.of(offer));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        var actualStudent = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Student.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualStudent).isEqualTo(offer);
    }
**/

    @Test
    public void testUpdateOffer_withNullId() throws Exception{
        Offer offer = new Offer();

        when(offerService.update(any())).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/offers/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        var actualOfferInString = mvcResult.getResponse().getContentAsString();

        assertThat(actualOfferInString).isEmpty();
    }

//    @Test
//    public void testUpdateOffer_withNullOffer(){
//        Optional<Offer> optionalOffer = offerService.update(null);
//
//        assertThat(optionalOffer.isPresent()).isFalse();
//    }

//    @Test
//    public void testUpdateOffer_withValidOffer(){
//        Offer offer = getDummyOffer();
//        when(offerRepository.existsById(any())).thenReturn(true);
//        when(offerRepository.save(any())).thenReturn(offer);
//
//        Optional<Offer> optionalOffer = offerService.update(offer);
//
//        assertThat(optionalOffer.isPresent()).isTrue();
//        assertThat(optionalOffer.get().getId()).isEqualTo(1L);
//    }


}
