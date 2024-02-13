package usePostgres.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import usePostgres.models.Student;

import java.util.Comparator;
import java.util.stream.Collectors;

@SpringBootTest
public class StudentRepositoryLesson405Test {

    @Autowired
    private StudentRepository studentRepo;

    @Test
    public void findAllByFirstChar() {
        var charName = "A";
        var result = studentRepo.findAllByFirstChar(charName);

        assertTrue(result.isPresent());
        assertTrue(result.orElseThrow().size()>0);
    }

    @Test
    public void findAllByFirstCharExt() {
        var charName = "A";
        var data = studentRepo.findAll();

        var result = data.stream()
                .filter(item -> item.getName().startsWith("A"))
                .sorted(Comparator.comparing(Student::getName))
                .toList();

        assertNotNull(result);
        assertEquals(5, result.size());

    }

}
