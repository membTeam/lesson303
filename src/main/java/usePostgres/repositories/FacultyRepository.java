package usePostgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usePostgres.models.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query(value="SELECT exists(Select * from student where faculty_id = :id ) res",
            nativeQuery = true )
    boolean existDataForStudentId(Long id);

    @Query(value="SELECT exists(Select * from faculty where name = :name ) res",
        nativeQuery = true )
    boolean existDataForFacultyName(String name);

    @Query("select function('maxIdFaculty')")
    Long getMaxID();

    @Query("select function('maxIdFacultyForTesting')")
    Long getMaxIdForTesting();

}
