package usePostgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usePostgres.models.Student;

import java.util.List;
import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "Select st.id id, fc.name facultyName, st.name name, st.age age " +
            "from Faculty fc join fc.students st " +
            "where fc.id = :id")
    List<DataStudent> findStudentInFaculty(Long id);

    @Query(value = "FROM Student where faculty.name = :faculty"  )
    List<Student> findStudentInFaculty(String faculty);

    List<Student> findByAgeBetween(int start, int end);

    List<Student> findByAge(int age);

    @Query(value="SELECT exists(Select * from faculty where id = :id) res;",
            nativeQuery = true )
    boolean existDataForFacultyId(Long id);

    @Query(value="SELECT exists(Select * from Student where name = :name) res",
            nativeQuery = true )
    boolean existDataForStudentName(String name);

    @Query(value="select max(st.id) from Student st" )
    Optional<Long> getMaxID();

}
