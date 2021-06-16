package com.work.result;

import lombok.Data;

@Data
public class Result {
    private ResultCode code;
    private String message;
    private Object data;

    public Result(ResultCode code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
