package usePostgres.repositories;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import usePostgres.models.Student;

import java.awt.print.Pageable;

@SpringBootTest
public class TestingAnyMethodForSQL {

    @Autowired
    private StudentRepository studentRepo;

    @Test
    public void getAll() {

        final int numPage = 1, pageSize = 3;
        var pageable = PageRequest.of(numPage, pageSize);

        Page<Student> studentPage = studentRepo.findAll(pageable);
        assertEquals(pageSize, studentPage.getContent().size() );
    }

    @Test
    public void getLastFiveStudent() {
        var lastNumber = 5;
        var data = studentRepo.getLastFiveStudent(lastNumber);

        assertEquals(5, data.size());
    }

    @Test
    public void getAvgStudent() {
        int avg = (int) studentRepo.getAvgStudent();

        assertTrue(avg > 0);
        assertEquals(17, avg);
    }

    @Test
    public void getAllAmountStudent() {
        var amount = studentRepo.getAllAmountStudent();

        assertTrue(amount>0);
    }

   // ----------- конец блока тестов задания 1 (четвертый курс первое задание)

    @Test
    public void getAvatarFromStudent() {
        var res = studentRepo.avatarByStudent(54563);

        var data = res.get(0);
        var avatar = data.avatar();

        assertEquals(54563, avatar.getFileSize());

    }

    @Test
    public void maxIdForTesting() {
        var maxId = studentRepo.maxIdForTesting();

        assertTrue(maxId>=0);
    }

    @Test
    public void maxIdForWeb() {
        var maxId = studentRepo.maxIdForWeb();

        assertTrue(maxId>100);
    }

}
