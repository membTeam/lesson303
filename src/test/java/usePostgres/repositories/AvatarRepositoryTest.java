package usePostgres.repositories;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
public class AvatarRepositoryTest {

    @Autowired
    private AvatarRepository avatarRepo;

    @Test
    public void getPageAvatar() {
        final int numPage = 0, pageSize = 2;
        var pageable = PageRequest.of(numPage, pageSize);

        var data = avatarRepo.findAll(pageable);

        assertEquals(pageSize, data.getContent().size());
    }

}
