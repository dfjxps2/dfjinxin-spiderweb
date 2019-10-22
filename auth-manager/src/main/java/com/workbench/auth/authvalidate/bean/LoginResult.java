package com.workbench.auth.authvalidate.bean;

/**
 * Created by pc on 2017/6/29.
 */
public class LoginResult {

    private LOGIN_RESULT result_code;

    private String validate_result;

    public LOGIN_RESULT getResult_code() {
        return result_code;
    }

    public void setResult_code(LOGIN_RESULT result_code) {
        this.result_code = result_code;
    }

    public String getValidate_result() {
        return validate_result;
    }

    public void setValidate_result(String validate_result) {
        this.validate_result = validate_result;
    }

    public enum LOGIN_RESULT{
        SUCCESS,
        PASSWORD_WRONG,
        USERNM_NOT_FOUND,
        VALIDATE_FAIL,
        USERNM_NOT_NULL,
        LOCK,
        STATUS_FAIL,
        PWD_EXPIRED;
    }
}
