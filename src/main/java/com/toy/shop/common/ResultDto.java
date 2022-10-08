package com.toy.shop.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import static com.toy.shop.common.ResultCode.SUCCESS;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultDto<T> {

    private String resultCode;
    private String resultMessage;
    private T data;

    public ResultDto(T data) {
        this.resultCode = SUCCESS.getCode();
        this.resultMessage = SUCCESS.getMessage();
        this.data = data;
    }

    public ResultDto(String resultCode, String resultMessage, T data) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.data = data;
    }
}