package usePostgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import usePostgres.models.Avatar;
import usePostgres.models.DataStudentImpl;
import usePostgres.models.Student;
import usePostgres.models.RecStudentWithAvatar;


public interface StudentRepository extends JpaRepository<Student, Long> {

    // id, String facultyName, String name, int age
    @Query("Select new usePostgres.repositories.RecDataStudent(s.id, f.name, s.name, s.age ) From Student s join s.faculty f")
    List<RecDataStudent> allStudentForRecDataStudent();

    @Query("FROM Student st where upper(substring(st.name,1,1)) = :charName order by st.name")
    Optional<List<Student>> findAllByFirstChar(String charName);


    @Query("FROM Student st order by st.name")
    List<Student> findAllByFirstCharExt();


    @Query("FROM Student order by id desc limit :number")
    List<Student> getLastFiveStudent(int number);
    Page<Student> findAll(Pageable pageable);

    @Query("SELECT count(id) as amount FROM Student")
    int getAllAmountStudent();

    @Query("select avg(s.age) from Student s")
    int getAvgStudent();

    // --------------------------------

    @Query(value = "select st, av FROM Student st, Avatar av " +
            "where st.id = av.student.id and av.fileSize = 54563 ")
    List<Object[]> getAvatarFromStudent();

    @Query(value = "select new usePostgres.models.RecStudentWithAvatar(st, av) " +
            "FROM Student st, Avatar av " +
            "where st.id = av.student.id and av.fileSize = :size ")
    List<RecStudentWithAvatar> avatarByStudent(int size);

    @Query(value = "select a FROM Avatar a join fetch a.student where a.fileSize = :size")
    List<Avatar> getAvatarData(int size);

    @Procedure("loaddatatostudent")
    void loadDataStudent();

    // ************************************************************************

    @Query(value = "Select new usePostgres.models.DataStudentImpl(st.id, fc.name, st.name, st.age) " +
            "from Faculty fc join fc.students st " +
            "where fc.id = :id")
    List<DataStudentImpl> findStudentInFaculty(Long id);

    @Query(value = "FROM Student where faculty.name = :faculty"  )
    List<Student> findStudentInFaculty(String faculty);

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
