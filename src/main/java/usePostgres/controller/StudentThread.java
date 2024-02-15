package usePostgres.controller;

import org.hibernate.dialect.StructHelper;
import org.springframework.beans.factory.annotation.Autowired;
import usePostgres.models.Student;
import usePostgres.repositories.StudentRepository;

import java.util.List;
import java.util.concurrent.Semaphore;

import usePostgres.exception.ErrBadRequestException;

public class StudentThread implements Runnable {

    private StudentRepository studentRepo;

    private int startIndex;
    private int num;

    private Semaphore sem;

    public StudentThread(StudentRepository studentRepo, Semaphore sm, int start, int num) {
        this.studentRepo = studentRepo;
        this.sem = sm;
        this.startIndex = start;
        this.num = num;
    }

    @Override
    public void run() {
        /*try {
            sem.acquire();
            studentRepo.findAll()
                    .stream()
                    .skip(startIndex)
                    .limit(num) .parallel().forEach(System.out::println);

        } catch (InterruptedException e) {
            throw new ErrBadRequestException(e.getMessage());
        } finally {
            sem.release();
        }*/

    }
}
