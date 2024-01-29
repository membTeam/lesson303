package usePostgres.models;

import usePostgres.repositories.DataStudent;

public class DataStudentImpl implements DataStudent {
    private Long id;
    private String facultyName;
    private String name;
    private int age;

    public DataStudentImpl(Long id, String facultyName, String name, int age) {
        this.id = id;
        this.facultyName = facultyName;
        this.name = name;
        this.age = age;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getFacultyName() {
        return facultyName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }
}
