package usePostgres.repositories;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FacultyRepositoryTest {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void existDataForName() {
        var res = facultyRepository.existDataForFacultyName("Slytherin");

        assertTrue(res);
    }

    @Test
    public void getMaxID() {
        var res = studentRepository.getMaxID();

        assertTrue (res>0);
    }

    @Test
    public void existDataForNameStudent() {
        var res = studentRepository.existDataForStudentName("Tom Riddle");
        assertTrue( res);
    }

    @Test
    public void getMaxIDForFaculty() {
        var res = facultyRepository.getMaxID();

        assertTrue(res > 0);
    }

    @Test
    public void existDataForFaculty() {
        var res = studentRepository.existDataForFacultyId(102L);

        assertTrue(res);
    }

    @Test
    public void findByAge() {
        var res = studentRepository.findByAge(18);

        assertTrue(res.size() > 0);
    }

    @Test
    public void existStudent() {
        var res = facultyRepository.existDataForStudentId(101L);

        assertTrue(res);
    }

}
