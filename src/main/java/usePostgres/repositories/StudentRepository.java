package usePostgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usePostgres.models.Student;

import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "Select fc.students from Faculty fc join fc.students " +
            "where fc.id = :id")
    List<Student> findStudentsByFacultyId(Long id);

    @Query(value = "select st.id, fc.name as facultyName, st.name, st.age FROM " +
            "student st, faculty fc where st.faculty_id = fc.id and st.faculty_id = :id",
    nativeQuery = true)
    List<DataStudent> findStudentForFacultyExt(Long id);

    @Query(value = "FROM Student where faculty.name = :faculty"  )
    List<Student> findStudentForFaculty(String faculty);

    List<Student> findByAgeBetween(int start, int end);

    List<Student> findByAge(int age);

    @Query(value="SELECT exists(Select * from faculty where id = :id) res;",
            nativeQuery = true )
    boolean existDataForFaculty(Long id);

    @Query(value="SELECT exists(Select * from Student where name = :name) res",
            nativeQuery = true )
    boolean existDataForName(String name);

    @Query(value="select COALESCE(max(id), 0) res from Student",
            nativeQuery = true )
    Long getMaxID();

}
