package usePostgres.repositories;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestingAnyMethodForSQL {

    @Autowired
    private StudentRepository studentRepo;

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
