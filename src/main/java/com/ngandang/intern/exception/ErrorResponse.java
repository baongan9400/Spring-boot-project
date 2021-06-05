package com.ngandang.intern.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
        private int code;
        private HttpStatus error;
        private String message;

    public ErrorResponse(HttpStatus error, String message) {
        this.error = error;
        this.message = message;
        this.code = error.value();
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getError() {
        return error;
    }

    public void setError(HttpStatus error) {
        this.error = error;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

