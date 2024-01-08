package usePostgres.exception;

public class RunErrBadRequestException {
    public static void runException(String err) {
        throw new ErrBadRequestException(err);
    }
}
