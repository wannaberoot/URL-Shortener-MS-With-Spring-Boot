package com.example.urlshortenerwithspringboot.exception;

import java.io.Serial;

public class MSException extends Exception{

    @Serial
    private static final long serialVersionUID = 7802902689856437055L;

    private final String resource;
    private final String errorCode;

    public MSException(final String errorCode, final String errorMessage, final String resource) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
