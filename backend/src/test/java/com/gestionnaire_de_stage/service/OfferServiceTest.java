package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepository offerRepository;

    @Test
    public void testMapToOffer(){
        OfferDTO offerDto = getDummyDto();

        Offer offer = offerService.mapToOffer(offerDto);

        assertNull(offer.getId());
        assertEquals(offerDto.getAddress(), offer.getAddress());
        assertEquals(offerDto.getDepartment(), offer.getDepartment());
        assertEquals(offerDto.getTitle(), offer.getTitle());
        assertEquals(offerDto.getDescription(), offer.getDescription());
        assertEquals(offerDto.getSalary(), offer.getSalary());
    }

    @Test
    public void testMapToOfferDto(){
        Offer offer = getDummyOffer();

        OfferDTO offerDto = offerService.mapToOfferDTO(offer);

        assertEquals(offerDto.getAddress(), offer.getAddress());
        assertEquals(offerDto.getDepartment(), offer.getDepartment());
        assertEquals(offerDto.getTitle(), offer.getTitle());
        assertEquals(offerDto.getDescription(), offer.getDescription());
        assertEquals(offerDto.getSalary(), offer.getSalary());
    }

    @Test
    public void testCreateOffer_withValidOffer(){
        Offer offer = getDummyOffer();
        offer.setId(null);

        when(offerRepository.save(any(Offer.class))).thenReturn(getDummyOffer());

        Optional<Offer> optionalOffer = offerService.create(offer);

        assertTrue(optionalOffer.isPresent());
        assertEquals(1L, optionalOffer.get().getId());
    }

    @Test
    public void testCreateOffer_withNullOffer(){
        Optional<Offer> optionalOffer = offerService.create(null);

        assertFalse(optionalOffer.isPresent());
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

    private OfferDTO getDummyDto(){
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setCreator_id(1L);
        offerDTO.setSalary(18.0d);
        offerDTO.setDescription("Une description");
        offerDTO.setAddress("Addresse du cégep");
        offerDTO.setTitle("Offer title");
        offerDTO.setDepartment("Department name");
        return offerDTO;
    }
}