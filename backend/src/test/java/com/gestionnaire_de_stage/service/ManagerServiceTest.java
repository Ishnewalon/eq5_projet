package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.ManagerAlreadyExistsException;
import com.gestionnaire_de_stage.exception.StudentAlreadyExistsException;
import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.ManagerRepository;
import com.gestionnaire_de_stage.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {

    @InjectMocks
    private ManagerService managerService;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private StudentRepository studentRepository;

    @Test
    public void testCreate_withValidManager() throws ManagerAlreadyExistsException {
        Manager manager = getManager();
        when(managerRepository.save(any())).thenReturn(manager);

        Manager actual = managerService.create(manager);

        assertThat(actual.getEmail()).isEqualTo(manager.getEmail());
    }

    private Manager getManager() {
        Manager manager = new Manager();
        manager.setPassword("Test1234");
        manager.setEmail("oussamakably@gmail.com");
        manager.setFirstName("Oussama");
        manager.setLastName("Kably");
        manager.setPhone("5143643320");
        return manager;
    }

    @Test
    public void testCreate_withNullManager() {
        assertThrows(IllegalArgumentException.class, () -> {
            managerService.create(null);
        });
    }

    @Test
    public void testCreate_alreadyExistsManager() {
        when(managerRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(ManagerAlreadyExistsException.class, () -> {
            managerService.create(getManager());
        });
    }

    @Test
    public void testGetByID_withValidID() throws IdDoesNotExistException {
        Long validID = 1L;
        Manager manager = getManager();
        when(managerRepository.existsById(validID)).thenReturn(true);
        when(managerRepository.getById(any())).thenReturn(manager);

        Manager actual = managerService.getOneByID(validID);

        assertThat(actual.getEmail()).isEqualTo(manager.getEmail());
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class, () -> {
            managerService.getOneByID(null);
        });
    }

    @Test
    public void testGetByID_doesntExistID() {
        Manager manager = getManager();
        manager.setId(1L);
        when(managerRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class, () -> {
            managerService.getOneByID(manager.getId());
        });
    }

    @Test
    public void testGetAll() {
        when(managerRepository.findAll()).thenReturn(getListOfManagers());

        final List<Manager> allManagers = managerService.getAll();

        assertThat(allManagers.size()).isEqualTo(3);
        assertThat(allManagers.get(0).getFirstName()).isEqualTo("Oussama");
    }

    private List<Manager> getListOfManagers() {
        List<Manager> managerList = new ArrayList<>();
        Long idIterator = 1L;
        for (int i = 0; i < 3; i++) {
            Manager manager = getManager();
            manager.setId(idIterator);
            managerList.add(manager);
            idIterator++;
        }
        return managerList;
    }

    @Test
    public void testUpdate_withValidEntries() throws  IdDoesNotExistException {
        Manager manager = getManager();
        manager.setId(2L);
        when(managerRepository.existsById(any())).thenReturn(true);
        when(managerRepository.save(any())).thenReturn(manager);

        Manager actual = managerService.update(manager, manager.getId());

        assertThat(actual.getEmail()).isEqualTo(manager.getEmail());
    }

    @Test
    public void testUpdate_withNullID() {
        Manager manager = getManager();

        assertThrows(IllegalArgumentException.class, () -> {
            managerService.update(manager, null);
        });
    }

    @Test
    public void testUpdate_withNullStudent() {
        assertThrows(IllegalArgumentException.class, () -> {
            managerService.update(null, 1L);
        });
    }

    @Test
    public void testUpdate_doesntExistID() {
        Manager manager = getManager();
        manager.setId(1L);
        when(managerRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class, () -> {
            managerService.update(manager, manager.getId());
        });
    }

    @Test
    public void testDelete_withValidID() throws IdDoesNotExistException {
        Long validID = 1L;
        when(managerRepository.existsById(any())).thenReturn(true);
        doNothing().when(managerRepository).deleteById(any());

        managerService.deleteByID(validID);

        verify(managerRepository, times(1)).deleteById(any());
    }

    @Test
    public void testDelete_withNullID() {
        assertThrows(IllegalArgumentException.class, () -> {
            managerService.deleteByID(null);
        });
    }

    @Test
    public void testDelete_doesntExistID() {
        Long id = 1L;
        when(managerRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class, () -> {
            managerService.deleteByID(id);
        });
    }

    @Test
    public void testGetOneByEmailAndPassword_withValidEntries() throws EmailAndPasswordDoesNotExistException {
        Manager manager = getManager();
        when(managerRepository.existsByEmailAndPassword(any(), any()))
                .thenReturn(true);
        when(managerRepository.findManagerByEmailAndPassword(any(), any()))
                .thenReturn(manager);

        Manager actual = managerService.getOneByEmailAndPassword(manager.getEmail(), manager.getPassword());

        assertThat(actual.getFirstName()).isEqualTo(manager.getFirstName());
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullEmail() {
        Manager manager = getManager();

        assertThrows(IllegalArgumentException.class, () -> {
            managerService.getOneByEmailAndPassword(null, manager.getPassword());
        });
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullPassword() {
        Manager manager = getManager();

        assertThrows(IllegalArgumentException.class, () -> {
            managerService.getOneByEmailAndPassword(manager.getEmail(), null);
        });
    }

    @Test
    public void testGetOneByEmailAndPassword_doesntExistEmailAndPassword() {
        Manager manager = getManager();
        when(managerRepository.existsByEmailAndPassword(any(), any())).thenReturn(false);

        assertThrows(EmailAndPasswordDoesNotExistException.class, () -> {
            managerService.getOneByEmailAndPassword(manager.getEmail(), manager.getPassword());
        });
    }

  /*  private Student getStudent() {
        Student student = new Student();
        student.setLastName("Scott");
        student.setFirstName("Jordan");
        student.setEmail("jscotty@gmail.com");
        student.setPhone("514-546-2375");
        student.setPassword("rockrocks");
        student.setAddress("758 George");
        student.setCity("LaSalle");
        student.setDepartment("Informatique");
        student.setPostalCode("H5N 9F2");
        student.setMatricule("6473943");
        return student;
    }

    @Test
    public void testValidateCurriculum_withValidCurriculum() throws IdDoesNotExistException {
        Student student = getStudent();
        Student student1 = getStudent();
        student1.setCurriculumValidated(true);
        when(studentRepository.existsById(any())).thenReturn(true);
        when(studentRepository.getById(any())).thenReturn(student);
        when(studentRepository.save(any())).thenReturn(student1);

        boolean actual = managerService.validateCurriculum(true, student.getId());

        assertThat(actual).isTrue();
    }*/

/*    @Test
    @DisplayName("test manager validate curriculum valid data")
    public void test_validateCurriculum_valid() throws Exception {
        AtomicReference<Student> student = new AtomicReference<>(getStudent());

        assertDoesNotThrow(() -> student.set(studentRepository.save(student.get())));

     //   assertTrue(last_id > 0);
        assertTrue(managerService.validateCurriculum(true, student.get().getId()));
        assertNotNull(student);
        assertNotNull(student.get());
        assertTrue(student.get().isCurriculumValidated());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    @DisplayName("test manager validate curriculum invalid data")
    public void test_validateCurriculum_invalid() throws Exception {
        assertThrows(Exception.class,() -> studentRepository.save(null));

     //   assertTrue(last_id > 0);

        assertFalse(managerService.validateCurriculum(true, 1));
    }*/
}