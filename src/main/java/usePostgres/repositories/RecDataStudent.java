package usePostgres.repositories;

public record RecDataStudent(Long id, String facultyName, String name, int age) {

    public RecDataStudent(DataStudent data) {
        this(data.getId(), data.getFacultyName(), data.getName(), data.getAge());
    }

}
