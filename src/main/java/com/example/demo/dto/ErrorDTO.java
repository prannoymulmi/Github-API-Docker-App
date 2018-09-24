package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDTO {
    @JsonProperty("error_msg")
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
