package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.User;
import com.gestionnaire_de_stage.repository.OfferRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OfferServiceTest {

    @Mock
    private OfferService offerService;

    @Mock
    private MonitorService monitorService;

    @Mock
    private ManagerService managerService;

    private Monitor getDummyMonitor() {
        Monitor monitor = new Monitor();
        monitor.setFirstName("Ouss");
        monitor.setLastName("ama");
        monitor.setEmail("ouste@gmail.com");
        monitor.setPhone("5145555112");
        monitor.setDepartment("Informatique");
        monitor.setPassword("testPassword");
        return monitor;
    }

    private Manager getDummyManager() {
        Manager manager = new Manager();
        manager.setPassword("Test1234");
        manager.setEmail("oussamakably@gmail.com");
        manager.setFirstName("Oussama");
        manager.setLastName("Kably");
        manager.setPhone("5143643320");
        return manager;
    }

    private Offer getDummyOffer(User user) {
        Offer offer = new Offer();
        offer.setTitle("Job title");
        offer.setAddress("Job address");
        offer.setDepartment("Department sample");
        offer.setDescription("Job description");
        offer.setSalary(18.0d);
        offer.setCreator(user);
        return offer;
    }

    @Test
    @Order(1)
    @DisplayName("Injection tests")
    public void testMockitoInjections() {
        assertNotNull(monitorService);
        assertNotNull(offerService);
    }

    @Test
    @Order(2)
    @DisplayName("Dummies test validity")
    public void testDummiesValidity(){
        assertNotNull(getDummyManager());
        assertNotNull(getDummyMonitor());
        assertNotNull(getDummyOffer(null));
        assertNotNull(getDummyOffer(getDummyMonitor()));
        assertNotNull(getDummyOffer(getDummyManager()));
    }

    @Test
    @Order(3)
    @DisplayName("Create offer - getting with valid id")
    public void testGetByID_withValidID() {
        assertThat(offerService.create(any(Offer.class))).isEqualTo(offerService.getOneByID(1L));
    }

    @Test
    @Order(4)
    @DisplayName("Delete offer - null id")
    public void testDelete_withNullID() {
        lenient().when(offerService.create(any(Offer.class))).then(invocationOnMock -> {
            Optional<Offer> opt = invocationOnMock.getArgument(0);
            assertThat(offerService.deleteByID(null)).isEqualTo(Boolean.FALSE);
            return opt.isPresent();
        });
    }

    @Test
    @Order(5)
    @DisplayName("Creating offer - null object")
    public void createOffer_withNullOffer() {
        lenient().when(offerService.create(null)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(6)
    @DisplayName("Creating offer - null fields")
    public void testCreateOffer_withNullFields() {
        Offer nullMonitorOffer = new Offer();

        assertThat(nullMonitorOffer).isNotNull();
        lenient().when(offerService.create(nullMonitorOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(7)
    @DisplayName("Monitor - Creating offer - valid objects")
    public void createOfferMonitor_withNonNullEntry() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(any(Monitor.class))).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer nullDescriptionOffer = getDummyOffer(monitor.get());

        assertThat(nullDescriptionOffer).isNotNull();
        lenient().when(offerService.create(nullDescriptionOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(8)
    @DisplayName("Monitor - Creating offer - negative salary")
    public void testCreateOfferMonitor_withInvalidSalary() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(any(Monitor.class))).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer shortTitleOffer = getDummyOffer(monitor.get());
        shortTitleOffer.setSalary(-10.0d);

        assertThat(shortTitleOffer).isNotNull();
        lenient().when(offerService.create(shortTitleOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(9)
    @DisplayName("Monitor - Creating offer - null description")
    public void testCreateOfferMonitor_withNullDescription() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(any(Monitor.class))).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer nullDescriptionOffer = getDummyOffer(monitor.get());
        nullDescriptionOffer.setDescription(null);

        assertThat(nullDescriptionOffer).isNotNull();
        lenient().when(offerService.create(nullDescriptionOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(10)
    @DisplayName("Monitor Creating offer - blank description")
    public void testCreateOfferMonitor_withBlankDescription() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(monitor.get())).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer blankDescriptionOffer = getDummyOffer(monitor.get());
        blankDescriptionOffer.setDescription("  ");

        assertThat(blankDescriptionOffer).isNotNull();
        lenient().when(offerService.create(blankDescriptionOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(11)
    @DisplayName("Monitor - Creating offer - less characters in description")
    public void testCreateOfferMonitor_withLessCaractersInDescription() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(any(Monitor.class))).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer shortDescriptionOffer = getDummyOffer(monitor.get());
        shortDescriptionOffer.setDescription("a");

        assertThat(shortDescriptionOffer).isNotNull();
        lenient().when(offerService.create(shortDescriptionOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(12)
    @DisplayName("Monitor - Creating offer - null title")
    public void testCreateOfferMonitor_withNullTitle() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(monitor.get())).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer nullTitleOffer = getDummyOffer(monitor.get());
        nullTitleOffer.setTitle(null);

        assertThat(nullTitleOffer).isNotNull();
        lenient().when(offerService.create(nullTitleOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(13)
    @DisplayName("Monitor - Creating offer - blank title")
    public void testCreateOfferMonitor_withBlankTitle() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(any(Monitor.class))).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer blankTitleOffer = getDummyOffer(monitor.get());
        blankTitleOffer.setTitle("  ");

        assertThat(blankTitleOffer).isNotNull();
        lenient().when(offerService.create(blankTitleOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(14)
    @DisplayName("Monitor - Creating offer - less characters in title")
    public void testCreateOfferMonitor_withLessCaractersInTitle() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(null);

        lenient().when(monitorService.create(any(Monitor.class))).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();

        Offer shortTitleOffer = getDummyOffer(getDummyMonitor());
        shortTitleOffer.setTitle("a");

        assertThat(shortTitleOffer).isNotNull();
        lenient().when(offerService.create(shortTitleOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(15)
    @DisplayName("Monitor - Creating offer - null department")
    public void testCreateOfferMonitor_withNullDepartment() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(any(Monitor.class))).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer nullDepartmentOffer = getDummyOffer(monitor.get());
        nullDepartmentOffer.setDepartment(null);

        assertThat(nullDepartmentOffer).isNotNull();
        lenient().when(offerService.create(nullDepartmentOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(16)
    @DisplayName("Monitor - Creating offer - blank department")
    public void testCreateOfferMonitor_withBlankDepartment() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(any(Monitor.class))).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer blankDepartmentOffer = getDummyOffer(monitor.get());
        blankDepartmentOffer.setDepartment("  ");

        assertThat(blankDepartmentOffer).isNotNull();
        lenient().when(offerService.create(blankDepartmentOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(17)
    @DisplayName("Monitor - Creating offer - less characters in department")
    public void testCreateOfferMonitor_withLessCaractersInDepartment() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(any(Monitor.class))).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer shortDepartmentOffer = getDummyOffer(monitor.get());
        shortDepartmentOffer.setDepartment("a");

        assertThat(shortDepartmentOffer).isNotNull();
        lenient().when(offerService.create(shortDepartmentOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(18)
    @DisplayName("Monitor - Creating offer - null address")
    public void testCreateOfferMonitor_withNullAddress() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(any(Monitor.class))).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer nullAddressOffer = getDummyOffer(monitor.get());
        nullAddressOffer.setAddress(null);

        assertThat(nullAddressOffer).isNotNull();
        lenient().when(offerService.create(nullAddressOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(19)
    @DisplayName("Monitor - Creating offer - blank address")
    public void testCreateOfferMonitor_withBlankAddress() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(any(Monitor.class))).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer blankAddressOffer = getDummyOffer(monitor.get());
        blankAddressOffer.setAddress("  ");

        assertThat(blankAddressOffer).isNotNull();
        lenient().when(offerService.create(blankAddressOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(20)
    @DisplayName("Monitor - Creating offer - less characters in address")
    public void testCreateOfferMonitor_withLessCaractersInAddress() {
        AtomicReference<Monitor> monitor = new AtomicReference<>(getDummyMonitor());

        lenient().when(monitorService.create(any(Monitor.class))).then(invocationOnMock -> {
            Optional<Monitor> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(monitor::set);
            return mon.isPresent();
        });

        assertThat(monitor).isNotNull();
        assertThat(monitor.get()).isNotNull();

        Offer shortAddressOffer = getDummyOffer(monitor.get());
        shortAddressOffer.setAddress("a");

        assertThat(shortAddressOffer).isNotNull();
        lenient().when(offerService.create(shortAddressOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(21)
    @DisplayName("Manager - Creating offer - valid objects")
    public void createOfferManager_withNonNullEntry() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(any(Manager.class))).then(invocationOnMock -> {
            Optional<Manager> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(manager::set);
            return mon.isPresent();
        });

        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer nullDescriptionOffer = getDummyOffer(manager.get());

        assertThat(nullDescriptionOffer).isNotNull();
        lenient().when(offerService.create(nullDescriptionOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(22)
    @DisplayName("Manager - Creating offer - negative salary")
    public void testCreateOfferManager_withInvalidSalary() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(any(Manager.class))).then(invocationOnMock -> {
            Optional<Manager> man = invocationOnMock.getArgument(0);
            man.ifPresent(manager::set);
            return man.isPresent();
        });
        
        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer shortTitleOffer = getDummyOffer(manager.get());
        shortTitleOffer.setSalary(-10.0d);

        assertThat(shortTitleOffer).isNotNull();
        lenient().when(offerService.create(shortTitleOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(23)
    @DisplayName("Manager - Creating offer - null description")
    public void testCreateOfferManager_withNullDescription() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(any(Manager.class))).then(invocationOnMock -> {
            Optional<Manager> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(manager::set);
            return mon.isPresent();
        });
        
        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer nullDescriptionOffer = getDummyOffer(manager.get());
        nullDescriptionOffer.setDescription(null);

        assertThat(nullDescriptionOffer).isNotNull();
        lenient().when(offerService.create(nullDescriptionOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(24)
    @DisplayName("Manager - Creating offer - blank description")
    public void testCreateOfferManager_withBlankDescription() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(manager.get())).then(invocationOnMock -> {
            Optional<Manager> man = invocationOnMock.getArgument(0);
            man.ifPresent(manager::set);
            return man.isPresent();
        });

        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer blankDescriptionOffer = getDummyOffer(manager.get());
        blankDescriptionOffer.setDescription("  ");

        assertThat(blankDescriptionOffer).isNotNull();
        lenient().when(offerService.create(blankDescriptionOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(25)
    @DisplayName("Manager - Creating offer - less characters in description")
    public void testCreateOfferManager_withLessCaractersInDescription() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(any(Manager.class))).then(invocationOnMock -> {
            Optional<Manager> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(manager::set);
            return mon.isPresent();
        });

        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer shortDescriptionOffer = getDummyOffer(manager.get());
        shortDescriptionOffer.setDescription("a");

        assertThat(shortDescriptionOffer).isNotNull();
        lenient().when(offerService.create(shortDescriptionOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(26)
    @DisplayName("Manager - Creating offer - null title")
    public void testCreateOfferManager_withNullTitle() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(manager.get())).then(invocationOnMock -> {
            Optional<Manager> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(manager::set);
            return mon.isPresent();
        });

        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer nullTitleOffer = getDummyOffer(manager.get());
        nullTitleOffer.setTitle(null);

        assertThat(nullTitleOffer).isNotNull();
        lenient().when(offerService.create(nullTitleOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(27)
    @DisplayName("Manager - Creating offer - blank title")
    public void testCreateOfferManager_withBlankTitle() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(any(Manager.class))).then(invocationOnMock -> {
            Optional<Manager> man = invocationOnMock.getArgument(0);
            man.ifPresent(manager::set);
            return man.isPresent();
        });

        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer blankTitleOffer = getDummyOffer(manager.get());
        blankTitleOffer.setTitle("  ");

        assertThat(blankTitleOffer).isNotNull();
        lenient().when(offerService.create(blankTitleOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(28)
    @DisplayName("Manager - Creating offer - less characters in title")
    public void testCreateOfferManager_withLessCaractersInTitle() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(any(Manager.class))).then(invocationOnMock -> {
            Optional<Manager> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(manager::set);
            return mon.isPresent();
        });

        assertThat(manager).isNotNull();

        Offer shortTitleOffer = getDummyOffer(getDummyMonitor());
        shortTitleOffer.setTitle("a");

        assertThat(shortTitleOffer).isNotNull();
        lenient().when(offerService.create(shortTitleOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(29)
    @DisplayName("Manager - Creating offer - null department")
    public void testCreateOfferManager_withNullDepartment() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(any(Manager.class))).then(invocationOnMock -> {
            Optional<Manager> man = invocationOnMock.getArgument(0);
            man.ifPresent(manager::set);
            return man.isPresent();
        });

        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer nullDepartmentOffer = getDummyOffer(manager.get());
        nullDepartmentOffer.setDepartment(null);

        assertThat(nullDepartmentOffer).isNotNull();
        lenient().when(offerService.create(nullDepartmentOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(30)
    @DisplayName("Manager - Creating offer - blank department")
    public void testCreateOfferManager_withBlankDepartment() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(any(Manager.class))).then(invocationOnMock -> {
            Optional<Manager> man = invocationOnMock.getArgument(0);
            man.ifPresent(manager::set);
            return man.isPresent();
        });

        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer blankDepartmentOffer = getDummyOffer(manager.get());
        blankDepartmentOffer.setDepartment("  ");

        assertThat(blankDepartmentOffer).isNotNull();
        lenient().when(offerService.create(blankDepartmentOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(31)
    @DisplayName("Manager - Creating offer - less characters in department")
    public void testCreateOfferManager_withLessCaractersInDepartment() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(any(Manager.class))).then(invocationOnMock -> {
            Optional<Manager> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(manager::set);
            return mon.isPresent();
        });

        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer shortDepartmentOffer = getDummyOffer(manager.get());
        shortDepartmentOffer.setDepartment("a");

        assertThat(shortDepartmentOffer).isNotNull();
        lenient().when(offerService.create(shortDepartmentOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(32)
    @DisplayName("Manager - Creating offer - null address")
    public void testCreateOfferManager_withNullAddress() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(any(Manager.class))).then(invocationOnMock -> {
            Optional<Manager> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(manager::set);
            return mon.isPresent();
        });

        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer nullAddressOffer = getDummyOffer(manager.get());
        nullAddressOffer.setAddress(null);

        assertThat(nullAddressOffer).isNotNull();
        lenient().when(offerService.create(nullAddressOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(33)
    @DisplayName("Manager - Creating offer - blank address")
    public void testCreateOfferManager_withBlankAddress() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(any(Manager.class))).then(invocationOnMock -> {
            Optional<Manager> man = invocationOnMock.getArgument(0);
            man.ifPresent(manager::set);
            return man.isPresent();
        });

        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer blankAddressOffer = getDummyOffer(manager.get());
        blankAddressOffer.setAddress("  ");

        assertThat(blankAddressOffer).isNotNull();
        lenient().when(offerService.create(blankAddressOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }

    @Test
    @Order(34)
    @DisplayName("Manager - Creating offer - less characters in address")
    public void testCreateOfferManager_withLessCaractersInAddress() {
        AtomicReference<Manager> manager = new AtomicReference<>(getDummyManager());

        lenient().when(managerService.create(any(Manager.class))).then(invocationOnMock -> {
            Optional<Manager> mon = invocationOnMock.getArgument(0);
            mon.ifPresent(manager::set);
            return mon.isPresent();
        });

        assertThat(manager).isNotNull();
        assertThat(manager.get()).isNotNull();

        Offer shortAddressOffer = getDummyOffer(manager.get());
        shortAddressOffer.setDescription("a");

        assertThat(shortAddressOffer).isNotNull();
        lenient().when(offerService.create(shortAddressOffer)).thenThrow(new ValidationException()).thenReturn(Optional.empty());
    }
}