package usePostgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usePostgres.models.Avatar;
import usePostgres.models.Student;

import java.util.List;
import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {

    /*

    @Query(value = "FROM Student s left join fetch s.avatar av where LOWER(trim(av.filePath)) = LOWER(trim(:file))")
    List<Student> getAvatarFromStudent(String file);

    */

    @Query(value = "select st, av FROM Student st, Avatar av " +
            "where st.id = av.student.id and av.fileSize = 54563 ")
    List<Object[]> getAvatarFromStudent();

    @Query(value = "select a FROM Avatar a join fetch a.student where a.fileSize = :size")
    List<Avatar> getAvatarData(int size);


    // ************************************************************************


/*    @Query(value = "SELECT av.* FROM Avatar av join Student st on st.id = av.id where st.id = 1;",
            nativeQuery = true)
    Object getAvatar(Long id);*/

    @Query(value = "FROM Student where id = :id")
    Student getItemAvatar2(Long id);

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
