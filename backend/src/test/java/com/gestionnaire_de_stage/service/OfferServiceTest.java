package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.OfferAlreadyExistsException;
import com.gestionnaire_de_stage.model.Monitor;
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
    public void testMapToOffer_withNullDto() {
        Offer mappedOffer = offerService.mapToOffer(null);

        assertThat(mappedOffer).isNull();
    }

    @Test
    public void testMapToOfferDto_withNullOffer() {
        OfferDTO mappedOfferDto = offerService.mapToOfferDTO(null);

        assertThat(mappedOfferDto).isNull();
    }

    @Test
    public void testMapToOffer() {
        OfferDTO dummyOfferDto = getDummyOfferDto();

        Offer mappedOffer = offerService.mapToOffer(dummyOfferDto);

        assertThat(mappedOffer.getId()).isNull();
        assertThat(dummyOfferDto.getAddress()).isEqualTo(mappedOffer.getAddress());
        assertThat(dummyOfferDto.getDepartment()).isEqualTo(mappedOffer.getDepartment());
        assertThat(dummyOfferDto.getDescription()).isEqualTo(mappedOffer.getDescription());
        assertThat(dummyOfferDto.getSalary()).isEqualTo(mappedOffer.getSalary());
    }

    @Test
    public void testMapToOfferDto() {
        Offer dummyOffer = getDummyOffer();

        OfferDTO mappedOfferDto = offerService.mapToOfferDTO(dummyOffer);

        assertThat(mappedOfferDto.getAddress()).isEqualTo(dummyOffer.getAddress());
        assertThat(mappedOfferDto.getDepartment()).isEqualTo(dummyOffer.getDepartment());
        assertThat(mappedOfferDto.getDescription()).isEqualTo(dummyOffer.getDescription());
        assertThat(mappedOfferDto.getTitle()).isEqualTo(dummyOffer.getTitle());
        assertThat(mappedOfferDto.getSalary()).isEqualTo(dummyOffer.getSalary());
    }

    @Test
    public void testCreateOffer_withNullOffer() {
        assertThrows(IllegalArgumentException.class,
                () -> offerService.create(null));
    }

    @Test
    public void testCreateOffer_withExistingOffer() {
        final Offer dummyOffer = getDummyOffer();
        when(offerRepository.findOne(any())).thenReturn(Optional.of(dummyOffer));

        assertThrows(OfferAlreadyExistsException.class,
                () -> offerService.create(dummyOffer));
    }

    @Test
    public void testCreateOffer_withValidOffer() throws OfferAlreadyExistsException {
        Offer dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerRepository.save(any())).thenReturn(getDummyOffer());
        when(offerRepository.findOne(any())).thenReturn(Optional.empty());

        Offer actualOffer = offerService.create(dummyOffer);

        assertThat(actualOffer).isNotNull();
        assertThat(actualOffer.getId())
                .isNotNull()
                .isGreaterThan(0);
    }

    @Test
    public void testUpdateOffer_withNullId() {
        final Offer dummyOffer = getDummyOffer();
        dummyOffer.setId(null);

        assertThrows(IllegalArgumentException.class,
                () -> offerService.update(dummyOffer));
    }

    @Test
    public void testUpdateOffer_withNullOffer() {
        assertThrows(IllegalArgumentException.class,
                () -> offerService.update(null));
    }

    @Test
    public void testUpdateOffer_withInvalidOffer() {
        Offer dummyOffer = getDummyOffer();
        when(offerRepository.existsById(1L)).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> offerService.update(dummyOffer));
    }

    @Test
    public void testUpdateOffer_withValidOffer() throws IdDoesNotExistException {
        Offer dummyOffer = getDummyOffer();
        when(offerRepository.existsById(1L)).thenReturn(true);
        when(offerRepository.save(any())).thenReturn(dummyOffer);

        Offer actualOffer = offerService.update(dummyOffer);

        assertThat(actualOffer)
                .isNotNull()
                .isEqualTo(dummyOffer);
    }

    @Test
    public void testGetAllOffers_withValidList() {
        List<Offer> dummyOfferList = getDummyOfferList();
        when(offerRepository.findAll()).thenReturn(dummyOfferList);

        List<Offer> actualOfferList = offerService.getAll();

        assertThat(actualOfferList).isEqualTo(dummyOfferList);
    }

    @Test
    public void testGetAllOffers_withEmptyList() {
        when(offerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Offer> actualOfferList = offerService.getAll();

        assertThat(actualOfferList).isEmpty();
    }

    @Test
    public void testMapArrayToOfferDto() {
        List<Offer> dummyOfferList = getDummyOfferList();

        List<OfferDTO> mappedOfferDtoList = offerService.mapArrayToOfferDTO(dummyOfferList);

        assertThat(mappedOfferDtoList.size()).isEqualTo(dummyOfferList.size());
        for (int i = 0, size = mappedOfferDtoList.size(); i < size; i++) {
            Offer dummyOffer = dummyOfferList.get(i);
            OfferDTO actualOfferDto = mappedOfferDtoList.get(i);

            assertThat(actualOfferDto.getAddress()).isEqualTo(dummyOffer.getAddress());
            assertThat(actualOfferDto.getDepartment()).isEqualTo(dummyOffer.getDepartment());
            assertThat(actualOfferDto.getTitle()).isEqualTo(dummyOffer.getTitle());
            assertThat(actualOfferDto.getDescription()).isEqualTo(dummyOffer.getDescription());
            assertThat(actualOfferDto.getSalary()).isEqualTo(dummyOffer.getSalary());
        }
    }

    @Test
    public void testGetOffersByDepartment_withNoOffer() {
        String department = "myDepartmentWithNoOffer";

        List<OfferDTO> actualOfferDtoList = offerService.getOffersByDepartment(department);

        assertThat(actualOfferDtoList).isEmpty();
    }

    @Test
    public void testGetOffersByDepartment() {
        List<Offer> dummyOfferList = getDummyOfferList();
        List<OfferDTO> mappedOfferDtoList = offerService.mapArrayToOfferDTO(dummyOfferList);
        when(offerRepository.findAllByDepartment(any())).thenReturn(dummyOfferList);

        List<OfferDTO> actualOfferDtoList = offerService.getOffersByDepartment("Un departement");

        assertThat(actualOfferDtoList).isEqualTo(mappedOfferDtoList);
    }

    @Test
    public void testFindOfferById() {
        Offer dummyOffer = getDummyOffer();
        when(offerRepository.findById(any())).thenReturn(Optional.of(dummyOffer));

        Optional<Offer> actualOffer = offerService.findOfferById(dummyOffer.getId());

        assertThat(actualOffer).isPresent();
        assertThat(actualOffer.get()).isEqualTo(dummyOffer);
    }

    private List<Offer> getDummyOfferList() {
        List<Offer> dummyOfferList = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            Offer dummyOffer = getDummyOffer();
            dummyOffer.setId(i);
            dummyOfferList.add(dummyOffer);
        }
        return dummyOfferList;
    }


    private Offer getDummyOffer() {
        Offer dummyOffer = new Offer();
        dummyOffer.setCreator(getDummyMonitor());
        dummyOffer.setDepartment("Un departement");
        dummyOffer.setAddress("ajsaodas");
        dummyOffer.setId(1L);
        dummyOffer.setDescription("oeinoiendw");
        dummyOffer.setSalary(10);
        dummyOffer.setTitle("oeinoiendw");
        return dummyOffer;
    }

    private Monitor getDummyMonitor() {
        Monitor dummyMonitor = new Monitor();
        dummyMonitor.setId(1L);
        dummyMonitor.setFirstName("same");
        dummyMonitor.setLastName("dude");
        dummyMonitor.setEmail("dudesame@gmail.com");
        dummyMonitor.setPhone("5145555112");
        dummyMonitor.setDepartment("Informatique");
        dummyMonitor.setPassword("testPassword");
        return dummyMonitor;
    }

    private OfferDTO getDummyOfferDto() {
        OfferDTO dummyOfferDTO = new OfferDTO();
        dummyOfferDTO.setCreator_id(1L);
        dummyOfferDTO.setSalary(18.0d);
        dummyOfferDTO.setDescription("Une description");
        dummyOfferDTO.setAddress("Addresse du cégep");
        dummyOfferDTO.setTitle("Offer title");
        dummyOfferDTO.setDepartment("Department name");
        return dummyOfferDTO;
    }
}