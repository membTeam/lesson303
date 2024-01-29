package usePostgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usePostgres.models.Avatar;

import java.util.List;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    List<Avatar> findByFileSize(int size);

/*    @Query(value = "select * FROM Avatar where file_size = :size",
            nativeQuery = true)
    Object findAvatarForSize(int size);*/

    @Query(value = "SELECT a FROM Avatar as a WHERE a.fileSize = :size")
    Object findAvatarForSize2(int size);

    /*@Query(value = "select a, st from usePostgres.models.Avatar as a join fetch a.student as st "+
            "where a.id = 1")
    Object findAvatarForSize3();*/




}
