package usePostgres.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HTTPHeaders {
    public static HttpHeaders httpHeaders() {

        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }

}
