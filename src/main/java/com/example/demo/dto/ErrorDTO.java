package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
    @JsonProperty("error_msg")
    private String errorMsg;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

           return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return errorMsg;
        }
    }
}
