package com.ngandang.intern.model.response;

public class ResponseTransfer {
    private String result;
    private Object data;
    public ResponseTransfer(String result,Object data) {
        this.result = result;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}