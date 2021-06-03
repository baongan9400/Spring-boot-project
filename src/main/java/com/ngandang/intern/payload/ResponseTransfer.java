package com.ngandang.intern.payload;

public class ResponseTransfer {
    private String error;
    private String result;
    private Object data;
    public ResponseTransfer(String error, String result,Object data) {
        this.error = error;
        this.result = result;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}