package usePostgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import usePostgres.models.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
