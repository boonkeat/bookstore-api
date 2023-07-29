package com.bookstore.app.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * @author boonkeat.poh
 * @since 19 Jul 2023
 */
public class RestApiException extends RuntimeException {
    private HttpStatus httpStatus;
    private String data;

    public RestApiException(HttpStatus httpStatus, String... data) {
        this.httpStatus = httpStatus;
        join(data);
    }

    public RestApiException(String message, HttpStatus httpStatus, String... data) {
        super(message);
        this.httpStatus = httpStatus;
        join(data);
    }

    public RestApiException(String message, Throwable cause, HttpStatus httpStatus, String... data) {
        super(message, cause);
        this.httpStatus = httpStatus;
        join(data);
    }

    public RestApiException(Throwable cause, HttpStatus httpStatus, String... data) {
        super(cause);
        this.httpStatus = httpStatus;
        join(data);
    }

    public RestApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus httpStatus, String... data) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.httpStatus = httpStatus;
        join(data);
    }

    private void join(String... data) {
        this.data = StringUtils.join(data, ",");
    }

    public String getData() {
        return data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
