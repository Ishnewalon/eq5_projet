package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepository offerRepository;

    @Test
    public void testMapToOffer() {
        OfferDTO offerDto = getDummyDto();

        Offer offer = offerService.mapToOffer(offerDto);

        assertThat(offer.getId()).isNull();
        assertThat(offerDto.getAddress()).isEqualTo(offer.getAddress());
        assertThat(offerDto.getDepartment()).isEqualTo(offer.getDepartment());
        assertThat(offerDto.getDescription()).isEqualTo(offer.getDescription());
        assertThat(offerDto.getSalary()).isEqualTo(offer.getSalary());
    }

    @Test
    public void testMapToOfferDto() {
        Offer offer = getDummyOffer();

        OfferDTO offerDto = offerService.mapToOfferDTO(offer);

        assertThat(offerDto.getAddress()).isEqualTo(offer.getAddress());
        assertThat(offerDto.getDepartment()).isEqualTo(offer.getDepartment());
        assertThat(offerDto.getDescription()).isEqualTo(offer.getDescription());
        assertThat(offerDto.getTitle()).isEqualTo(offer.getTitle());
        assertThat(offerDto.getSalary()).isEqualTo(offer.getSalary());
    }

    @Test
    public void testCreateOffer_withValidOffer() {
        Offer offer = getDummyOffer();
        offer.setId(null);

        when(offerRepository.save(any(Offer.class))).thenReturn(getDummyOffer());

        Optional<Offer> optionalOffer = offerService.create(offer);

        assertThat(optionalOffer.isPresent()).isTrue();
        assertThat(optionalOffer.get().getId()).isEqualTo(1L);
    }

    @Test
    public void testCreateOffer_withNullOffer() {
        Optional<Offer> optionalOffer = offerService.create(null);

        assertThat(optionalOffer.isPresent()).isFalse();
    }

    @Test
    public void testUpdateOffer_withNullId(){
        Offer offer = new Offer();
        when(offerRepository.existsById(any())).thenReturn(false);

        Optional<Offer> optionalOffer = offerService.update(offer);

        assertThat(optionalOffer.isPresent()).isFalse();
    }

    @Test
    public void testUpdateOffer_withNullOffer(){
        Optional<Offer> optionalOffer = offerService.update(null);

        assertThat(optionalOffer.isPresent()).isFalse();
    }

    @Test
    public void testUpdateOffer_withValidOffer(){
        Offer offer = getDummyOffer();
        when(offerRepository.existsById(any())).thenReturn(true);
        when(offerRepository.save(any())).thenReturn(offer);

        Optional<Offer> optionalOffer = offerService.update(offer);

        assertThat(optionalOffer.isPresent()).isTrue();
        assertThat(optionalOffer.get().getId()).isEqualTo(1L);
    }

    @Test
    public void testGetAllOffers_withValidList(){
        List<Offer> list = getDummyArrayOffer();
        when(offerRepository.findAll()).thenReturn(list);

        List<Offer> offerList = offerService.getAll();

        assertThat(offerList).isEqualTo(list);
    }

    @Test
    public void testGetAllOffers_withEmptyList(){
        when(offerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Offer> offerList = offerService.getAll();

        assertThat(offerList).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testMapArrayToOfferDto() {
        List<Offer> dummyArrayOffer = getDummyArrayOffer();


        List<OfferDTO> arrayOfferDTOS = offerService.mapArrayToOfferDTO(dummyArrayOffer);


        for (int i = 0; i < dummyArrayOffer.size(); i++) {
            assertThat(arrayOfferDTOS.get(i).getAddress()).isEqualTo(dummyArrayOffer.get(i).getAddress());
            assertThat(arrayOfferDTOS.get(i).getDepartment()).isEqualTo(dummyArrayOffer.get(i).getDepartment());
            assertThat(arrayOfferDTOS.get(i).getTitle()).isEqualTo(dummyArrayOffer.get(i).getTitle());
            assertThat(arrayOfferDTOS.get(i).getDescription()).isEqualTo(dummyArrayOffer.get(i).getDescription());
            assertThat(arrayOfferDTOS.get(i).getSalary()).isEqualTo(dummyArrayOffer.get(i).getSalary());
        }
    }

    @Test
    public void testGetOffersByDepartment_withNoOffer() {
        String department = "myDepartmentWithNoOffer";

        List<OfferDTO> offers = offerService.getOffersByDepartment(department);

        assertThat(offers.size()).isEqualTo(0);
    }

    @Test
    public void testGetOffersByDepartment() {
        when(offerRepository.findAllByDepartment(any())).thenReturn(getDummyArrayOffer());

        List<OfferDTO> offers = offerService.getOffersByDepartment("Un departement");

        assertThat(offerService.mapArrayToOfferDTO(getDummyArrayOffer())).isEqualTo(offers);
    }

    private List<Offer> getDummyArrayOffer() {
        List<Offer> myList = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            Offer dummyOffer = getDummyOffer();
            dummyOffer.setId(i);
            myList.add(dummyOffer);
        }
        return myList;
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

    private OfferDTO getDummyDto() {
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