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
    public void getAvatarFromStudent() {
        var res = studentRepo.avatarByStudent(54563);

        var data = res.get(0);      // т.к. это список, выбираем первый элемент
        var avatar = data.avatar(); // это объект класса RecStudentWithAvatar

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
