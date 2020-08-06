package com.jw.letssplit.common;

public enum ResultCode implements IErrorCode {

    SUCCESS(200, "Operation Success"),
    FAILED(500, "Operation Failed"),
    VALIDATE_FAILED(404, "Parameter Validate Failed"),
    UNAUTHORIZED(401, "Unauthorized or token expired"),
    FORBIDDEN(403, "Forbidden");

    private final long code;
    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
