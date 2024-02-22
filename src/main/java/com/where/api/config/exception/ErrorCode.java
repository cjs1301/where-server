
package com.where.api.config.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "INVALID_INPUT_VALUE", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED", "Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "ENTITY_NOT_FOUND", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "Server Error"),
    INVALID_TYPE_VALUE(400, "INVALID_TYPE_VALUE", "Invalid Type Value"),
    UNAUTHORIZED(401, "UNAUTHORIZED", "UnAuthorized"),
    HANDLE_ACCESS_DENIED(403, "HANDLE_ACCESS_DENIED", "Access is Denied"),
    USER_NOT_FOUND(403, "USER_NOT_FOUND", "User not found"),
    BAD_REQUEST(400, "BAD_REQUEST", "Bad request"),


    // Member
    EMAIL_DUPLICATION(400, "EMAIL_DUPLICATION", "Email is Duplication"),
    // LoginCodeHistoryId
    EXPIRED_CODE(400, "EXPIRED_CODE", "Code is Expired"),

    // AWS
    AWS_SERVICE_ERROR(400, "AWS_SERVICE_ERROR", "AWS error"),
    UPLOAD_ERROR(400,"UPLOAD_ERROR","Upload to s3 fail");
    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }


}
