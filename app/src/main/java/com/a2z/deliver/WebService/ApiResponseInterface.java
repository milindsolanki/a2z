package com.a2z.deliver.webService;

public interface ApiResponseInterface {
    public void isError(String errorCode, int apiCode);
    public void isSuccess(Object response, int apiCode);
}