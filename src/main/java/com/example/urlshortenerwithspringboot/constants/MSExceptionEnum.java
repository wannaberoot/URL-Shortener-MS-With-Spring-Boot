package com.example.urlshortenerwithspringboot.constants;

public enum MSExceptionEnum {

    USER_ALREADY_EXISTS("MS-001", "User already exists!"),
    USER_NOT_FOUND("MS-002", "User not found!"),
    URL_NOT_FOUND("MS-003", "URL not found!"),
    USER_HAS_NO_URL("MS-004", "User has no URLs!"),
    NOT_AUTHORIZED("MS-005", "You are not authorized to perform this action!"),
    INVALID_INPUT("MS-006", "Invalid input!");

    private String errorCode;
    private String errorMessage;

    MSExceptionEnum(final String errorCode, final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
