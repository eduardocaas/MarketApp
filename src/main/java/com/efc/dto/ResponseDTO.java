package com.efc.dto;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ResponseDTO<T> {

    @Getter
    private List<String> messages;
    @Getter
    private T data;

    public ResponseDTO(List<String> messages, T data) {
        this.messages = messages;
        this.data = data;
    }

    public ResponseDTO(String message, T data) {
        this.messages = Arrays.asList(message);
        this.data = data;
    }

    public ResponseDTO(List<String> messages) {
        this.messages = messages;
    }

    public ResponseDTO(String message) {
        this.messages = Arrays.asList(message);
    }
}
