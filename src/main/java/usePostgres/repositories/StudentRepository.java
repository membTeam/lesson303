package usePostgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import usePostgres.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Iterable<Student> findByAge(int age);

    @Query(value="SELECT exists(Select * from Student where name = :name) res",
            nativeQuery = true )
    boolean existDataForName(String name);

    @Query(value="select COALESCE(max(id), 0) res from Student",
            nativeQuery = true )
    Long getMaxID();


}
