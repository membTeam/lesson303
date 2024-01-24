package usePostgres.repositories;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private FacultyRepository facultyRepository;


    @Test
    public void getMaxIdStudent() {
        var res = studentRepo.getMaxID();

        assertTrue(res > 0);
    }

    @Test
    public void findStudentsForFaculty() {
        var res = studentRepo.findStudentInFaculty(1L);

        assertTrue(res.size()>0);
    }

    @Test
    public void findByAgeBetween() {
        var res = studentRepo.findByAgeBetween(10,20);

        assertTrue(res.size()>0);
    }

    @Test
    public void findByStudentForFaculty() {
        var faculty = "Gryffindor";
        var res = studentRepo.findStudentInFaculty(faculty);

        assertTrue(res.size()>0);
    }

    @Test
    public void findStudentForFacultyExt() {
        var data = studentRepo.findStudentInFaculty(1L);

        List<RecDataStudent> lsRecordData = data.stream()
                .map(obj-> new RecDataStudent(obj)).collect(Collectors.toList());

        assertNotNull(lsRecordData);
        assertTrue(lsRecordData.size()>0);

        assertEquals("Gryffindor", lsRecordData.get(0).facultyName());
        assertEquals("Tom Riddle", lsRecordData.get(0).name());

    }

}
