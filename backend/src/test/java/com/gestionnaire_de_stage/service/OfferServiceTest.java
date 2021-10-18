package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.OfferAlreadyExistsException;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("ConstantConditions")
public class OfferServiceTest {

    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepository offerRepository;

    @Test
    public void testMapToOffer_withNullDto(){
        Offer mappedOffer = offerService.mapToOffer(null);
        assertThat(mappedOffer).isNull();
    }

    @Test
    public void testMapToOfferDto_withNullOffer(){
        OfferDTO mappedDto = offerService.mapToOfferDTO(null);
        assertThat(mappedDto).isNull();
    }

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
    public void testCreateOffer_withValidOffer() throws OfferAlreadyExistsException {
        Offer offer = getDummyOffer();
        offer.setId(null);
        when(offerRepository.save(any())).thenReturn(getDummyOffer());
        when(offerRepository.findOne(any())).thenReturn(Optional.empty());

        Offer createdOffer = offerService.create(offer);

        assertThat(createdOffer).isNotNull();
        assertThat(createdOffer.getId())
                .isNotNull()
                .isGreaterThan(0);
    }

    @Test
    public void testCreateOffer_withNullOffer(){
        assertThrows(IllegalArgumentException.class, () -> offerService.create(null));
    }

    @Test
    public void testCreateOffer_withExistingOffer(){
        final Offer offer = getDummyOffer();

        assertDoesNotThrow(() -> offerService.create(offer));
    }

    @Test
    public void testUpdateOffer_withNullId(){
        final Offer offer = getDummyOffer();
        offer.setId(null);

        assertThrows(IllegalArgumentException.class, () -> offerService.update(offer));
    }

    @Test
    public void testUpdateOffer_withNullOffer(){
        assertThrows(IllegalArgumentException.class, () -> offerService.update(null));
    }

    @Test
    public void testUpdateOffer_withInvalidOffer(){
        Offer offer = getDummyOffer();
        when(offerRepository.existsById(1L)).thenReturn(false);

        assertThrows(IdDoesNotExistException.class, () -> offerService.update(offer));
    }

    @Test
    public void testUpdateOffer_withValidOffer() throws IdDoesNotExistException {
        Offer offer = getDummyOffer();
        when(offerRepository.existsById(1L)).thenReturn(true);
        when(offerRepository.save(any())).thenReturn(offer);

        Offer updatedOffer = offerService.update(offer);

        assertThat(updatedOffer)
                .isNotNull()
                .isEqualTo(offer);
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

        assertThat(offerList).isEmpty();
    }

    @Test
    public void testMapArrayToOfferDto() {
        List<Offer> dummyArrayOffer = getDummyArrayOffer();

        List<OfferDTO> arrayOfferDTOS = offerService.mapArrayToOfferDTO(dummyArrayOffer);

        int maxSize = Math.max(dummyArrayOffer.size(), arrayOfferDTOS.size());

        for (int i = 0; i < maxSize; i++) {
            Offer offer = dummyArrayOffer.get(i);
            OfferDTO offerDto = arrayOfferDTOS.get(i);

            assertThat(offerDto.getAddress()).isEqualTo(offer.getAddress());
            assertThat(offerDto.getDepartment()).isEqualTo(offer.getDepartment());
            assertThat(offerDto.getTitle()).isEqualTo(offer.getTitle());
            assertThat(offerDto.getDescription()).isEqualTo(offer.getDescription());
            assertThat(offerDto.getSalary()).isEqualTo(offer.getSalary());
        }
    }

    @Test
    public void testGetOffersByDepartment_withNoOffer() {
        String department = "myDepartmentWithNoOffer";

        List<OfferDTO> offers = offerService.getOffersByDepartment(department);

        assertThat(offers).isEmpty();
    }

    @Test
    public void testGetOffersByDepartment() {
        when(offerRepository.findAllByDepartment(any())).thenReturn(getDummyArrayOffer());

        List<OfferDTO> offers = offerService.getOffersByDepartment("Un departement");

        List<OfferDTO> mappedDtos = offerService.mapArrayToOfferDTO(getDummyArrayOffer());

        assertThat(mappedDtos).isEqualTo(offers);
    }

    @Test
    public void testFindOfferById(){
        Offer offer = getDummyOffer();
        when(offerRepository.findById(any())).thenReturn(Optional.of(offer));

        Optional<Offer> optionalOffer = offerService.findOfferById(offer.getId());

        assertThat(optionalOffer).isPresent();
        assertThat(optionalOffer.get()).isEqualTo(offer);
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