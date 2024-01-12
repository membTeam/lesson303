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
    public void findStudentsForFaculty() {
        var res = studentRepo.findStudentsByFacultyId(1L);

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
        var res = studentRepo.findStudentForFaculty(faculty);

        assertTrue(res.size()>0);
    }

    // TODO: Надо разобраться с методом из репозитория
    /*@Test
    public void findStudentsByIdFaculty() {
        var res = studentRepo.findStudentsForIdFaculty(1L);

        assertNotNull(res);
        assertTrue(res.size()>0);
    }*/

    @Test
    public void findStudentForFacultyExt() {
        List<DataStudent> data = studentRepo.findStudentForFacultyExt(1L);

        List<RecDataStudent> lsRecordData = data.stream().map(obj-> new RecDataStudent(
                    obj.getId(), obj.getFacultyName(), obj.getName(), obj.getAge() )
        ).collect(Collectors.toList());

        assertNotNull(lsRecordData);
        assertTrue(lsRecordData.size()>0);

        assertEquals("Gryffindor", lsRecordData.get(0).facultyString());
        assertEquals("Lucius Malfoy", lsRecordData.get(0).name());

    }

}
