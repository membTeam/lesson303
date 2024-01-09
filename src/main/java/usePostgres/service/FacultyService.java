package usePostgres.service;

import org.springframework.stereotype.Service;
import usePostgres.repositories.FacultyRepository;

@Service
public class FacultyService {
    private FacultyRepository facultyRepo;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepo = facultyRepository;
    }




}
