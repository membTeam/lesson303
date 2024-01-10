package usePostgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usePostgres.models.Faculty;
import usePostgres.models.Student;

import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "FROM Student WHERE facultyId = :id" )
    List<Student> findStudentsForIdFaculty(Long id);

    @Query(value = "select st.id, fc.name as facultyName, st.name, st.age FROM " +
            "student st, faculty fc where st.faculty_id = fc.id and st.faculty_id = :id",
    nativeQuery = true)
    List<DataStudent> findStudentForFacultyExt(Long id);

    /*@Query(value = "select st.id, fc.name as facultyName, st.name, st.age FROM Student st left join Faculty fc on st.facultyId = fc.id where st.facultyId = :id")
    List<DataStudent> findStudentForFacultyExt(Long id);*/

    @Query(value = "select * from student where faculty_id = (select id from faculty where name = :faculty limit 1)",
    nativeQuery = true)
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
