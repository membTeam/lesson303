package usePostgres.service;

import usePostgres.repositories.RecDataStudent;
import usePostgres.repositories.StudentRepository;

import java.util.List;

public class StudentThreadRepository {
    private final List<RecDataStudent> listStudent;

    public StudentThreadRepository(StudentRepository studentRepository) {
        listStudent = studentRepository.allStudentForRecDataStudent();
    }

    public synchronized List<RecDataStudent> getListStudent(int start, int account) {
        return listStudent.stream().parallel().skip(start).limit(account).toList();
    }


}
