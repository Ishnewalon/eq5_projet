package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.service.OfferService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    private Offer offer;

    @Test
    public void testUpdateOffer_withNullId() throws Exception{
        offer = getDummyOffer();
        offer.setId(null);
        when(offerService.update(offer)).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(put("/offers/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();
        var actualOfferInString = mvcResult.getResponse().getContentAsString();

        assertThat(actualOfferInString).isEqualTo("Erreur : offre non existante!");
    }

    @Test
    public void testUpdateOffer_withEmptyOffer() throws Exception{
        offer = new Offer();
        when(offerService.update(offer)).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(put("/offers/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        var actualOfferInString = mvcResult.getResponse().getContentAsString();
        assertThat(actualOfferInString).isEqualTo("Erreur : offre non existante!");
    }

    @Test
    public void testUpdateOffer_withValidOffer() throws Exception {
        offer = getDummyOffer();
        when(offerService.update(any())).thenReturn(Optional.of(offer));

        MvcResult mvcResult = mockMvc.perform(put("/offers/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        var actualOfferInString = mvcResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(actualOfferInString, Offer.class)).isEqualTo(offer);
    }

    @Test
    public void testGetOffers_withValidOffers() throws Exception {
        List<Offer> list = getDummyListOffer();
        when(offerService.getAll()).thenReturn(list);

        MvcResult mvcResult = mockMvc.perform(get("/offers")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actualOffersInString = mvcResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(actualOffersInString,
                new TypeReference<List<Offer>>(){})).isEqualTo(list);
    }


    private Offer getDummyOffer() {
        Offer offer = new Offer();
        offer.setDepartment("Un departement");
        offer.setAddress("ajsaodas");
        offer.setId(1L);
        offer.setDescription("oeinoiendw");
        offer.setSalary(10);
        offer.setTitle("oeinoiendw");
        return offer;
    }

    private List<Offer> getDummyListOffer(){
        List<Offer> list = new ArrayList<>();
        Offer offer = getDummyOffer();
        offer.setId(2L);
        list.add(getDummyOffer());
        list.add(offer);
        return list;
    }

}
