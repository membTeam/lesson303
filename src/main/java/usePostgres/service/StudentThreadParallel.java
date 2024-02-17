package usePostgres.service;

public class StudentThreadParallel implements Runnable {
    private final StudentThreadRepository studentThreadRepo;
    private final int indexStart;
    private final int account;

    public StudentThreadParallel(StudentThreadRepository studentThreadRepo, int start, int account) {
            this.studentThreadRepo = studentThreadRepo;
            this.indexStart = start;
            this.account = account;
    }

    @Override
    public void run() {
        var data = studentThreadRepo.getListStudent(indexStart, account);
        data.stream().forEach(System.out::println);
    }
}
