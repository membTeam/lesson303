package usePostgres.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErrBadRequestException extends RuntimeException {
    public ErrBadRequestException(String err) {
        super(err);
    }
}
