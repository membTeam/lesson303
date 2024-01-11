package usePostgres.models;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import usePostgres.exception.ErrBadRequestException;
import usePostgres.repositories.FacultyRepository;
import usePostgres.repositories.StudentRepository;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    private Long id;

    @Column(name = "faculty_id")
    private Long facultyId;

    private String name;
    private  int age;

   /* @Transient
    private String facultyName;*/

}
