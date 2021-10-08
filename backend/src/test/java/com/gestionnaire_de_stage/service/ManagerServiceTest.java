package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.ManagerRepository;
import com.gestionnaire_de_stage.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = JpaRepository.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ManagerServiceTest {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private StudentRepository studentRepository;

    private long last_id;

    private Manager getDummyManager() {
        Manager manager = new Manager();
        manager.setPassword("Test1234");
        manager.setEmail("oussamakably@gmail.com");
        manager.setFirstName("Oussama");
        manager.setLastName("Kably");
        manager.setPhone("5143643320");
        return manager;
    }

    private Student getDummyStudent() {
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


    @BeforeEach
    public void init() {
        last_id = managerRepository.save(getDummyManager()).getId();
    }

    @AfterEach
    public void after() {
        managerRepository.deleteAll();
    }

    @Test
    @DisplayName("Test findAll pour ManagerRepository")
    public void testFindall() {
        Manager manager = getDummyManager();
        manager.setId(1L);
        assertThat(managerRepository.findAll()).hasSize(1).contains(manager);
    }

    @Test
    @DisplayName("Test créer un manager null")
    public void testCreate_withNullManager() throws Exception {
        Optional<Manager> manager = Optional.empty();
        try {
            manager = managerService.create(null);
        } catch (Exception e) {
            fail(e);
        }
        assertTrue(manager.isEmpty());
    }

    @Test
    @DisplayName("test créer un manager valide")
    public void testCreate_withValidManager() {
        Optional<Manager> manager = Optional.empty();
        Manager dummy = getDummyManager();
        dummy.setId(1L);
        try {
            manager = managerService.create(dummy);
        } catch (Exception e) {
            fail(e);
        }
        assertFalse(manager.isEmpty());
    }

    @Test
    @DisplayName("test chercher par id valide un manager")
    public void testGetByID_withValidID() {
        Long validID = managerService.getAll().get(0).getId();

        Optional<Manager> actual = managerService.getOneByID(validID);

        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("test chercher par id null un manager")
    public void testGetByID_withNullID() {
        Optional<Manager> actual = managerService.getOneByID(null);

        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("test chercher tous les managers")
    public void testGetAll() {
        int expectedLength = 1;

        List<Manager> monitorList = managerService.getAll();

        assertEquals(expectedLength, monitorList.size());
    }

    @Test
    @DisplayName("test modifier un manager par des données valides")
    public void testUpdate_withValidEntries() {
        Manager manager = getDummyManager();
        Long validID = 2L;

        Optional<Manager> actual = Optional.empty();
        try {
            actual = managerService.update(manager, validID);
        } catch (Exception e) {
            fail(e);
        }

        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("test modifier un manager par des données vides")
    public void testUpdate_withNullEntries() {
        Manager manager = getDummyManager();

        Optional<Manager> actual = Optional.empty();
        try {
            actual = managerService.update(manager, null);
        } catch (Exception e) {
            fail(e);
        }

        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("test supprimer un manager avec un id valide")
    public void testDelete_withValidID() {
        Long validID = 1L;

        boolean actual = managerService.deleteByID(validID);

        assertFalse(actual);
    }

    @Test
    @DisplayName("test supprimer un manager avec un id invalide")
    public void testDelete_withNullID() {
        boolean actual = managerService.deleteByID(null);

        assertFalse(actual);
    }

    @Test
    @DisplayName("test chercher un manager par email et mot de passe")
    public void testFindManagerByEmailAndPassword() {
        String email = "oussamakably@gmail.com";
        String password = "Test1234";

        Optional<Manager> manager = managerRepository.findManagerByEmailAndPassword(email, password);
        assertTrue(manager.isPresent());
        Manager m = null;
        try{
            m = manager.orElseThrow();
            assertEquals(m.getFirstName(), "Oussama");
        }catch (Exception e){
            fail(e);
        }
    }

    @Test
    @DisplayName("test manager existByEmailAndPassword avec des données valides")
    public void testExistsByEmailAndPassword_withValidEntries() {
        String email = "oussamakably@gmail.com";
        String password = "Test1234";

        boolean actual = managerRepository.findManagerByEmailAndPassword(email, password).isPresent();

        assertTrue(actual);
    }

    @Test
    @DisplayName("test manager existByEmailAndPassword du repository avec des données invalides")
    public void testExistsByEmailAndPassword_withNullEntries() {
        boolean actual = managerRepository.findManagerByEmailAndPassword(null, null).isPresent();

        assertFalse(actual);
    }

    @Test
    @DisplayName("test manager getOneByEmailAndPassword du repository avec des données valides")
    public void testGetOneByEmailAndPassword_withValidEntries() {
        String email = "oussamakably@gmail.com";
        String password = "Test1234";

        Optional<Manager> actual = managerService.getOneByEmailAndPassword(email, password);

        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("test manager getOneByEmailAndPassword avec des données invalides")
    public void testGetOneByEmailAndPassword_withNullEntries() {
        Optional<Manager> actual = managerService.getOneByEmailAndPassword(null, null);

        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("test manager validate curriculum valid data")
    public void test_validateCurriculum_valid(){
        AtomicReference<Student> student = new AtomicReference<>(getDummyStudent());

        assertDoesNotThrow(() -> student.set(studentRepository.save(student.get())));

        assertTrue(last_id > 0);
        assertTrue(managerService.validateCurriculum(true, student.get().getId()));
        assertNotNull(student);
        assertNotNull(student.get());
        assertTrue(student.get().isCurriculumValidated());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    @DisplayName("test manager validate curriculum invalid data")
    public void test_validateCurriculum_invalid(){
        assertThrows(Exception.class,() -> studentRepository.save(null));

        assertTrue(last_id > 0);

        assertFalse(managerService.validateCurriculum(true, 1));
    }
}