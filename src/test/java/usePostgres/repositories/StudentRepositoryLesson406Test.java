package usePostgres.repositories;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import usePostgres.service.StudentService;

@SpringBootTest
public class StudentRepositoryLesson406Test {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private StudentService studentService;

    @Test
    public void allStudentByRecDataStudent() {

        var data = studentRepo.allStudentForRecDataStudent();

        assertNotNull(data);
        assertEquals(studentRepo.count(), data.size());
    }

    @Test
    public void printParallel() {
        studentService.printParallel();

    }


}
