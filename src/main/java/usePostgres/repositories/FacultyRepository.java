package usePostgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import usePostgres.models.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query(value="SELECT exists(Select * from faculty where name = :name ) res",
        nativeQuery = true )
    boolean existDataForName(String name);

    @Query(value="select COALESCE(max(id), 0) res from faculty",
            nativeQuery = true )
    Long getMaxID();
}
