package usePostgres.repositories;

import jakarta.persistence.StoredProcedureParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import usePostgres.models.Avatar;
import usePostgres.models.DataStudentImpl;
import usePostgres.models.Student;

import java.util.List;
import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "from Student st where st.id "+
            "in (select a.id From Avatar a where a.fileSize > 1000) order by st.id")
    List<Student> getStudentByFileSize();

    @Query(value = "select st, av FROM Student st, Avatar av " +
            "where st.id = av.student.id and av.fileSize = 54563 ")
    List<Object[]> getAvatarFromStudent();

    @Query(value = "select a FROM Avatar a join fetch a.student where a.fileSize = :size")
    List<Avatar> getAvatarData(int size);

    @Procedure("loaddatatostudent")
    void loadDataStudent();

    // ************************************************************************

    @Query(value = "FROM Student where id = :id")
    Student getItemAvatar2(Long id);

    @Query(value = "Select new usePostgres.models.DataStudentImpl(st.id, fc.name, st.name, st.age) " +
            "from Faculty fc join fc.students st " +
            "where fc.id = :id")
    List<DataStudentImpl> findStudentInFaculty(Long id);

    @Query(value = "FROM Student where faculty.name = :faculty"  )
    List<Student> findStudentInFaculty(String faculty);

    //List<Student> findByAgeBetween(Integer start, Integer end);
    @Query("FROM Student s where s.age > :start and s.age < :end")
    List<Student> findByAgeBetween(Integer start, Integer end);

    List<Student> findByAge(int age);

    @Query(value="SELECT exists(Select * from faculty where id = :id) res;",
            nativeQuery = true )
    boolean existDataForFacultyId(Long id);

    @Query(value="SELECT exists(Select * from Student where name = :name) res",
            nativeQuery = true )
    boolean existDataForStudentName(String name);


    // -------------------------------------
    @Query(value = "SELECT function('maxidstudent')")
    Long getMaxID();

    @Query(value = "SELECT function('maxidstudentfortesting')")
    Long getMaxIDForTesting();

    @Query("select case " +
            "when (select count(*) from Student where id < 100) > 0 " +
                    "then (select max(id) from Student where id < 100) " +
            "else 0 end")
    int maxIdForTesting();

    @Query("select case " +
            "when (select count(*) from Student where id > 100) > 0 " +
                    "then (select max(id) from Student where id > 100) " +
            "else 0 end")
    int maxIdForWeb();


}
