package usePostgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import usePostgres.models.Avatar;
import java.util.List;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    List<Avatar> findByFileSize(int size);

    Page<Avatar> findAll(Pageable pageable);

}
