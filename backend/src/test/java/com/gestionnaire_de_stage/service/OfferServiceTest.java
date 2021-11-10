package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.dto.ValidationOffer;
import com.gestionnaire_de_stage.enums.TypeSession;
import com.gestionnaire_de_stage.exception.*;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.Session;
import com.gestionnaire_de_stage.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private MonitorService monitorService;

    @Mock
    private SessionService sessionService;

    @Mock
    private Clock clock;

    private Clock fixedClock;

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
        when(offerRepository.findOne(any())).thenReturn(Optional.of(getDummyOffer()));

        assertThrows(OfferAlreadyExistsException.class,
                () -> offerService.create(getDummyOfferDto()));
    }

    @Test
    public void testCreateOffer_withValidOffer() throws OfferAlreadyExistsException, EmailDoesNotExistException, SessionDoesNotExistException, IdDoesNotExistException {

        Offer dummyOffer = getDummyOffer();
        when(offerRepository.save(any())).thenReturn(dummyOffer);
        when(offerRepository.findOne(any())).thenReturn(Optional.empty());
        when(monitorService.getOneByEmail(any())).thenReturn(getDummyMonitor());
        when(sessionService.getOneBySessionId(any())).thenReturn(dummyOffer.getSession());
        Offer actualOffer = offerService.create(getDummyOfferDto());

        assertThat(actualOffer).isNotNull();
        assertThat(actualOffer.getId())
                .isNotNull()
                .isGreaterThan(0);
    }

    @Test
    public void testUpdateOffer_withNullId() {

        assertThrows(IllegalArgumentException.class,
                () -> offerService.validation(new ValidationOffer(null, true)));
    }

    @Test
    public void testUpdateOffer_withOfferNonExistant() {
        when(offerRepository.existsById(any())).thenReturn(false);
        assertThrows(IdDoesNotExistException.class,
                () -> offerService.validation(new ValidationOffer(1L, true)));
    }

    @Test
    public void testUpdateOffer_withInvalidOffer() {
        Offer dummyOffer = getDummyOffer();
        when(offerRepository.existsById(1L)).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> offerService.validation(new ValidationOffer(dummyOffer.getId(), false)));
    }

    @Test
    public void testUpdateOffer_withOfferAlreadyTreated() {
        Offer dummyOffer = getDummyOffer();
        when(offerRepository.existsById(1L)).thenReturn(true);
        when(offerRepository.existsByIdAndValidNotNull(any())).thenReturn(true);

        assertThrows(OfferAlreadyTreatedException.class,
                () -> offerService.validation(new ValidationOffer(dummyOffer.getId(), false)));
    }

    @Test
    public void testUpdateOffer_withValidOffer() throws IdDoesNotExistException, OfferAlreadyTreatedException {
        Offer dummyOffer = getDummyOffer();
        when(offerRepository.existsById(any())).thenReturn(true);
        when(offerRepository.existsByIdAndValidNotNull(any())).thenReturn(false);
        when(offerRepository.getById(any())).thenReturn(dummyOffer);
        when(offerRepository.save(any())).thenReturn(dummyOffer);

        Offer actualOffer = offerService.validation(new ValidationOffer(dummyOffer.getId(), true));

        assertThat(actualOffer)
                .isNotNull()
                .isEqualTo(dummyOffer);
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
        fixedClock = Clock.fixed(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        List<Offer> actualOfferDtoList = offerService.getOffersByDepartment(department);

        assertThat(actualOfferDtoList).isEmpty();
    }

    @Test
    public void testGetOffersByDepartment() {
        List<Offer> dummyOfferList = getDummyOfferWithDifferentSession();
        fixedClock = Clock.fixed(LocalDate.of(Year.now().getValue(), 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
        when(offerRepository.findAllByDepartmentIgnoreCaseAndValidIsTrueAndSession_YearGreaterThanEqual(any(),any())).thenReturn(dummyOfferList);

        List<Offer> actualOfferDtoList = offerService.getOffersByDepartment("Un departement");

        assertThat(actualOfferDtoList).isEqualTo(dummyOfferList);
    }
    @Test
    public void testGetOffersByDepartment_forNextYear() {
        List<Offer> dummyOfferList = getDummyOfferWithDifferentSession();
        fixedClock = Clock.fixed(LocalDate.of(Year.now().getValue(), 10, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
        when(offerRepository.findAllByDepartmentIgnoreCaseAndValidIsTrueAndSession_YearGreaterThanEqual(any(),any())).thenReturn(dummyOfferList);

        List<Offer> actualOfferDtoList = offerService.getOffersByDepartment("Un departement");

        assertThat(actualOfferDtoList).isEqualTo(dummyOfferList);
    }
    @Test
    public void testGetOffersByDepartment_SummerAndFuture() {
        List<Offer> dummyOfferList = getDummyOfferWithDifferentSession();
        System.out.println(dummyOfferList.size());
        fixedClock = Clock.fixed(LocalDate.of(Year.now().getValue(), 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
        when(offerRepository.findAllByDepartmentIgnoreCaseAndValidIsTrueAndSession_YearGreaterThanEqual(any(),any())).thenReturn(dummyOfferList);

        List<Offer> actualOfferDtoList = offerService.getOffersByDepartment("Un departement");

        System.out.println(actualOfferDtoList.size());
        assertThat(actualOfferDtoList).containsExactlyInAnyOrder(dummyOfferList.get(0), dummyOfferList.get(1), dummyOfferList.get(2), dummyOfferList.get(3));
    }



    @Test
    public void testGetOffersByDepartment_withNullDepartment() {
        List<Offer> dummyOfferList = getDummyOfferList();

        assertThrows(IllegalArgumentException.class,
                () -> offerService.getOffersByDepartment(null));
    }

    @Test
    public void testFindOfferById() {
        Offer dummyOffer = getDummyOffer();
        when(offerRepository.findById(any())).thenReturn(Optional.of(dummyOffer));

        Optional<Offer> actualOffer = offerService.findOfferById(dummyOffer.getId());

        assertThat(actualOffer).isPresent();
        assertThat(actualOffer.get()).isEqualTo(dummyOffer);
    }

    @Test
    void testGetValidOffers() {
        List<Offer> dummyArrayOffer = getDummyArrayOffer();
        when(offerRepository.findAllByValid(any())).thenReturn(dummyArrayOffer);

        List<Offer> returnedOffers = offerService.getValidOffers();

        assertThat(returnedOffers).isEqualTo(dummyArrayOffer);
    }

    @Test
    void testGetNotValidatedOffers() {
        List<Offer> dummyArrayOffer = getDummyArrayOffer();
        when(offerRepository.findAllByValidNull()).thenReturn(dummyArrayOffer);

        List<Offer> returnedOffers = offerService.getNotValidatedOffers();

        assertThat(returnedOffers).isEqualTo(dummyArrayOffer);
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
        dummyOffer.setDateDebut(LocalDate.now());
        dummyOffer.setDateFin(LocalDate.now().plusMonths(1));
        dummyOffer.setSession(getDummySession());
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
    private List<Offer> getDummyOfferWithDifferentSession() {
        List<Offer> dummyOfferList = getDummyOfferList();

        Offer dummyOffer = getDummyOffer();
        dummyOffer.getSession().setYear(Year.now().plusYears(1));
        dummyOfferList.add(dummyOffer);

        Offer dummyOffer2 = getDummyOffer();
        dummyOffer2.getSession().setTypeSession(TypeSession.HIVER);
        dummyOfferList.add(dummyOffer2);

        return dummyOfferList;
    }
    private OfferDTO getDummyOfferDto() {
        OfferDTO dummyOfferDTO = new OfferDTO();
        dummyOfferDTO.setCreator_email("thisemail@email.com");
        dummyOfferDTO.setSalary(18.0d);
        dummyOfferDTO.setDescription("Une description");
        dummyOfferDTO.setAddress("Addresse du cégep");
        dummyOfferDTO.setTitle("Offer title");
        dummyOfferDTO.setDepartment("Department name");
        dummyOfferDTO.setDateDebut(LocalDate.now());
        dummyOfferDTO.setDateFin(LocalDate.now().plusMonths(1));
        dummyOfferDTO.setIdSession(1L);
        return dummyOfferDTO;
    }

    private List<Offer> getDummyArrayOffer() {
        List<Offer> dummyArrayOffer = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            Offer dummyOffer = getDummyOffer();
            dummyOffer.setId(i);
            dummyArrayOffer.add(dummyOffer);
        }
        return dummyArrayOffer;
    }

    private Session getDummySession() {
        Session session = new Session();
        session.setId(1L);
        session.setTypeSession(TypeSession.ETE);
        session.setYear(Year.now());
        return session;
    }
}