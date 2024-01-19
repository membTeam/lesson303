package usePostgres.models;

import org.springframework.http.HttpHeaders;

public record RecResponseData(byte[] byteData, HttpHeaders httpHeader) { }
