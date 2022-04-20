package com.example.food.dto.view;

public enum Response {
    SUCCESS("0000", "Success"),
    USERNAME_IS_EXISTS("0001", "Username is existed"),
    EMAIL_IS_EXISTS("0002", "Email is existed"),
    OBJECT_IS_EXISTS("0003", "Object is existed"),
    OBJECT_NOT_FOUND("0004", "Object is not founded"),
    USERNAME_NOT_FOUND("0005", "Không tồn tại tài khoản đăng nhập"),
    PASSWORD_INCORRECT("0006", "Mật khẩu không đúng"),
    OBJECT_INVALID("0007","Object is invalid"),
    TAX_CODE_IS_EXISTS("0008","Object is invalid"),
    DATA_NOT_FOUND("0009", "Data not found"),
    ACCOUNT_IS_LOCK("1999", "Tài khoản của bạn tạm thời bị khóa! Vui lòng kiểm tra hòm thư và liên hệ với cơ sở CSKH!"),
    DATA_CANT_DELETE("0509", "Data can't delete"),
    SYSTEM_ERROR("9999", "System errors");

    private String responseCode;
    private String responseMessage;

    private Response(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
